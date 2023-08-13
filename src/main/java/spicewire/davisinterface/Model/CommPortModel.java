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
        cpmString.append("dataBits:" + getDataBits());
        cpmString.append("stopBits:" + getStopBits());
        cpmString.append("parity:" + getParity());
        cpmString.append("commParamsSet" + isCommParamsSet());
        cpmString.append("last updated by: " + getUpdatedBy());
        return cpmString.toString();
    }


    /**
     * List of available com ports. Generated by Serial Port class.
     */
    private static String[] commPortList;

    /**
     * Index number of user-selected com port in the com port list. Set by View.
     */
    private static int comPortIndex;

    /**
     * Name of user-selected com port (e.g. "COM4"). Set by view, also generated by Serial Port class.
     */
    private static String commPort;

    /**
     * Description of user-selected com port. Generated by Serial Port class.
     */
    private static String commPortDescription;

    /**
     * System path of user-selected com port. Generated by Serial Port class.
     */
    private static String commPortPath;

    /**
     * Baud rate of com port after being set. Selected by View before being set and
     * reported by Serial Port after being set.
     * 1200, 2400, 4800, 9600, or 19200. Davis default:19200.
     */
    private static int baudRate;

    /**
     * Data bits of com port. Selected by View before being set and reported by Serial Port
     * after being set.
     * 7 or 8. Davis default: 8
     */
    private static int dataBits;

    /**
     * Index of selected bits of com port. Selected by View before being set and reported by Serial Port
     * after being set.
     *  0, 1, 2, 3 (index from list {0, 1, 1.5, 2}) Davis default: 1
     */
    private static int stopBits;


    /**
     * Index of selected parity of com port. Selected by View before being set and reported by Serial Port
     * after being set.
     * 0, 1, 2 (index from list {no, even, odd}  Davis default: 1
     */
    private static int parity;

    /**
     * True if com port params have been set.
     */
    private static boolean commParamsSet;

    /**
     * Name of last module to update the CommPortModel. Updated by View, Controller, Serial Port.
     * @return String
     */
    private static String updatedBy;

    public static String getUpdatedBy() {
        return updatedBy;
    }

    public static void setUpdatedBy(String updatedBy) {
        CommPortModel.updatedBy = updatedBy;
    }

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
