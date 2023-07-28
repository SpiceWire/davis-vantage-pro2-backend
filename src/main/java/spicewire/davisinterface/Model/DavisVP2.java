package spicewire.davisinterface.Model;

import org.springframework.stereotype.Component;

/**
 * This class emulates an instance of the Davis Vantage Pro 2 console. The DisplayWeather subclass
 * holds the variables necessary to display current weather in imitation of the Davis console.
 * It also contains the firmware number and firmware date.
 * It is planned that this class will also contain alarms.
 */

public class DavisVP2 {
    private static String versionNumber;
    private static String versionDate;

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

    public static DavisVP2 davisVP2(){
        return null;
    }

    /**
     * This subclass contains variables necessary to emulate the display of the Vantage Pro 2 console.
     * The values are drawn from the database and serialized.
     */
    public static class DisplayWeather{
        private double outsideTemperature;
        private int outsideHumidity;
        private double insideTemperature;
        private int insideHumidity;
        private int windSpeed;
        private int windDirection;
        private int barTrend;
        private double barometer;
        private int forecastIcon;
        private double dayRain;
        private double stormRain;
        private double rainRate;
        private int heatIndex;
        private int windChill;

        public DisplayWeather() {
        }

        public DisplayWeather(double outsideTemperature, int outsideHumidity, double insideTemperature,
                              int insideHumidity, int windSpeed, int windDirection, int barTrend,
                              double barometer, int forecastIcon, double dayRain, double stormRain,
                              double rainRate, int heatIndex, int windChill)
        {
            this.outsideTemperature = outsideTemperature;
            this.outsideHumidity = outsideHumidity;
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
        }


        public double getOutsideTemperature() {
            return outsideTemperature;
        }

        public void setOutsideTemperature(double outsideTemperature) {
            this.outsideTemperature = outsideTemperature;
        }

        public int getOutsideHumidity() {
            return outsideHumidity;
        }

        public void setOutsideHumidity(int outsideHumidity) {
            this.outsideHumidity = outsideHumidity;
        }

        public double getInsideTemperature() {
            return insideTemperature;
        }

        public void setInsideTemperature(double insideTemperature) {
            this.insideTemperature = insideTemperature;
        }

        public int getInsideHumidity() {
            return insideHumidity;
        }

        public void setInsideHumidity(int insideHumidity) {
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

        public void setBarometer(double barometer) {
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

        public void setDayRain(double dayRain) {
            this.dayRain = dayRain;
        }

        public double getStormRain() {
            return stormRain;
        }

        public void setStormRain(double stormRain) {
            this.stormRain = stormRain;
        }

        public double getRainRate() {
            return rainRate;
        }

        public void setRainRate(double rainRate) {
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
}
