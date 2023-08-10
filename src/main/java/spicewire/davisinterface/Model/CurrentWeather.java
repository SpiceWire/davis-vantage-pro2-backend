package spicewire.davisinterface.Model;

import java.time.LocalDate;

/**
 * This is a Data Transfer Object (DTO). It contains most of the variables
 * related to current weather. It excludes agg
 *
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
    private Integer dewPoint;
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
    private Integer leafWetness1;
    private Integer leafWetness2;
    private Integer leafWetness3;
    private Integer leafWetness4;
    private Double dayET;
    private Double monthET;
    private Double yearET;
    private Double lastFifteenMinRain;
    private Double lastHourRain;
    private Double last24HourRain;





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

    public Double getDayET() {
        return dayET;
    }

    public void setDayET(Double dayET) {
        this.dayET = dayET;
    }

    public Double getMonthET() {
        return monthET;
    }

    public void setMonthET(Double monthET) {
        this.monthET = monthET;
    }

    public Double getYearET() {
        return yearET;
    }

    public void setYearET(Double yearET) {
        this.yearET = yearET;
    }

    public Double getLastFifteenMinRain() {
        return lastFifteenMinRain;
    }

    public void setLastFifteenMinRain(Double lastFifteenMinRain) {
        this.lastFifteenMinRain = lastFifteenMinRain;
    }

    public Double getLastHourRain() {
        return lastHourRain;
    }

    public void setLastHourRain(Double lastHourRain) {
        this.lastHourRain = lastHourRain;
    }

    public Double getLast24HourRain() {
        return last24HourRain;
    }

    public void setLast24HourRain(Double last24HourRain) {
        this.last24HourRain = last24HourRain;
    }

    public Integer getTenMinAvgWindSpeed() {
        return tenMinAvgWindSpeed;
    }

    public void setTenMinAvgWindSpeed(Integer tenMinAvgWindSpeed) {
        this.tenMinAvgWindSpeed = tenMinAvgWindSpeed;
    }

    public Integer getTwoMinAvgWindSpeed() {
        return twoMinAvgWindSpeed;
    }

    public void setTwoMinAvgWindSpeed(Integer twoMinAvgWindSpeed) {
        this.twoMinAvgWindSpeed = twoMinAvgWindSpeed;
    }

    public Integer getWindDirectionForTheTenMinuteWindGust() {
        return windDirectionForTheTenMinuteWindGust;
    }

    public void setWindDirectionForTheTenMinuteWindGust(Integer windDirectionForTheTenMinuteWindGust) {
        this.windDirectionForTheTenMinuteWindGust = windDirectionForTheTenMinuteWindGust;
    }

    public Integer getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Integer dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Integer getThswIndex() {
        return thswIndex;
    }

    public void setThswIndex(Integer thswIndex) {
        this.thswIndex = thswIndex;
    }

    public Double getExtraTemperature1() {
        return extraTemperature1;
    }

    public void setExtraTemperature1(Double extraTemperature1) {
        this.extraTemperature1 = extraTemperature1;
    }

    public Double getExtraTemperature2() {
        return extraTemperature2;
    }

    public void setExtraTemperature2(Double extraTemperature2) {
        this.extraTemperature2 = extraTemperature2;
    }

    public Double getExtraTemperature3() {
        return extraTemperature3;
    }

    public void setExtraTemperature3(Double extraTemperature3) {
        this.extraTemperature3 = extraTemperature3;
    }

    public Double getExtraTemperature4() {
        return extraTemperature4;
    }

    public void setExtraTemperature4(Double extraTemperature4) {
        this.extraTemperature4 = extraTemperature4;
    }

    public Double getExtraTemperature5() {
        return extraTemperature5;
    }

    public void setExtraTemperature5(Double extraTemperature5) {
        this.extraTemperature5 = extraTemperature5;
    }

    public Double getExtraTemperature6() {
        return extraTemperature6;
    }

    public void setExtraTemperature6(Double extraTemperature6) {
        this.extraTemperature6 = extraTemperature6;
    }

    public Double getExtraTemperature7() {
        return extraTemperature7;
    }

    public void setExtraTemperature7(Double extraTemperature7) {
        this.extraTemperature7 = extraTemperature7;
    }

    public Double getSoilTemperature1() {
        return soilTemperature1;
    }

    public void setSoilTemperature1(Double soilTemperature1) {
        this.soilTemperature1 = soilTemperature1;
    }

    public Double getSoilTemperature2() {
        return soilTemperature2;
    }

    public void setSoilTemperature2(Double soilTemperature2) {
        this.soilTemperature2 = soilTemperature2;
    }

    public Double getSoilTemperature3() {
        return soilTemperature3;
    }

    public void setSoilTemperature3(Double soilTemperature3) {
        this.soilTemperature3 = soilTemperature3;
    }

    public Double getSoilTemperature4() {
        return soilTemperature4;
    }

    public void setSoilTemperature4(Double soilTemperature4) {
        this.soilTemperature4 = soilTemperature4;
    }

    public Double getLeafTemperature1() {
        return leafTemperature1;
    }

    public void setLeafTemperature1(Double leafTemperature1) {
        this.leafTemperature1 = leafTemperature1;
    }

    public Double getLeafTemperature2() {
        return leafTemperature2;
    }

    public void setLeafTemperature2(Double leafTemperature2) {
        this.leafTemperature2 = leafTemperature2;
    }

    public Double getLeafTemperature3() {
        return leafTemperature3;
    }

    public void setLeafTemperature3(Double leafTemperature3) {
        this.leafTemperature3 = leafTemperature3;
    }

    public Double getLeafTemperature4() {
        return leafTemperature4;
    }

    public void setLeafTemperature4(Double leafTemperature4) {
        this.leafTemperature4 = leafTemperature4;
    }

    public Integer getExtraHumidity1() {
        return extraHumidity1;
    }

    public void setExtraHumidity1(Integer extraHumidity1) {
        this.extraHumidity1 = extraHumidity1;
    }

    public Integer getExtraHumidity2() {
        return extraHumidity2;
    }

    public void setExtraHumidity2(Integer extraHumidity2) {
        this.extraHumidity2 = extraHumidity2;
    }

    public Integer getExtraHumidity3() {
        return extraHumidity3;
    }

    public void setExtraHumidity3(Integer extraHumidity3) {
        this.extraHumidity3 = extraHumidity3;
    }

    public Integer getExtraHumidity4() {
        return extraHumidity4;
    }

    public void setExtraHumidity4(Integer extraHumidity4) {
        this.extraHumidity4 = extraHumidity4;
    }

    public Integer getExtraHumidity5() {
        return extraHumidity5;
    }

    public void setExtraHumidity5(Integer extraHumidity5) {
        this.extraHumidity5 = extraHumidity5;
    }

    public Integer getExtraHumidity6() {
        return extraHumidity6;
    }

    public void setExtraHumidity6(Integer extraHumidity6) {
        this.extraHumidity6 = extraHumidity6;
    }

    public Integer getExtraHumidity7() {
        return extraHumidity7;
    }

    public void setExtraHumidity7(Integer extraHumidity7) {
        this.extraHumidity7 = extraHumidity7;
    }

    public Integer getUv() {
        return uv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public Integer getSolarRadiation() {
        return solarRadiation;
    }

    public void setSolarRadiation(Integer solarRadiation) {
        this.solarRadiation = solarRadiation;
    }

    public LocalDate getStartDateOfCurrentStorm() {
        return startDateOfCurrentStorm;
    }

    public void setStartDateOfCurrentStorm(LocalDate startDateOfCurrentStorm) {
        this.startDateOfCurrentStorm = startDateOfCurrentStorm;
    }

    public Double getMonthRain() {
        return monthRain;
    }

    public void setMonthRain(Double monthRain) {
        this.monthRain = monthRain;
    }

    public Double getYearRain() {
        return yearRain;
    }

    public void setYearRain(Double yearRain) {
        this.yearRain = yearRain;
    }

    public Double getSoilMoisture1() {
        return soilMoisture1;
    }

    public void setSoilMoisture1(Double soilMoisture1) {
        this.soilMoisture1 = soilMoisture1;
    }

    public Double getSoilMoisture2() {
        return soilMoisture2;
    }

    public void setSoilMoisture2(Double soilMoisture2) {
        this.soilMoisture2 = soilMoisture2;
    }

    public Double getSoilMoisture3() {
        return soilMoisture3;
    }

    public void setSoilMoisture3(Double soilMoisture3) {
        this.soilMoisture3 = soilMoisture3;
    }

    public Double getSoilMoisture4() {
        return soilMoisture4;
    }

    public void setSoilMoisture4(Double soilMoisture4) {
        this.soilMoisture4 = soilMoisture4;
    }

    public Integer getLeafWetness1() {
        return leafWetness1;
    }

    public void setLeafWetness1(Integer leafWetness1) {
        this.leafWetness1 = leafWetness1;
    }

    public Integer getLeafWetness2() {
        return leafWetness2;
    }

    public void setLeafWetness2(Integer leafWetness2) {
        this.leafWetness2 = leafWetness2;
    }

    public Integer getLeafWetness3() {
        return leafWetness3;
    }

    public void setLeafWetness3(Integer leafWetness3) {
        this.leafWetness3 = leafWetness3;
    }

    public Integer getLeafWetness4() {
        return leafWetness4;
    }

    public void setLeafWetness4(Integer leafWetness4) {
        this.leafWetness4 = leafWetness4;
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
