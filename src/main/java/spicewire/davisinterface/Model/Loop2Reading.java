package spicewire.davisinterface.Model;

import spicewire.davisinterface.Dao.JdbcWeatherRecord;
import spicewire.davisinterface.Dao.WeatherRecord;

import java.time.LocalDate;


public class Loop2Reading implements DataRecord {
    private JdbcWeatherRecord jdbcWeatherRecord;

    private String[] loopRecord;
    private int barTrend;
    private int packetType;
    private double barometer;
    private double insideTemperature;
    private int insideHumidity;
    private double outsideTemperature;
    private int windSpeed;
    private int windDirection;
    private double tenMinAvgWindSpeed;
    private double twoMinAvgWindSpeed;
    private double tenMinWindGust;
    private double windDirectionForTheTenMinuteWindGust;
    private int dewPoint;
    private int outsideHumidity;
    private int heatIndex;
    private int windChill;
    private Integer thswIndex;
    private double rainRate;
    private Integer UV;
    private Integer solarRadiation;
    private double stormRain;
    private LocalDate startDateOfCurrentStorm;
    private double dayRain;
    private double last15MinRain;
    private double lastHourRain;
    private double dayET;
    private double last24HourRain;
    private int barometricReductionMethod;
    private int userEnteredBarometricOffset;
    private double barometricCalibrationNumber;
    private double barometricSensorRawReading;
    private double absoluteBarometricPressure;
    private double altimeterSetting;
    private int nextTenMinWindSpeedGraphPointer;
    private int nextFifteenMinWindSpeedGraphPointer;
    private int nextHourlyWindSpeedGraphPointer;
    private int nextDailyWindSpeedGraphPointer;
    private int nextMinuteRainGraphPointer;
    private int nextRainStormGraphPointer;
    private int indexToTheMinuteWithinAnHour;
    private int nextMonthlyRain;
    private int nextYearlyRain;
    private int nextSeasonalRain;
    private String dataSource;

    public Loop2Reading(String loopData, JdbcWeatherRecord jdbcWeatherRecord) {

        this.loopRecord = makeLoopDataArray(loopData);
        this.jdbcWeatherRecord = jdbcWeatherRecord;
        this.barTrend = setBarTrend();
        this.packetType = setPacketType();
        this.barometer = setBarometer();
        this.insideTemperature = setInsideTemperature();
        this.insideHumidity = setInsideHumidity();
        this.outsideTemperature = setOutsideTemperature();
        this.windSpeed = setWindSpeed();
        this.windDirection = setWindDirection();
        this.tenMinAvgWindSpeed = setTenMinAvgWindSpeed();
        this.twoMinAvgWindSpeed = setTwoMinAvgWindSpeed();
        this.tenMinWindGust = setTenMingWindGust();
        this.windDirectionForTheTenMinuteWindGust = setWindDirectionForTheTenMinuteWindGust();
        this.dewPoint = setDewPoint();
        this.outsideHumidity = setOutsideHumidity();

        this.heatIndex = setHeatIndex();
        this.windChill = setWindChill();
        this.thswIndex = setThswIndex();
        this.rainRate = setRainRate();
        this.UV = setUV();
        this.solarRadiation = setSolarRadiation();
        this.stormRain = setStormRain();
        this.startDateOfCurrentStorm = setStartDateOfCurrentStorm();
        this.dayRain = setDayRain();
        this.last15MinRain = setLast15MinRain();
        this.lastHourRain = setLastHourRain();
        this.dayET = setDailyET();
        this.last24HourRain = setLast24HourRain();
        this.barometricReductionMethod = setBarometricReductionMethod();
        this.userEnteredBarometricOffset = setUserEnteredBarometricOffset();
        this.barometricCalibrationNumber = setBarometricCalibrationNumber();
        this.barometricSensorRawReading = setBarometricSensorRawReading();
        this.dataSource= "DavisVP2L2";
        jdbcWeatherRecord.createRecord(this);
        System.out.println("Loop2Reading: Loop2 Reading object created. Inside temp is: " + getInsideTemperature());
        //todo additional parameters to be added later

    }

