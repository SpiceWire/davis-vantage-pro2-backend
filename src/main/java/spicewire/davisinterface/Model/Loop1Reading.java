package spicewire.davisinterface.Model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is similar to a C struct. It accepts a 99 byte string and parses it into values to assign to parameters.
 * It then sends data to the JBDCWeatherEvent so that the information can be logged into a table.
 * The parameters in this class are different from those in the Loop2Reading, although there is some overlap.
 * Because its parameter values are set by parsing an incoming string, this class does not have public setters.
 *
 * The passed LoopData string has already been stripped of the ACK message (binary 110) and confirmed accurate
 * by a Cyclic Redundancy Check by the DataProcessor service.
 *
 * The LoopData's first three values are always 1001100 1001111 1001111 ("L O O"). "L" is considered index 0.
 *
 * As implemented by Davis, LoopData packets have two formats, LOOP1 and LOOP2.
 * The format type is encoded in byte offset 4. 0 = LOOP1  1 = LOOP2.
 *
 * Loop1 and Loop2 have different parameters (eg. only Loop1 contains alarms, only Loop 2 contains calibration data)
 * but have some overlap (both have a Ten Minute Average Wind Speed, but Loop1 uses an integer type while
 * Loop2 uses a double). The parameters in common do not necessarily have the same index in the LoopData array.
 *
 * When Loop1Record is sent a string of LoopData, it populates its object parameters and
 * calls a related method in another class. For example, after receiving a LoopData string, it calls
 * the JdbcWeatherRecord class so a weather event can be constructed from the Loop1Record object.
 */

//todo implement remaining variables

public class Loop1Reading implements LoopReading {

    private TempHumStation station = new TempHumStation();

    private String[] loopRecord;

    private int barTrend;
    private int packetType;
    private int nextRecord;
    private double barometer;
    private double insideTemperature;
    private int insideHumidity;
    private double outsideTemperature;
    private int windSpeed;
    private double tenMinAvgWindSpeed;
    private int windDirection;
    private Integer extraTemperature1;
    private Integer extraTemperature2;
    private Integer extraTemperature3;
    private Integer extraTemperature4;
    private Integer extraTemperature5;
    private Integer extraTemperature6;
    private Integer extraTemperature7;
    private Integer soilTemperature1;
    private Integer soilTemperature2;
    private Integer soilTemperature3;
    private Integer soilTemperature4;
    private Integer leafTemperature1;
    private Integer leafTemperature2;
    private Integer leafTemperature3;
    private Integer leafTemperature4;
    private int outsideHumidity;
    private Integer extraHumidity1;
    private Integer extraHumidity2;
    private Integer extraHumidity3;
    private Integer extraHumidity4;
    private Integer extraHumidity5;
    private Integer extraHumidity6;
    private Integer extraHumidity7;
    private double rainRate;
    private Integer uv;
    private Integer solarRadiation;
    private double stormRain;
    private LocalDate startDateOfCurrentStorm;
    private double dayRain;
    private double monthRain;
    private double yearRain;
    private double dayET;
    private double monthET;
    private double yearET;
    private Integer soilMoisture1;
    private Integer soilMoisture2;
    private Integer soilMoisture3;
    private Integer soilMoisture4;
    private Integer leafWetness1;
    private Integer leafWetness2;
    private Integer leafWetness3;
    private Integer leafWetness4;
    private Integer insideAlarms;
    private Integer rainAlarms;
    private Integer outsideAlarms1;
    private Integer outsideAlarms2;
    private Integer outsideHumidityAlarms;
    private Integer extraTempHumAlarms1;
    private Integer extraTempHumAlarms2;
    private Integer extraTempHumAlarms3;
    private Integer extraTempHumAlarms4;
    private Integer extraTempHumAlarms5;
    private Integer extraTempHumAlarms6;
    private Integer extraTempHumAlarms7;
    private Integer soilLeafAlarms1;
    private Integer soilLeafAlarms2;
    private Integer soilLeafAlarms3;
    private Integer soilLeafAlarms4;
    private int transmitterBatteryStatus;
    private Double consoleBatteryVoltage;
    private int forecastIcons;
    private int forecastRuleNumber;
    private int timeOfSunrise;
    private int timeOfSunset;
    private String dataSource;


