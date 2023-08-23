package spicewire.davisinterface.Controller;

import spicewire.davisinterface.Dao.JdbcWeatherRecord;
import spicewire.davisinterface.Model.*;
import spicewire.davisinterface.Services.DataProcessor;
import spicewire.davisinterface.View.ComsPanes;
import spicewire.davisinterface.View.TextAreas;
import spicewire.davisinterface.View.ViewDTO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import java.util.Arrays;

/*This class manages interactions between the serialData class, the view, and the records received. The
Davis Serial Protocol describes several commands and parameters that can be sent to the Davis console.
Not all data has a CRC (Cyclic Redundancy Check, for detecting transmission errors
in data).

*/

public class ConsoleController {

    private Seriall serialModel;
    private ComsPanes view = new ComsPanes();
    private Command command = new Command();
    private JdbcWeatherRecord jdbcWeatherRecord;
    private ViewDTO viewDTO= new ViewDTO();
    private boolean comPortParametersAreSet = false;
    private final Logger logger = Logger.getLogger(ConsoleController.class.getName());


    public ConsoleController(Seriall serialModel, ComsPanes view, JdbcWeatherRecord jdbcWeatherRecord) {
        System.out.println("Console controller started.");
        this.serialModel = serialModel;
        this.view = view;
        this.jdbcWeatherRecord = jdbcWeatherRecord;
        Seriall.getSerialPortList();
        //setComPortParameters(1,9600, 8, 1, 1, 0, 0, 0);

        logger.info("Console controller started.");
        listenerFromController();
//        runCurrentData(command.getLoop());
//        runCurrentData(command.getLps());
    }
    public ConsoleController(){}
//    /**
//     * Takes a String from the View, converts it into a Command class, sends it to the Davis console.
//     * String must be a Command as described in the Davis manual. Currently accepts the following
//     * Commands : test, rxCheck, rxTest, ver, nver, receivers, loop, lps
//     * @param commandWord One of {test, rxCheck, rxTest, ver, nver, receivers, loop, lps}
//     */
//    public void runCommand(String commandWord){
//        Command command = new Command();
//        switch(commandWord.toLowerCase()){
//            case "loop": createLoopRecord(command.getLoop());
//                break;
//            case "lps": createLoopRecord(command.getLps());
//                break;
//            case "test": runConsoleTest(command.getTest());
//                break;
//            case "rxcheck": runConsoleTest(command.getRxCheck());
//                break;
//            case "rxtest": runConsoleTest(command.getRxTest());
//                break;
//            case "ver": runConsoleTest(command.getVer());
//                break;
//            case "nver": runConsoleTest(command.getNver());
//                break;
//            case "receivers": runConsoleTest(command.getReceivers());
//                break;
//            default:
//                System.out.println("Command " + commandWord + " does not exist or is not yet implemented.");
//        }
//    }

    public CurrentWeather getCurrentWeather(){
        getSerialData(command.getLoop());
        createLoopRecord(command.getLoop());
        getSerialData(command.getLps());
        createLoopRecord(command.getLps());
        return jdbcWeatherRecord.getWeather();
    }
        //todo transfer these to the coms view?


    /**
     * This method handles all console testing commands: TEST, RXCHECK, RXTEST, VER, RECEIVERS, NVER.
     * Console testing commands are type 1.
     * @param command of the Command class
     */
    private void runConsoleTest(Command command) {
        //todo refactor so there is a com port params check with any button press
        //todo add a "you just pressed this button" field to the view.
        if (confirmCommandClass(command, 1)) {
            ViewDTO.setTestingRawText(getSerialData(command));
            ViewDTO.setTestingDescription(command.getDescription());
            ViewDTO.setTestingFriendlyText(TextAreas.consoleFriendlyText(command));
            ViewDTO.setLastCommandSent(command.getWord());

//            view.setTestDescriptionTextArea(command.getDescription());
//            view.setConsoleFriendlyTextArea(TextAreas.consoleFriendlyText(command));
//            view.setConsoleRawTextArea(DataProcessor.getSerialData());
        }
    }

