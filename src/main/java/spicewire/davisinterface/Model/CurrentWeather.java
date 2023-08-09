package spicewire.davisinterface.Model;

import java.time.LocalDate;

/**
 * This class contains variables necessary to emulate the display of the Vantage Pro 2 console.
 * The values are drawn from the database and serialized.
 */

public class CurrentWeather {

    private Double outsideTemperature;
    private Integer outsideHumidity;
    private Double insideTemperature;
    private Integer insideHumidity;
    private int windSpeed;
    private int windDirection;
    private int barTrend;
    private Double barometer;
    private Integer forecastIcon;
    private Double dayRain;
    private Double stormRain;
    private Double rainRate;
    private int heatIndex;
    private int windChill;
    private int tenMinAvgWindSpeed;
    private int twoMinAvgWindSpeed;
    private Double tenMinuteWindGust;
    private int windDirectionForTheTenMinuteWindGust;
    private int dewPoint;
    private int thswIndex;
    private Double extraTemperature1;
    private Double extraTemperature2;
    private Double extraTemperature3;
    private Double extraTemperature4;
    private Double extraTemperature5;
    private Double extraTemperature6;
    private Double extraTemperature7;
    private Double soilTemperature1;
    private Double soilTemperature2;
    private Double soilTemperature3;
    private Double soilTemperature4;
    private Double leafTemperature1;
    private Double leafTemperature2;
    private Double leafTemperature3;
    private Double leafTemperature4;
    private Integer extraHumidity1;
    private Integer extraHumidity2;
    private Integer extraHumidity3;
    private Integer extraHumidity4;
    private Integer extraHumidity5;
    private Integer extraHumidity6;
    private Integer extraHumidity7;
    private Integer uv;
    private Integer solarRadiation;
    private LocalDate startDateOfCurrentStorm;
    private Double monthRain;
    private Double yearRain;
    private Double soilMoisture1;
    private Double soilMoisture2;
    private Double soilMoisture3;
    private Double soilMoisture4;
    private Double leafWetness1;
    private Double leafWetness2;
    private Double leafWetness3;
    private Double leafWetness4;





    public CurrentWeather() {
    }

    public CurrentWeather(Double outsideTemperature, int outsideHumidity, Double insideTemperature,
                          int insideHumidity, int windSpeed, int windDirection, int barTrend,
                          Double barometer, int forecastIcon, Double dayRain, Double stormRain,
                          Double rainRate, int heatIndex, int windChill, Double tenMinuteWindGust) {
        this.outsideTemperature = outsideTemperature;this.outsideHumidity = outsideHumidity;
        this.insideTemperature = insideTemperature;
        this.insideHumidity = insideHumidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.barTrend = barTrend;
        this.barometer = barometer;
        this.forecastIcon = forecastIcon;
        this.dayRain = dayRain;
        this.stormRain = stormRain;
        this.rainRate = rainRate;
        this.heatIndex = heatIndex;
        this.windChill = windChill;
        this.tenMinuteWindGust = tenMinuteWindGust;
    }

    public double getTenMinuteWindGust() {
        return tenMinuteWindGust;
    }

    public void setTenMinuteWindGust(Double windGust) {
        this.tenMinuteWindGust = windGust;
    }

    public double getOutsideTemperature() {
        return outsideTemperature;
    }

    public void setOutsideTemperature(Double outsideTemperature) {
        this.outsideTemperature = outsideTemperature;
    }

    public Integer getOutsideHumidity() {
        return outsideHumidity;
    }

    public void setOutsideHumidity(Integer outsideHumidity) {
        this.outsideHumidity = outsideHumidity;
    }

    public double getInsideTemperature() {
        return insideTemperature;
    }

    public void setInsideTemperature(Double insideTemperature) {
        this.insideTemperature = insideTemperature;
    }

    public Integer getInsideHumidity() {
        return insideHumidity;
    }

    public void setInsideHumidity(Integer insideHumidity) {
        this.insideHumidity = insideHumidity;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public int getBarTrend() {
        return barTrend;
    }

    public void setBarTrend(int barTrend) {
        this.barTrend = barTrend;
    }

    public double getBarometer() {
        return barometer;
    }

    public void setBarometer(Double barometer) {
        this.barometer = barometer;
    }

    public int getForecastIcon() {
        return forecastIcon;
    }

    public void setForecastIcon(int forecastIcon) {
        this.forecastIcon = forecastIcon;
    }

    public double getDayRain() {
        return dayRain;
    }

    public void setDayRain(Double dayRain) {
        this.dayRain = dayRain;
    }

    public double getStormRain() {
        return stormRain;
    }

    public void setStormRain(Double stormRain) {
        this.stormRain = stormRain;
    }

    public double getRainRate() {
        return rainRate;
    }

    public void setRainRate(Double rainRate) {
        this.rainRate = rainRate;
    }

    public int getHeatIndex() {
        return heatIndex;
    }

    public void setHeatIndex(int heatIndex) {
        this.heatIndex = heatIndex;
    }

    public int getWindChill() {
        return windChill;
    }

    public void setWindChill(int windChill) {
        this.windChill = windChill;
    }


}
