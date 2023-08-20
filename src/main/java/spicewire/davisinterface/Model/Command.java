package spicewire.davisinterface.Model;

/**
 * Class contains commands sent to the DavisVP2 console over a serial port.
 *
 * Davis commands, when sent to the Davis console:
 *     -Do not all use the same terminating character.
 *     -Can return hex, binary or ASCII data from the Davis console, depending on the command.
 *     -Sometimes return data with a CRC.
 *     -Sometimes have user-defined parameters that need to be sent with the command.
 *     -Return serial port data varying in length from 6 to 436 bytes.
 *     -Are categorized in Davis Serial Communication Reference Manual by:
 *      1) Testing Commands
 *      2) Current Data
 *      3) Download
 *      4) EEPROM
 *      5) Calibration
 *      6) Clearing
 *      7) Configuration
 *
 *      At present, the first two categories are at least partially implemented by this software.
 */

public class Command {
    private String word; //The string of all-caps chars sent to the console.
    private int numberOfDataParameters;  //How many parameters are sent along with this command word?
    private char terminatingChar; //A command might use either a line feed or a carriage return
    private String description; //Description of the command and any return data expected
    private boolean binaryReturnData; //commands return binary, hex or ASCII data
    private int type;  //1=Testing  2=Current data  3=Download  4=EEPROM 5=Calibration 6=Clearing  7= Configuration
    private boolean fixedNumberOfReplyBytes;//does the command's reply have a fixed number of bytes?
    private int expectedNumberOfBytesInReply; //specified in Davis Serial Communication Protocol
    private boolean fixedNumberOfReplyCharacters;//does the command's reply have a fixed number of characters?
    private int expectedNumberOfCharactersInReply; //specified in Davis Serial Communication Protocol
    private boolean crcInReply; //True if console reply will have a Cyclic Redundancy Check
    private String payload;  //data values (if any) that are part of command.

    //Davis console needs a single line feed or a single carriage return (but not both) after a command string.
    //Windows usually uses a carriage return AND line feed for a new line.
    private static final char LINE_FEED = (char) 10; //HEX A
//    private final char NOT_ACKNOWLEDGE = (char) 21; //Davis instructions indicate HEX 21 (!).
//    private final char CARRIAGE_RETURN = (char) 13; //HEX D.
//    private final char CANCEL = (char) 24; // HEX 18. Davis uses this with a bad CRC code

    private static final String TEST_DESCRIPTION = "Sends the command TEST to the console. The response TEST is expected.";

    private static final String RX_CHECK_DESCRIPTION = "Requests the Console Diagnostics report. All values are recorded since " +
            "midnight, or since the diagnostics were cleared manually.";
    private static final String RX_TEST_DESCRIPTION = "Changes console screen from Receiving From to the current conditions " +
            "screen. This command is useful to programmatically recover from a power loss when the console boots " +
            "into the Receiving From screen. RXTEST also clears the count of CRC errors seen in RXCHECK.";
    private static final String VER_DESCRIPTION = "Requests console's firmware date code (Mmm dd yyyy).";
    private static final String RECEIVERS_DESCRIPTION = "Console sends a byte containing stations received in the Receiving " +
            "From setup screen. Console sends OK followed by a bit map. For each bit position, a value of 1 " +
            "indicates an active transmitter. Bit position 0 (least significant bit) corresponds with Tx ID 1 in " +
            "the Davis Talk protocol.";
    private static final String NVER_DESCRIPTION = "Requests console's firmware version as a text string.";
    private static final String LOOP_DESCRIPTION = "Console sends the specified number of LOOP packets, 1 every 2 seconds. " +
            "Console sleeps between each packet sent. The station responds with an <ACK> then with binary " +
            "data packet every 2 seconds. To halt the sending of LOOP packets before receiving all of the requested " +
            "packets, send <CR> by itself. Each data packet is 99 bytes long and contains most of the current data " +
            "values shown on the Vantage console.";
    private static final String LPS_DESCRIPTION = "Console sends specified number of the different type LOOP packets, " +
            "one every 2.5 seconds.";
    private static final String HILOWS_DESCRIPTION = "Console sends all the current high/low data in a single data " +
            "block. The station responds with an <ACK> then a 436 byte data block that includes all the daily, " +
            "monthly, and yearly high and low values on the Vantage console, and then a 2 byte CRC value.";
    private static final String PUTET_DESCRIPTION = "Sets the Yearly ET amount on the console. For example, sending" +
            "2483 would set yearly ET (evapotranspiration) amount to 24.83 inches.";
    private static final String PUTRAIN_DESCRIPTION = "Sets the Yearly rainfall amount on the console.For example, " +
            "sending 2483 would set yearly rain to 24.83 inches.";