    /**
     * Sends a single command to the Davis console and returns the sanitized and validated
     * serial data it generates. It can be used with LOOP and LPS commands before making a LOOP record.
     *
     * @param command  A member of the Command class that generates serial data
     * @return String from DataProcessor
     */
    private String getSerialData(Command command) {
        ViewDTO.setLastCommandSent(command.getWord());
        sendCommandToConsole(command);
        System.out.println("Controller: getSerialData called with " + command.getWord());
        if (command.getType()==2) {
            ViewDTO.setCurrentDataText(DataProcessor.getSerialData());
        }
        return DataProcessor.getSerialData();
    }

    /**
     * Creates a JDBC record of a Current Data command (LOOP or LPS) using
     * new serial port data from the DataProcessor.
     *
     * @param command LOOP or LPS
     */
    private void createLoopRecord(Command command) {

        if (confirmCommandClass(command, 2)) {
            System.out.println("Controller: CreateLoopRecord called with " + command.getWord());
            if (DataProcessor.getSerialData().length() > 0) {
                if (command.getWord().equalsIgnoreCase("LOOP")) {
                    new Loop1Reading(DataProcessor.getSerialData());
                } else if (command.getWord().equalsIgnoreCase("LPS")) {
                    new Loop2Reading(DataProcessor.getSerialData());
                }
            }
        }
    }

    /**
     * Sends an assembled command to the Serial class to forward to the Davis console.
     * initialSending is default true. The Serial class changes this to "false" if the serial data has errors.
     * @param command
     */
    public void sendCommandToConsole(Command command) {
        serialModel.sendCommand(command, true);
    }

    /**
     * Confirms that a command is the correct type for the method being called.
     * Command types: 1=Testing  2=Current Data  3=Download  4=EEPROM 5=Calibration 6=Clearing  7= Configuration
     *  (Categorized in Davis Serial Communication Reference Manual)
     * @param command from Command class
     * @param commandType int
     * @return boolean, true if there is a type match
     */
    private boolean confirmCommandClass (Command command, int commandType){

        boolean commandIsCorrectType = (command.getType()==commandType);
        System.out.println("Controller: confirmCommandClass called with " + command.getWord());
        if (!commandIsCorrectType){
            System.out.println("Internal error. Command " + command.getWord() +
                    " of type " + command.getType() + " was sent where only type " +
                    commandType + "fits.");
        }
        return commandIsCorrectType;
    }

    /**
     * Sets default timeouts for the serial port read/write.
     */
    private void setTimeouts(int timeoutMode, int writeTimeout, int readTimeout) {
        try {
//            timeoutMode = view.getTimeoutMode();
//            writeTimeout = view.getWriteTimeout();
//            readTimeout = view.getReadTimeout();
        } catch (Exception exception) {
            //System.out.println("Error in setting timeouts.");  //todo remove
            comPortParametersAreSet = false;
            setEvalMessage("Error in setting timeouts.");
        }
        serialModel.setTimeouts(timeoutMode, readTimeout, writeTimeout);
        comPortParametersAreSet = true;
    }

    /**
     * The list of available com port names is generated by the Serial Class
     * Controller removes non-alphanumeric chars and updates the CommPortModel
     */
    public void updateComPortList() {
        String[] serialPortArr = serialModel.getSerialPortList();
        String[] comPortList = new String[serialPortArr.length];
        System.out.println("Controller says: Initial Console com port list is: " +  Arrays.toString(serialModel.getSerialPortList()));
        if (serialPortArr.length == 0) {
            CommPortModel.setCommPortList(new String[]{"NO_COM_PORTS_FOUND"});
            //return new String[]{"NO_COM_PORTS_FOUND"};
        }
        //view.clearComPortList();
        StringBuilder friendlySPName = new StringBuilder();
        for (String sp : serialPortArr) {  //strips system com port name of leading slashes, etc

            char c;
            for (int i = 0; i < sp.length(); i++) {
                c = sp.charAt(i);
                if (Character.isAlphabetic(c) || Character.isDigit(c)){
                    friendlySPName.append(c);
                }
            }
            friendlySPName.append(" ");
            System.out.println("Controller says: Final console com port list item is: " + friendlySPName.toString());

            //view.addComPortToCmbComPort(friendlySPName.toString()); //adds stripped name to comm port list
        }
        comPortList = friendlySPName.toString().split(" ");
        CommPortModel.setCommPortList(comPortList);
    }