    public Loop1Reading(String loopData) {

        this.loopRecord = makeLoopDataArray(loopData);
        this.insideHumidity = setInsideHumidity();
        this.packetType = setPacketType();
        this.barTrend = setBarTrend();
        this.nextRecord = setNextRecord();
        this.barometer = setBarometer();
        this.insideTemperature = setInsideTemperature();
        this.insideHumidity = setInsideHumidity();
        this.outsideTemperature = setOutsideTemperature();
        this.windSpeed = setWindSpeed();
        this.tenMinAvgWindSpeed = setTenMinAvgWindSpeed();
        this.windDirection = setWindDirection();
        this.extraTemperature1 = setExtratemperature1();
        this.extraTemperature2 = setExtratemperature2();
        this.extraTemperature3 = setExtratemperature3();
        this.extraTemperature4 = setExtratemperature4();
        this.extraTemperature5 = setExtratemperature5();
        this.extraTemperature6 = setExtratemperature6();
        this.extraTemperature7 = setExtratemperature7();
        this.soilTemperature1 = setSoilTemperature1();
        this.soilTemperature2 = setSoilTemperature2();
        this.soilTemperature3 = setSoilTemperature3();
        this.soilTemperature4 = setSoilTemperature4();
        this.leafTemperature1 = setLeafTemperature1();
        this.leafTemperature2 = setLeafTemperature2();
        this.leafTemperature3 = setLeafTemperature3();
        this.leafTemperature4 = setLeafTemperature4();
        this.outsideHumidity = setOutsideHumidity();
        this.extraHumidity1 = setExtraHumidity1();
        this.extraHumidity2 = setExtraHumidity2();
        this.extraHumidity3 = setExtraHumidity3();
        this.extraHumidity4 = setExtraHumidity4();
        this.extraHumidity5 = setExtraHumidity5();
        this.extraHumidity6 = setExtraHumidity6();
        this.extraHumidity7 = setExtraHumidity7();
        this.rainRate = setRainRate();
        this.uv = setUv();
        this.solarRadiation = setSolarRadiation();
        this.stormRain = setStormRain();
        this.startDateOfCurrentStorm = setStartDateOfCurrentStorm();
        this.dayRain = setDayRain();
        this.monthRain = setMonthRain();
        this.yearRain = setYearRain();
        this.dayET = setDayET();
        this.monthET = setMonthET();
        this.yearET = setYearET();
        this.soilMoisture1 = setSoilMoisture1();
        this.soilMoisture2 = setSoilMoisture2();
        this.soilMoisture3 = setSoilMoisture3();
        this.soilMoisture4 = setSoilMoisture4();
        this.leafWetness1 = setLeafWetness1();
        this.leafWetness2 = setLeafWetness2();
        this.leafWetness3 = setLeafWetness3();
        this.leafWetness4 = setLeafWetness4();

        this.insideAlarms = setInsideAlarms();
        this.rainAlarms = setRainAlarms();
        this.outsideAlarms1 = setOutsideAlarms1();
        this.outsideAlarms2 = setOutsideAlarms2();

        this.outsideHumidityAlarms = setOutsideHumidityAlarms();
        this.extraTempHumAlarms1 = setExtraTempHumAlarms1();
        this.extraTempHumAlarms2 = setExtraTempHumAlarms2();
        this.extraTempHumAlarms3 = setExtraTempHumAlarms3();
        this.extraTempHumAlarms4 = setExtraTempHumAlarms4();
        this.extraTempHumAlarms5 = setExtraTempHumAlarms5();
        this.extraTempHumAlarms6 = setExtraTempHumAlarms6();
        this.extraTempHumAlarms7 = setExtraTempHumAlarms7();

       this.soilLeafAlarms1 = setSoilLeafAlarms1();
       this.soilLeafAlarms2 = setSoilLeafAlarms2();
       this.soilLeafAlarms3 = setSoilLeafAlarms3();
       this.soilLeafAlarms4 = setSoilLeafAlarms4();

       this.transmitterBatteryStatus = setTransmitterBatteryStatus();
       this.consoleBatteryVoltage = setConsoleBatteryVoltage();

        this.forecastIcons = setForecastIcon();
        this.forecastRuleNumber = setForecastRuleNumber();
        this.insideAlarms = setInsideAlarms();
        this.dataSource = "DavisVP2L1";

    }

