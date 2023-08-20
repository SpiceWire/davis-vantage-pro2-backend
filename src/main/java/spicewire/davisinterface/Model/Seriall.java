package spicewire.davisinterface.Model;


import com.fazecast.jSerialComm.SerialPort;
import org.springframework.http.converter.json.GsonBuilderUtils;
import spicewire.davisinterface.Services.DataProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Arrays;


public class Seriall {
 /*    Note:resetting the serial port settings forces a readSerial event.
     The SerialPort object has no constructors in the Fazecast.jSerialComm.SerialPort model.
     The SerialPort object is assigned to a communications port, and then properties can be set.*/


    private StringBuilder rawData = new StringBuilder(); // raw data before CRC
    private static Integer delayTime = 1000; //delay between requests to the console in the event of a failed CRC

    private static SerialPort port;
    //static SerialPort port = selectSerialPort("COM4");

    //assigns serial port path from view selection passed from controller
    public String getComPortPath(int index) {
        return SerialPort.getCommPorts()[index].getSystemPortPath();
    }

    public static SerialPort selectSerialPort(String serialPortSystemPath) {
        System.out.println("Seriall : SelectSerialPort called with " + serialPortSystemPath);
        //return SerialPort.getCommPort("COM4"); //sets the SerialPort object
        if (SerialPort.getCommPort(serialPortSystemPath)!=null) {
            CommPortModel.setComPortSet(true);
        }
        port = SerialPort.getCommPort(serialPortSystemPath);

        return port;
    }

    private static boolean serialPortSettingsSet(){
        boolean portSettingsSet = false;
        if(CommPortModel.isComPortSet() && CommPortModel.isBaudSet() &&
           CommPortModel.isDataBitsSet() && CommPortModel.isStopBitsSet() &&
            CommPortModel.isParitySet()) {
            portSettingsSet = true;
        }
        return portSettingsSet;

    }
    public static String getPortSettings(){
        System.out.println("Seriall: getPortSettings triggered");
        System.out.printf("Seriall: setCommPortList set to " + Arrays.toString(Arrays.toString(SerialPort.getCommPorts()).split(" ")));
        //CommPortModel.setCommPortList(Arrays.toString(SerialPort.getCommPorts()).split(" "));
        //CommPortModel.setCommPort(port.getDescriptivePortName());
 /*       CommPortModel.setCommPortDescription(port.getDescriptivePortName());
        CommPortModel.setCommPortPath(port.getSystemPortPath());
        CommPortModel.setBaudRate(port.getBaudRate());
        CommPortModel.setDataBits(port.getNumDataBits());
        CommPortModel.setStopBits(port.getNumStopBits());
        CommPortModel.setParity(port.getParity());
        CommPortModel.setCommParamsSet(serialPortSettingsSet());
        CommPortModel.setWriteTimeout(port.getWriteTimeout());
        CommPortModel.setReadTimeout(port.getReadTimeout());*/
        CommPortModel.setUpdatedBy("Serial Port");

        return new CommPortModel().toString();
    }