    /**
     * Gets and returns current port settings for baud, databits, stopbits, parity,
     * path, description, systemPortName
     * @return String
     */
    public static String getComPortSettings() {
        return Seriall.getPortSettings();
    }

    /*
     * View sends com port parameters to be set. The com port index number is sent to the
     * serial model, which generates the com port path.
     * @param comPortIndex index number of user-selected com port from com port list.
     * @param baud 1200, 2400, 4800, 9600, or 19200 Davis default=19200
     * @param dataBits 7 0r 8. Default 8
     * @param stopBits index 0, 1, 2, 3 corresponding to options are {0, 1, 1.5, 2}. Default 1
     * @param parity index 0, 1, or 2 corresponding to options  {no, even, odd}
     * @param timeoutMode 0 or 1. 0= nonblocking, 1=semiBlocking. Default 0
     * @param writeTimeout In milliseconds. Default 0
     * @param readTimeout In milliseconds. Default 0
     */

    public boolean setComPortParameters(){
        return true;
    }
//    public boolean setComPortParameters(int comPortIndex, int baud, int dataBits, int stopBits,
//                                     int parity, int timeoutMode, int writeTimeout, int readTimeout) {
//
//        try {
//            if (serialModel.getSerialPortList().length==0) {
//                //System.out.println("No port is selected.");
//                setEvalMessage("No port is selected.");
//                return false;
//            }
//
//            System.out.println(" Ports: " + Arrays.toString(serialModel.getSerialPortList()));
//            comPortParametersAreSet = serialModel.setSerialPortParameters(comPortIndex, baud, dataBits, stopBits, parity);
//
//
//        } catch (Exception exception) {
//            //System.out.println("Com port parameters not set.");
//            setEvalMessage("Com port parameters not set.");
//        }
//        setEvalMessage("Com port parameters are set.");
//        setTimeouts(timeoutMode, writeTimeout, readTimeout);
//        return comPortParametersAreSet;
//    }

    public boolean isComPortParametersAreSet() {
        return comPortParametersAreSet;
    }

    public void setComPortParametersAreSet(boolean comPortParametersAreSet) {
        this.comPortParametersAreSet = comPortParametersAreSet;
    }

    private boolean checkIfComPortParametersAreSet() { //called from some GUI button action listeners, before trying to get console data
        if (!CommPortModel.isCommParamsSet()) {
            System.out.println(CommPortModel.getAllParams());
            setEvalMessage("Com port parameters are not set yet.");
        }
        return CommPortModel.isCommParamsSet();//else System.out.println("if statement is evaluated as else"); //todo remove
    }

    private void setEvalMessage(String evalMessage) {
        view.setTfEval(evalMessage);
    }

    public void listenerFromController() {  //adds listeners to objects in the view
        view.getApplyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                setComPortParameters(view.getComPortIndex(),view.getBaudRate(),
//                        view.getDataBits(), view.getStopBits(), view.getParity(),
//                        view.getTimeoutMode(), view.getWriteTimeout(),
//                        view.getReadTimeout());
//                setComPortParameters(1,9600, 8,1,1,
//                        0,0,0);
                System.out.println("Console: Listener set params.");
                serialModel.setSerialPortParameters(CommPortModel.getComPortIndex());
            }
        });
        view.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateComPortList();
            }
        });
        view.getLoopButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Controller: loop called");
                checkIfComPortParametersAreSet();

                if (checkIfComPortParametersAreSet()) {
                    getSerialData(command.getLoop());

                }
            }
        });
        view.getLPSButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Controller: LPS called. Com check: " + checkIfComPortParametersAreSet());
                checkIfComPortParametersAreSet();
                if (checkIfComPortParametersAreSet()) {
                    getSerialData(command.getLps());
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
        view.getBtnGetWeather().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCurrentWeather();
            }
        });

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

