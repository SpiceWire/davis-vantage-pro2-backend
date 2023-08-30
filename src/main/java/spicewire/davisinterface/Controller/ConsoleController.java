package spicewire.davisinterface.Controller;
import org.apache.commons.dbcp2.BasicDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.ls.LSOutput;
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


//todo convert system out to logger

/**This class manages interactions between the serialData class, the view, and the records received. The
Davis Serial Protocol describes several commands and parameters that can be sent to the Davis console.
Not all data has a CRC (Cyclic Redundancy Check, for detecting transmission errors
in data).

*/
@Configuration
public class ConsoleController {

    public static  BasicDataSource dataSource = new BasicDataSource();
    private Seriall serialModel = new Seriall();
    @Autowired
    private ComsPanes view = new ComsPanes();
    private Command command = new Command();
    private JdbcWeatherRecord jdbcWeatherRecord = new JdbcWeatherRecord(dataSource);
    private final Logger logger = Logger.getLogger(ConsoleController.class.getName());


    public ConsoleController(Seriall serialModel, ComsPanes view, JdbcWeatherRecord jdbcWeatherRecord) {
        System.out.println("Console controller started.");
        this.serialModel = serialModel;
        this.view = view;
        this.jdbcWeatherRecord = jdbcWeatherRecord;
        Seriall.getSerialPortList();
        System.out.println("Console controller started.");
        listenerFromController();
        System.out.println();
    }
    public ConsoleController(){}

    public CurrentWeather getMostRecentWeather(){
        return jdbcWeatherRecord.getWeather();
    }

    /**
     * This method gathers sensor data from the Davis system using the LOOP and LPS
     * Commands, inserts the readings into the database, and returns a CurrentWeather
     * object containing the gathered sensor data.
     * @return CurrentWeather object with new data.
     */
    public CurrentWeather getCurrentWeatherReadings(){
        getSerialData(command.getLoop());
        createLoopRecord(command.getLoop());
        getSerialData(command.getLps());
        createLoopRecord(command.getLps());
        return jdbcWeatherRecord.getWeather();
    }

    /**
     * Uses a DTO to set CommPort settings.
     * @param settingsDTO
     * @return  boolean true if settings are set successfully.
     */
    public boolean setPortParams(SerialSettingsDTO settingsDTO ){
        return serialModel.setPortParams((settingsDTO));
    }

    /**
     * This method handles all console testing commands: TEST, RXCHECK, RXTEST, VER, RECEIVERS, NVER.
     * Console testing commands are type 1.
     * @param command of the Command class (i.e. type 1)
     */
    private void runConsoleTest(Command command) {
        //todo refactor so there is a com port params check with any button press
        if (confirmCommandClass(command, 1)) {
            ViewDTO.setTestingRawText(getSerialData(command));
            ViewDTO.setTestingDescription(command.getDescription());
            ViewDTO.setTestingFriendlyText(TextAreas.consoleFriendlyText(command));
            ViewDTO.setLastCommandSent(command.getWord());

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
     * Creates a JDBC record of a LOOP or LPS Current Data command (i.e. Type 2) using
     *  serial port data from the DataProcessor.
     *
     * @param command LOOP or LPS
     */
    private void createLoopRecord(Command command) {

        if (confirmCommandClass(command, 2)) {
            System.out.println("Controller: CreateLoopRecord called with " + command.getWord());
            if (DataProcessor.getSerialData().length() > 0) {
                if (command.getWord().equalsIgnoreCase("LOOP")) {
                    Loop1Reading l1Reading = new Loop1Reading(DataProcessor.getSerialData());
                    jdbcWeatherRecord.createRecord(l1Reading);
                } else if (command.getWord().equalsIgnoreCase("LPS")) {
                    Loop2Reading l2Reading =  new Loop2Reading(DataProcessor.getSerialData());
                    jdbcWeatherRecord.createRecord(l2Reading);
                }
            }
        }
    }

    /**
     * Sends an assembled command to the Serial class to forward to the Davis console.
     * initialSending is default true. The Serial class changes this to "false" if the serial data has errors.
     * @param command from the Command class
     */
    public void sendCommandToConsole(Command command) {
        serialModel.sendCommand(command, true);
    }

    /**
     * Confirms that a command is the correct type for the method being called.
     * Command types: 1=Testing  2=Current Data  3=Download  4=EEPROM 5=Calibration 6=Clearing  7= Configuration
     *  (Categorized in Davis Serial Communication Reference Manual)
     * @param command from the Command class
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
     * The list of available com port names is generated by the Serial Class
     * Controller removes non-alphanumeric chars and updates the CommPortModel
     */
    public void updateComPortList() {
        String[] serialPortArr = serialModel.getSerialPortList();
        String[] comPortList = new String[serialPortArr.length];
        System.out.println("Controller says: Initial Console com port list is: " +  Arrays.toString(serialModel.getSerialPortList()));
        if (serialPortArr.length == 0) {
            CommPortModel.setCommPortList(new String[]{"NO_COM_PORTS_FOUND"});

        }
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
            System.out.println("Controller says: Final console com port list item is: " + friendlySPName);
        }
        comPortList = friendlySPName.toString().split(" ");
        CommPortModel.setCommPortList(comPortList);
    }

    /**
     * Gets and returns current port settings for baud, databits, stopbits, parity,
     * path, description, systemPortName
     * @return String
     */
    public static SerialStatus getComPortSettings() {
        return Seriall.getPortSettings();
    }


    private boolean checkIfComPortParametersAreSet() {
        if (!CommPortModel.isCommParamsSet()) {
            setEvalMessage("Com port parameters are not set yet.");
        }
        else {
            setEvalMessage("Com port parameters are set.");
        }
        System.out.println(CommPortModel.getAllParams());
        return CommPortModel.isCommParamsSet();
    }

    private void setEvalMessage(String evalMessage) {
        view.setTfEval(evalMessage);
    }

    public void listenerFromController() {  //adds listeners to objects in the view
        view.getApplyButton().addActionListener(e -> {
            System.out.println("Console: Listener set params.");
            serialModel.setSerialPortParameters(CommPortModel.getComPortIndex());
        });
        view.getLoopButton().addActionListener(e -> {
            System.out.println("Controller: loop called");
            checkIfComPortParametersAreSet();

            if (checkIfComPortParametersAreSet()) {
                getSerialData(command.getLoop());

            }
        });
        view.getLPSButton().addActionListener(e -> {
            System.out.println("Controller: LPS called. Com check: " + checkIfComPortParametersAreSet());
            checkIfComPortParametersAreSet();
            if (checkIfComPortParametersAreSet()) {
                getSerialData(command.getLps());
            }
        });

        view.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateComPortList();
                System.out.println("consoleController: Refresh called.");
            }
        });
        view.getTestButton().addActionListener(e -> runConsoleTest(command.getTest()));
        view.getRxCheckButton().addActionListener(e -> runConsoleTest(command.getRxCheck()));
        view.getRxTestButton().addActionListener(e -> runConsoleTest(command.getRxTest()));
        view.getVersionButton().addActionListener(e -> runConsoleTest(command.getVer()));
        view.getNverButton().addActionListener(e -> runConsoleTest(command.getNver()));
        view.getReceiversButton().addActionListener(e -> runConsoleTest(command.getReceivers()));
        view.getBtnGetWeather().addActionListener(e -> getCurrentWeatherReadings());

    }


}

