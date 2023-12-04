package spicewire.davisinterface.View;

/**
 * Data Transfer Object for the View's text fields. Particularly relevant for Davis Command categories of Testing and
 * Current Data.
 */
public class ViewDTO {
    public ViewDTO(){}

    /**
     * Holds binary results from Davis Loop and LPS commands.
     * Davis Command category: Current Data
     */
    private static String currentDataText;

    /**
     * Holds Controller's response to Loop and LPS commands.
     * Davis Command category: Current Data
     */
    private static String currentDataEvalText;

    /**
     * Holds raw Davis Console results from testing commands: Test, RxCheck, RxTest, Ver, Nver, Receivers
     * Davis Command category: Testing
     */
    private static String testingRawText;

    /**
     * Holds Controller-interpreted Davis Console results from testing commands: Test,
     * RxCheck, RxTest, Ver, Nver, Receivers
     * Davis Command category: Testing
     */
    private static String testingFriendlyText;

    /**
     * Holds description of the current Davis Testing command.
     * Davis Command category: Testing
     */
    private static String testingDescription;

    private static String lastCommandSent;

    private static String errorMessage;

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static void setErrorMessage(String errorMessage) {
        ViewDTO.errorMessage = errorMessage;
    }

    public static void setErrorMessage(int errorMessageNumber) {
        ViewDTO.errorMessage = getErrorMessage(errorMessageNumber);
    }
    public static String getLastCommandSent() {
        return lastCommandSent;
    }

    public static final String WRONG_TYPE = "The Command sent was the wrong type for the operation.";
    public static final String UNKNOWN_TYPE = "The Command sent did not use a valid word, was unknown, " +
            "or not yet implemented";
    public static final String WRONG_PARAMS = "The Command sent had missing or invalid parameters.";
    public static final String NO_CONNECTION = "The console did not have a working connection to the backend.";
    public static final String UNKNOWN = "An unexpected error occurred.";
    public static final String CRC_ERROR = "An unresolvable CRC error occurred.";

    public static void setLastCommandSent(String lastCommandSent) {
        ViewDTO.lastCommandSent = lastCommandSent;
    }

    public static void setCurrentDataText(String currentDataText) {
        ViewDTO.currentDataText = currentDataText;
    }

    public static String getCurrentDataText() {
        return currentDataText;
    }

    public static String getCurrentDataEvalText() {
        return currentDataEvalText;
    }

    public static void setCurrentDataEvalText(String currentDataEvalText) {
        ViewDTO.currentDataEvalText = currentDataEvalText;
    }

    /**
     * Uses an index number to include an error message to the ViewDTO.
     * Index is:       0 WRONG_TYPE,
     *                 1 UNKNOWN_TYPE,
     *                 2 WRONG_PARAMS,
     *                 3 NO_CONNECTION,
     *                 4 UNKNOWN,
     * @param errorNumber
     * @return
     */
    public static String getErrorMessage(int errorNumber){
        String[] errorMsgAry = new String[] {
                WRONG_TYPE,
                UNKNOWN_TYPE,
                WRONG_PARAMS,
                NO_CONNECTION,
                UNKNOWN,
        };
        return errorMsgAry[errorNumber];
    }

    public static String getTestingRawText() {
        return testingRawText;
    }

    public static void setTestingRawText(String testingRawText) {
        ViewDTO.testingRawText = testingRawText;
    }

    public static String getTestingFriendlyText() {
        return testingFriendlyText;
    }

    public static void setTestingFriendlyText(String testingFriendlyText) {
        ViewDTO.testingFriendlyText = testingFriendlyText;
    }

    public static String getTestingDescription() {
        return testingDescription;
    }

    public static void setTestingDescription(String testingDescription) {
        ViewDTO.testingDescription = testingDescription;
    }
}
