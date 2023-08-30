package spicewire.davisinterface.Model;

import com.fazecast.jSerialComm.SerialPort;

import java.util.Arrays;

/**
 * Serves as a DTO to indicate the current actual status and settings of the
 * Serial Port.
 */
public class SerialStatus {
    String systemPortName;
    String[] commPortList;
    SerialPort[]comPortList;
    Integer comPortIndex;
    String commPortDescription;
    String commPortPath;
    Integer baudRate;
    Integer dataBits;
    Integer stopBits;
    Integer parity;
    Integer timeoutMode;
    Integer writeTimeout;
    Integer readTimeout;

    public SerialStatus(String systemPortName, String[] commPortList, Integer comPortIndex,
                        String commPortDescription, String commPortPath, Integer baudRate, Integer dataBits,
                        Integer stopBits, Integer parity) {
        this.systemPortName = systemPortName;
        this.commPortList = commPortList;
        this.comPortIndex = comPortIndex;
        this.commPortDescription = commPortDescription;
        this.commPortPath = commPortPath;
        this.baudRate = baudRate;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
        this.parity = parity;
    }

    public SerialStatus(String systemPortName, SerialPort[] commPortList, Integer comPortIndex, String commPortDescription,
                        String commPortPath, Integer baudRate, Integer dataBits, Integer stopBits, Integer parity,
                        Integer timeoutMode, Integer writeTimeout, Integer readTimeout) {
        this.systemPortName = systemPortName;
        this.commPortList = commPortList;
        this.comPortIndex = comPortIndex;
        this.commPortDescription = commPortDescription;
        this.commPortPath = commPortPath;
        this.baudRate = baudRate;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
        this.parity = parity;
        this.timeoutMode = timeoutMode;
        this.writeTimeout = writeTimeout;
        this.readTimeout = readTimeout;
    }

    String[] serialPortsAsString (SerialPort[] spArr){
        String[] compPortsArray = new String[spArr.length];  //todo should be .length?
        for (int i = 0; i <= spArr.length - 1; i++) {
            compPortsArray[i] = spArr[i].getSystemPortPath();
        }
        String[] comPortList = new String[spArr.length];
        System.out.println("Controller says: Initial Console com port list is: " +  Arrays.toString(serialModel.getSerialPortList()));
        if (spArr.length == 0) {
            CommPortModel.setCommPortList(new String[]{"NO_COM_PORTS_FOUND"});
        }
        StringBuilder friendlySPName = new StringBuilder();
        for (String sp : spArr) {  //strips system com port name of leading slashes, etc
            char c;
            for (int i = 0; i < sp.length(); i++) {
                c = sp.charAt(i);
                if (Character.isAlphabetic(c) || Character.isDigit(c)){
                    friendlySPName.append(c);
                }
            }
            friendlySPName.append(" ");
            System.out.println("Controller says: Final console com port list item is: " + friendlySPName);
        }
        comPortList = friendlySPName.toString().split(" ");
        CommPortModel.setCommPortList(comPortList);
    }
    public Integer getTimeoutMode() {
        return timeoutMode;
    }

    public void setTimeoutMode(Integer timeoutMode) {
        this.timeoutMode = timeoutMode;
    }

    public Integer getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Integer writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public SerialStatus() {
    }

    public String getSystemPortName() {
        return systemPortName;
    }

    public void setSystemPortName(String systemPortName) {
        this.systemPortName = systemPortName;
    }

    public String[] getCommPortList() {
        return commPortList;
    }

    public void setCommPortList(String[] commPortList) {
        this.commPortList = commPortList;
    }

    public Integer getComPortIndex() {
        return comPortIndex;
    }

    public void setComPortIndex(Integer comPortIndex) {
        this.comPortIndex = comPortIndex;
    }

    public String getCommPortDescription() {
        return commPortDescription;
    }

    public void setCommPortDescription(String commPortDescription) {
        this.commPortDescription = commPortDescription;
    }

    public String getCommPortPath() {
        return commPortPath;
    }

    public void setCommPortPath(String commPortPath) {
        this.commPortPath = commPortPath;
    }

    public Integer getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(Integer baudRate) {
        this.baudRate = baudRate;
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
}
