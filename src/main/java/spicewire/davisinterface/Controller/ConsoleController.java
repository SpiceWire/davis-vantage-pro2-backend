package spicewire.davisinterface.Controller;

import com.fazecast.jSerialComm.SerialPort;
import spicewire.davisinterface.Dao.JdbcWeatherRecord;
import spicewire.davisinterface.Model.Command;
import spicewire.davisinterface.Model.Loop1Reading;
import spicewire.davisinterface.Model.Loop2Reading;
import spicewire.davisinterface.Model.Seriall;
import spicewire.davisinterface.Services.DataProcessor;
import spicewire.davisinterface.View.ComsPanes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/*This class manages interactions between the serialData class, the view, and the records received. The
Davis Serial Protocol describes several commands and parameters that can be sent to the Davis console.
Not all data has a CRC (Cyclic Redundancy Check, for detecting transmission errors
in data).

Davis commands:
    -Do not all have the same terminating character.
    -Can return hex, binary or ASCII data from the Davis console, depending on the command.
    -Sometimes return data with a CRC.
    -Sometimes have parameters that need to be sent with a command.
    -Return serial port data varying in length from 6 to 436 bytes.

Live weather Data:
   -Davis uses two data formats for its real-time weather data: Loop1 and Loop2.
   -Both formats are binary.
   -Both formats are 99 bytes including the CRC value.
   -An ACK message is prepended to both formats.
   -The formats have some variables in common, some different.
   -The LOOP command results in Loop1 format data only.
   -The console's reply to the LPS command can return either or both formats, in any order, any number of times.
   -Variables common to both formats may or may not share a common index.
   -Variables common to both formats may or may not have the same data type.
   -The number of sensors can change.
   -The types of sensors can change.
   -The index order of sensors can change.
   -The Davis console needs a single line feed or a single carriage return (but not both) after a command string.
     Of note, Windows usually uses a carriage return AND line feed for a new line.
   -Values of two bytes are usually but not always sent LSB (Least Significant Bit) first (i.e. "in the wrong order").
   -Some values are held across nibbles of adjacent bytes.
   -Some bytes are mapped to booleans.
   -Some bits within bytes are mapped to booleans.
   -Per Davis: "Please note that in some strings numeric values are in decimal, while in others are in hexadecimal.
    Multi-byte binary values are generally stored and sent least significant byte first. Negative
    numbers use 2's complement notation. CRC values are sent and received most significant byte
    first."
*/

public class ConsoleController {

    private Seriall serialModel;
    private ComsPanes view;
    private Command command = new Command();
    private JdbcWeatherRecord jdbcWeatherRecord;
    private final String NO_COM_PORTS_FOUND = "None found";
    private boolean comPortParametersAreSet = false;

    private final char LINE_FEED = (char) 10; //HEX A
    private final char ACKNOWLEDGE = (char) 6; //HEX 6
    private final char NOT_ACKNOWLEDGE = (char) 21; //Davis instructions indicate HEX 21 (!).
    private final char CARRIAGE_RETURN = (char) 13; //HEX D.
    private final char CANCEL = (char) 24; // HEX 18. Davis uses this with a bad CRC code


    public ConsoleController(Seriall serialModel, ComsPanes view, JdbcWeatherRecord jdbcWeatherRecord) {
        this.serialModel = serialModel;
        this.view = view;
        this.jdbcWeatherRecord = jdbcWeatherRecord;
        populateComPorts();
    }