    /**
     * Sets the serial port based on user's selected index number from the serial port list.
     * The com port index is translated to the com port path needed by Fazecast Jserialcomm library
     * The port.setComPortParameters method uses the parameter of useRS485Mode, defaulted as false.
     * Fazecast JserialComm library does not have a native getTimeoutMode method.
     *
     * @param comPortIndex  comPortList's index number of the selected com port
     * @return boolean if params are  set successfully
     *
     */
    public boolean setSerialPortParameters(int comPortIndex) {
        String comPortPath = getComPortPath(comPortIndex);
        CommPortModel.setCommPortPath(comPortPath);
        CommPortModel.setComPortIndex(comPortIndex);
        SerialPort port = selectSerialPort(comPortPath);
        System.out.println(getPortSettings());
        port.setBaudRate(CommPortModel.getBaudRate());
        port.setNumDataBits(CommPortModel.getDataBits());
        port.setNumStopBits(CommPortModel.getStopBits());
        port.setParity(CommPortModel.getParity());
        port.setComPortTimeouts(CommPortModel.getTimeoutMode(),
                CommPortModel.getReadTimeout(),CommPortModel.getWriteTimeout());
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
     * Updates available serial ports from the Fazecast library.
     * @return String[] of available port names. Names include leading slashes, dots, etc.
     */
    public static String[]  getSerialPortList() {
        System.out.println("seriall: getSerialPortList triggered.");
        String[] compPortsArray = new String[SerialPort.getCommPorts().length];  //todo should be .length?
        for (int i = 0; i <= SerialPort.getCommPorts().length - 1; i++) {
            compPortsArray[i] = SerialPort.getCommPorts()[i].getSystemPortPath();
        }
        System.out.println("Seriall: ComPortsArray = " + Arrays.toString(compPortsArray));
        return compPortsArray;
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
        port.setComPortTimeouts(1, 500, newWriteTimeout);

    }

    /**
     * Builds and sends a Command class object to the console. The Fazecast Serial Port write method requires a buffer.
     * If this method results in any data transmission errors, it increases the time between
     * attempts, stopping after a total of 4 attempts.
     * @param command an object of the Command class with payloads or parameters set
     * @param initialSending default true by ConsoleController. Reset by Serial Model if
     *                       there are data errors in transmission.
     */

    public void sendCommand(Command command, boolean initialSending) {
        if (initialSending) {delayTime = 1000;}
        StringBuilder cmdSB = new StringBuilder(); //todo name it better
        cmdSB.append(command.getWord());
        if (command.getNumberOfDataParameters() > 0) {  //adds payload to the command word being sent to the console
            cmdSB.append(" " + command.getPayload());
        }
        cmdSB.append(command.getTerminatingChar());  //Terminating chars might be \n, \r or both together.
        String cmdString = cmdSB.toString();
        byte[] cmdBytes = cmdString.getBytes(StandardCharsets.UTF_8);  //converts String to Byte array

        //this.port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);  //todo evaluate if this is needed

        port.openPort();
        System.out.println("Port opened:"+ port.isOpen());
        if (!port.isOpen()) {
            System.out.println("Port could not be opened.");
            return;
        }

        try (OutputStream out = port.getOutputStream()) { //the try block closes the port in the event of error
            port.writeBytes(cmdBytes, cmdBytes.length);
            Thread.sleep(500);
            port.writeBytes(cmdBytes, cmdBytes.length);
            System.out.println("\nSerial Model: command sent: " + cmdString);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        listenForData(command);
    }

    /**
     * Serial port listens for data. An InputStream receives the data, translates it to a binary
     * string if necessary, sends it to another method for validation
     * @param command an object of the Command class with payloads or parameters set
     */
    public void listenForData(Command command) {
        System.out.println("Serial Model: listenForData called");
        if (!port.isOpen()) {  //port is not auto-closable, can not be used in "try-with-resources"
            port.openPort();
        }
        rawData.setLength(0); //erases StringBuilder rawData
        System.out.println("Seriall: RawData StringBuilder reset");
        int inputCount = 0;
        try {
            Thread.sleep(500);  //Delay is necessary. Console needs time to "wake up" to a command
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try (InputStream in = port.getInputStream()) {
            System.out.println("Seriall: inputstream called");
            while (port.bytesAvailable() >= 1 && inputCount <= ) {
                if(command.isFixedNumberOfReplyCharacters()){
                    inputCount ++;
                }

                char thisChar = (char) in.read();
                System.out.println("Char: " + thisChar);
                if (command.isBinaryReturnData()) {  //commands can return binary, hex or text data
                    System.out.println("Seriall: command is binary return data");
                    inputCount ++;
                    rawData.append(Integer.toBinaryString(thisChar) + " ");
                } else {
                    //System.out.println("Seriall: command is not binary");
                    rawData.append(thisChar);
                }
                //System.out.println("Seriall: count is; " + count);

            }

        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        } finally {
            port.flushIOBuffers();
            port.closePort();
        }
        System.out.println("Serial model: raw data is: " + rawData.toString());
        System.out.println("Character count is: " + characterCount);
        confirmData(rawData.toString(), command);
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

        boolean goodData = DataProcessor.processRawData(data, command);
        if (!goodData){
            try {
                if (delayTime<8000) {
                    Thread.sleep(delayTime);
                    delayTime *= 2;
                    sendCommand(command, false);
                } else {
                    System.out.println("Error communicating with console. Valid data has not returned. " +
                            "Check connection or increase comPortTimeouts");
                    //todo log
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
}


