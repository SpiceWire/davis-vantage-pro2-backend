package spicewire.davisinterface.Controller;
import org.apache.commons.dbcp2.BasicDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import spicewire.davisinterface.Dao.JdbcWeatherRecord;
import spicewire.davisinterface.Model.*;
import spicewire.davisinterface.Services.DataProcessor;
import spicewire.davisinterface.View.ComsPanes;
import spicewire.davisinterface.View.TextAreas;
import spicewire.davisinterface.View.ViewDTO;
import org.springframework.scheduling.annotation.Scheduled;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


//todo convert system out to logger

/**This class manages interactions between the serialData class, the view, and the records received. The
Davis Serial Protocol describes several commands and parameters that can be sent to the Davis console.
Not all data has a CRC (Cyclic Redundancy Check, for detecting transmission errors
in data).

*/
@Configuration
@Service
@EnableScheduling
public class ConsoleController {

    public static  BasicDataSource dataSource = new BasicDataSource();
    @Autowired
    private SerialPortDataCom serialModel = new SerialPortDataCom();
    @Autowired
    private ComsPanes view = new ComsPanes();
    private Command command = new Command();  //todo is this really needed here? Or at all?
    private JdbcWeatherRecord jdbcWeatherRecord = new JdbcWeatherRecord();
    private final Logger logger = Logger.getLogger(ConsoleController.class.getName());


    public ConsoleController(SerialPortDataCom serialModel, JdbcWeatherRecord jdbcWeatherRecord) {
//        System.out.println("Console controller started.");
        this.serialModel = serialModel;

        this.jdbcWeatherRecord = jdbcWeatherRecord;
        SerialPortDataCom.getSerialPortList();
//        System.out.println("Console controller started.");
        listenerFromController();
        System.out.println();
    }
    public ConsoleController(){}