    public Command() {
    }

    public Command(String word, int dataParameter, char terminatingChar, String description,
                   boolean binaryReturnData, int type, boolean fixedNumberOfReplyBytes,
                   int expectedNumberOfBytesInReply, boolean fixedNumberOfReplyCharacters,
                   int expectedNumberOfCharactersInReply, boolean crcInReply,
                   String payload) {
        this.word = word;
        this.numberOfDataParameters = dataParameter;
        this.terminatingChar = terminatingChar;
        this.description = description;
        this.binaryReturnData = binaryReturnData;
        this.type = type;
        this.fixedNumberOfReplyBytes = fixedNumberOfReplyBytes;
        this.expectedNumberOfBytesInReply = expectedNumberOfBytesInReply;
        this.fixedNumberOfReplyCharacters = fixedNumberOfReplyCharacters;
        this.expectedNumberOfCharactersInReply = expectedNumberOfCharactersInReply;
        this.crcInReply = crcInReply;
        this.payload = payload;
    }


    private static final Command test = new Command("TEST", 0, LINE_FEED, TEST_DESCRIPTION,
            false, 1, false, 1, true, 8, false, null);

    private static final Command rxCheck = new Command("RXCHECK", 0, LINE_FEED,
            RX_CHECK_DESCRIPTION, false, 1, false,32,  false, 27, false, null);

    private static final Command rxTest = new Command("RXTEST", 0, LINE_FEED,
            RX_TEST_DESCRIPTION, false, 1, false, 1, true, 6,false, null);

    private static final Command ver = new Command("VER", 0, LINE_FEED,
            VER_DESCRIPTION, false, 1, false, 3, true, 19, false, null);

    private static final Command nver = new Command("NVER", 0, LINE_FEED,
            NVER_DESCRIPTION, false, 1, false, 1, true, 12, false, null);

    private static final Command receivers = new Command("RECEIVERS", 0, LINE_FEED,
            RECEIVERS_DESCRIPTION, false, 1, false, 1, true, 14, false, null);

    private static final Command loop = new Command("LOOP", 1, LINE_FEED, LOOP_DESCRIPTION,
            true, 2, true, 99, false, 800, true, "1");

    private static final Command lps = new Command("LPS", 2, LINE_FEED, LPS_DESCRIPTION,
            true, 2, true, 99, false, 800, true, "2 1");

    private static final Command hilows = new Command("HILOWS", 0, LINE_FEED, HILOWS_DESCRIPTION,
            false, 2,true, 436, false, 1000, true, null);

    private static final Command putrain = new Command("PUTRAIN", 1, LINE_FEED, PUTRAIN_DESCRIPTION,
            false, 2, true, 1, true, 3, false, null);

    private static final Command putet = new Command("PUTET", 1, LINE_FEED, PUTET_DESCRIPTION,
            false, 2, true,  1, true, 3, false, null);

    public String getPayload() {
        return payload;
    }

    public String getWord() {
        return word;
    }

    public int getNumberOfDataParameters() {
        return numberOfDataParameters;
    }

    public char getTerminatingChar() {
        return terminatingChar;
    }

    public String getDescription() {
        return description;
    }

    public boolean isBinaryReturnData() {
        return binaryReturnData;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getExpectedNumberOfBytesInReply() {
        return expectedNumberOfBytesInReply;
    }

    public boolean isCrcInReply() {
        return crcInReply;
    }

    public Command getTest() {
        return test;
    }

    public Command getRxCheck() {
        return rxCheck;
    }

    public Command getRxTest() {
        return rxTest;
    }

    public Command getVer() {
        return ver;
    }

    public Command getNver() {
        return nver;
    }

    public Command getReceivers() {
        return receivers;
    }

    public Command getLoop() {
        return loop;
    }

    public Command getLps() {return lps;}
}