    public Loop1Reading() {

    }

    private String[] makeLoopDataArray(String loopData) {
        return loopData.split(" ");
    }

    //returns one or more elements of the loopRecord array. "Length" is measured by bytes.
    private String getByteOrWord(int offset, int length) {
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
        return Integer.parseInt(prependWord(firstIndex), 2);
    }

    /**
     * Given the index of a two byte word, prepends 0's onto both bytes, reverses the order of the bytes,
     * and returns the result still as binary.
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

    private int setPacketType() {
        return parseBinaryNumberAtIndex(4);
    }

    private int setBarTrend() {
        int barometerTrend = parseBinaryNumberAtIndex(3);

        switch (barometerTrend) {
            case 196:
                barometerTrend = -60; //Falling rapidly
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
                barometerTrend = 999;//three hours of bar data is not available, or firmware Rev A is active
        }
        return barometerTrend;
    }


    private int setNextRecord() {
        return parseBinaryWordsAtIndex(5);
    }

    private double setBarometer() {
        return parseBinaryString(prependWord(7)) / 1000.00;
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

    //Davis manual: "It is a byte unsigned value in mph."
    //This setter returns a double because Loop2 type getTenMinAvgWindSpeed is double.
    private double setTenMinAvgWindSpeed() {
        return parseBinaryNumberAtIndex(15) / 1.0;
    }

    private int setWindDirection() {

        return parseBinaryWordsAtIndex(16);
    }


    /*  From Davis manual:
    "This field supports seven extra temperature stations. Each byte is one extra temperature value in whole
    degrees F with an offset of 90 degrees. For example, a value of 0 = -90°F ; a value of 100 = 10°F ; and a
    value of 169 = 79°F.*/
/*    private void setExtraTemperatures() {
        System.out.println("extraTemps triggered");
        Integer[] extraTemperatures = new Integer[7];
        String extraTemp;
        for (int i = 0; i<=6; i++){
            if(getByteOrWord(18+i,1).equalsIgnoreCase("11111111")){
                extraTemperatures[i] = null;
            } else extraTemperatures[i] = parseBinaryNumberAtIndex(18+i) -90;
        }
        this.extraTemperatures = extraTemperatures;
    }*/
    //Integer is used (rather than int) so that a null value can be returned.
    private Integer setExtratemperature1() {
        return parseTemperatureWith90Offset(18);
    }

    private Integer setExtratemperature2() {
        return parseTemperatureWith90Offset(19);
    }

    public Integer setExtratemperature3() {
        return parseTemperatureWith90Offset(20);
    }

    public Integer setExtratemperature4() {
        return parseTemperatureWith90Offset(21);
    }

    public Integer setExtratemperature5() {
        return parseTemperatureWith90Offset(22);
    }

    public Integer setExtratemperature6() {
        return parseTemperatureWith90Offset(23);
    }

    public Integer setExtratemperature7() {
        return parseTemperatureWith90Offset(24);
    }

    //Loop1 Extra Temperatures(7), Leaf Temperatures(4) and Soil Temperatures(4) all are offset by 90 degrees F.
    //Integer is used (rather than int) so that a null value can be returned.
    private Integer parseTemperatureWith90Offset(int index) {
        if (checkForNullAtIndex(index) != null) {
            return parseBinaryNumberAtIndex(index) - 90;
        } else return null;

    }

    /*From Davis manual:
    This field supports four soil temperature sensors, in the same
    format as the Extra Temperature field above*/
    //Integer is used (rather than int) so that a null value can be returned.
    private Integer setSoilTemperature1() {
        return parseTemperatureWith90Offset(25);
    }

