package spicewire.davisinterface.Model;

import java.io.Serializable;
import java.util.Map;


public class StreetAddress implements Serializable {
    private String streetAddress;
    private String zip;
    private String city;
    private String state;
    private String latLon;
    private String latitude;
    private String longitude;
    private String gridpointsURL;
    private String forecastURL;
    private String forecastHourlyURL;
    private String forecastGridDataURL;
    private String activeAlertsByPointURL;

    public  StreetAddress() {
    }

    public StreetAddress(Map<String, String> map){
        this.streetAddress = map.get("streetAddress");
        this.zip = map.get("zip");
        this.city = map.get("city");
        this.state = map.get("state");
        this.latLon = map.get("latLon");
        this.latitude = map.get("latitude");
        this.longitude = map.get("longitude");
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatLon() {
        return latLon;
    }

    public void setLatLon(String latLon) {
        this.latLon = latLon;
    }

    public String getGridpointsURL() {
        return gridpointsURL;
    }

    public void setGridpointsURL(String gridpointsURL) {
        this.gridpointsURL = gridpointsURL;
    }

    public StreetAddress(String streetAddress, String zip, String city, String state) {
        this.streetAddress = streetAddress;
        this.zip = zip;
        this.city = city;
        this.state = state;
    }



    @Override
    public String toString(){
        return getStreetAddress() + " " + getCity() + ", "
                + getState() + ", " + getZip() + ", " + getLatLon();
}
    public String getStreetAddress() {
        return streetAddress;
    }

    public String getActiveAlertsByPointURL() {
        return activeAlertsByPointURL;
    }

    public void setActiveAlertsByPointURL(String activeAlertsByPointURL) {
        this.activeAlertsByPointURL = activeAlertsByPointURL;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public String getForecastURL() {
        return forecastURL;
    }

    public void setForecastURL(String forecastURL) {
        this.forecastURL = forecastURL;
    }

    public String getForecastHourlyURL() {
        return forecastHourlyURL;
    }

    public void setForecastHourlyURL(String forecastHourlyURL) {
        this.forecastHourlyURL = forecastHourlyURL;
    }

    public String getForecastGridDataURL() {
        return forecastGridDataURL;
    }

    public void setForecastGridDataURL(String forecastGridDataURL) {
        this.forecastGridDataURL = forecastGridDataURL;
    }

    public void setState(String state) {
        this.state = state;
    }
}