    public Loop2Reading() {
    }



    private String[] makeLoopDataArray(String loopData) {
        return loopData.split(" ");
    }

    private String getByteOrWord(int offset, int length) { //todo this method is a duplicate from loop1
        StringBuilder byteOrWord = new StringBuilder();
        for (int i = 0; i < length; i++) {
            byteOrWord.append(loopRecord[offset + i] + " ");
        }
        return byteOrWord.toString().trim();
    }

    //given an index number from the loopRecord array (see Davis documentation), returns the binary converted to int
    private int parseBinaryNumberAtIndex(int index) {
        return Integer.parseInt(getByteOrWord(index, 1), 2);
    }

    private int parseBinaryString(String binaryString) {
        return Integer.parseInt(binaryString, 2);
    }

    //several values (e.g. temperatures) are sent LSB first, which means the byte order is incorrect for parsing.
    private int parseBinaryWordsAtIndex(int firstIndex) {
        String firstBinary = getByteOrWord(firstIndex, 1);
        String secondBinary = getByteOrWord(firstIndex + 1, 1);
        //byte order is reversed
        String reversedBinary = secondBinary + firstBinary;
        return Integer.parseInt(reversedBinary, 2);
    }

    /**
     * Given the index of a two byte word, prepends 0's onto both bytes, reverses the order of the bytes,
     * and parses the result
     *
     * @param firstIndex the lower index of the two bytes in the word
     * @return String for further interpretation if needed.
     */
    private String prependWord(int firstIndex) {
        String firstBinary = prependZerosToBinaryNumber(getByteOrWord(firstIndex, 1));
        String secondBinary = prependZerosToBinaryNumber(getByteOrWord(firstIndex + 1, 1));
        return secondBinary + firstBinary;
    }

    private Integer checkForNullAtIndex(int index) {
        Integer value = parseBinaryNumberAtIndex(index);
        if (value == 255) {
            return null;
        } else return value;
    }

    private String prependZerosToBinaryNumber(String nibble) {
        String prependedZeros = "0".repeat(8 - nibble.length());
        return prependedZeros + nibble;
    }

    private int setBarTrend() {
        int barometerTrend = parseBinaryNumberAtIndex(3);
        switch (barometerTrend) {
            case 196:
                barometerTrend = -60;//Falling rapidly
                break;
            case 236:
                barometerTrend = -20; //Falling slowly
                break;
            case 0:
                barometerTrend = 0; //Steady
                break;
            case 20:
                barometerTrend = 20; //rising slowly
                break;
            case 60:
                barometerTrend = 60;  //Rising rapidly
                break;
            case 80:
                barometerTrend = 80; //no trend info is available
                break;
            default:
                barometerTrend = 999;//three hours of bar data is not available
        }
        return barometerTrend;
    }

    private double setBarometer() {
        return parseBinaryString(prependWord(7)) / 1000.00;
    }

    //packet type is 0 for LOOP record type, 1 for LOOP2 record type
    private int setPacketType() {
        return parseBinaryNumberAtIndex(4);
    }

    private double setInsideTemperature() {
        return parseBinaryWordsAtIndex(9) / 10.0;
    }

    private int setInsideHumidity() {
        return parseBinaryNumberAtIndex(11);
    }

    private double setOutsideTemperature() {
        String secondByte = prependZerosToBinaryNumber(getByteOrWord(12, 1));
        String firstByte = getByteOrWord(13, 1);
        return Integer.parseInt(firstByte + secondByte, 2) / 10.0;
    }

    private int setWindSpeed() {
        return parseBinaryNumberAtIndex(14);
    }

    private int setWindDirection() {
        return parseBinaryWordsAtIndex(16);
    }

    //It is a two-byte unsigned value in 0.1mph resolution
    private double setTenMinAvgWindSpeed() {
        return parseBinaryWordsAtIndex(18) / 10.0;
    }