    private Integer setSoilTemperature2() {
        return parseTemperatureWith90Offset(26);
    }

    private Integer setSoilTemperature3() {
        return parseTemperatureWith90Offset(27);
    }

    private Integer setSoilTemperature4() {
        return parseTemperatureWith90Offset(28);
    }

    /*From Davis manual:
    This field supports four leaf temperature sensors, in the same
    format as the Extra Temperature field above*/
    //Integer is used (rather than int) so that a null value can be returned.
    private Integer setLeafTemperature1() {
        return parseTemperatureWith90Offset(29);
    }

    private Integer setLeafTemperature2() {
        return parseTemperatureWith90Offset(30);
    }

    private Integer setLeafTemperature3() {
        return parseTemperatureWith90Offset(31);
    }

    private Integer setLeafTemperature4() {
        return parseTemperatureWith90Offset(32);
    }

    //Davis manual: "This is the relative humidity in %. "
    private int setOutsideHumidity() {
        return parseBinaryNumberAtIndex(33);
    }

    //    From Davis manual: "Relative humidity in % for extra seven humidity stations."
    private Integer setExtraHumidity1() {
        return checkForNullAtIndex(34);
    }

    private Integer setExtraHumidity2() {
        return checkForNullAtIndex(35);

    }

    private Integer setExtraHumidity3() {
        return checkForNullAtIndex(36);
    }

    private Integer setExtraHumidity4() {
        return checkForNullAtIndex(37);
    }

    private Integer setExtraHumidity5() {
        return checkForNullAtIndex(38);
    }

    private Integer setExtraHumidity6() {
        return checkForNullAtIndex(39);
    }

    private Integer setExtraHumidity7() {
        return checkForNullAtIndex(40);
    }


    /*    From Davis manual:
        "This value is sent as number of rain clicks (0.2mm or 0.01in).
        For example, 256 can represent 2.56 inches/hour."*/
    private double setRainRate() {
        return parseBinaryWordsAtIndex(41) / 100.0;
    }

    //    From Davis manual: "The unit is in UV index"
    private Integer setUv() {
        return checkForNullAtIndex(43);
    }

    //    From Davis manual:"The unit is in watt/meter2"
    //    32767 = null value for Solar Radiation (p. 31 of Davis Manual)
    private Integer setSolarRadiation() {
        if (parseBinaryWordsAtIndex(44) == 32767) {
            return null;
        } else return parseBinaryWordsAtIndex(44);
    }

    //    From Davis manual: "The storm is stored as 100th of an inch."
    private double setStormRain() {
        return parseBinaryWordsAtIndex(46) / 100.00;
    }

    private LocalDate setStartDateOfCurrentStorm() {

        /* Per Davis manual:  "Bit 15 to bit 12 is the month, bit 11 to bit 7 is the day and bit 6 to
        bit 0 is the year offset by 2000."

        00010111 01011101   is an example of binaries (LOOP offsets 48 and 49).
        Byte 48 must be prepended with 0's and placed after byte 49 for the date to be parsed.
        After bytes are rearranged, bit index 0 is rightmost. Example rearranges to
         (MMMMDDDD DYYYYYYY) 01011101 00010111, parses to date 2023-05-26.

        */
        LocalDate stormStartDate;
        String firstDateByte = prependZerosToBinaryNumber(getByteOrWord(48, 1));
        String secondDateByte = prependZerosToBinaryNumber(getByteOrWord(49, 1));
        if (firstDateByte.equalsIgnoreCase("11111111") || secondDateByte.equalsIgnoreCase("11111111")) {
            stormStartDate = null;
        } else {
            String dateWord = secondDateByte + firstDateByte;
            int monthNumber = Integer.parseInt(dateWord.substring(0, 4), 2);
            int dayNumber = Integer.parseInt(dateWord.substring(4, 9), 2);
            int yearNumber = Integer.parseInt(dateWord.substring(9), 2) + 2000;
            stormStartDate = LocalDate.of(yearNumber, monthNumber, dayNumber);
        }
        return stormStartDate;
    }

