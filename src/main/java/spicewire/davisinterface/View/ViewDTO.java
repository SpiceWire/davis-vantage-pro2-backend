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
 */
public class ViewDTO {
    //todo apply other error types
    public ViewDTO() {
    }

    public static final String WRONG_TYPE = "The Command sent was the wrong type for the operation.";
    public static final String UNKNOWN_TYPE = "The Command sent did not use a valid word, was unknown, " +
            "or not yet implemented";
    public static final String WRONG_PARAMS = "The Command sent had missing or invalid parameters.";
    public static final String NO_CONNECTION = "The console did not have a working connection to the backend.";
    public static final String UNKNOWN = "An unexpected error occurred.";
    public static final String CRC_ERROR = "An unresolvable CRC error occurred.";
    public static final String NO_ERROR = "No error was found.";

    /**
     * Holds binary results from Davis Loop and LPS commands.
     * Davis Command category: Current Data
     */
    private String currentDataText;

    /**
     * Holds Controller's response to Loop and LPS commands.
     * Davis Command category: Current Data
     */
    private String currentDataEvalText;

    /**
     * Holds raw Davis Console results from testing commands: Test, RxCheck, RxTest, Ver, Nver, Receivers
     * Davis Command category: Testing
     */
    private String testingRawText;

    /**
     * Holds Controller-interpreted Davis Console results from testing commands: Test,
     * RxCheck, RxTest, Ver, Nver, Receivers
     * Davis Command category: Testing
     */
    private String testingFriendlyText;

    /**
     * Holds description of the current Davis Testing command.
     * Davis Command category: Testing
     */
    private String testingDescription;

    /**
     * Identifies the command that generated the ViewDTO object.
     */
    private String lastCommandSent;

    /**
     * Carries an error message, if any, to the view.
     */
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getLastCommandSent() {
        return lastCommandSent;
    }


    public void setLastCommandSent(String lastCommandSent) {
        this.lastCommandSent = lastCommandSent;
    }

    public void setCurrentDataText(String currentDataText) {
        System.out.println("ViewDTO setCurrentDataText called with " + currentDataText);
        this.currentDataText = currentDataText;
    }

    public String getCurrentDataText() {
        return currentDataText;
    }

    public String getCurrentDataEvalText() {
        return currentDataEvalText;
    }

    public void setCurrentDataEvalText(String currentDataEvalText) {
        System.out.println("ViewDTO setCurrentDataEvalText called with " + currentDataEvalText);

        this.currentDataEvalText = currentDataEvalText;
    }

    public String getTestingRawText() {
        return testingRawText;
    }

    public void setTestingRawText(String testingRawText) {
        System.out.println("ViewDTO setTestingRawText called with " + testingRawText);
        this.testingRawText = testingRawText;
    }

    public String getTestingFriendlyText() {
        return testingFriendlyText;
    }

    public void setTestingFriendlyText(String testingFriendlyText) {
        this.testingFriendlyText = testingFriendlyText;
    }

    public String getTestingDescription() {
        return testingDescription;
    }

    public void setTestingDescription(String testingDescription) {
        System.out.println("ViewDTO testingDescription called with " + testingDescription);
        this.testingDescription = testingDescription;
    }
}
