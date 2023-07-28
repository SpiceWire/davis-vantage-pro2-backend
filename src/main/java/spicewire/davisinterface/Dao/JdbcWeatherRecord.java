package spicewire.davisinterface.Dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import spicewire.davisinterface.Model.DavisVP2;
import spicewire.davisinterface.Model.Loop1Reading;
import spicewire.davisinterface.Model.Loop2Reading;
import spicewire.davisinterface.Model.LoopReading;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;


public class JdbcWeatherRecord implements WeatherRecord {
    private LocalDate datestamp;
    private LocalTime timestamp;

    private final JdbcTemplate jdbcTemplate;

    public JdbcWeatherRecord(DataSource datasource) {
        jdbcTemplate = new JdbcTemplate(datasource);
    }
    private Loop1Reading l1 = new Loop1Reading();

    @Override
    public DavisVP2.DisplayWeather getDavisConsoleWeather(){
        String consoleSqlLoop1 = "SELECT outside_temperature, outside_humidity, wind_speed, wind_direction, " +
                "bar_trend, barometer, inside_temperature,inside_humidity," +
                " forecast_icon, day_rain, storm_rain, rain_rate " +
                " FROM record " +
                "WHERE for_export = 'TRUE'  AND source = 'DavisVP2' " +
                "ORDER DESC BY entry_serial LIMIT 1";
        String consoleSqlLoop2 = "SELECT wind_chill, heat_index from record WHERE (for_export = 'TRUE' AND " +
                "packet_type = 1) ORDER BY entry DESC LIMIT 1 " ;
        SqlRowSet loop1Srs = jdbcTemplate.queryForRowSet(consoleSqlLoop1);
        SqlRowSet loop2Srs = jdbcTemplate.queryForRowSet(consoleSqlLoop2);
        return mapRowToDavis(loop1Srs, loop2Srs);
    }

    @Override
    public void createRecord(LoopReading loopReading){
        System.out.println("loopreading = " + loopReading.toString());
        if (loopReading.getClass().getName().equalsIgnoreCase("Model.Loop1Reading")){
            createLoop1Record((Loop1Reading) loopReading);
        } else if (loopReading.getClass().getName().equalsIgnoreCase("Model.Loop2Reading")) {
            createLoop2Record((Loop2Reading) loopReading);
        } else System.out.println("Unknown type tried to create a Loop Record.");
    }


    public void createLoop1Record(Loop1Reading l1) {
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

    public void createLoop2Record(Loop2Reading l2) {
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


    private DavisVP2.DisplayWeather mapRowToDavis(SqlRowSet l1srs, SqlRowSet l2srs){
        DavisVP2.DisplayWeather showWeather = new DavisVP2.DisplayWeather();
        showWeather.setBarometer(l1srs.getInt("barometer"));
        showWeather.setBarTrend(l1srs.getInt("bar_trend"));
        showWeather.setDayRain(l1srs.getDouble("day_rain"));
        showWeather.setForecastIcon(l1srs.getInt("forecast_icon"));
        showWeather.setHeatIndex(l2srs.getInt("heat_index"));
        showWeather.setInsideHumidity(l1srs.getInt("inside_humidity"));
        showWeather.setInsideTemperature(l1srs.getDouble( "inside_temperature"));
        showWeather.setOutsideHumidity(l1srs.getInt("outside_humidity"));
        showWeather.setOutsideTemperature(l1srs.getDouble("outside_temperature"));
        showWeather.setRainRate(l1srs.getDouble("rain_rate"));
        showWeather.setStormRain(l1srs.getDouble("storm_rain"));
        showWeather.setWindChill(l2srs.getInt("wind_chill"));
        showWeather.setWindSpeed(l1srs.getInt("wind_speed"));
        showWeather.setWindDirection(l1srs.getInt("wind_direction"));
        return showWeather;
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