    //    From Davis manual: "This value is sent as number of rain clicks. (0.2mm or 0.01in) "
    //Of note, Davis calls a similar value in the LOOP2 packet "Daily Rain"
    private double setDayRain() {
        return parseBinaryWordsAtIndex(50) / 100.00;
    }

    private double setMonthRain() {
        return parseBinaryWordsAtIndex(52) / 100.00;
    }

    private double setYearRain() {
        return parseBinaryWordsAtIndex(54) / 100.00;
    }

    private double setDayET() {
        return parseBinaryWordsAtIndex(56);
    }

    private double setMonthET() {
        return parseBinaryWordsAtIndex(58);
    }

    private double setYearET() {
        return parseBinaryWordsAtIndex(60);
    }

    //From Davis Manual: The unit is in centibar. It supports four soil sensors.
    private Integer setSoilMoisture1() {
        return checkForNullAtIndex(62);
    }

    private Integer setSoilMoisture2() {
        return checkForNullAtIndex(63);
    }

    private Integer setSoilMoisture3() {
        return checkForNullAtIndex(64);
    }

    private Integer setSoilMoisture4() {
        return checkForNullAtIndex(65);
    }

    private Integer setLeafWetness1() {
        return checkForNullAtIndex(66);
    }

    private Integer setLeafWetness2() {
        return checkForNullAtIndex(67);
    }

    private Integer setLeafWetness3() {
        return checkForNullAtIndex(68);
    }

    private Integer setLeafWetness4() {
        return checkForNullAtIndex(69);
    }

    private Integer setInsideAlarms(){
        return parseBinaryNumberAtIndex(70);
    }
    private Integer setRainAlarms(){
        return parseBinaryNumberAtIndex(71);
    }
    private Integer setOutsideAlarms1(){
        return parseBinaryNumberAtIndex(72);
    }
    private Integer setOutsideAlarms2(){
        return parseBinaryNumberAtIndex(73);
    }
    private Integer setOutsideHumidityAlarms(){
        return parseBinaryNumberAtIndex(74);
    }
    private Integer setExtraTempHumAlarms1(){
        return parseBinaryNumberAtIndex(75);
    }
    private Integer setExtraTempHumAlarms2(){
        return parseBinaryNumberAtIndex(76);
    }
    private Integer setExtraTempHumAlarms3(){
        return parseBinaryNumberAtIndex(77);
    }
    private Integer setExtraTempHumAlarms4(){
        return parseBinaryNumberAtIndex(78);
    }
    private Integer setExtraTempHumAlarms5(){
        return parseBinaryNumberAtIndex(79);
    }
    private Integer setExtraTempHumAlarms6(){
        return parseBinaryNumberAtIndex(80);
    }
    private Integer setExtraTempHumAlarms7(){
        return parseBinaryNumberAtIndex(81);
    }

    private Integer setSoilLeafAlarms1(){
        return parseBinaryNumberAtIndex(82);
    }
    private Integer setSoilLeafAlarms2(){
        return parseBinaryNumberAtIndex(83);
    }
    private Integer setSoilLeafAlarms3(){
        return parseBinaryNumberAtIndex(84);
    }
    private Integer setSoilLeafAlarms4(){
        return parseBinaryNumberAtIndex(85);
    }
    private Integer setForecastRuleNumber(){
        return parseBinaryNumberAtIndex(90);
    }
//    private Map<String, Boolean> setInsideAlarms() {
//        return makeBinaryMap(new String[]{"Falling Bar Trend", "Rising Bar Trend", "Low Inside Temp",
//                        "High Inside Temp", "Low Inside Hum", "High Inside Hum"},
//                prependZerosToBinaryNumber(getByteOrWord(70, 1)));
//    }
//
//    private Map<String, Boolean> setRainAlarms() {
//        return makeBinaryMap(new String[]{"High Rain Rate", "15Min Rain", "24 Hour Rain", "Storm Total"},
//                prependZerosToBinaryNumber(getByteOrWord(71, 1)));
//
//    }
//
//    private Map<String, Boolean> setOutsideAlarmsB1() {
//        return makeBinaryMap(new String[]{"Low Outside Temp", "High Outside Temp", "Wind Speed", "10 Min Avg Speed",
//                        "Low Dewpoint", "High Dewpoint", "Low Wind Chill"},
//                prependZerosToBinaryNumber(getByteOrWord(72, 1)));
//    }
//
//    private Map<String, Boolean> setOutsideAlarmsB2() {
//        return makeBinaryMap(new String[]{"High THSW", "High Solar Rad", "High UV", "UV Dose", "UV Dose Enabled"},
//                prependZerosToBinaryNumber(getByteOrWord(73, 1)));
//    }
//
//    private Map<String, Boolean> setOutsideHumidityAlarms() {
//        return makeBinaryMap(new String[]{"Low Humidity", "High Humidity"},
//                prependZerosToBinaryNumber(getByteOrWord(74, 1)));
//    }

