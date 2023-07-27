package spicewire.davisinterface.Model;

public class TempHumStation {
    private int stationID;
    private int stationType;
    private boolean lowTempAlarmActivated;
    private boolean highTempAlarmActivated;
    private boolean lowHumAlarmActivated;
    private boolean highHumAlarmActivated;
    private int repeaterID;



    //This model represents an extra temperature and/or humidity station as described in the Davis manual.
    //Davis allows up to 7 extra stations.

/*  From Davis manual:
    List of Station Types (Rev B and VantagePro 2):
    Station Name                Station Type (hex)      "standard" period
    ISS                             0                           1
    Temperature Only Station        1                           4
    Humidity Only Station           2                           4
    Temperature/Humidity Station    3                           4
    Wireless Anemometer Station     4                           1
    Rain Station                    5                           1
    Leaf Station                    6                           1
    Soil Station                    7                           1
    Soil/Leaf Station               8                           1
    SensorLink Station *            9                           1
    No station – OFF                A                           0
            * Vantage Pro2 and Vantage Vue do not support the SensorLink station type
        The humidity sensor number and temperature sensor number fields are only used if the
    transmitter type is a Temperature-Humidity station or a Temperature only station. These fields
    determine how the extra temperature and humidity data values are logged. These fields are
    ignored for other station types.
            */

    //todo add this model to a lookup table

    public TempHumStation(int stationID, int stationType) {
        stationID = this.stationID;
        stationType = this.stationType;
    }

    public TempHumStation(int stationID, int stationType, boolean lowTempAlarmActivated, boolean highTempAlarmActivated,
                          boolean lowHumAlarmActivated, boolean highHumAlarmActivated) {
        this.stationID = stationID;
        this.stationType = stationType;
        this.lowTempAlarmActivated = lowTempAlarmActivated;
        this.highTempAlarmActivated = highTempAlarmActivated;
        this.lowHumAlarmActivated = lowHumAlarmActivated;
        this.highHumAlarmActivated = highHumAlarmActivated;
    }

    public TempHumStation() {
    }

    public int getRepeaterID() {
        return repeaterID;
    }

    public void setRepeaterID(int repeaterID) {
        this.repeaterID = repeaterID;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public int getStationType() {
        return stationType;
    }

    public void setStationType(int stationType) {
        this.stationType = stationType;
    }

    public boolean isLowTempAlarmActivated() {
        return lowTempAlarmActivated;
    }

    public void setLowTempAlarmActivated(boolean lowTempAlarmActivated) {
        this.lowTempAlarmActivated = lowTempAlarmActivated;
    }

    public boolean isHighTempAlarmActivated() {
        return highTempAlarmActivated;
    }

    public void setHighTempAlarmActivated(boolean highTempAlarmActivated) {
        this.highTempAlarmActivated = highTempAlarmActivated;
    }

    public boolean isLowHumAlarmActivated() {
        return lowHumAlarmActivated;
    }

    public void setLowHumAlarmActivated(boolean lowHumAlarmActivated) {
        this.lowHumAlarmActivated = lowHumAlarmActivated;
    }

    public boolean isHighHumAlarmActivated() {
        return highHumAlarmActivated;
    }

    public void setHighHumAlarmActivated(boolean highHumAlarmActivated) {
        this.highHumAlarmActivated = highHumAlarmActivated;
    }
}
