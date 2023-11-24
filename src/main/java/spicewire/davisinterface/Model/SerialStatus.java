package spicewire.davisinterface.Model;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Serves as a DTO from the backend to the View to indicate the current actual status
 * and settings of the Serial Port.
 *
 */
@Component
public class SerialStatus {
    String systemPortName;
    String[] commPortList;
    int commPortIndex;
    String commPortDescription;
    String commPortPath;
    Integer baudRate;
    Integer dataBits;
    Integer stopBits;
    Integer parity;
    Integer writeTimeout;
    Integer readTimeout;

    public SerialStatus(String systemPortName, String[] commPortList,
                        String commPortDescription, String commPortPath, Integer baudRate, Integer dataBits,
                        Integer stopBits, Integer parity) {
        this.systemPortName = systemPortName;
        this.commPortList = commPortList;
        this.commPortIndex = findPortIndex(commPortList, systemPortName);
        this.commPortDescription = commPortDescription;
        this.commPortPath = commPortPath;
        this.baudRate = baudRate;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
        this.parity = parity;
    }

    public SerialStatus(String systemPortName, SerialPort[] commPortList,  String commPortDescription,
                        String commPortPath, Integer baudRate, Integer dataBits, Integer stopBits, Integer parity,
                         Integer writeTimeout, Integer readTimeout) {
        this.systemPortName = systemPortName;
        this.commPortList = serialPortsAsString(commPortList);

        this.commPortDescription = commPortDescription;
        this.commPortPath = commPortPath;
        this.baudRate = baudRate;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
        this.parity = parity;
        this.writeTimeout = writeTimeout;
        this.readTimeout = readTimeout;
        this.commPortIndex = findPortIndex(serialPortsAsString(commPortList), systemPortName);
    }

    @Override
    public String toString() {
        return "SerialStatus{" +
                "systemPortName='" + systemPortName + '\'' +
                ", commPortList=" + Arrays.toString(commPortList) +
                ", commPortDescription='" + commPortDescription + '\'' +
                ", commPortPath='" + commPortPath + '\'' +
                ", commPortIndex= " + commPortIndex +
                ", baudRate=" + baudRate +
                ", dataBits=" + dataBits +
                ", stopBits=" + stopBits +
                ", parity=" + parity +
                ", writeTimeout=" + writeTimeout +
                ", readTimeout=" + readTimeout +
                '}';
    }

    /**
     * Helper method for the constructor. Makes a String[] from a SerialPort[].
     * @param spArr Array of SerialPorts from the operating system.
     * @return String array of serial port names stripped of non-alphanumerics.
     */
    public String[] serialPortsAsString (SerialPort[] spArr){
        String[] spNameArray = new String[spArr.length];

        System.out.println("SerialStatus: Com port length: " + spArr.length);
        if (spNameArray.length==0){
            System.out.println("no com ports found");
            return new String[]{"NO_COM_PORTS_FOUND"};
        }
        for (int i = 0; i <= spArr.length - 1; i++) {
            spNameArray[i] = spArr[i].getSystemPortPath();
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
        String[] commPortList = friendlySPName.toString().split(" ");
        System.out.println("SerialStatus: ports are: " + Arrays.toString(commPortList));
        return commPortList;
    }

    /**
     * Helper method for the constructor. Finds the index of the active comm port in
     * the commPortList.
     * @return integer = -1 => no comm ports listed
     *                 = index of active comm port in com port list.
     */
    private int findPortIndex(String[] portList, String port) {
        System.out.println("Find Port index triggered");
//        System.out.println("commportlist: " + this.commPortList.toString());
//        System.out.println("portName: " + this.systemPortName);
//        String[] portList = this.commPortList;
//        String port = this.systemPortName;
        int indexNum = 0;
        if(portList.length==0){
            indexNum= -1;
        }
        else if (portList.length==1) {
            indexNum = 0;
        }
        else indexNum = Arrays.asList(portList).indexOf(port);
        System.out.println("indexnum is " + indexNum);

        return indexNum;

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
