package spicewire.davisinterface.Model;

import org.springframework.stereotype.Component;

/**
 * This class holds variables of the Davis Vantage Pro 2 console. The DisplayWeather subclass
 * holds the variables necessary to display current weather in imitation of the Davis console.
 * It also contains the firmware number and firmware date.
 * It is planned that this class will also contain alarms.
 */

public class DavisVP2 {
    public static DavisVP2 davisVP2(){
    return null;
}
    private static String versionNumber;
    private static String versionDate;
    public static int timeoutMode;
    public static int writeTimeout;
    public static int readTimeout;
    public static int alarms;
    public static int calibration;
    public static CommPortModel commPortModel;


    public static String getVersionDate() {
        return versionDate;
    }

    public static String getVersionNumber() {
        return versionNumber;
    }

    public static void setVersionNumber(String versionNumber) {
        DavisVP2.versionNumber = versionNumber;
    }

    public static void setVersionDate(String versionDate) {
        DavisVP2.versionDate = versionDate;
    }







}
