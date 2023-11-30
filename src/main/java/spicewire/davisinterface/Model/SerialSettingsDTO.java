package spicewire.davisinterface.Model;

/**
 * This class is used to transfer commPort settings from the View to the Controller.
 */
public class SerialSettingsDTO {
    Integer baud;
    Integer commPortIndex;
    Integer timeoutMode;
    Integer readTimeout;
    Integer writeTimeout;
    String[] commPortList;


    public SerialSettingsDTO(Integer baud, Integer commPortIndex, Integer timeoutMode,
                             Integer readTimeout, Integer writeTimeout, String[] commPortList) {
        this.baud = baud;
        this.commPortIndex = commPortIndex;
        this.timeoutMode = timeoutMode;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
        this.commPortList = commPortList;
    }

    public SerialSettingsDTO(){};

    public Integer getBaud() {
        return baud;
    }

    public void setBaud(Integer baud) {
        this.baud = baud;
    }

    public Integer getCommPortIndex() {
        return commPortIndex;
    }

    public void setCommPortIndex(Integer commPortIndex) {
        this.commPortIndex = commPortIndex;
    }

    public Integer getTimeoutMode() {
        return timeoutMode;
    }

    public void setTimeoutMode(Integer timeoutMode) {
        this.timeoutMode = timeoutMode;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Integer getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Integer writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public String[] getCommPortList() {
        return commPortList;
    }

    public void setCommPortList(String[] commPortList) {
        this.commPortList = commPortList;
    }
}
