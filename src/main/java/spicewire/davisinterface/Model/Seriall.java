package spicewire.davisinterface.Model;


import com.fazecast.jSerialComm.SerialPort;
import spicewire.davisinterface.Services.DataProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class Seriall {
 /*    Note:resetting the serial port settings forces a readSerial event.
     The SerialPort object has no constructors in the Fazecast.jSerialComm.SerialPort model.
     The SerialPort object is assigned to a communications port, and then properties can be set.*/


    private StringBuilder rawData = new StringBuilder(); // raw data before CRC
    private static Integer delayTime = 1000; //delay between requests to the console in the event of a failed CRC

    static SerialPort port = selectSerialPort("COM4");

    //assigns serial port path from view selection passed from controller
    public String getComPortPath(int index) {
        return SerialPort.getCommPorts()[0].getSystemPortPath();
    }

    public static SerialPort selectSerialPort(String serialPortSystemPath) {
        System.out.println("Seriall : SelectSeralPort called");
        //return SerialPort.getCommPort("COM4"); //sets the SerialPort object
        return SerialPort.getCommPort(serialPortSystemPath);
    }

    public static String getPortSettings(){
        StringBuilder settings = new StringBuilder();
        if (port!=null){
            settings.append("|baud rate: " + port.getBaudRate());
            settings.append("|data bits: " + port.getNumDataBits());
            settings.append("|stop bits: " + port.getNumStopBits());
            settings.append("|parity: " + port.getParity());
            settings.append("|path: " + port.getSystemPortPath());
            settings.append("|descriptive: " + port.getDescriptivePortName());
            settings.append("|system name: " + port.getSystemPortName());
        } else {
            settings.append("Port has not been assigned. No settings to return.");
        }
        return settings.toString();
    }

    /**
     * Accepts and sets parameters for the serial port.
     * The com port index is translated to the com port path needed by Fazecast Jserialcomm library
     * The port.setComPortParameters uses the parameter of useRS485Mode, defaulted as false.
     *
     * @param comPortIndex  comPortList's index number of the selected com port
     * @param newBaudRate 1200, 2400, 4800, 9600, or 19200. Davis default:19200.
     * @param newDataBits 7 or 8. Davis default: 8
     * @param newStopBits  0, 1, 2, 3 (index from list {0, 1, 1.5, 2}) Davis default: 1
     * @param newParity  0, 1, 2 (index from list {no, even, odd}  Davis default: 1
     * @return true if port was set, false if not
     */
    public boolean setSerialPortParameters(int comPortIndex, int newBaudRate, int newDataBits, int newStopBits,
                                           int newParity) {
        //use the index of the com port to set the com port
        String comPortPath = getComPortPath(comPortIndex);
        SerialPort port = selectSerialPort(comPortPath);
        System.out.println(getPortSettings());
        return port.setComPortParameters(newBaudRate, newDataBits, newStopBits, newParity, false);
    }

    public String[] getSerialPorts() {
        String[] compPortsArray = new String[SerialPort.getCommPorts().length];  //todo should be .length?
        System.out.println("seriall Poorts: "+ Arrays.toString(getSerialPorts()));
        System.out.println("Seriall: How many ports? "+ SerialPort.getCommPorts().length);
        for (int i = 0; i <= SerialPort.getCommPorts().length - 1; i++) {
            compPortsArray[i] = SerialPort.getCommPorts()[i].getSystemPortPath();
        }
        System.out.println("Seriall Poooorts: "+  Arrays.toString(getSerialPorts()));
        return compPortsArray;
    }

    /*        TimeoutMode 0= nonblocking, 1=semiBlocking, others
            newReadTimeout = milliseconds of inactivity to tolerate before returning from a readBytes(byte[],long) call*/
    public void setTimeouts(int newTimeoutMode, int newReadTimeout, int newWriteTimeout) {
        this.port.setComPortTimeouts(newTimeoutMode, newReadTimeout, newWriteTimeout);

    }

    public final boolean setComPortTimeouts(int newTimeoutMode, int newReadTimeout, int newWriteTimeout) {
        return this.port.setComPortTimeouts(newTimeoutMode, newReadTimeout, newWriteTimeout);
    }

    //This method builds and sends a command to the console. The Fazecast writeMethod requires a buffer.
    public void sendCommand(Command command, boolean initialSending) {
        if (initialSending==true) {delayTime = 1000;}
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
        if (!port.isOpen()) {
            System.out.println("Port could not be opened.");
            return;
        }

        try (OutputStream out = port.getOutputStream();) { //the try block closes the port in the event of error
            port.writeBytes(cmdBytes, cmdBytes.length);
            System.out.println("\ncommand sent: " + cmdString);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        listenForData(command);
    }


    public void listenForData(Command command) {
        if (!port.isOpen()) {  //port is not auto-closable, can not be used in "try-with-resources"
            port.openPort();
        }
        rawData.setLength(0); //erases StringBuilder rawData
        try {
            Thread.sleep(500);  //Delay is necessary. Console needs time to "wake up" to a command
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try (InputStream in = port.getInputStream()) {
            while (port.bytesAvailable() >= 1) {
                char thisChar = (char) in.read();
                if (command.isBinaryReturnData()) {  //commands can return binary, hex or text data
                    rawData.append(Integer.toBinaryString(thisChar) + " ");
                } else rawData.append(thisChar);
            }

        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        } finally {
            port.flushIOBuffers();
            port.closePort();
        }
        confirmData(rawData.toString(), command);
    }

    /**
     * Sends raw data to DataProcessor for validation. If data fails validation, a delay is added and
     * the command is re-sent to the console. Delay is increased with repeated failures. Process ends after
     * four consecutive failures.
     *
     * delayTime is 1000 at initialization, incremented on failure, re-set to 1000 whenever a
     * command is sent to method sendCommand with initialSending = true.
     *
     * @param data raw data from serialPort
     * @param command command that had been sent to the console
     *
     */
    private void confirmData(String data, Command command) {

        boolean goodData = DataProcessor.processRawData(data, command);
        if (goodData==false){
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


