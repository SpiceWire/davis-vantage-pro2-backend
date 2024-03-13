package spicewire.davisinterface.Model;

import java.io.Serializable;


public class StreetAddress implements Serializable {
    private String streetAddress;
    private String zip;
    private String city;
    private String state;

    public void StreetAddress(String streetAddress, String zip, String city, String state) {
        this.streetAddress = streetAddress;
        this.zip = zip;
        this.city = city;
        this.state = state;
    }

    public void StreetAddress() {

    }

    @Override
    public String toString(){
        return getStreetAddress() + " " + getCity() + ", " + getState() + getZip();
}
    public String getStreetAddress() {
        return streetAddress;
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

    public void setState(String state) {
        this.state = state;
    }
}
