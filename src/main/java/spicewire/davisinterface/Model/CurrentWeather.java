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
    private Integer windSpeed;
    private Integer windDirection;
    private Integer barTrend;
    private Double barometer;
    private Integer forecastIcon;
    private Double dayRain;
    private Double stormRain;
    private Double rainRate;
    private Integer heatIndex;
    private Integer windChill;
    private Integer tenMinAvgWindSpeed;
    private Integer twoMinAvgWindSpeed;
    private Double tenMinuteWindGust;
    private Integer windDirectionForTheTenMinuteWindGust;
    private Integer dewPoInteger;
    private Integer thswIndex;
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

    public CurrentWeather(Double outsideTemperature, Integer outsideHumidity, Double insideTemperature,
                          Integer insideHumidity, Integer windSpeed, Integer windDirection, Integer barTrend,
                          Double barometer, Integer forecastIcon, Double dayRain, Double stormRain,
                          Double rainRate, Integer heatIndex, Integer windChill, Double tenMinuteWindGust) {
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

    public Double getTenMinuteWindGust() {
        return tenMinuteWindGust;
    }

    public void setTenMinuteWindGust(Double windGust) {
        this.tenMinuteWindGust = windGust;
    }

    public Double getOutsideTemperature() {
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

    public Double getInsideTemperature() {
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

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    public Integer getBarTrend() {
        return barTrend;
    }

    public void setBarTrend(Integer barTrend) {
        this.barTrend = barTrend;
    }

    public Double getBarometer() {
        return barometer;
    }

    public void setBarometer(Double barometer) {
        this.barometer = barometer;
    }

    public Integer getForecastIcon() {
        return forecastIcon;
    }

    public void setForecastIcon(Integer forecastIcon) {
        this.forecastIcon = forecastIcon;
    }

    public Double getDayRain() {
        return dayRain;
    }

    public void setDayRain(Double dayRain) {
        this.dayRain = dayRain;
    }

    public Double getStormRain() {
        return stormRain;
    }

    public void setStormRain(Double stormRain) {
        this.stormRain = stormRain;
    }

    public Double getRainRate() {
        return rainRate;
    }

    public void setRainRate(Double rainRate) {
        this.rainRate = rainRate;
    }

    public Integer getHeatIndex() {
        return heatIndex;
    }

    public void setHeatIndex(Integer heatIndex) {
        this.heatIndex = heatIndex;
    }

    public Integer getWindChill() {
        return windChill;
    }

    public void setWindChill(Integer windChill) {
        this.windChill = windChill;
    }


}
