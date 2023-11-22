package spicewire.davisinterface.Model;

/**
 * This class is used to transfer commPort settings from the View to the Controller.
 */
public class SerialSettingsDTO {
    Integer baud;
    Integer dataBits;
    Integer stopBits;
    Integer parity;
    Integer commPortIndex;
    Integer timeoutMode;
    Integer readTimeout;
    Integer writeTimeout;
    String[] commPortList;


    public SerialSettingsDTO(Integer baud, Integer dataBits, Integer stopBits,
                             Integer parity, Integer commPortIndex, Integer timeoutMode,
                             Integer readTimeout, Integer writeTimeout, String[] commPortList) {
        this.baud = baud;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
        this.parity = parity;
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

    public Integer getDataBits() {
        return dataBits;
    }

    public void setDataBits(Integer dataBits) {
        this.dataBits = dataBits;
    }

    public Integer getStopBits() {
        return stopBits;
    }

    public void setStopBits(Integer stopBits) {
        this.stopBits = stopBits;
    }

    public Integer getParity() {
        return parity;
    }

    public void setParity(Integer parity) {
        this.parity = parity;
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
