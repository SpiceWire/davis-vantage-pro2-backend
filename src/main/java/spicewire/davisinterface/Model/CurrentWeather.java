package spicewire.davisinterface.Model;

import java.time.LocalDate;

/**
 * CurrentWeather is a Data Transfer Object (DTO). It contains variables
 * related to current weather and aggregate data that is
 *  incidentally collected from a DavisVP2 console as part of
 *  a Davis Loop or LPS reading (e.g. monthRain).
 *  It excludes:
 *    1) aggregated data that must be calculated from the database
 *         (e.g. average temp CurrentWeather week),
 *    2) calibration information
 *    3) alarms
 *    4) firmware info
 */

public class CurrentWeather {

    private static Double outsideTemperature;
    private static Integer outsideHumidity;
    private static Double insideTemperature;
    private static Integer insideHumidity;
    private static Integer windSpeed;
    private static Integer windDirection;
    private static Integer barTrend;
    private static Double barometer;
    private static Integer forecastIcon;
    private static Double dayRain;
    private static Double stormRain;
    private static Double rainRate;
    private static Integer heatIndex;
    private static Integer windChill;
    private static Integer tenMinAvgWindSpeed;
    private static Integer twoMinAvgWindSpeed;
    private static Double tenMinuteWindGust;
    private static Integer windDirectionForTheTenMinuteWindGust;
    private static Integer dewPoint;
    private static Integer thswIndex;
    private static Double extraTemperature1;
    private static Double extraTemperature2;
    private static Double extraTemperature3;
    private static Double extraTemperature4;
    private static Double extraTemperature5;
    private static Double extraTemperature6;
    private static Double extraTemperature7;
    private static Double soilTemperature1;
    private static Double soilTemperature2;
    private static Double soilTemperature3;
    private static Double soilTemperature4;
    private static Double leafTemperature1;
    private static Double leafTemperature2;
    private static Double leafTemperature3;
    private static Double leafTemperature4;
    private static Integer extraHumidity1;
    private static Integer extraHumidity2;
    private static Integer extraHumidity3;
    private static Integer extraHumidity4;
    private static Integer extraHumidity5;
    private static Integer extraHumidity6;
    private static Integer extraHumidity7;
    private static Integer uv;
    private static Integer solarRadiation;
    private static LocalDate startDateOfCurrentStorm;
    private static Double monthRain;
    private static Double yearRain;
    private static Double soilMoisture1;
    private static Double soilMoisture2;
    private static Double soilMoisture3;
    private static Double soilMoisture4;
    private static Integer leafWetness1;
    private static Integer leafWetness2;
    private static Integer leafWetness3;
    private static Integer leafWetness4;
    private static Double dayET;
    private static Double monthET;
    private static Double yearET;
    private static Double lastFifteenMinRain;
    private static Double lastHourRain;
    private static Double last24HourRain;





    public CurrentWeather() {
    }

    public CurrentWeather(Double outsideTemperature, Integer outsideHumidity, Double insideTemperature,
                          Integer insideHumidity, Integer windSpeed, Integer windDirection, Integer barTrend,
                          Double barometer, Integer forecastIcon, Double dayRain, Double stormRain,
                          Double rainRate, Integer heatIndex, Integer windChill, Double tenMinuteWindGust) {
        CurrentWeather.outsideTemperature = outsideTemperature;CurrentWeather.outsideHumidity = outsideHumidity;
        CurrentWeather.insideTemperature = insideTemperature;
        CurrentWeather.insideHumidity = insideHumidity;
        CurrentWeather.windSpeed = windSpeed;
        CurrentWeather.windDirection = windDirection;
        CurrentWeather.barTrend = barTrend;
        CurrentWeather.barometer = barometer;
        CurrentWeather.forecastIcon = forecastIcon;
        CurrentWeather.dayRain = dayRain;
        CurrentWeather.stormRain = stormRain;
        CurrentWeather.rainRate = rainRate;
        CurrentWeather.heatIndex = heatIndex;
        CurrentWeather.windChill = windChill;
        CurrentWeather.tenMinuteWindGust = tenMinuteWindGust;
    }

    public static Double getDayET() {
        return dayET;
    }

    public static void setDayET(Double dayET) {
        CurrentWeather.dayET = dayET;
    }

