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
}
