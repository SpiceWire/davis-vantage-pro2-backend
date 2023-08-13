package spicewire.davisinterface.Model;

public class CommPortModel {
    public static void commPortModel(){

    }



    @Override
    public String toString(){
        StringBuilder cpmString = new StringBuilder();
        cpmString.append("commPortList: " + getCommPortList());
        cpmString.append("comPortIndex: " + getComPortIndex());
        cpmString.append("commPort:" + getCommPort());
        cpmString.append("commPortDescription:" + getCommPortDescription());
        cpmString.append("commPortPath:" + getCommPortPath());
        cpmString.append("baudRate:" + getBaudRate());
        cpmString.append("stopBits:" + getStopBits());
        cpmString.append("dataBits:" + getDataBits());
        cpmString.append("parity:" + getParity());
        cpmString.append("commParamsSet" + isCommParamsSet());
        return cpmString.toString();
    }


    /**
     * List of available com ports.
     */
    private static String[] commPortList;

    /**
     * Index number of user-selected com port in the com port list
     */
    private static int comPortIndex;

    /**
     * Name of user-selected com port (e.g. "COM4")
     */
    private static String commPort;

    /**
     * Description of user-selected com port.
     */
    private static String commPortDescription;

    /**
     * System path of user-selected com port.
     */
    private static String commPortPath;

    /**
     * Baud rate of com port after being set.
     */
    private static int baudRate;

    /**
     * Stop bits of com port after being set.
     */
    private static int stopBits;

    /**
     * Data bits of com port after being set.
     */
    private static int dataBits;

    /**
     * Parity of com port after being set.
     */
    private static int parity;

    /**
     * True if com port params have been set.
     */
    private static boolean commParamsSet;

    public static String[] getCommPortList() {
        return commPortList;
    }

    public static void setCommPortList(String[] commPortList) {
        CommPortModel.commPortList = commPortList;
    }

    public static int getComPortIndex() {
        return comPortIndex;
    }

    public static void setComPortIndex(int comPortIndex) {
        CommPortModel.comPortIndex = comPortIndex;
    }

    public static String getCommPort() {
        return commPort;
    }

    public static void setCommPort(String commPort) {
        CommPortModel.commPort = commPort;
    }

    public static String getCommPortDescription() {
        return commPortDescription;
    }

    public static void setCommPortDescription(String commPortDescription) {
        CommPortModel.commPortDescription = commPortDescription;
    }

    public static String getCommPortPath() {
        return commPortPath;
    }

    public static void setCommPortPath(String commPortPath) {
        CommPortModel.commPortPath = commPortPath;
    }

    public static int getBaudRate() {
        return baudRate;
    }

    public static void setBaudRate(int baudRate) {
        CommPortModel.baudRate = baudRate;
    }

    public static int getStopBits() {
        return stopBits;
    }

    public static void setStopBits(int stopBits) {
        CommPortModel.stopBits = stopBits;
    }

    public static int getDataBits() {
        return dataBits;
    }

    public static void setDataBits(int dataBits) {
        CommPortModel.dataBits = dataBits;
    }

    public static int getParity() {
        return parity;
    }

    public static void setParity(int parity) {
        CommPortModel.parity = parity;
    }

    public static boolean isCommParamsSet() {
        return commParamsSet;
    }

    public static void setCommParamsSet(boolean commParamsSet) {
        CommPortModel.commParamsSet = commParamsSet;
    }
}