package spicewire.davisinterface.Model;

import java.util.Date;

/**
 * This class is intended as a Data Transfer Object (DTO) between the backend and the view.
 * It facilitates serializing weather data that is calculated from a database.
 * StartDate of aggregate data is assumed to be the date of the call.
 */
public class AggregateWeather {

private int duration;
private double temperatureHigh;
private double temperatureLow;
private double temperatureAvg;
private int temperatureChange;
private int humidityAvg;
private int humidityLow;
private int humidityHigh;
private double totalRain;
private double barometerHigh;
private double barometerLow;
private double barometerDerivative;
private int windHigh;
private int windAvg;


    public AggregateWeather() {
    }

    public double getTemperatureHigh() {
        return temperatureHigh;
    }

    public void setTemperatureHigh(double temperatureHigh) {
        this.temperatureHigh = temperatureHigh;
    }

    public double getTemperatureLow() {
        return temperatureLow;
    }

    public void setTemperatureLow(double temperatureLow) {
        this.temperatureLow = temperatureLow;
    }

    public double getTemperatureAvg() {
        return temperatureAvg;
    }

    public void setTemperatureAvg(double temperatureAvg) {
        this.temperatureAvg = temperatureAvg;
    }

    public int getTemperatureChange() {
        return temperatureChange;
    }

    public void setTemperatureChange(int temperatureChange) {
        this.temperatureChange = temperatureChange;
    }

    public int getHumidityAvg() {
        return humidityAvg;
    }

    public void setHumidityAvg(int humidityAvg) {
        this.humidityAvg = humidityAvg;
    }

    public int getHumidityLow() {
        return humidityLow;
    }

    public void setHumidityLow(int humidityLow) {
        this.humidityLow = humidityLow;
    }

    public int getHumidityHigh() {
        return humidityHigh;
    }

    public void setHumidityHigh(int humidityHigh) {
        this.humidityHigh = humidityHigh;
    }

    public double getTotalRain() {
        return totalRain;
    }

    public void setTotalRain(double totalRain) {
        this.totalRain = totalRain;
    }

    public double getBarometerHigh() {
        return barometerHigh;
    }

    public void setBarometerHigh(double barometerHigh) {
        this.barometerHigh = barometerHigh;
    }

    public double getBarometerLow() {
        return barometerLow;
    }

    public void setBarometerLow(double barometerLow) {
        this.barometerLow = barometerLow;
    }

    public double getBarometerDerivative() {
        return barometerDerivative;
    }

    public void setBarometerDerivative(double barometerDerivative) {
        this.barometerDerivative = barometerDerivative;
    }

    public int getWindHigh() {
        return windHigh;
    }

    public void setWindHigh(int windHigh) {
        this.windHigh = windHigh;
    }

    public int getWindAvg() {
        return windAvg;
    }

    public void setWindAvg(int windAvg) {
        this.windAvg = windAvg;
    }


}
