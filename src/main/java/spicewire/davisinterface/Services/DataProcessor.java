package spicewire.davisinterface.Services;

import spicewire.davisinterface.Model.Command;


/**This class processes raw data from the serial port. It removes any ACK messages and performs CRC checks. It also
 * evaluates whether a Davis Testing Command was successful. It does not have a constructor.
 * If data from the serial port passes validation, the data is made available to other classes in String form.
 * **/


public class DataProcessor {

    //Console OK message is usually <Linefeed><Carriage Return>OK<Linefeed><Carriage Return>
    private static final String CONSOLE_OK_MESSAGE = "\n\rOK\n\r";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static StringBuilder serialData = new StringBuilder();  //sanitized data from the serial port


    /**
     * This method receives and processes raw data from the serial port. If data is validated,
     * it removes any ack message from the data and sends the data to a String builder method.
     * @param rawData raw data from serial port
     * @param command A command to the console as described in Davis documentation.
     */

    public static boolean processRawData(String rawData, Command command) {
        boolean dataIsValid = false;
        if (!confirmMessageSize(rawData, command)) {
            System.out.println("There was an error detected in the data. Try again.");
            //todo log
            clearSerialDataString();

        } else {
            setSerialData(removeAckMessage(rawData, command));
            dataIsValid = true;}
        return dataIsValid;
    }

    /**
     * This method accepts serial data with the ack message removed. It creates a String builder from the data
     * for other classes to access.
     * @param data serial data with the ack message removed
     */
    private static void setSerialData(String data) {
        clearSerialDataString();
        serialData.append(data);
    }

    /**
     * This method makes validated and sanitized serial data available for other classes to consume. The
     * serial data persists until the next time serial port data is read and validated.
     *
     * @return String of sanitized and validated serial data
     */
    public static String getSerialData() {
        return serialData.toString();
    }

    /**
     * Evaluates if data from the Davis console is of the expected length for the command that was sent.
     *
     * Some commands generate more than one data packet at a time.
     * Testing Commands (Command type 1) generate exactly one packet.
     * Current Data Commands (Command type 2) generate one or more packets.
     * Some commands do not have a CRC, but their data can be somewhat validated by message size.
     *
     * @param data raw data from the serial port
     * @param command the command sent to the Davis console that generated the serial port data
     * @return boolean true = message passes CRC check (if any) and is proper size. False = wrong size or
     * did not pass CRC check
     */
    public static boolean confirmMessageSize(String data, Command command) {
        boolean dataIsOK = false;
        int expectedNumberOfPackets = 1;
        String dataForTesting = new String();
        if (command.getType() == 2) {
            dataForTesting = removeAckMessage(data, command);  //removes ACK from front of data
            if (command.getWord().equalsIgnoreCase("LOOP")) {
                expectedNumberOfPackets = Integer.parseInt(command.getPayload());
            } else if (command.getWord().equalsIgnoreCase("LPS")) {
                String str = command.getPayload();
                expectedNumberOfPackets = Integer.parseInt(str.substring(str.length() - 1));
            } else {
                System.out.println("Command not implemented yet.");
                return false;
            }
            /* Of note, the ACK is not repeated when LoopData is sent back-to-back. For example,
             * LOOP 2 results in a single ACK followed by two LOOP1 packets. Packets are NOT separated by
             * time. */
        }

        //Catch the wrong number of bytes before any CRC check.
        String[] dataStrArr = dataForTesting.split(" ");
        int expectedMessageSize = command.getExpectedNumberOfBytesInReply() * expectedNumberOfPackets;
        if (dataStrArr.length != expectedMessageSize) {
            if (command.getWord().equalsIgnoreCase("RXCHECK")) {
                if (dataStrArr.length < expectedMessageSize) { //RXCHECK has varying size of reply
                    dataIsOK = true;
                }
            } else { //wrong size,
                System.out.println("Wrong number of bytes in data. Bytes = " + dataStrArr.length + " . Expected " +
                        "bytes = " + expectedMessageSize);
            }
        } else {  //message is the right size but CRC might need processing
            if (command.isCrcInReply()) {
                return crcCheck(dataStrArr, command);
            } else dataIsOK = true;
        }
        System.out.println("Data validated? " + dataIsOK);
        return dataIsOK;
    }
        /*      The CRC method below does not directly follow the Davis protocol nor use the Java CRC32 utility.
            Davis documentation (page 38) specifies how the CRC (Cyclic Redundancy Check) is calculated:
               crc = crc_table [(crc >> 8) ^ data] ^ (crc << 8);
            and provides the crc lookup table to use. Of note, the table starts with 0x0. The Java CRC32
            utility, however, uses 0xFF as an initial value, meaning that the methods are not compatible.

            The equation, as provided, is not compatible with Java. Because Java does not have inherent
            unsigned bytes, care must be taken when left bit shifting a 1 into the most significant bit's place, as the
            resulting byte will be signed and negative, and the "overflow" will become part of a 32-bit integer.
            E.g.  0xB98A << 8 = 0x00B9 8A00 (decimal 12159488) in Java, but would be 0x8A00 (decimal 138) if the
            "overflow" were discarded. In order to select the leftmost 2 bytes in Java, the Davis implementation was
            modified by using "& 0xFF" before left-shifting. E.g. 0xB98A & 0x00FF = 0x008A.

            Data arrays evaluated by confirmCRC has the calculated CRC value in the last two bytes. A confirmCRC
            result of 0 from this array indicates there were no transmission errors.
        */

