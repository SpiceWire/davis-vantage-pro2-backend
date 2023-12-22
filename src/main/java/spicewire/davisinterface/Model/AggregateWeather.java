package spicewire.davisinterface.Model;

import java.util.Date;

/**
 * This class is intended as a Data Transfer Object (DTO) between the backend and the view.
 * It facilitates serializing weather data that is calculated from a database.
 * StartDate of aggregate data is assumed to be the date of the call.
 */
public class AggregateWeather {


private double temperatureHigh;
private double temperatureLow;
private double temperatureAvg;
private double temperatureChange;
private double humidityAvg;
private double humidityLow;
private double humidityHigh;
private double totalRain;
private double barometerHigh;
private double barometerLow;
private double barometerDerivative;
private double windHigh;
private double windAvg;

    public double getAccumulatedRain() {
        return accumulatedRain;
    }

    public void setAccumulatedRain(double accumulatedRain) {
        this.accumulatedRain = accumulatedRain;
    }

    private double accumulatedRain;


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

    public double getTemperatureChange() {
        return temperatureChange;
    }

    public void setTemperatureChange(double temperatureChange) {
        this.temperatureChange = temperatureChange;
    }

    public double getHumidityAvg() {
        return humidityAvg;
    }

    public void setHumidityAvg(double humidityAvg) {
        this.humidityAvg = humidityAvg;
    }

    public double getHumidityLow() {
        return humidityLow;
    }

    public void setHumidityLow(double humidityLow) {
        this.humidityLow = humidityLow;
    }

    public double getHumidityHigh() {
        return humidityHigh;
    }

    public void setHumidityHigh(double humidityHigh) {
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

    public double getWindHigh() {
        return windHigh;
    }

    public void setWindHigh(double windHigh) {
        this.windHigh = windHigh;
    }

    public double getWindAvg() {
        return windAvg;
    }

    public void setWindAvg(double windAvg) {
        this.windAvg = windAvg;
    }


}