    public static Double getMonthET() {
        return monthET;
    }

    public static void setMonthET(Double monthET) {
        CurrentWeather.monthET = monthET;
    }

    public static Double getYearET() {
        return yearET;
    }

    public static void setYearET(Double yearET) {
        CurrentWeather.yearET = yearET;
    }

    public static Double getLastFifteenMinRain() {
        return lastFifteenMinRain;
    }

    public static void setLastFifteenMinRain(Double lastFifteenMinRain) {
        CurrentWeather.lastFifteenMinRain = lastFifteenMinRain;
    }

    public static Double getLastHourRain() {
        return lastHourRain;
    }

    public static void setLastHourRain(Double lastHourRain) {
        CurrentWeather.lastHourRain = lastHourRain;
    }

    public static Double getLast24HourRain() {
        return last24HourRain;
    }

    public static void setLast24HourRain(Double last24HourRain) {
        CurrentWeather.last24HourRain = last24HourRain;
    }

    public static Integer getTenMinAvgWindSpeed() {
        return tenMinAvgWindSpeed;
    }

    public static void setTenMinAvgWindSpeed(Integer tenMinAvgWindSpeed) {
        CurrentWeather.tenMinAvgWindSpeed = tenMinAvgWindSpeed;
    }

    public static Integer getTwoMinAvgWindSpeed() {
        return twoMinAvgWindSpeed;
    }

    public static void setTwoMinAvgWindSpeed(Integer twoMinAvgWindSpeed) {
        CurrentWeather.twoMinAvgWindSpeed = twoMinAvgWindSpeed;
    }

    public static Integer getWindDirectionForTheTenMinuteWindGust() {
        return windDirectionForTheTenMinuteWindGust;
    }

    public static void setWindDirectionForTheTenMinuteWindGust(Integer windDirectionForTheTenMinuteWindGust) {
        CurrentWeather.windDirectionForTheTenMinuteWindGust = windDirectionForTheTenMinuteWindGust;
    }

    public static Integer getDewPoint() {
        return dewPoint;
    }

    public static void setDewPoint(Integer dewPoint) {
        CurrentWeather.dewPoint = dewPoint;
    }

    public static Integer getThswIndex() {
        return thswIndex;
    }

    public static void setThswIndex(Integer thswIndex) {
        CurrentWeather.thswIndex = thswIndex;
    }

    public static Double getExtraTemperature1() {
        return extraTemperature1;
    }

    public static void setExtraTemperature1(Double extraTemperature1) {
        CurrentWeather.extraTemperature1 = extraTemperature1;
    }

    public static Double getExtraTemperature2() {
        return extraTemperature2;
    }

    public static void setExtraTemperature2(Double extraTemperature2) {
        CurrentWeather.extraTemperature2 = extraTemperature2;
    }

    public static Double getExtraTemperature3() {
        return extraTemperature3;
    }

    public static void setExtraTemperature3(Double extraTemperature3) {
        CurrentWeather.extraTemperature3 = extraTemperature3;
    }

    public static Double getExtraTemperature4() {
        return extraTemperature4;
    }

    public static void setExtraTemperature4(Double extraTemperature4) {
        CurrentWeather.extraTemperature4 = extraTemperature4;
    }

    public static Double getExtraTemperature5() {
        return extraTemperature5;
    }

    public static void setExtraTemperature5(Double extraTemperature5) {
        CurrentWeather.extraTemperature5 = extraTemperature5;
    }

    public static Double getExtraTemperature6() {
        return extraTemperature6;
    }

    public static void setExtraTemperature6(Double extraTemperature6) {
        CurrentWeather.extraTemperature6 = extraTemperature6;
    }

    public static Double getExtraTemperature7() {
        return extraTemperature7;
    }

    public static void setExtraTemperature7(Double extraTemperature7) {
        CurrentWeather.extraTemperature7 = extraTemperature7;
    }

    public static Double getSoilTemperature1() {
        return soilTemperature1;
    }

    public static void setSoilTemperature1(Double soilTemperature1) {
        CurrentWeather.soilTemperature1 = soilTemperature1;
    }

    public static Double getSoilTemperature2() {
        return soilTemperature2;
    }

