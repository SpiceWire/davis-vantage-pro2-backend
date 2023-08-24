package spicewire.davisinterface.Dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import spicewire.davisinterface.Model.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class uses JDBC to create and retrieve weather records from a database. The createRecord method
 * accepts a WeatherRecord object and uses the getPacketType method on it to determine how to enter
 * it into the database.
 *
 * Class populates and returns a CurrentWeather DTO.
 */

public class JdbcWeatherRecord implements WeatherRecord {

    private final String url = "jdbc:postgresql://localhost:5432/WeatherDB";
    private final String user = "postgres";
    private final String password = "postgres";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    private final JdbcTemplate jdbcTemplate;
    CurrentWeather currentWeather = new CurrentWeather();
    BasicDataSource dataSource = new BasicDataSource();

    public JdbcWeatherRecord(DataSource datasource) {
        jdbcTemplate = new JdbcTemplate(datasource);
    }

    public CurrentWeather getWeather(){
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
        SqlRowSet loop1Srs = jdbcTemplate.queryForRowSet(consoleSqlLoop1);
        SqlRowSet loop2Srs = jdbcTemplate.queryForRowSet(consoleSqlLoop2);
        while (loop1Srs.next()){
            mapL1RowToDavis(loop1Srs);
        }
        while (loop2Srs.next()){
            mapL2RowToDavis(loop2Srs);
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
        System.out.println("Loop1 database entry created. Get outTemp = " + l1.getOutsideTemperature());
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
        System.out.println("Loop2 database entry created");
    }


    @Override
    public LocalDate getDatestamp() {
        return LocalDate.now();
    }
    @Override
    public LocalTime getTimestamp() {
        return LocalTime.now();
    }

    private void mapL1RowToDavis(SqlRowSet l1srs){
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

        System.out.println("l1srs date: " + l1srs.getDate("entry_date"));
    }

    private void mapL2RowToDavis(SqlRowSet l2srs){
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