    //It is a two-byte unsigned value in 0.1mph resolution
    private double setTwoMinAvgWindSpeed() {
        return parseBinaryWordsAtIndex(20) / 10.0;
    }

    //It is a two-byte unsigned value in 0.1mph resolution
    private double setTenMingWindGust() {
        return parseBinaryWordsAtIndex(22);
    }

    private double setWindDirectionForTheTenMinuteWindGust() {
        return parseBinaryWordsAtIndex(24);
    }

    private int setDewPoint() {
        return parseBinaryWordsAtIndex(30);
    }

    private int setOutsideHumidity() {
        return parseBinaryNumberAtIndex(33);
    }

    private int setHeatIndex() {
        return parseBinaryWordsAtIndex(35);
    }

    private int setWindChill() {
        return parseBinaryWordsAtIndex(37);
    }

    //THSW Index = "Temperature Humidity Sun Wind" Index. Needs solar radiation sensor.
    //The value is a signed two byte value in whole degrees F. 255= dashed value
    private Integer setThswIndex() {
        int thsw = parseBinaryWordsAtIndex(39);
        if (thsw == 255) {
            return null;
        } else return thsw;
    }

    //In rain clicks per hour.
    private double setRainRate() {
        return parseBinaryWordsAtIndex(41) / 100.0;
    }

    private Integer setUV() {
        return checkForNullAtIndex(43);
    }

    //The unit is in watt/meter2
    //32767 = null value for Solar Radiation (p. 31 of Davis Manual)
    private Integer setSolarRadiation() {
        if (parseBinaryWordsAtIndex(44) == 32767) {
            return null;
        } else return parseBinaryWordsAtIndex(44);
    }

    private double setStormRain() {
        return parseBinaryWordsAtIndex(46) / 100.0;
    }

    private LocalDate setStartDateOfCurrentStorm() {

        /* Per Davis manual:  "Bit 15 to bit 12 is the month, bit 11 to bit 7 is the day and bit 6 to
        bit 0 is the year offset by 2000."

        10111 1011101 is an example of binaries (LOOP offsets 48 and 49).
        Byte 48 must be prepended with 0's and placed after byte 49 for the date to be parsed.
        After bytes are rearranged, bit index 0 is rightmost. Example rearranges to
         01011101 00010111, parses to date 2023-05-26.
        */
        String firstDateByte = prependZerosToBinaryNumber(getByteOrWord(48, 1));
        String secondDateByte = prependZerosToBinaryNumber(getByteOrWord(49, 1));
        //Davis uses a string of 1's if there has been no recent storm event;
        if (firstDateByte.equalsIgnoreCase("11111111") || secondDateByte.equalsIgnoreCase("11111111")) {
            return null;
        }
        String dateWord = secondDateByte + firstDateByte;
        int monthNumber = Integer.parseInt(dateWord.substring(0, 4), 2);
        int dayNumber = Integer.parseInt(dateWord.substring(4, 9), 2);
        int yearNumber = Integer.parseInt(dateWord.substring(9), 2) + 2000;
        LocalDate stormStartDate = LocalDate.of(yearNumber, monthNumber, dayNumber);
        return stormStartDate;
    }

    //This value is sent as number of rain clicks. (0.2mm or 0.01in)
    private double setDayRain() {
        return parseBinaryWordsAtIndex(50) / 100.00;
    }

    //This value is sent as number of rain clicks. (0.2mm or 0.01in)
    private double setLast15MinRain() {
        return parseBinaryWordsAtIndex(52) / 100.00;
    }

    //This value is sent as number of rain clicks. (0.2mm or 0.01in)
    private double setLastHourRain() {
        return parseBinaryWordsAtIndex(54) / 100.00;
    }

    //This value is sent as the 1000th of an inch.
    private double setDailyET() {
        return parseBinaryWordsAtIndex(56);
    }

