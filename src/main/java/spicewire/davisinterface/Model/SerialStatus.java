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
        this.commPortList = serialPortsAsString(commPortList);
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

    /**
     * Helper method for the constructor. Makes a String[] from a SerialPort[].
     * @param spArr Array of SerialPorts from the operating system.
     * @return String array of serial port names stripped of non-alphanumerics.
     */
    String[] serialPortsAsString (SerialPort[] spArr){
        String[] spNameArray = new String[spArr.length];
        System.out.println("SerialStatus: Com port length: " + spArr.length);
        if (spNameArray.length==0){
            System.out.println("no com ports found");
            return new String[]{"NO_COM_PORTS_FOUND"};
        }
        StringBuilder friendlySPName = new StringBuilder();
        for (String sp : spNameArray) {  //strips system com port name of leading slashes, etc
            char c;
            for (int i = 0; i < sp.length(); i++) {
                c = sp.charAt(i);
                if (Character.isAlphabetic(c) || Character.isDigit(c)){
                    friendlySPName.append(c);
                }
            }
            friendlySPName.append(" ");
            System.out.println("SerialStatus: Cumulative console com port list is: " + friendlySPName);
        }
        String[] comPortList = friendlySPName.toString().split(" ");
        System.out.println("SerialStatus: ports are: " + Arrays.toString(comPortList));
        return comPortList;
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
