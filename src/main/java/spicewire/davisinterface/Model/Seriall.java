package spicewire.davisinterface.Model;


import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import spicewire.davisinterface.Services.DataProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
@Service
public class Seriall {
 /*    Note:resetting the serial port settings forces a readSerial event.
     The SerialPort object has no constructors in the Fazecast.jSerialComm.SerialPort model.
     The SerialPort object is assigned to a communications port, and then properties can be set.*/


    private StringBuilder rawData = new StringBuilder(); // raw data before CRC
    private static Integer delayTime = 1000; //delay between requests to the console in the event of a failed CRC
    private static final String WAKE_UP_CHAR =  "\n";
    private static final byte[] WAKE_UP = WAKE_UP_CHAR.getBytes();
    private static final int WAKE_UP_LENGTH = WAKE_UP_CHAR.length();
    private static final String NO_COM_PORT = "NO_COM_PORT_SELECTED";
    private static SerialPort port;
    private static SerialSettingsDTO serialSettingsDTO;

    /**
     * Assigns serial port path from view selection passed from controller.
     * @param index of the user-selected serial port in the serial port list.
     * @return system port path as string.
     */
    public String getCommPortPath(int index) {
        String path = "";
        try{
            path = SerialPort.getCommPorts()[index].getSystemPortPath();
        }
        catch(Exception e){
            System.out.println("Error: likely no comm ports are active. Make sure all plugs are secure.");
        }
       return path;
    }

    /**
     * This method sets and returns the serial port, and sets relevant CommPortModel boolean.
     * @param serialPortSystemPath
     * @return SerialPort object.
     */
    public static SerialPort selectSerialPort(String serialPortSystemPath) {
        //System.out.println("Seriall : SelectSerialPort called with " + serialPortSystemPath);

        if (SerialPort.getCommPort(serialPortSystemPath)!=null) {
            CommPortModel.setCommPortSet(true);
            return port = SerialPort.getCommPort(serialPortSystemPath);
        }
        else return null;
    }

    private static boolean serialPortSettingsSet(){
        boolean portSettingsSet = false;
        System.out.println("Seriall: baudset? " + CommPortModel.isBaudSet());
        System.out.println("Seriall: dataSet? " + CommPortModel.isDataBitsSet());
        System.out.println("Seriall: StopSet? " + CommPortModel.isStopBitsSet());
        System.out.println("seriall: Parityset? " + CommPortModel.isParitySet());
        System.out.println("seriall: commPortset? " + CommPortModel.isCommPortSet());

        if(CommPortModel.isCommPortSet() && CommPortModel.isBaudSet() &&
           CommPortModel.isDataBitsSet() && CommPortModel.isStopBitsSet() &&
            CommPortModel.isParitySet()) {
            System.out.println("Serial: all settings are set");
            portSettingsSet = true;
            CommPortModel.setCommParamsSet(true);
            CommPortModel.setUpdatedBy("Serial");
            new CommPortModel();
        }
        return portSettingsSet;

    }

    /**
     * Queries the serial port for the current settings being used.
     * @return SerialStatus object.
     */
    public static SerialStatus getPortSettings(){
        String systemPortName;
        String commPortPath;
        String commPortDescription;
        Integer baudRate;
        Integer dataBits;
        Integer stopBits;
        Integer parity;
        Integer write;
        Integer read;

        if (port == null){
            systemPortName = NO_COM_PORT;
            commPortPath= NO_COM_PORT;
            commPortDescription = NO_COM_PORT;
            baudRate = null;
            dataBits = null;
            stopBits = null;
            parity = null;
            write = null;
            read = null;

        } else{
            systemPortName = port.getSystemPortName();
            commPortPath= port.getSystemPortPath();
            commPortDescription = port.getPortDescription();
            baudRate = port.getBaudRate();
            dataBits = port.getNumDataBits();
            stopBits = port.getNumStopBits();
            parity = port.getParity();
            write = port.getWriteTimeout();
            read = port.getReadTimeout();
        }


//        SerialPort[] commPortList = SerialPort.getCommPorts();
        String[] commPortList = stringifyPortList(SerialPort.getCommPorts());
        int commPortIndex = findComPortIndex(commPortList, systemPortName);

        System.out.println("Seriall is about to make a SerialStatus object");
        SerialStatus serialStatus = new SerialStatus(systemPortName, commPortList, commPortIndex,
                commPortDescription, commPortPath, baudRate, dataBits, stopBits, parity, write, read);


        StringBuilder sbSettings = new StringBuilder();
        sbSettings.append("Seriall: getPortSettings triggered\n");
        sbSettings.append("Serial port says current settings are: \n");
        sbSettings.append("Baud: " + baudRate + "\n");
        sbSettings.append("DataBits: " + dataBits+ "\n");
        sbSettings.append("StopBits: " + stopBits+ "\n");
        sbSettings.append("Parity: " + parity + "\n");
        sbSettings.append("Port list: " + Arrays.toString(serialStatus.commPortList));
        sbSettings.append("Port description: " + commPortDescription+ "\n");
        sbSettings.append("Port path: " + commPortPath+ "\n");
        sbSettings.append("Port name: " + systemPortName+ "\n");
        sbSettings.append("WriteTimeout (ms): " + write + "\n");
        sbSettings.append("ReadTimeout (ms): " + read + "\n");

        System.out.println(sbSettings);
        System.out.println("Seriall says serialStatusObject is:");
        System.out.println(serialStatus.toString());

        return serialStatus;
    }

