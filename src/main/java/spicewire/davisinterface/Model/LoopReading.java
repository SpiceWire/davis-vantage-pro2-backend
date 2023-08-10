package spicewire.davisinterface.Model;

import java.time.LocalDate;

/** Dao for creating a weather record from a Davis Weather Instruments Vantage Pro2 Console. The console
 *  sends live weather data in two different binary formats, called "Loop1" and "Loop2". The formats have some
 *  values in common, but those values are not necessarily at the same index in the two formats and are not
 *  necessarily of the same data type. Each format has values not carried by the other.
 *
 *  Setters use data parsed from a string passed to the class. In general, setters do not have public
 *  setters.
 */


public interface LoopReading extends DataRecord{

    /**
     * Gets the source of data. E.g. name or location of sensor or
     * sensor package. DavisVP2L1 for Loop data, DavisVP2L2 for LPS data,
     * Used in database for labels.
     * @return String
     */
    String getDataSource();


    /**Get the packet type associated with the data format.
     *
     * @return int  Type 0 is Loop1 format, type 1 is Loop2 format.
     * */
    int getPacketType();


    /** Get the barometer trend. The value is used by the Davis console to display up, down or right arrows.
     * Of note, the binary value in the array is an unsigned byte which must be changed to a signed int.
     *
     * value   interpretation    Barometer trend is
     * 196          -60           Falling Rapidly
     * 236          -20           Falling Slowly
     * 0              0           Steady
     * 20             20          Rising Slowly
     * 60             60          Rising Rapidly
     * 80             P           not available
     * anything       999         not available
     * else

     * @return int, following the "interpretation" column above.
     */
    int getBarTrend();


    /**
     * From Davis manual: "Current Barometer. Units are (in Hg / 1000). The barometric
     * value should be between 20 inches and 32.5 inches in Vantage
     * Pro and between 20 inches and 32.5 inches in both Vantatge (sic) Pro
     * Vantage Pro2. Values outside these ranges will not be logged"
     *
     * @return inches of Hg/1000
     */
    double getBarometer();

    /**
     * From Davis manual: "The value is sent as 10th of a degree in F. For example, 795 is
     * returned for 79.5°F."
     *
     * @return temperature in Fahrenheit to the tenth of a degree.
     */
    double getInsideTemperature();


    /**
     * From Davis manual: "This is the relative humidity in %, such as 50 is returned for 50%"
     *
     * @return relative humidity as a whole number, understood as a percentage.
     */
    int getInsideHumidity();


    /**
     * From Davis manual: "The value is sent as 10th of a degree in F. For example, 795 is
     * returned for 79.5°F."
     *
     * @return temperature in Fahrenheit to the tenth of a degree.
     */
    double getOutsideTemperature();


    /**
     * From Davis manual: "This is the relative humitiy (sic) in %. "
     *
     * @return relative humidity as a whole number, understood as a percentage.
     */
    int getOutsideHumidity();



    /**
     * From Davis manual: "It is a byte unsigned value in mph. If the wind speed is dashed
     * because it lost synchronization with the radio or due to some
     * other reason, the wind speed is forced to be 0."
     *
     * @return wind speed as a whole number in miles per hour
     */
    int getWindSpeed();


    /**
     * From Davis manual: "It is a two byte unsigned value from 1 to 360 degrees. (0° is no
     * wind data, 90° is East, 180° is South, 270° is West and 360° is
     * north)"
     *
     * @return wind direction as a whole number in degrees, with 0 representing "no wind data", and
     *      values from 1 to 360 representing the direction the wind is coming FROM.
     *
     */
    int getWindDirection();



    /**
     * From Davis manual: LOOP1: "It is a byte unsigned value in mph."
     *                    LOOP2: "It is a two-byte unsigned value in 0.1mph resolution."
     *
     * @return a double in 0.1 miles per hour resolution.
     */
    double getTenMinAvgWindSpeed();



    /**
     * From Davis manual: "This value is sent as number of rain clicks (0.2mm or 0.01in).
     * For example, 256 can represent 2.56 inches/hour."
     *
     * @return rate in inches/hour in 0.01 inches resolution.
     */
    double getRainRate();


    /**
     * From Davis manual: "The unit is in UV index."
     *
     * @return whole number between 0 and 14
     */
    Integer getUv();


    /**
     * From Davis manual: "The unit is in watt/meter2"
     * @return a number between 0 and 1000.
     */
    Integer getSolarRadiation();


    /**
     * From Davis manual: "The storm is stored as 100th of an inch."
     *
     * @return  a double representing inches in 0.01 inches resolution.
     */
    double getStormRain();


    /**
     * From Davis manual: "Bit 15 to bit 12 is the month, bit 11 to bit 7 is the day and bit 6 to
     * bit 0 is the year offseted (sic) by 2000."
     *
     *
     * @return LocalDate representing the most recent storm date start.
     */
    LocalDate getStartDateOfCurrentStorm();



    /**
     * From Davis manual: "This value is sent as number of rain clicks. (0.2mm or 0.01in)"
     *
     * @return a double representing inches in 0.01 inches resolution.
     */
    double getDayRain();



    /**
     * From Davis manual: "This value is setnt (sic) as the 100th of an inch"
     *
     * @return a double representing inches in 0.01 inches resolution.
     */
    double getDayET();
}