    public static void setSoilTemperature2(Double soilTemperature2) {
        CurrentWeather.soilTemperature2 = soilTemperature2;
    }

    public static Double getSoilTemperature3() {
        return soilTemperature3;
    }

    public static void setSoilTemperature3(Double soilTemperature3) {
        CurrentWeather.soilTemperature3 = soilTemperature3;
    }

    public static Double getSoilTemperature4() {
        return soilTemperature4;
    }

    public static void setSoilTemperature4(Double soilTemperature4) {
        CurrentWeather.soilTemperature4 = soilTemperature4;
    }

    public static Double getLeafTemperature1() {
        return leafTemperature1;
    }

    public static void setLeafTemperature1(Double leafTemperature1) {
        CurrentWeather.leafTemperature1 = leafTemperature1;
    }

    public static Double getLeafTemperature2() {
        return leafTemperature2;
    }

    public static void setLeafTemperature2(Double leafTemperature2) {
        CurrentWeather.leafTemperature2 = leafTemperature2;
    }

    public static Double getLeafTemperature3() {
        return leafTemperature3;
    }

    public static void setLeafTemperature3(Double leafTemperature3) {
        CurrentWeather.leafTemperature3 = leafTemperature3;
    }

    public static Double getLeafTemperature4() {
        return leafTemperature4;
    }

    public static void setLeafTemperature4(Double leafTemperature4) {
        CurrentWeather.leafTemperature4 = leafTemperature4;
    }

    public static Integer getExtraHumidity1() {
        return extraHumidity1;
    }

    public static void setExtraHumidity1(Integer extraHumidity1) {
        CurrentWeather.extraHumidity1 = extraHumidity1;
    }

    public static Integer getExtraHumidity2() {
        return extraHumidity2;
    }

    public static void setExtraHumidity2(Integer extraHumidity2) {
        CurrentWeather.extraHumidity2 = extraHumidity2;
    }

    public static Integer getExtraHumidity3() {
        return extraHumidity3;
    }

    public static void setExtraHumidity3(Integer extraHumidity3) {
        CurrentWeather.extraHumidity3 = extraHumidity3;
    }

    public static Integer getExtraHumidity4() {
        return extraHumidity4;
    }

    public static void setExtraHumidity4(Integer extraHumidity4) {
        CurrentWeather.extraHumidity4 = extraHumidity4;
    }

    public static Integer getExtraHumidity5() {
        return extraHumidity5;
    }

    public static void setExtraHumidity5(Integer extraHumidity5) {
        CurrentWeather.extraHumidity5 = extraHumidity5;
    }

    public static Integer getExtraHumidity6() {
        return extraHumidity6;
    }

    public static void setExtraHumidity6(Integer extraHumidity6) {
        CurrentWeather.extraHumidity6 = extraHumidity6;
    }

    public static Integer getExtraHumidity7() {
        return extraHumidity7;
    }

    public static void setExtraHumidity7(Integer extraHumidity7) {
        CurrentWeather.extraHumidity7 = extraHumidity7;
    }

    public static Integer getUv() {
        return uv;
    }

    public static void setUv(Integer newUv) {
        uv = newUv;
    }

    public static Integer getSolarRadiation() {
        return solarRadiation;
    }

    public static void setSolarRadiation(Integer solarRadiation) {
        CurrentWeather.solarRadiation = solarRadiation;
    }

    public static LocalDate getStartDateOfCurrentStorm() {
        return startDateOfCurrentStorm;
    }

    public static void setStartDateOfCurrentStorm(LocalDate newStartDateOfCurrentStorm) {
        startDateOfCurrentStorm = newStartDateOfCurrentStorm;
    }

    public static Double getMonthRain() {
        return monthRain;
    }

    public static void setMonthRain(Double newMonthRain) {
        monthRain = newMonthRain;
    }

    public static Double getYearRain() {
        return yearRain;
    }

    public static void setYearRain(Double yearRain) {
        CurrentWeather.yearRain = yearRain;
    }

    public static Double getSoilMoisture1() {
        return soilMoisture1;
    }

    public static void setSoilMoisture1(Double soilMoisture1) {
        CurrentWeather.soilMoisture1 = soilMoisture1;
    }

    public static Double getSoilMoisture2() {
        return soilMoisture2;
    }

