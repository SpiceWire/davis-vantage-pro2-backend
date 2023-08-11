package spicewire.davisinterface.View;

import spicewire.davisinterface.Model.CurrentWeather;

/**
 * Minimal methods needed in the View to interact with the controller.
 */
public interface ViewDao {
    /**
     * View accepts a String array of available com ports. Array must be displayed
     * in order received.
     * @return none
     */
    void populateComPortList(String[] comPortList);

    /**
     * The View returns the index number of the user-selected com port
     * from the com port list supplied by the controller.
     * @return int of index number
     */
    int getComPortIndex();

    /**
     * Returns the baud rate selected by the user. Options are:
     * 1200, 2400, 4800, 9600, 19200. Davis default:19200.
     * @return 1200, 2400, 4800, 9600, or 19200
     */
    int getBaudRate();

    /**
     * Returns the dataBits selected by the user. Options are 7 or 8.
     * Davis default: 8
     * @return 7 0r 8
     */
    int getDataBits();

    /**
     * Returns index of the stopBits selected by the user. Options are 0, 1, 1.5, 2,
     * corresponding to index 0, 1, 2, 3
     * Davis default: 1
     * @return 0, 1, 2, or 3
     */
    int getStopBits();

    /**
     * Returns the index of the parity selected by the user. Options are no, even, odd,
     * corresponding to index 0, 1, 2
     * Davis default: 1
     * @return 0, 1, or 2
     */
    int getParity();

    /**
     * Clears the displayed list of available com ports in preparation for a new list.
     */
    void clearComPortList();

    /**
     * View accepts a boolean to show if com port parameters have been set.
     * @param paramsSet
     */
    void comPortParametersAreSet(boolean paramsSet);


    /**
     * Sends user-selected parameters to the controller so serial port can be set.
     *
     * @param comPortIndex index number of user-selected com port from com port list.
     * @param baud 1200, 2400, 4800, 9600, or 19200 Davis default=19200
     * @param dataBits 7 0r 8. Default 8
     * @param stopBits index 0, 1, 2, 3 corresponding to options are {0, 1, 1.5, 2}. Default 1
     * @param parity index 0, 1, or 2 corresponding to options  {no, even, odd}
     * @param timeoutMode 0 or 1. 0= nonblocking, 1=semiBlocking. Default 0
     * @param writeTimeout In milliseconds. Default 0
     * @param readTimeout In milliseconds. Default 0
     */
    void setComPortParameters(int comPortIndex, int baud, int dataBits, int stopBits,
                              int parity, int timeoutMode, int writeTimeout, int readTimeout);

    /**
     * Sets text area after a Testing Commands (type 1) is sent to the console. Text area contains
     * the results of the test.
     * @param friendlyText String
     */
    void setConsoleFriendlyTextArea(String friendlyText);

    /**
     * Sets text area after a Current Data Command (type 2) is sent to the console. Text area contains
     * binary data.
     */
    void setCurrentDataTextArea();

    /**
     * Sets the text area with a description of the Testing Command being used.
     * @param description
     */
    void setTestDescriptionTextArea(String description);

    /**
     * Sets the text area with the raw data generated from Testing Command.
     * @param rawData
     */
    void setConsoleRawTextArea(String rawData);


    /**
     * Sends a String from the View to the Controller, converts String to Command class, sends it to the Davis console.
     * String must be a Command as described in the Davis manual. Currently accepts the following
     * Commands : test, rxCheck, rxTest, ver, nver, receivers, loop, lps
     * @param commandWord
     */
    void runCommand(String commandWord);

    /**
     * Requests controller to send the current com port settings.
     */
    void getComPortSettings();

   //CurrentWeather getCurrentWeather();
}