    /**
     * Makes a Map from a String[] and a binary String, returning true/false for each
     * key in the String[].  The method accepts a String[] of alarm names and the
     * corresponding binary string, returning whether those alarms are "currently active" or not.
     *
     * @param nameArr
     * @param binString
     * @return
     */
    private Map<String, Boolean> makeBinaryMap(String[] nameArr, String binString) {
        Map<String, Boolean> stringBoolMap = new HashMap<>();
        StringBuilder binReverse = new StringBuilder(binString).reverse();
        for (int i = nameArr.length - 1; i >= 0; i--) {
            stringBoolMap.put(nameArr[i], binReverse.charAt(i) == '1');
        }
        return stringBoolMap;
    }


    /*    From Davis manual: "Bytes 75-81: Each byte contains four alarm bits (0 – 3) for a single extra
        Temp/Hum station. Bits (4 – 7) are not used and reserved for future use.
        Use the temperature and humidity sensor numbers, as
        described in Section XIV.4 to locate which byte contains the
        appropriate alarm bits. In particular, the humidity and
        temperature alarms for a single station will be found in
        different bytes.
        Field               Bit
        Low temp X alarm    0
        High temp X alarm   1
        Low hum X alarm     2
        High hum X alarm    3"

        */
//    private Map[] setExtraAlarms() {
//        String[] alarmNames = new String[]{"Low Temp", "High Temp", "Low Hum", "High Hum"};
//        Map<String, Boolean> alarm1Map = makeBinaryMap(alarmNames,
//                prependZerosToBinaryNumber(getByteOrWord(75, 1)));
//        Map<String, Boolean> alarm2Map = makeBinaryMap(alarmNames,
//                prependZerosToBinaryNumber(getByteOrWord(76, 1)));
//        Map<String, Boolean> alarm3Map = makeBinaryMap(alarmNames,
//                prependZerosToBinaryNumber(getByteOrWord(77, 1)));
//        Map<String, Boolean> alarm4Map = makeBinaryMap(alarmNames,
//                prependZerosToBinaryNumber(getByteOrWord(78, 1)));
//        Map<String, Boolean> alarm5Map = makeBinaryMap(alarmNames,
//                prependZerosToBinaryNumber(getByteOrWord(79, 1)));
//        Map<String, Boolean> alarm6Map = makeBinaryMap(alarmNames,
//                prependZerosToBinaryNumber(getByteOrWord(80, 1)));
//        Map<String, Boolean> alarm7Map = makeBinaryMap(alarmNames,
//                prependZerosToBinaryNumber(getByteOrWord(81, 1)));
//        return new Map[]{alarm1Map, alarm2Map, alarm3Map, alarm4Map, alarm5Map,
//                alarm6Map, alarm7Map};
//    }