    public void listenerFromController() {  //adds listeners to objects in the view
        view.getApplyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setComPortParameters();
            }
        });
        view.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateComPorts();
            }
        });
        view.getLoopButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkIfComPortParametersAreSet();
                if (comPortParametersAreSet) {
                    runCurrentData(command.getLoop());
                }
            }
        });
        view.getLPSButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkIfComPortParametersAreSet();
                if (comPortParametersAreSet) {
                    runCurrentData(command.getLps());
                }
            }
        });
        view.getTestButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runConsoleTest(command.getTest());
            }
        });
        view.getRxCheckButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runConsoleTest(command.getRxCheck());
            }
        });
        view.getRxTestButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runConsoleTest(command.getRxTest());
            }
        });
        view.getVersionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runConsoleTest(command.getVer());
            }
        });
        view.getNverButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runConsoleTest(command.getNver());
            }
        });
        view.getReceiversButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runConsoleTest(command.getReceivers());
            }
        });


    }

    //This method handles all console testing commands: TEST, RXCHECK, RXTEST, VER, RECEIVERS, NVER
    private void runConsoleTest(Command command) {
        //todo refactor so there is a com port params check with any button press
        //todo add a "you just pressed this button" field to the view.
        sendCommandToConsole(command);
        view.setTestDescriptionTextArea(command.getDescription());
        view.setConsoleFriendlyTextArea(DataProcessor.consoleFriendlyText(command));
        view.setConsoleRawTextArea(DataProcessor.getSerialData());
    }

    //This method handles current data commands: LOOP, LPS
    private void runCurrentData(Command command) {
        sendCommandToConsole(command);
        view.setCurrentDataTextArea(DataProcessor.getSerialData());
        if (DataProcessor.getSerialData().length() > 0) {
            if (command.getWord().equalsIgnoreCase("LOOP")) {
                new Loop1Reading(DataProcessor.getSerialData(), jdbcWeatherRecord);
            } else if (command.getWord().equalsIgnoreCase("LPS")) {
                new Loop2Reading(DataProcessor.getSerialData(), jdbcWeatherRecord);
            }
        }
    }

    public void sendCommandToConsole(Command command) {
        serialModel.sendCommand(command, true);
    }

    private void setTimeouts() {
        int timeoutMode = 0;
        int writeTimeout = 0;
        int readTimeout = 0;

        try {
            timeoutMode = view.getTimeoutMode();
            writeTimeout = view.getWriteTimeout();
            readTimeout = view.getReadTimeout();
        } catch (Exception exception) {
            //System.out.println("Error in setting timeouts.");  //todo remove
            comPortParametersAreSet = false;
            setEvalMessage("Error in setting timeouts.");
        }
        serialModel.setTimeouts(timeoutMode, readTimeout, writeTimeout);
        comPortParametersAreSet = true;
    }

    public void populateComPorts() {  //sends list of available com ports to be listed in View
        String[] serialPortArr = serialModel.getSerialPorts();
        if (serialPortArr.length == 0) {
            view.addComPortToCmbComPort(NO_COM_PORTS_FOUND);
            return;
        }
        view.clearComPortList();  //clears list from com port drop down
        for (String sp : serialPortArr) {  //strips system com port name of leading slashes, etc
            StringBuilder friendlySPName = new StringBuilder();
            char c;
            for (int i = 0; i < sp.length(); i++) {
                c = sp.charAt(i);
                if (Character.isAlphabetic(c) || Character.isDigit(c)) {
                    friendlySPName.append(c);
                }
            }
            view.addComPortToCmbComPort(friendlySPName.toString()); //adds stripped name to com port list

        }
    }

    public static String getComPortSettings() {
        return Seriall.getPortSettings();
    }

    public void setComPortParameters() {
        int baud, databits, stopbits, parity, comPortIndex;
        String comPortPath, comPortName;
        try {
            baud = view.getBaudRate();
            parity = view.getParity();
            stopbits = view.getStopBits();
            databits = view.getDataBits();
            comPortIndex = view.getComPortIndex();
            comPortName = "COM4";

            if (comPortName.equalsIgnoreCase(NO_COM_PORTS_FOUND)) {
                //System.out.println("No port is selected.");
                setEvalMessage("No port is selected.");
                return;
            }
            System.out.println(" Ports: " + Arrays.toString(serialModel.getSerialPorts()));
            comPortPath = serialModel.getComPortPath(comPortIndex);
            SerialPort newPort = serialModel.selectSerialPort(comPortPath);
            serialModel.setComPortParameters(newPort, comPortName, baud, databits, stopbits, parity, false);
            System.out.println("controller says baud is " + baud);
            setTimeouts();
        } catch (Exception exception) {
            //System.out.println("Com port parameters not set.");
            setEvalMessage("Com port parameters not set.");
        }
        comPortParametersAreSet = true;
        setEvalMessage("Com port parameters are set.");
    }

    private void checkIfComPortParametersAreSet() { //called from some GUI button action listeners, before trying to get console data
        if (comPortParametersAreSet == false) {
            setEvalMessage("Com port parameters are not set yet.");
        } //else System.out.println("if statement is evaluated as else"); //todo remove
    }

    private void setEvalMessage(String evalMessage) {
        view.setTfEval(evalMessage);
    }