    private static int findComPortIndex(String[] portList, String port){
        int indexNum = 0;
        if(portList.length==0){
            indexNum= -1;
        }
        else if (portList.length==1) {
            indexNum = 0;
        }
        else indexNum = Arrays.asList(portList).indexOf(port);
        System.out.println("indexnum is " + indexNum);

        return indexNum;
    }

    private static String[] stringifyPortList(SerialPort[] spArr) {
        String[] spNameArray = new String[spArr.length];
        if (spNameArray.length==0){
            System.out.println("no com ports found");
            return new String[]{"NO_COM_PORTS_FOUND"};
        }
        for (int i = 0; i <= spArr.length - 1; i++) {
            spNameArray[i] = spArr[i].getSystemPortPath();
        }

        StringBuilder friendlySPName = new StringBuilder();
        for (String sp : spNameArray) {  //strips system com port name of leading slashes, etc
            char c;
            for (int i = 0; i < sp.length(); i++) {
                c = sp.charAt(i);
                if (Character.isAlphabetic(c) || Character.isDigit(c)){
                    friendlySPName.append(c);
                }
            }
            friendlySPName.append(" ");

        }
        String[] commPortList = friendlySPName.toString().split(" ");
        System.out.println("Seriall: ports are: " + Arrays.toString(commPortList));
        return commPortList;
    }
    /**
     * Sets the serial port based on user's submitted parameters. Uses a DTO.
     * @param serialDTO
     * @return boolean, true if all settings were successfully set.
     */
    public boolean setPortParams(SerialSettingsDTO serialDTO){
        String commPortPath = getCommPortPath(serialDTO.getCommPortIndex());
        port = selectSerialPort(commPortPath);
        boolean baudSet = port.setBaudRate(serialDTO.getBaud());
        boolean numDataBitsSet = port.setNumDataBits(8);
        boolean numStopBitsSet = port.setNumStopBits(1);
        boolean paritySet = port.setParity(0);
        boolean timeoutsSet = port.setComPortTimeouts(serialDTO.getTimeoutMode(),
                serialDTO.getReadTimeout(),serialDTO.getWriteTimeout());
        setCommPortModel(baudSet, numDataBitsSet, numStopBitsSet, paritySet, timeoutsSet);
        System.out.println("seriall says: baudset? " + baudSet);
        System.out.println("numbitset? " + numDataBitsSet);
        System.out.println("numstopset? " + numStopBitsSet);
        System.out.println("parity?" + paritySet);
        System.out.println("tmeout?" + timeoutsSet);

        return serialPortSettingsSet();
    }

