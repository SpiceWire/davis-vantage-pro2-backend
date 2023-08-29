package spicewire.davisinterface.Model;

public class SerialSettingsDTO {
    Integer baud;
    Integer dataBits;
    Integer stopBits;
    Integer parity;
    Integer comPortIndex;
    Integer timeoutMode;
    Integer readTimeout;
    Integer writeTimeout;
    String[] comPortList;


    public SerialSettingsDTO(Integer baud, Integer dataBits, Integer stopBits,
                             Integer parity, Integer comPortIndex, Integer timeoutMode,
                             Integer readTimeout, Integer writeTimeout, String[] comPortList) {
        this.baud = baud;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
        this.parity = parity;
        this.comPortIndex = comPortIndex;
        this.timeoutMode = timeoutMode;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
        this.comPortList = comPortList;
    }

    public SerialSettingsDTO(){};

    public SerialSettingsDTO getCurrentSettings() {
        this.baud = CommPortModel.getBaudRate();
        this.dataBits = CommPortModel.getDataBits();
        this.stopBits = CommPortModel.getStopBits();
        this.parity = CommPortModel.getParity();
        this.comPortIndex = CommPortModel.getComPortIndex();
        this.timeoutMode = CommPortModel.getTimeoutMode();
        this.readTimeout = CommPortModel.getReadTimeout();
        this.writeTimeout = CommPortModel.getWriteTimeout();
        this.comPortList = CommPortModel.getCommPortList();
        return this;
    }
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

    public Integer getComPortIndex() {
        return comPortIndex;
    }

    public void setComPortIndex(Integer comPortIndex) {
        this.comPortIndex = comPortIndex;
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

    public String[] getComPortList() {
        return comPortList;
    }

    public void setComPortList(String[] comPortList) {
        this.comPortList = comPortList;
    }
}
