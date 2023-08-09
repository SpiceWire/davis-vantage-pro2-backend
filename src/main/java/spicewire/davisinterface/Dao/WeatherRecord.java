package spicewire.davisinterface.Dao;

import spicewire.davisinterface.Model.DavisVP2;
import spicewire.davisinterface.Model.LoopReading;

import java.time.LocalDate;
import java.time.LocalTime;

/** DAO interface for entering or retrieving a single weather record into the database of weather records. Data for a
 * weather record might be generated from several sources, including a Davis weather station, an outdoor
 * Arduino temperature sensor, or an indoor humidity sensor. These readings are not necessarily
 * synchronous. By some mechanism, these readings are aggregated and sent to be recorded in the database.
 * Of note, there might be a delay between capturing weather data and entering it into the database;
 *
 */

public interface WeatherRecord {

    /**    Creates a single weather record for entry into the database. Record must contain a date, time,
     *  sourceName, and whether that record should be included for export.
     */
    void createRecord(LoopReading loopReading);

    void
    /**
     * Creates an instance of the DavisVP2.DisplayWeather class, which contains all necessary
     * variables to emulate a Davis Vantage Pro 2 display.
     * @return DavisVP2.DisplayWeather object with all parameters populated
     */
    DavisVP2.DisplayWeather getDavisConsoleWeather();

    /**Creates a datestamp for the weather record. Timestamp should reflect when sensor data was gathered,
     * not necessarily when it was entered into the database.**/
    LocalDate getDatestamp();


    /**Creates a timestamp for the weather record. Timestamp should reflect when sensor data was gathered,
     * not necessarily when it was entered into the database.**/
    LocalTime getTimestamp();

}