    //This value is sent as number of rain clicks. (0.2mm or 0.01in)
    private double setLast24HourRain() {
        return parseBinaryWordsAtIndex(58) / 100.00;
    }

    //Bar reduction method: 0 = user offset; 1= Altimeter Setting 2=
    //NOAA Bar Reduction. For VP2, this will always be 2.
    private int setBarometricReductionMethod() {
        return parseBinaryNumberAtIndex(60);
    }

    private int setUserEnteredBarometricOffset() {
        return parseBinaryWordsAtIndex(61);
    }

    //Calibration offset in 1000th of an inch
    private int setBarometricCalibrationNumber() {
        return parseBinaryWordsAtIndex(63);
    }

    //In 1000th of an inch
    private int setBarometricSensorRawReading() {
        return parseBinaryWordsAtIndex(65);
    }

    //In 1000th of an inch, equals to the raw sensor reading plus user
    //entered offset
    private int setAbsoluteBarometricPressure() {
        return parseBinaryWordsAtIndex(67);
    }

    private int setNext10MinWindSpeedGraphPointer() {
        return parseBinaryNumberAtIndex(73);
    }

    private int setNext15MinWindSpeedGraphPointer() {
        return parseBinaryNumberAtIndex(74);
    }

    private int setNextHourlyWindSpeedGraphPointer() {
        return parseBinaryNumberAtIndex(75);
    }

    private int setNextDailyWindSpeedGraphPointer() {
        return parseBinaryNumberAtIndex(76);
    }

    private int setNextMinuteRainGraphPointer() {
        return parseBinaryNumberAtIndex(77);
    }

    private int setNexRainStormGraphPointer() {
        return parseBinaryNumberAtIndex(78);
    }

    private int setIndexToTheMinuteWithinAnHour() {
        return parseBinaryNumberAtIndex(79);
    }

    private int setNextMonthlyRain() {
        return parseBinaryNumberAtIndex(80);
    }

    private int setNextYearlyRain() {
        return parseBinaryNumberAtIndex(81);
    }

    private int setNextSeasonalRain() {
        return parseBinaryNumberAtIndex(82);
    }


    public int getBarTrend() {
        return barTrend;
    }

    public int getPacketType() {
        return packetType;
    }

    public double getBarometer() {
        return barometer;
    }

    public double getInsideTemperature() {
        return insideTemperature;
    }

    public int getInsideHumidity() {
        return insideHumidity;
    }

    public double getOutsideTemperature() {
        return outsideTemperature;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public int getWindDirection() {
        return windDirection;
    }

    @Override
    public double getTenMinAvgWindSpeed() {
        return tenMinAvgWindSpeed;
    }

    public double getTwoMinAvgWindSpeed() {
        return twoMinAvgWindSpeed;
    }

    public double getTenMinWindGust() {
        return tenMinWindGust;
    }

    public double getWindDirectionForTheTenMinuteWindGust() {
        return windDirectionForTheTenMinuteWindGust;
    }

    public int getDewPoint() {
        return dewPoint;
    }

    public int getOutsideHumidity() {
        return outsideHumidity;
    }

    public int getHeatIndex() {
        return heatIndex;
    }

    public int getWindChill() {
        return windChill;
    }

    public Integer getThswIndex() {
        return thswIndex;
    }

    public double getRainRate() {
        return rainRate;
    }

    public Integer getUv() {
        return UV;
    }

    public Integer getSolarRadiation() {
        return solarRadiation;
    }

    public double getStormRain() {
        return stormRain;
    }

    public LocalDate getStartDateOfCurrentStorm() {
        return startDateOfCurrentStorm;
    }

    public double getDayRain() {
        return dayRain;
    }

    public double getLast15MinRain() {
        return last15MinRain;
    }

    public double getLastHourRain() {
        return lastHourRain;
    }

    public double getDayET() {
        return dayET;
    }

    public double getLast24HourRain() {
        return last24HourRain;
    }

    public String getDataSource() {
        return dataSource;
    }
}
