package spicewire.davisinterface.View;

/**
 * Data Transfer Object for the View's text fields for Testing and Current Data commands.
 *
 * The Class does not have a constructor. Relevant attributes are given values by the Console Controller
 * and the instantiated object is sent to the Console Testing Controller to be sent to the View.
 *
 * Davis Test Commands (TEST, RXCHECK, RXTEST, VER, NVER, RECEIVERS) sent to the controller generate
 * two text fields: testingRawText and testingFriendlyText.
 *
 * Davis Current Data commands (LOOP, LPS) sent to the controller generate text for fields currentDataText
 * (usually binary data) and currentDataEvalText (whether the binary passed CRC, validation, etc).
 *
 */
public class ViewDTO {
    public ViewDTO(){}

    /**
     * Holds binary results from Davis Loop and LPS commands.
     * Davis Command category: Current Data
     */
    private  String currentDataText;

    /**
     * Holds Controller's response to Loop and LPS commands.
     * Davis Command category: Current Data
     */
    private  String currentDataEvalText;

    /**
     * Holds raw Davis Console results from testing commands: Test, RxCheck, RxTest, Ver, Nver, Receivers
     * Davis Command category: Testing
     */
    private  String testingRawText;

    /**
     * Holds Controller-interpreted Davis Console results from testing commands: Test,
     * RxCheck, RxTest, Ver, Nver, Receivers
     * Davis Command category: Testing
     */
    private  String testingFriendlyText;

    /**
     * Holds description of the current Davis Testing command.
     * Davis Command category: Testing
     */
    private  String testingDescription;
    /**
     * Identifies the command that generated the ViewDTO object.
     */
    private  String lastCommandSent;

    /**
     * Carries an error message, if any, to the view.
     */
    private  String errorMessage;

    public  String getErrorMessage() {
        return errorMessage;
    }

    public  void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public  void setErrorMessage(int errorMessageNumber) {
        this.errorMessage = getErrorMessage(errorMessageNumber);
    }
    public  String getLastCommandSent() {
        return lastCommandSent;
    }

    public  final String WRONG_TYPE = "The Command sent was the wrong type for the operation.";
    public  final String UNKNOWN_TYPE = "The Command sent did not use a valid word, was unknown, " +
            "or not yet implemented";
    public  final String WRONG_PARAMS = "The Command sent had missing or invalid parameters.";
    public  final String NO_CONNECTION = "The console did not have a working connection to the backend.";
    public  final String UNKNOWN = "An unexpected error occurred.";
    public  final String CRC_ERROR = "An unresolvable CRC error occurred.";

    public  void setLastCommandSent(String lastCommandSent) {
        this.lastCommandSent = lastCommandSent;
    }

    public  void setCurrentDataText(String currentDataText) {
        this.currentDataText = currentDataText;
    }

    public  String getCurrentDataText() {
        return currentDataText;
    }

    public  String getCurrentDataEvalText() {
        return currentDataEvalText;
    }

    public  void setCurrentDataEvalText(String currentDataEvalText) {
        this.currentDataEvalText = currentDataEvalText;
    }

    /**
     * Uses an index number to include an error message to the this.
     * Index is:       0 WRONG_TYPE,
     *                 1 UNKNOWN_TYPE,
     *                 2 WRONG_PARAMS,
     *                 3 NO_CONNECTION,
     *                 4 UNKNOWN,
     * @param errorNumber
     * @return
     */
    public  String getErrorMessage(int errorNumber){
        String[] errorMsgAry = new String[] {
                WRONG_TYPE,
                UNKNOWN_TYPE,
                WRONG_PARAMS,
                NO_CONNECTION,
                UNKNOWN,
        };
        return errorMsgAry[errorNumber];
    }

    public  String getTestingRawText() {
        return testingRawText;
    }

    public  void setTestingRawText(String testingRawText) {
        this.testingRawText = testingRawText;
    }

    public  String getTestingFriendlyText() {
        return testingFriendlyText;
    }

    public  void setTestingFriendlyText(String testingFriendlyText) {
        this.testingFriendlyText = testingFriendlyText;
    }

    public  String getTestingDescription() {
        return testingDescription;
    }

    public  void setTestingDescription(String testingDescription) {
        this.testingDescription = testingDescription;
    }
}