    /**
     * Creates and populates Temperature/Humidity stations from Loop data. Not
     * yet tested as hardware is unavailable.
     */
    private void setExtraTempHumStations() {

        String[] temperatureHumidityAlarms = new String[8];
        for (int i = 0; i <= 7; i++) {
            temperatureHumidityAlarms[i] = getByteOrWord(75 + i, 1);
        }

        for (int i = 0; i <= 7; i++) {
            String nibble = temperatureHumidityAlarms[i];
            if (nibble.equalsIgnoreCase("0")) {
                station = new TempHumStation(i, 3, false,
                        false, false, false);
            } else {
                String stationCode = prependZerosToBinaryNumber(nibble);
                //char 49 = "1"
                station = new TempHumStation(i, 3,
                        stationCode.charAt(0) == 49,
                        stationCode.charAt(1) == 49,
                        stationCode.charAt(2) == 49,
                        stationCode.charAt(3) == 49);

            }


        }

    }

    private Integer setTransmitterBatteryStatus(){
        return parseBinaryNumberAtIndex(86);
    }
    /**
     * Sets console battery voltage from Loop data. Formula is from Davis documentation.
     *
     * @return voltage reading
     */
    private double setConsoleBatteryVoltage() {
        int voltData = parseBinaryWordsAtIndex(87);
        double voltage = ((voltData * 300) / 512) / 100.0;
        return voltage;
    }

    private int setForecastIcon() {
        /* Per Davis manual: "bit maps for forecast icons:
        <field><Bit#> Rain 0; Cloud 1; Partly Cloudy 2; Sun 3; Snow 4;"

        Value Decimal Value Hex Segments Shown                      Forecast
            8           0x08    Sun                                 Mostly Clear
            6           0x06    Partial Sun + Cloud                 Partly Cloudy
            2           0x02    Cloud                               Mostly Cloudy
            3           0x03    Cloud + Rain                        Mostly Cloudy, Rain within 12 hours
            18          0x12    Cloud + Snow                        Mostly Cloudy, Snow within 12 hours
            19          0x13    Cloud + Rain + Snow                 Mostly Cloudy, Rain or Snow within 12 hours
            7           0x07    Partial Sun + Cloud + Rain          Partly Cloudy, Rain within 12 hours
            22          0x16    Partial Sun + Cloud + Snow          Partly Cloudy, Snow within 12 hours
            23          0x17    Partial Sun + Cloud +Rain + Snow    Partly Cloudy, Rain or Snow within 12 hours
        */

        return parseBinaryNumberAtIndex(89);

    }

    private void setTimeOfSunrise() {
        this.timeOfSunrise = parseTimeFromIndex(91);
    }

    private void setTimeOfSunset() {
        this.timeOfSunset = parseTimeFromIndex(93);
    }

    //extracts time from index of first of two bytes.  The strippedLeading least significant bit (LSB) is sent first.
    // Davis manual: "Time is stored as hour * 100 + min"
    //
    private int parseTimeFromIndex(int index) {
        String firstTimeByte = prependZerosToBinaryNumber(getByteOrWord(index, 1));
        String secondTimeByte = getByteOrWord(index + 1, 1);
        return Integer.parseInt(secondTimeByte + firstTimeByte, 2);
    }

    private String prependZerosToBinaryNumber(String nibble) {
        String prependedZeros = "0".repeat(8 - nibble.length());
        return prependedZeros + nibble;
    }

    public int getBarTrend() {
        return barTrend;
    }

    public int getPacketType() {
        return packetType;
    }