    //This method converts the hex or binary data to int for a CRC check.
    public static boolean crcCheck(String[] data, Command command) {
        int[] dataArr = new int[data.length];
        int radix;
        int crc = 0;
        boolean validCRC;
        radix = command.isBinaryReturnData() ? 2 : 16; //radix is 2 for binary, 16 for hex

        for (int i = 0; i < data.length; i++) {
            dataArr[i] = Integer.parseInt(data[i], radix); //converts binary or hex numbers to int
        }

        for (int i = 0; i < dataArr.length; i++) {
            crc = crc_table[(crc >> 8 ^ dataArr[i])] ^ ((crc & 0xff) << 8); //Modified Davis CRC equation.
        }

        if (crc != 0) {
            System.out.println("Error in data. CRC = " + crc);
            validCRC = false;
        } else {
            //System.out.println("Data is clean.");
            clearSerialDataString();
            validCRC = true;
        }
        return validCRC;
    }

    public static void clearSerialDataString() {
        serialData.setLength(0);
    }

    private static String removeAckMessage(String fullData, Command command) {
        String data = new String();
        if (command.getType() == 2) {
            data = fullData.substring(4);
        }
        return data;
    }

    public static String consoleFriendlyText(Command command) {
        StringBuilder friendlyText = new StringBuilder();
        if (getSerialData().length() < CONSOLE_OK_MESSAGE.length()) {
            friendlyText.append("No valid data was returned. Something went wrong.");
            return friendlyText.toString();
        }
        switch (command.getWord()) {
            case "TEST":
                friendlyText.append("Successful if TEST is returned.\n");
                if (getSerialData().trim().equalsIgnoreCase("TEST")) {
                    friendlyText.append("It appears the test was successful.");
                }
                break;
            case "NVER":
                friendlyText.append("Successful if a number is returned.\n");
                friendlyText.append("Firmware number installed is ");
                friendlyText.append(getSerialData().substring(CONSOLE_OK_MESSAGE.length() - 1));
                break;
            case "RECEIVERS":
                friendlyText.append("You are listening to station number: ");
                String msg = getSerialData().substring(CONSOLE_OK_MESSAGE.length());
                for (char c : msg.toCharArray()) {
                    friendlyText.append(Integer.toBinaryString(c));
                }
                break;  //todo capture binary info and extract assigned receivers
            case "VER":
                friendlyText.append("Successful if a date is returned.\n");
                friendlyText.append("Firmware date code is: ");
                friendlyText.append(getSerialData().substring(CONSOLE_OK_MESSAGE.length() - 1));
                break;
            case "RXTEST":
                friendlyText.append("Successful if OK is returned.\n");
                if (getSerialData().equalsIgnoreCase(CONSOLE_OK_MESSAGE)) {
                    friendlyText.append("Test appears successful.");
                }
                break;
            case "RXCHECK":
                friendlyText.append("Successful if several numbers were returned.\n");
                String diagnosticData = getSerialData().substring(5);
                String[] drAry = diagnosticData.split(" ");
                friendlyText.append("Total packets received: ").append(drAry[0]).append(LINE_SEPARATOR);
                friendlyText.append("Total packets missed: ").append(drAry[1]).append(LINE_SEPARATOR);
                friendlyText.append("Number of resynchronizations: ").append(drAry[2]).append(LINE_SEPARATOR);
                friendlyText.append("Largest number of packets received in a row: ").append(drAry[3]).append(LINE_SEPARATOR);
                friendlyText.append("Number of CRC errors detected: ").append(drAry[4]);
                break;
            default:
                friendlyText.append("Something went wrong.");
        }
        return friendlyText.toString();
    }


