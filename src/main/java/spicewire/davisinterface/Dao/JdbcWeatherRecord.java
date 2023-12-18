package spicewire.davisinterface.Dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.PropertyUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import spicewire.davisinterface.DavisinterfaceApplication;
import spicewire.davisinterface.Model.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.Properties;

/**
 * Class uses JDBC to create a database entry from LOOP1 or LOOP2 data, and also pulls
 * most recent LOOP1 and LOOP2 data from the database to create a CurrentWeather object.
 *
 * The
 */

/**
 * Class uses JDBC to create and retrieve current weather records from a database. The createRecord method
 * accepts a WeatherRecord object and uses the getPacketType method to determine how to enter
 * it into the database.
 *
 * Class populates and returns a CurrentWeather DTO.
 */

public class JdbcWeatherRecord implements WeatherRecord {

    private final JdbcTemplate jdbcTemplate;

//    @Autowired
//    BasicDataSource dataSource = new BasicDataSource();
//    BasicDataSource dataSource = DavisinterfaceApplication.getDatasource();
    public JdbcWeatherRecord(BasicDataSource dataSource) {
        dataSource = DavisinterfaceApplication.getDatasource();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public CurrentWeather getWeather(){
        CurrentWeather currentWeather = new CurrentWeather();
        String consoleSqlLoop1 = "SELECT bar_trend, entry_date, " +
                "barometer, inside_temperature, inside_humidity," +
                "outside_temperature, wind_speed, ten_min_avg_wind_speed, wind_direction, extra_temperature1," +
                "extra_temperature2, extra_temperature3, extra_temperature4, extra_temperature5, extra_temperature6," +
                "extra_temperature7, soil_temperature1, soil_temperature2, soil_temperature3, soil_temperature4," +
                "leaf_temperature1, leaf_temperature2, leaf_temperature3, leaf_temperature4, outside_humidity," +
                "extra_humidity1, extra_humidity2, extra_humidity3, extra_humidity4, extra_humidity5," +
                "extra_humidity6, extra_humidity7, rain_rate, uv, solar_radiation," +
                "storm_rain, start_date_of_current_storm, day_rain, month_rain, year_rain," +
                "day_ET, month_ET, year_ET, soil_moisture1, soil_moisture2," +
                "soil_moisture3, soil_moisture4, leaf_wetness1, leaf_wetness2, leaf_wetness3," +
                "leaf_wetness4, forecast_icon " +
                "FROM record " +
                "WHERE for_export = 'TRUE'  AND data_source = 'DavisVP2L1' " +
                "ORDER BY entry DESC LIMIT 1";
        String consoleSqlLoop2 = "SELECT  " +
                "two_min_avg_wind_speed, ten_min_wind_gust, wind_direction_for_the_ten_minute_wind_gust, " +
                "dew_point, heat_index, wind_chill, thsw_index, " +
                "last_fifteen_min_rain, last_hour_rain, last_24_hour_rain " +
                "FROM record " +
                "WHERE for_export = 'TRUE'  AND data_source = 'DavisVP2L2' " +
                "ORDER BY entry DESC LIMIT 1";
//        System.out.println("jdbctemplate = " + jdbcTemplate.toString());
        SqlRowSet loop1Srs = jdbcTemplate.queryForRowSet(consoleSqlLoop1);
        SqlRowSet loop2Srs = jdbcTemplate.queryForRowSet(consoleSqlLoop2);
        while (loop1Srs.next()){
            mapL1RowToDavis(loop1Srs, currentWeather);
        }
        while (loop2Srs.next()){
            mapL2RowToDavis(loop2Srs, currentWeather);
        }
        return currentWeather;
    }

    public void createRecord(DataRecord dataRecord){
        if(dataRecord.getPacketType()==0){
            createLoop1Record((Loop1Reading) dataRecord);
        } else if (dataRecord.getPacketType()==1){
            createLoop2Record((Loop2Reading) dataRecord);
        } else System.out.println("Unknown type tried to create a weather record.");
    }


    private void createLoop1Record(Loop1Reading l1) {
        String loop1Sql = "INSERT INTO record (entry_date, entry_time, for_export, data_source, bar_trend," +
                "packet_type, next_record, barometer, inside_temperature, inside_humidity," +
                "outside_temperature, wind_speed, ten_min_avg_wind_speed, wind_direction, extra_temperature1," +
                "extra_temperature2, extra_temperature3, extra_temperature4, extra_temperature5, extra_temperature6," +
                "extra_temperature7, soil_temperature1, soil_temperature2, soil_temperature3, soil_temperature4," +
                "leaf_temperature1, leaf_temperature2, leaf_temperature3, leaf_temperature4, outside_humidity," +
                "extra_humidity1, extra_humidity2, extra_humidity3, extra_humidity4, extra_humidity5," +
                "extra_humidity6, extra_humidity7, rain_rate, uv, solar_radiation," +
                "storm_rain, start_date_of_current_storm, day_rain, month_rain, year_rain," +
                "day_ET, month_ET, year_ET, soil_moisture1, soil_moisture2," +
                "soil_moisture3, soil_moisture4, leaf_wetness1, leaf_wetness2, leaf_wetness3," +
                "leaf_wetness4, forecast_icon)" +
                "VALUES (?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?)";

        jdbcTemplate.update(loop1Sql, getDatestamp(), getTimestamp(), true, "DavisVP2L1", l1.getBarTrend(),
                l1.getPacketType(), l1.getNextRecord(), l1.getBarometer(), l1.getInsideTemperature(), l1.getInsideHumidity(),
                l1.getOutsideTemperature(), l1.getWindSpeed(), l1.getTenMinAvgWindSpeed(), l1.getWindDirection(), l1.getExtraTemperature1(),
                l1.getExtraTemperature2(), l1.getExtraTemperature3(), l1.getExtraTemperature4(), l1.getExtraTemperature5(),
                l1.getExtraTemperature6(),
                l1.getExtraTemperature7(), l1.getSoilTemperature1(), l1.getSoilTemperature2(),
                l1.getSoilTemperature3(), l1.getSoilTemperature4(),
                l1.getLeafTemperature1(), l1.getLeafTemperature2(), l1.getLeafTemperature3(),
                l1.getLeafTemperature4(), l1.getOutsideHumidity(),
                l1.getExtraHumidity1(), l1.getExtraHumidity2(), l1.getExtraHumidity3(), l1.getExtraHumidity4(),
                l1.getExtraHumidity5(),
                l1.getExtraHumidity6(), l1.getExtraHumidity7(), l1.getRainRate(), l1.getUv(), l1.getSolarRadiation(),
                l1.getStormRain(), l1.getStartDateOfCurrentStorm(), l1.getDayRain(), l1.getMonthRain(), l1.getYearRain(),
                l1.getDayET(), l1.getMonthET(), l1.getYearET(), l1.getSoilMoisture1(), l1.getSoilMoisture2(),
                l1.getSoilMoisture3(), l1.getSoilMoisture4(), l1.getLeafWetness1(), l1.getLeafWetness2(),
                l1.getLeafWetness3(),
                l1.getLeafWetness4(), l1.getForecastIcon());
//        System.out.println("Loop1 database entry created. Get outTemp = " + l1.getOutsideTemperature());
    }

    private void createLoop2Record(Loop2Reading l2) {
        String loop2Sql = "INSERT INTO record (entry_date, entry_time, for_export, data_source, bar_trend," +
                "barometer, inside_temperature, inside_humidity, outside_temperature, wind_speed," +
                "wind_direction, ten_min_avg_wind_speed, two_min_avg_wind_speed, ten_min_wind_gust, wind_direction_for_the_ten_minute_wind_gust," +
                "dew_point, outside_humidity, heat_index, wind_chill, thsw_index," +
                "rain_rate, uv, solar_radiation, storm_rain, start_date_of_current_storm, " +
                "day_rain, last_fifteen_min_rain, last_hour_rain, day_ET, last_24_hour_rain," +
                "packet_type) " +
                "VALUES (?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?)";
        jdbcTemplate.update(loop2Sql, getDatestamp(), getTimestamp(), true, "DavisVP2L2", l2.getBarTrend(),
                l2.getBarometer(), l2.getInsideTemperature(), l2.getInsideHumidity(), l2.getOutsideTemperature(), l2.getWindSpeed(),
                l2.getWindDirection(), l2.getTenMinAvgWindSpeed(), l2.getTwoMinAvgWindSpeed(), l2.getTenMinWindGust(),
                l2.getWindDirectionForTheTenMinuteWindGust(),
                l2.getDewPoint(), l2.getOutsideHumidity(), l2.getHeatIndex(), l2.getWindChill(), l2.getThswIndex(),
                l2.getRainRate(), l2.getUv(), l2.getSolarRadiation(), l2.getStormRain(), l2.getStartDateOfCurrentStorm(),
                l2.getDayRain(), l2.getLast15MinRain(), l2.getLastHourRain(), l2.getDayET(), l2.getLast24HourRain(),
                l2.getPacketType());
//        System.out.println("Loop2 database entry created");
    }


    @Override
    public LocalDate getDatestamp() {
        return LocalDate.now();
    }
    @Override
    public LocalTime getTimestamp() {
        return LocalTime.now();
    }

    private void mapL1RowToDavis(SqlRowSet l1srs, CurrentWeather currentWeather){
        currentWeather.setBarTrend(l1srs.getInt("bar_trend"));
        currentWeather.setBarometer(l1srs.getDouble("barometer"));
        currentWeather.setInsideTemperature(l1srs.getDouble( "inside_temperature"));
        currentWeather.setInsideHumidity(l1srs.getInt("inside_humidity"));

        currentWeather.setOutsideTemperature(l1srs.getDouble("outside_temperature"));
        currentWeather.setWindSpeed(l1srs.getInt("wind_speed"));
        currentWeather.setTenMinAvgWindSpeed(l1srs.getInt("ten_min_avg_wind_speed"));
        currentWeather.setWindDirection(l1srs.getInt("wind_direction"));
        currentWeather.setExtraTemperature1(l1srs.getDouble("extra_temperature1"));

        currentWeather.setExtraTemperature2(l1srs.getDouble("extra_temperature2"));
        currentWeather.setExtraTemperature3(l1srs.getDouble("extra_temperature3"));
        currentWeather.setExtraTemperature4(l1srs.getDouble("extra_temperature4"));
        currentWeather.setExtraTemperature5(l1srs.getDouble("extra_temperature5"));
        currentWeather.setExtraTemperature6(l1srs.getDouble("extra_temperature6"));

        currentWeather.setExtraTemperature7(l1srs.getDouble("extra_temperature7"));
        currentWeather.setSoilTemperature1(l1srs.getDouble("soil_temperature1"));
        currentWeather.setSoilTemperature2(l1srs.getDouble("soil_temperature2"));
        currentWeather.setSoilTemperature3(l1srs.getDouble("soil_temperature3"));
        currentWeather.setSoilTemperature4(l1srs.getDouble("soil_temperature4"));

        currentWeather.setLeafTemperature1(l1srs.getDouble("leaf_temperature1"));
        currentWeather.setLeafTemperature2(l1srs.getDouble("leaf_temperature2"));
        currentWeather.setLeafTemperature3(l1srs.getDouble("leaf_temperature3"));
        currentWeather.setLeafTemperature4(l1srs.getDouble("leaf_temperature4"));
        currentWeather.setOutsideHumidity(l1srs.getInt("outside_humidity"));

        currentWeather.setExtraHumidity1(l1srs.getInt("extra_humidity1"));
        currentWeather.setExtraHumidity2(l1srs.getInt("extra_humidity2"));
        currentWeather.setExtraHumidity3(l1srs.getInt("extra_humidity3"));
        currentWeather.setExtraHumidity4(l1srs.getInt("extra_humidity4"));
        currentWeather.setExtraHumidity5(l1srs.getInt("extra_humidity5"));

        currentWeather.setExtraHumidity6(l1srs.getInt("extra_humidity6"));
        currentWeather.setExtraHumidity7(l1srs.getInt("extra_humidity7"));
        currentWeather.setRainRate(l1srs.getDouble("rain_rate"));
        currentWeather.setUv(l1srs.getInt("uv"));
        currentWeather.setSolarRadiation(l1srs.getInt("solar_radiation"));

        currentWeather.setStormRain(l1srs.getDouble("storm_rain"));
        if (l1srs.getString("start_date_of_current_storm")!=null){
            currentWeather.setStartDateOfCurrentStorm(l1srs.getDate("start_date_of_current_storm").toLocalDate());
        } else {
            currentWeather.setStartDateOfCurrentStorm(null);
        }
        currentWeather.setDayRain(l1srs.getDouble("day_rain"));
        currentWeather.setMonthRain(l1srs.getDouble("month_rain"));
        currentWeather.setYearRain(l1srs.getDouble("year_rain"));

        currentWeather.setDayET(l1srs.getDouble("day_ET"));
        currentWeather.setMonthET(l1srs.getDouble("month_ET"));
        currentWeather.setYearET(l1srs.getDouble("year_ET"));
        currentWeather.setSoilMoisture1(l1srs.getDouble("soil_moisture1"));
        currentWeather.setSoilMoisture2(l1srs.getDouble("soil_moisture2"));

        currentWeather.setSoilMoisture3(l1srs.getDouble("soil_moisture3"));
        currentWeather.setSoilMoisture4(l1srs.getDouble("soil_moisture4"));
        currentWeather.setLeafWetness1(l1srs.getInt("leaf_wetness1"));
        currentWeather.setLeafWetness2(l1srs.getInt("leaf_wetness2"));
        currentWeather.setLeafWetness3(l1srs.getInt("leaf_wetness3"));

        currentWeather.setLeafWetness4(l1srs.getInt("leaf_wetness4"));
        currentWeather.setForecastIcon(l1srs.getInt("forecast_icon"));

//        System.out.println("l1srs date: " + l1srs.getDate("entry_date"));
    }

    private void mapL2RowToDavis(SqlRowSet l2srs, CurrentWeather currentWeather){
        currentWeather.setTwoMinAvgWindSpeed(l2srs.getInt("two_min_avg_wind_speed"));
        currentWeather.setTenMinuteWindGust(l2srs.getDouble("ten_min_wind_gust"));
        currentWeather.setWindDirectionForTheTenMinuteWindGust(l2srs.getInt("wind_direction_for_the_ten_minute_wind_gust"));

        currentWeather.setDewPoint(l2srs.getInt("dew_point"));
        currentWeather.setHeatIndex(l2srs.getInt("heat_index"));
        currentWeather.setWindChill(l2srs.getInt("wind_chill"));
        currentWeather.setThswIndex(l2srs.getInt("thsw_index"));

        currentWeather.setLastFifteenMinRain(l2srs.getDouble("last_fifteen_min_rain"));
        currentWeather.setLastHourRain(l2srs.getDouble("last_hour_rain"));
        currentWeather.setLast24HourRain(l2srs.getDouble("last_24_hour_rain"));
    }

    private LocalDate yesterdayDate(){
        return getDatestamp().minusDays(1);
    }
    private void getPreviousTemperatureHigh(int daysOffset){

        String previousTempHighSql = " SELECT MAX(outside_temperature)\n" +
                "                FROM record\n" +
                "                WHERE     for_export = 'TRUE'\n" +
                "        AND entry_date = " +
                getDatestamp().minusDays(daysOffset);

    }

    private void getPreviousTemperatureLow(int daysOffset){

        String previousTempLowSql = " SELECT MIN(outside_temperature)\n" +
                "                FROM record\n" +
                "                WHERE     for_export = 'TRUE'\n" +
                "        AND entry_date = " +
                getDatestamp().minusDays(daysOffset);

    }
    private void getPreviousTemperatureAvg(int daysOffset){

        String previousTempSql = " SELECT AVG(outside_temperature)\n" +
                "                FROM record\n" +
                "                WHERE     for_export = 'TRUE'\n" +
                "        AND entry_date = " +
                getDatestamp().minusDays(daysOffset);

    }
    private void getPreviousTemperatureChange(int daysOffset){
        String previousTempChange =
        "SELECT(SELECT MAX(outside_temperature) " +
                "FROM public.record "+
                "WHERE for_export = 'TRUE' "+
        "AND entry_date =" + getDatestamp().minusDays(daysOffset) +
                "-(SELECT MIN(outside_temperature) " +
        "FROM record "+
               "WHERE for_export = 'TRUE' "+
        "AND entry_date =" +getDatestamp().minusDays(daysOffset);
    }

    private void getPreviousHumidityAvg(int daysOffset){

        String previousHumAvgSql = " SELECT AVG(outside_humidity)\n" +
                "                FROM record\n" +
                "                WHERE for_export = 'TRUE'\n" +
                "        AND entry_date = " +
                getDatestamp().minusDays(daysOffset);

    }
    private void getPreviousHumidityHigh(int daysOffset){

        String previousHumHighSql = " SELECT MAX(outside_humidity)\n" +
                "                FROM record\n" +
                "                WHERE for_export = 'TRUE'\n" +
                "        AND entry_date = " +
                getDatestamp().minusDays(daysOffset);

    }

    private void getPreviousHumidityLow(int daysOffset){
        String previousHumLowSql = " SELECT MIN(outside_humidity)\n" +
                "                FROM record\n" +
                "                WHERE for_export = 'TRUE'\n" +
                "        AND entry_date = " +
                getDatestamp().minusDays(daysOffset);
    }

    private void getPreviousTotalRain(int daysOffset){
        String previousRain = "SELECT MAX(day_rain) FROM record WHERE WHERE for_export = 'TRUE'"+
                "        AND entry_date = " +
                getDatestamp().minusDays(daysOffset);
    }

    private void getPreviousBarometerHigh(int daysOffset){
        String previousBarHighSql = " SELECT MAX(outside_humidity)\n" +
                "                FROM record\n" +
                "                WHERE for_export = 'TRUE'\n" +
                "        AND entry_date = " +
                getDatestamp().minusDays(daysOffset);
    }
    private void getPreviousBarometerLow(int daysOffset){
        String previousBarLowSql = " SELECT MIN(outside_humidity)\n" +
                "                FROM record\n" +
                "                WHERE for_export = 'TRUE'\n" +
                "        AND entry_date = " +
                getDatestamp().minusDays(daysOffset);
    }
    private void getPreviousWindHigh(int daysOffset){
        String previousWindHighSql = " SELECT MAX(wind_speed)\n" +
                "                FROM record\n" +
                "                WHERE for_export = 'TRUE'\n" +
                "        AND entry_date = " +
                getDatestamp().minusDays(daysOffset);
    }
    private void getAvgWindSpeed(int daysOffset){
        String previousAvgWindSpeed = "SELECT Round(Avg(wind_speed),2) " +
                " FROM record WHERE entry_date = "+
                getDatestamp().minusDays(daysOffset);
    }


/*    Vars:

    entry_date,
    entry_time,
	for_export,
    data_source,
    bar_trend,
    packet_type,
    next_record,
    barometer,
    inside_temperature,
    inside_humidity,
    outside_temperature,
    wind_speed,
    ten_min_avg_wind_speed,
    two_min_avg_wind_speed,
    ten_min_wind_gust,
    wind_direction,
    wind_direction_for_the_ten_minute_wind_gust,
    dew_point,
    heat_index,
    wind_chill,
    thsw_index,
    extra_temperature1,
    extra_temperature2,
    extra_temperature3,
    extra_temperature4,
    extra_temperature5,
    extra_temperature6,
    extra_temperature7,
    soil_temperature1,
    soil_temperature2,
    soil_temperature3,
    soil_temperature4,
    leaf_temperature1,
    leaf_temperature2,
    leaf_temperature3,
    leaf_temperature4,
    outside_humidity,
    extra_humidity1,
    extra_humidity2,
    extra_humidity3,
    extra_humidity4,
    extra_humidity5,
    extra_humidity6,
    extra_humidity7,
    rain_rate,
    uv,
    solar_radiation,
    storm_rain,
    start_date_of_current_storm,
    day_rain,
    month_rain,
    year_rain,
    last_fifteen_min_rain,
    last_hour_rain,
    last_24_hour_rain,
    day_ET,
    month_ET,
    year_ET,
    soil_moisture1,
    soil_moisture2,
    soil_moisture3,
    soil_moisture4,
    leaf_wetness1,
    leaf_wetness2,
    leaf_wetness3,
    leaf_wetness4,
    forecast_icon

    end vars*/
}