    boolean initializeParams = setDefaultPortParams();

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    private void autoGenerateWeatherRecords(){
        getCurrentWeatherReadings();
    }


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
        System.out.println("ConsoleController: setPortParams called");
        return serialModel.setPortParams((settingsDTO));
    }

    private boolean setDefaultPortParams(){
        SerialSettingsDTO defaultSettings = new SerialSettingsDTO(19200, 0, 0, 0, 0, new String[]{"COM4", "COM6"});
        return serialModel.setPortParams((defaultSettings));
    }
    /**
     * This method handles all console testing commands: TEST, RXCHECK, RXTEST, VER, RECEIVERS, NVER
     * sent from the Swing form.
     * Console testing commands are type 1.
     * @param command of the Command class (i.e. type 1)
     */
    private void runConsoleTest(Command command) {
        //todo refactor so there is a com port params check with any button press
        ViewDTO viewDTO = new ViewDTO();
        if (confirmCommandClass(command, 1)) {
            viewDTO.setTestingRawText(getSerialData(command));
            viewDTO.setTestingDescription(command.getDescription());
            viewDTO.setTestingFriendlyText(TextAreas.consoleFriendlyText(command));
            viewDTO.setLastCommandSent(command.getWord());
        }
    }

    /**
     * Handles console testing commands from frontend. Accepts a String (a Command class word
     * like "TEST" or "RXCHECK"), implements it, and returns the result in a DTO to the View.
     * @param commandWord type 1 (TEST, RXTEST, RXCHECK, RECEIVERS, VER, NVER)
     * @return ViewDTO, an object that has the "text results" from the Command.
     */
    public ViewDTO getConsoleTest(String commandWord) {
        System.out.println("ConsoleController getConsoleTest called.");
        ViewDTO viewDTO = new ViewDTO();
        command = command.matchCommandWithWord(commandWord);
        viewDTO.setLastCommandSent(command.getWord());
        if(confirmCommandClass(command, 1)){
            viewDTO.setTestingRawText(getSerialData(command));
            viewDTO.setTestingDescription(command.getDescription());
            viewDTO.setTestingFriendlyText(TextAreas.consoleFriendlyText(command));
            viewDTO.setErrorMessage(viewDTO.NO_ERROR);
        }
        else viewDTO.setErrorMessage(viewDTO.WRONG_TYPE);
        System.out.println("ConsoleController getConsoleTest called. obj returned is" + viewDTO);
        System.out.println("and the object's error message is " + viewDTO.getErrorMessage());
        return viewDTO;
    }

    /**
     * Handles Current Data commands from the frontend. Accepts a String(a Command class word
     * like "LOOP" or "LPS"), implements it, and returns a DTO to the View.
     * @param commandWord LOOP and LPS are implemented
     * @return ViewDTO, an object that has the "text results" from the Command.
     */
    public ViewDTO getCurrentData(String commandWord){
        ViewDTO viewDTO = new ViewDTO();
        System.out.println("Console Controller getCurrentData called.");
        command = command.matchCommandWithWord(commandWord);
        viewDTO.setLastCommandSent(command.getWord());
        getSerialData(command);
        createLoopRecord(command);
        if(confirmCommandClass(command, 2)){
            System.out.println("Console Controller confirmCommandClass called.");
            viewDTO.setCurrentDataText(DataProcessor.getSerialData());
            viewDTO.setCurrentDataEvalText(DataProcessor.getEvaluationMessage());
        }
        else viewDTO.setErrorMessage(viewDTO.WRONG_TYPE);

        return viewDTO;
    }
    /**
     * Sends a single command to the Davis console and returns the sanitized and validated
     * serial data it generates. It can be used with LOOP and LPS commands before making a LOOP record.
     *
     * @param command  A member of the Command class that generates serial data
     * @return String from DataProcessor
     */
    private String getSerialData(Command command) {
        sendCommandToConsole(command);
//        System.out.println("Controller: getSerialData called with " + command.getWord());
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
//            System.out.println("Controller: CreateLoopRecord called with " + command.getWord());
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
    private void sendCommandToConsole(Command command) {
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
//        System.out.println("Controller: confirmCommandClass called with " + command.getWord());
        if (!commandIsCorrectType){
            System.out.println("Internal error. Command " + command.getWord() +
                    " of type " + command.getType() + " was sent where only type " +
                    commandType + "fits.");
        }
        return commandIsCorrectType;
    }


    /**
     * The list of available com port names is generated by the Serial Class
     *
     */
    public void updateCommPortList() {
        CommPortModel.setCommPortList(getCommPortList());
    }

    /**
     * Gets and returns current port settings for baud, databits, stopbits, parity,
     * path, description, systemPortName
     * @return String
     */
    public static SerialStatus getCommPortSettings() {
        return SerialPortDataCom.getPortSettings();
    }

    /**
     * Returns a String[] of user-friendly comm port names.
     * @return
     */
    public String[] getCommPortList(){
        return serialModel.getSerialPortList();
    }

    private boolean checkIfCommPortParametersAreSet() {
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

    /**
     * Returns the serial port settings being used by the serial port.
     * @return SerialStatus DTO with params filled out by Serial class.
     */
    public SerialStatus getSerialPortSettings(){
        return SerialPortDataCom.getPortSettings();
    }

    public AggregateWeather getPreviousWeatherByDay(Integer offset){
        return jdbcWeatherRecord.getPreviousWeather(offset);
    }

    /**
     * Given the name of a table header, returns that column's first entry of each hour of the past 24 hours.
     * @param headerName valid name of a table header
     * @return Hashmap of entrytime as key, corresponding value of tableheader at entry time as value
     */
    public HashMap<LocalDateTime, String> get24HoursOfHeaderData (String headerName){
        System.out.println("Console controller: rec'd request for 24hours header data");
        return jdbcWeatherRecord.getMapOfTimeAndHeaderValue(headerName);
    }

    public void listenerFromController() {  //adds listeners to objects in the view
        view.getApplyButton().addActionListener(e -> {
//            System.out.println("Console: Listener set params.");
            serialModel.setSerialPortParameters(CommPortModel.getCommPortIndex());
        });
        view.getLoopButton().addActionListener(e -> {
//            System.out.println("Controller: loop called");
            checkIfCommPortParametersAreSet();

            if (checkIfCommPortParametersAreSet()) {
                getSerialData(command.getLoop());
            }
        });
        view.getLPSButton().addActionListener(e -> {
//            System.out.println("Controller: LPS called. Com check: " + checkIfCommPortParametersAreSet());
            checkIfCommPortParametersAreSet();
            if (checkIfCommPortParametersAreSet()) {
                getSerialData(command.getLps());
            }
        });

        view.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCommPortList();
//                System.out.println("consoleController: Refresh called.");
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