    /**
     * Sets the serial port based on user's selected index number from the serial port list.
     * The com port index is translated to the com port path needed by Fazecast Jserialcomm library
     * The port.setComPortParameters method uses the parameter of useRS485Mode, defaulted as false.
     * Fazecast JserialComm library does not have a native getTimeoutMode method.
     *
     * @param comPortIndex  commPortList's index number of the selected com port
     * @return boolean if params are  set successfully
     *
     */
    //todo use the timeout method instead.
    public boolean setSerialPortParameters(int comPortIndex) {
        String commPortPath = getCommPortPath(comPortIndex);
        CommPortModel.setCommPortPath(commPortPath);
        CommPortModel.setCommPortIndex(comPortIndex);
        port = selectSerialPort(commPortPath);
        System.out.println(getPortSettings());
//        System.out.println("serial: setSerialPortParameters called.");
        boolean baudSet = port.setBaudRate(CommPortModel.getBaudRate());
        boolean numDataBitsSet = port.setNumDataBits(CommPortModel.getDataBits());
        boolean numStopBitsSet = port.setNumStopBits(CommPortModel.getStopBits());
        boolean paritySet = port.setParity(CommPortModel.getParity());
        boolean timeoutsSet = port.setComPortTimeouts(CommPortModel.getTimeoutMode(),
                CommPortModel.getReadTimeout(),CommPortModel.getWriteTimeout());
        setCommPortModel(baudSet, numDataBitsSet, numStopBitsSet, paritySet, timeoutsSet);
/*        CommPortModel.setBaudSet(port.setBaudRate(CommPortModel.getBaudRate()));
        CommPortModel.setDataBitsSet(port.setNumDataBits(CommPortModel.getDataBits()));
        CommPortModel.setStopBitsSet(port.setNumStopBits(CommPortModel.getStopBits()));
        CommPortModel.setParitySet(port.setParity(CommPortModel.getParity()));
        CommPortModel.setTimeoutModeSet(port.setComPortTimeouts(CommPortModel.getTimeoutMode(),
                CommPortModel.getReadTimeout(),CommPortModel.getWriteTimeout()));
       */ System.out.println("Seriall: setSerialPortParams ran");
//         port.setComPortParameters(CommPortModel.getBaudRate(), CommPortModel.getBaudRate(),
//        CommPortModel.getStopBits(), CommPortModel.getParity(), false);
        return serialPortSettingsSet();
    }

    /**
     * Sets the booleans of the ComPortModel to indicate whether the serial port settings were set
     * successfully.
     * @param baud Was baud successfully set?
     * @param data Was numDataBits successfully set?
     * @param stop Was numStopBits successfully set?
     * @param parity Was parity successfully set?
     * @param timeouts  Timeout mode, readTimeout and writeTimeout are bundled in the same boolean
     */
    private void setCommPortModel(boolean baud, boolean data, boolean stop, boolean parity, boolean timeouts){
        CommPortModel.setBaudSet(baud);
        CommPortModel.setDataBitsSet(data);
        CommPortModel.setStopBitsSet(stop);
        CommPortModel.setParitySet(parity);
        CommPortModel.setTimeoutModeSet(timeouts);
        new CommPortModel();
    }

    /**
     * Updates available serial ports from the Fazecast library.
     * @return String[] of available port names. Strings/names include leading slashes, dots, etc.
     */
    public static String[]  getSerialPortList() {
        System.out.println("seriall: getSerialPortList triggered.");
        SerialStatus serialStatus = new SerialStatus();
        return serialStatus.serialPortsAsString(SerialPort.getCommPorts());

    }

    /**
     * Sets Fazecast timeout params. See Fazecast JserialComm for complete documentation.
     * Default for all parameters is 0.
     *
     * @param newTimeoutMode TimeoutMode 0= nonblocking, 1= read semi-blocking, 256 = write blocking, (others)
     *                        TIMEOUT_NONBLOCKING mode specifies that corresponding readBytes(byte[],int)
     *                        and writeBytes(byte[],int) calls will return immediately with any available data.
     *                        TIMEOUT_READ_SEMI_BLOCKING mode specifies that a corresponding read call will block until
     *                        either newReadTimeout milliseconds of inactivity have elapsed or at least 1 byte of data
     *                        can be read.
     *                       TIMEOUT_WRITE_BLOCKING mode specifies that a corresponding write call will block until all
     *                       data bytes have been successfully written to the output serial device.
     * @param newReadTimeout number of milliseconds of inactivity to tolerate before returning from a readBytes[] call
     * @param newWriteTimeout number of milliseconds of inactivity to tolerate before returning from a writeBytes[] call
     *
     */
    public void setTimeouts(int newTimeoutMode, int newReadTimeout, int newWriteTimeout) {
        //port.setComPortTimeouts(1, 500, newWriteTimeout);//worked OK
        port.setComPortTimeouts(newTimeoutMode, newReadTimeout, newWriteTimeout);
    }

    /**
     * Builds a command string to send to the Davis console. Some commands (LPS, PUTRAIN, PUTET) require
     * a payload of user-specified data. Terminating chars depend on command type.
     * @param command Object of the Command class.
     * @return String to send to Davis console
     */
    private String buildCommand(Command command){
        StringBuilder commandString = new StringBuilder();
        commandString.append(command.getWord());
        if(command.getNumberOfDataParameters()>0){//adds payload to the command word being sent to the console
            commandString.append(" " + command.getPayload());
        }
        commandString.append(command.getTerminatingChar()); //Terminating chars might be \n, \r or both together.
        return commandString.toString();
    }