    public int getNextRecord() {
        return nextRecord;
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

    public double getTenMinAvgWindSpeed() {
        return tenMinAvgWindSpeed;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public Integer getExtraTemperature1() {
        return extraTemperature1;
    }

    public Integer getExtraTemperature2() {
        return extraTemperature2;
    }

    public Integer getExtraTemperature3() {
        return extraTemperature3;
    }

    public Integer getExtraTemperature4() {
        return extraTemperature4;
    }

    public Integer getExtraTemperature5() {
        return extraTemperature5;
    }

    public Integer getExtraTemperature6() {
        return extraTemperature6;
    }

    public Integer getExtraTemperature7() {
        return extraTemperature7;
    }

    public Integer getSoilTemperature1() {
        return soilTemperature1;
    }

    public Integer getSoilTemperature2() {
        return soilTemperature2;
    }

    public Integer getSoilTemperature3() {
        return soilTemperature3;
    }

    public Integer getSoilTemperature4() {
        return soilTemperature4;
    }

    public Integer getLeafTemperature1() {
        return leafTemperature1;
    }

    public Integer getLeafTemperature2() {
        return leafTemperature2;
    }

    public Integer getLeafTemperature3() {
        return leafTemperature3;
    }

    public Integer getLeafTemperature4() {
        return leafTemperature4;
    }

    public int getOutsideHumidity() {
        return outsideHumidity;
    }

    public Integer getExtraHumidity1() {
        return extraHumidity1;
    }

    public Integer getExtraHumidity2() {
        return extraHumidity2;
    }

    public Integer getExtraHumidity3() {
        return extraHumidity3;
    }

    public Integer getExtraHumidity4() {
        return extraHumidity4;
    }

    public Integer getExtraHumidity5() {
        return extraHumidity5;
    }

    public Integer getExtraHumidity6() {
        return extraHumidity6;
    }

    public Integer getExtraHumidity7() {
        return extraHumidity7;
    }

    public Integer getUv() {
        return uv;
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

    public double getMonthRain() {
        return monthRain;
    }

    public double getYearRain() {
        return yearRain;
    }

    public double getDayET() {
        return dayET;
    }

    public double getMonthET() {
        return monthET;
    }

    public double getYearET() {
        return yearET;
    }

    public Integer getSoilMoisture1() {
        return soilMoisture1;
    }

    public Integer getSoilMoisture2() {
        return soilMoisture2;
    }

    public Integer getSoilMoisture3() {
        return soilMoisture3;
    }

    public Integer getSoilMoisture4() {
        return soilMoisture4;
    }

    public Integer getLeafWetness1() {
        return leafWetness1;
    }

    public Integer getLeafWetness2() {
        return leafWetness2;
    }

    public Integer getLeafWetness3() {
        return leafWetness3;
    }

    public Integer getLeafWetness4() {
        return leafWetness4;
    }

    public int getInsideAlarms() {
        return insideAlarms;
    }

    public int getRainAlarms() {
        return rainAlarms;
    }

    public double getRainRate() {
        return rainRate;
    }


    public int getTransmitterBatteryStatus() {
        return transmitterBatteryStatus;
    }

    public double getConsoleBatteryVoltage() {
        return consoleBatteryVoltage;
    }

    public int getForecastIcons() {
        return forecastIcons;
    }

    public int getTimeOfSunrise() {
        return timeOfSunrise;
    }

    public int getTimeOfSunset() {
        return timeOfSunset;
    }

    public String getDataSource() {
        return dataSource;
    }

    public Integer getOutsideAlarms1() {
        return outsideAlarms1;
    }

    public Integer getOutsideAlarms2() {
        return outsideAlarms2;
    }

    public Integer getOutsideHumidityAlarms() {
        return outsideHumidityAlarms;
    }

    public Integer getExtraTempHumAlarms1() {
        return extraTempHumAlarms1;
    }

    public Integer getExtraTempHumAlarms2() {
        return extraTempHumAlarms2;
    }

    public Integer getExtraTempHumAlarms3() {
        return extraTempHumAlarms3;
    }

    public Integer getExtraTempHumAlarms4() {
        return extraTempHumAlarms4;
    }

    public Integer getExtraTempHumAlarms5() {
        return extraTempHumAlarms5;
    }

    public Integer getExtraTempHumAlarms6() {
        return extraTempHumAlarms6;
    }

    public Integer getExtraTempHumAlarms7() {
        return extraTempHumAlarms7;
    }

    public Integer getSoilLeafAlarms1() {
        return soilLeafAlarms1;
    }

    public Integer getSoilLeafAlarms2() {
        return soilLeafAlarms2;
    }

    public Integer getSoilLeafAlarms3() {
        return soilLeafAlarms3;
    }

    public Integer getSoilLeafAlarms4() {
        return soilLeafAlarms4;
    }

    public int getForecastRuleNumber() {
        return forecastRuleNumber;
    }
}