/*    Lines below are not implemented because Davis console does not follow published specs. Davis manual (p. 12)
    indicates the following regarding a LOOP command:
      "LOOP <number of LOOP packets to send-decimal>
        It sends the specified number of LOOP packets, 1 every 2 seconds. Console sleeps between
        each packet sent. The station responds with an <ACK> then with binary data packet every 2
            seconds."

       When tested with a command of "LOOP 2" , the console sends an ACK followed by two LOOP1
       format packets with no delay between them. Sending "LOOP 3" and "LOOP 4" resulted in inconsistent
       outcomes. Sometimes three packets were received, sometimes two, before the readTimeout blocked further
       reception.

       To simplify matters, the Command Model for the Loop command was changed so it only carries a payload of
       "1". Likewise, the LPS instantiation was changed to include a defined payload of "2 1" to send
       a single LOOP2 packet.

       I could not conceive of a situation in which I needed a weather record updated within milliseconds.
       The code is preserved for future implementation.
             */


/*        String payload = getPayloadFromTextField(command);  //returns validated integers from command's associated JTextField
        command.setPayload(payload);

/*
    private String getPayloadFromTextField(Command command){
        JTextField payloadField = payloadTextField(command); //gets JTextField associated with the command button
        return(validateIntegers(payloadField));  //validates that only integers are present in text fields.
    }

    //validates that a JTextField contains only integers or spaces.
    private String validateIntegers(JTextField textField) {
        StringBuilder validInts = new StringBuilder();
        String text = textField.getText().trim();
        for (char c: text.toCharArray()){
            if (Character.isDigit(c) || Character.isSpaceChar(c)){
                validInts.append(c);
            }
            else {
                System.out.println("You must enter whole numbers only.");
                setEvalMessage("You must enter whole numbers only.");
                break;
            }
        }
        boolean validatedParams = validateNumberOfIntegers(validInts.toString());
        if(validatedParams){
            return validInts.toString();
        } else return null;
    }

    //validates that the correct number of parameters are present in command button's corresponding JTextField.
    private Boolean validateNumberOfIntegers(String validInts){
        boolean dataIsValid = false;
        int numberOfSpacesFoundInPayload = 0;
        for (char c: validInts.toCharArray()){
            if(Character.isSpaceChar(c)){
                numberOfSpacesFoundInPayload +=1;
            }
        }
        if(command.getNumberOfDataParameters() ==0 && numberOfSpacesFoundInPayload == 0){
            dataIsValid = true;
        } else if (command.getNumberOfDataParameters() - numberOfSpacesFoundInPayload ==1){
            dataIsValid = true;
        } else {
            System.out.println("You have an incorrect number of parameters for that command.");
            setEvalMessage("You have an incorrect number of parameters for that command.");
            dataIsValid= false;}

        return dataIsValid;
    }
    private JTextField payloadTextField (Command command){
        switch (command.getWord()){
            case "LOOP": return view.getTfLoop();
            case "LPS" :return view.getTfLPS();
            case "PUTET": return view.getTfPutET();
            case "PUTRAIN": return view.getTfPutRain();
            case "HILOWS" : return view.getTfNoParametersNeededTextField();
            default: return null;
        }
    }
*/


}