    /**
     * Sends a Command class object to the console. The Fazecast Serial Port write method requires a buffer.
     * If this method results in any data transmission errors, it increases the time between
     * attempts, stopping after a total of 4 attempts.
     * @param command an object of the Command class with payloads or parameters set
     * @param initialSending default true by ConsoleController. Reset by Serial Model if
     *                       there are data errors in transmission.
     */

    public void sendCommand(Command command, boolean initialSending) {
        if (initialSending) {delayTime = 1000;}
        String cmdString = buildCommand(command);
        byte[] cmdBytes = cmdString.getBytes(StandardCharsets.UTF_8);  //converts String to Byte array

        port.openPort();
        //System.out.println("Seriall: sendCommand Port opened? "+ port.isOpen());
        if (!port.isOpen()) {
            System.out.println("Port could not be opened.");
            getPortSettings();
            return;
        }

        try (OutputStream out = port.getOutputStream()) { //the try block closes the port in the event of error
            port.writeBytes(WAKE_UP,WAKE_UP_LENGTH);
            Thread.sleep(500);
            port.writeBytes(cmdBytes, cmdBytes.length);
            //System.out.println("\nSerial Model: command sent: " + cmdString);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        listenForData(command);
    }

    /**
     * Serial port listens for data. An InputStream receives the data, translates it to a binary
     * string if necessary, sends it to another method for validation.
     * This method stops listening for data if the data reaches the size expected.
     * @param command an object of the Command class with payloads or parameters set
     */
    public void listenForData(Command command) {
        //System.out.println("Serial Model: listenForData called");
        if (!port.isOpen()) {  //port is not auto-closable, can not be used in "try-with-resources"
            port.openPort();
        }
        rawData.setLength(0); //erases StringBuilder rawData
        //System.out.println("Seriall: RawData StringBuilder reset");
        int inputCount = 0;
        try {
            Thread.sleep(500);  //Delay is necessary. Console needs time to "wake up" to a command
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try (InputStream in = port.getInputStream()) {
            //System.out.println("Seriall: inputstream called");
            while (port.bytesAvailable() >= 1 && inputCount < command.getExpectedNumberOfUnitsInReply()) {
                if(command.isFixedNumberOfReplyCharacters()){
                    inputCount ++;
                }
                char thisChar = (char) in.read();
                if (command.isBinaryReturnData()) {  //commands can return binary, hex or text data
                    inputCount ++;
                    rawData.append(Integer.toBinaryString(thisChar) + " ");
                } else {
                    rawData.append(thisChar);
                }
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        } finally {
            port.flushIOBuffers();
            port.closePort();
        }

        //System.out.println("Serial model: raw data is: " + rawData.toString());
        //System.out.println("Character or byte count is: " + inputCount);

        //System.out.println("Seriall: listenForData Port opened? "+ port.isOpen());
        if (command.getType()==2){
            initialSizeCheck(command, rawData);
        }
        confirmData(rawData.toString(), command);
    }

    /**
     * Before CRC, checks to see if the number of received bytes matches the Command's
     * required size.
     * @param command Instance of Command class type 2 (i.e. LOOP and LPS commands)
     * @param testData StringBuilder of raw serial data
     * @return void. Re-sends Command to console if data length is too short
     */
    private void initialSizeCheck(Command command, StringBuilder testData){
        long count = testData.codePoints().filter(ch -> ch == 32).count();
        if (command.getType()==2){
            if (command.getExpectedNumberOfUnitsInReply()<count-2){
                sendCommand(command, false);
            }
        }
    }
    /**
     * Sends raw data to DataProcessor for validation. If data fails validation, a delay is added and
     * the command is re-sent to the console. Delay is increased with repeated failures. Process ends after
     * four consecutive failures.
     * delayTime is 1000 at initialization, incremented on failure, re-set to 1000 whenever a
     * command is sent to method sendCommand with initialSending = true.
     *
     * @param data raw data from serialPort
     * @param command command that had been sent to the console
     *
     */
    private void confirmData(String data, Command command) {

        boolean goodData = DataProcessor.processRawData(command,data);
        if (!goodData){
            try {
                if (delayTime<8000) {
                    Thread.sleep(delayTime);
                    delayTime *= 2;
                    sendCommand(command, false);
                } else {
                    System.out.println("Error communicating with console. Valid data has not returned. " +
                            "Check connection or increase commPortTimeouts");
                    //todo log
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
}


