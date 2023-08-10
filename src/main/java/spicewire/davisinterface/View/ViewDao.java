package spicewire.davisinterface.View;

/**
 * Minimal methods needed by the view to interact with the controller.
 */
public interface ViewDao {
    int getComPortIndex();
    int getBaudRate();
    int getParity();
    int getStopBits();
    int getDataBits();
    int getTimeoutMode();
    void setConsoleFriendlyTextArea(String friendlyText);
    void setCurrentDataTextArea();
    void parseDiagnosticsReport(String diagnosticReport)
    int getWriteTimeout();
    int getReadTimeout();
    void setTestDescriptionTextArea(String description);
    void setConsoleRawTextArea(String rawData);

}