    //CRC table copied from Davis manual
    static final int[] crc_table = {
            0x0, 0x1021, 0x2042, 0x3063, 0x4084, 0x50a5, 0x60c6, 0x70e7,
            0x8108, 0x9129, 0xa14a, 0xb16b, 0xc18c, 0xd1ad, 0xe1ce, 0xf1ef,
            0x1231, 0x210, 0x3273, 0x2252, 0x52b5, 0x4294, 0x72f7, 0x62d6,
            0x9339, 0x8318, 0xb37b, 0xa35a, 0xd3bd, 0xc39c, 0xf3ff, 0xe3de,
            0x2462, 0x3443, 0x420, 0x1401, 0x64e6, 0x74c7, 0x44a4, 0x5485,
            0xa56a, 0xb54b, 0x8528, 0x9509, 0xe5ee, 0xf5cf, 0xc5ac, 0xd58d,
            0x3653, 0x2672, 0x1611, 0x630, 0x76d7, 0x66f6, 0x5695, 0x46b4,
            0xb75b, 0xa77a, 0x9719, 0x8738, 0xf7df, 0xe7fe, 0xd79d, 0xc7bc,
            0x48c4, 0x58e5, 0x6886, 0x78a7, 0x840, 0x1861, 0x2802, 0x3823,
            0xc9cc, 0xd9ed, 0xe98e, 0xf9af, 0x8948, 0x9969, 0xa90a, 0xb92b,
            0x5af5, 0x4ad4, 0x7ab7, 0x6a96, 0x1a71, 0xa50, 0x3a33, 0x2a12,
            0xdbfd, 0xcbdc, 0xfbbf, 0xeb9e, 0x9b79, 0x8b58, 0xbb3b, 0xab1a,
            0x6ca6, 0x7c87, 0x4ce4, 0x5cc5, 0x2c22, 0x3c03, 0xc60, 0x1c41,
            0xedae, 0xfd8f, 0xcdec, 0xddcd, 0xad2a, 0xbd0b, 0x8d68, 0x9d49,
            0x7e97, 0x6eb6, 0x5ed5, 0x4ef4, 0x3e13, 0x2e32, 0x1e51, 0xe70,
            0xff9f, 0xefbe, 0xdfdd, 0xcffc, 0xbf1b, 0xaf3a, 0x9f59, 0x8f78,
            0x9188, 0x81a9, 0xb1ca, 0xa1eb, 0xd10c, 0xc12d, 0xf14e, 0xe16f,
            0x1080, 0xa1, 0x30c2, 0x20e3, 0x5004, 0x4025, 0x7046, 0x6067,
            0x83b9, 0x9398, 0xa3fb, 0xb3da, 0xc33d, 0xd31c, 0xe37f, 0xf35e,
            0x2b1, 0x1290, 0x22f3, 0x32d2, 0x4235, 0x5214, 0x6277, 0x7256,
            0xb5ea, 0xa5cb, 0x95a8, 0x8589, 0xf56e, 0xe54f, 0xd52c, 0xc50d,
            0x34e2, 0x24c3, 0x14a0, 0x481, 0x7466, 0x6447, 0x5424, 0x4405,
            0xa7db, 0xb7fa, 0x8799, 0x97b8, 0xe75f, 0xf77e, 0xc71d, 0xd73c,
            0x26d3, 0x36f2, 0x691, 0x16b0, 0x6657, 0x7676, 0x4615, 0x5634,
            0xd94c, 0xc96d, 0xf90e, 0xe92f, 0x99c8, 0x89e9, 0xb98a, 0xa9ab,
            0x5844, 0x4865, 0x7806, 0x6827, 0x18c0, 0x8e1, 0x3882, 0x28a3,
            0xcb7d, 0xdb5c, 0xeb3f, 0xfb1e, 0x8bf9, 0x9bd8, 0xabbb, 0xbb9a,
            0x4a75, 0x5a54, 0x6a37, 0x7a16, 0xaf1, 0x1ad0, 0x2ab3, 0x3a92,
            0xfd2e, 0xed0f, 0xdd6c, 0xcd4d, 0xbdaa, 0xad8b, 0x9de8, 0x8dc9,
            0x7c26, 0x6c07, 0x5c64, 0x4c45, 0x3ca2, 0x2c83, 0x1ce0, 0xcc1,
            0xef1f, 0xff3e, 0xcf5d, 0xdf7c, 0xaf9b, 0xbfba, 0x8fd9, 0x9ff8,
            0x6e17, 0x7e36, 0x4e55, 0x5e74, 0x2e93, 0x3eb2, 0xed1, 0x1ef0,
    };
}