    public static void setSoilMoisture2(Double soilMoisture2) {
        CurrentWeather.soilMoisture2 = soilMoisture2;
    }

    public static Double getSoilMoisture3() {
        return soilMoisture3;
    }

    public static void setSoilMoisture3(Double soilMoisture3) {
        CurrentWeather.soilMoisture3 = soilMoisture3;
    }

    public static Double getSoilMoisture4() {
        return soilMoisture4;
    }

    public static void setSoilMoisture4(Double soilMoisture4) {
        CurrentWeather.soilMoisture4 = soilMoisture4;
    }

    public static Integer getLeafWetness1() {
        return leafWetness1;
    }

    public static void setLeafWetness1(Integer leafWetness1) {
        CurrentWeather.leafWetness1 = leafWetness1;
    }

    public static Integer getLeafWetness2() {
        return leafWetness2;
    }

    public static void setLeafWetness2(Integer leafWetness2) {
        CurrentWeather.leafWetness2 = leafWetness2;
    }

    public static Integer getLeafWetness3() {
        return leafWetness3;
    }

    public static void setLeafWetness3(Integer leafWetness3) {
        CurrentWeather.leafWetness3 = leafWetness3;
    }

    public static Integer getLeafWetness4() {
        return leafWetness4;
    }

    public static void setLeafWetness4(Integer leafWetness4) {
        CurrentWeather.leafWetness4 = leafWetness4;
    }

    public static Double getTenMinuteWindGust() {
        return tenMinuteWindGust;
    }

    public static void setTenMinuteWindGust(Double windGust) {
        tenMinuteWindGust = windGust;
    }

    public static Double getOutsideTemperature() {
        return outsideTemperature;
    }

    public static void setOutsideTemperature(Double outsideTemperature) {
        CurrentWeather.outsideTemperature = outsideTemperature;
    }

    public static Integer getOutsideHumidity() {
        return outsideHumidity;
    }

    public static void setOutsideHumidity(Integer outsideHumidity) {
        CurrentWeather.outsideHumidity = outsideHumidity;
    }

    public static Double getInsideTemperature() {
        return insideTemperature;
    }

    public static void setInsideTemperature(Double insideTemperature) {
        CurrentWeather.insideTemperature = insideTemperature;
    }

    public static Integer getInsideHumidity() {
        return insideHumidity;
    }

    public static void setInsideHumidity(Integer insideHumidity) {
        CurrentWeather.insideHumidity = insideHumidity;
    }

    public static Integer getWindSpeed() {
        return windSpeed;
    }

    public static void setWindSpeed(Integer windSpeed) {
        CurrentWeather.windSpeed = windSpeed;
    }

    public static Integer getWindDirection() {
        return windDirection;
    }

    public static void setWindDirection(Integer windDirection) {
        CurrentWeather.windDirection = windDirection;
    }

    public static Integer getBarTrend() {
        return barTrend;
    }

    public static void setBarTrend(Integer barTrend) {
        CurrentWeather.barTrend = barTrend;
    }

    public static Double getBarometer() {
        return barometer;
    }

    public static void setBarometer(Double barometer) {
        CurrentWeather.barometer = barometer;
    }

    public static Integer getForecastIcon() {
        return forecastIcon;
    }

    public static void setForecastIcon(Integer forecastIcon) {
        CurrentWeather.forecastIcon = forecastIcon;
    }

    public static Double getDayRain() {
        return dayRain;
    }

    public static void setDayRain(Double dayRain) {
        CurrentWeather.dayRain = dayRain;
    }

    public static Double getStormRain() {
        return stormRain;
    }

    public static void setStormRain(Double stormRain) {
        CurrentWeather.stormRain = stormRain;
    }

    public static Double getRainRate() {
        return rainRate;
    }

    public static void setRainRate(Double rainRate) {
        CurrentWeather.rainRate = rainRate;
    }

    public static Integer getHeatIndex() {
        return heatIndex;
    }

    public static void setHeatIndex(Integer heatIndex) {
        CurrentWeather.heatIndex = heatIndex;
    }

    public static Integer getWindChill() {
        return windChill;
    }

    public static void setWindChill(Integer windChill) {
        CurrentWeather.windChill = windChill;
    }


}
