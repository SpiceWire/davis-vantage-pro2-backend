package spicewire.davisinterface.Dao;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.text.CaseUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import spicewire.davisinterface.DavisinterfaceApplication;
import spicewire.davisinterface.Model.*;

import java.lang.invoke.TypeDescriptor;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;


import static java.time.LocalTime.now;

/**
 * Class uses JDBC to create and retrieve weather database records. The createRecord method
 * accepts a WeatherRecord object and uses the getPacketType method to determine how to enter
 * it into the database.
 *
 * Class populates and returns a CurrentWeather DTO.
 */
//todo make string list of L1 and L2 var names, concat it for the JDBC query, use it as a lookup table
//todo add combined wind direction, speed to hourly search
//todo combine daily tempLow, tempHigh, windGust,etc to a single method with these as vars
//todo see if there can be collisions b/t loop1 and loop 2 data when accessing static class

public class JdbcWeatherRecord implements WeatherRecord {

    private final JdbcTemplate jdbcTemplate;
    private DecimalFormat df = new DecimalFormat("#.##");

    public JdbcWeatherRecord() {
        BasicDataSource dataSource = DavisinterfaceApplication.getDatasource();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public CurrentWeather getWeather() {
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

        SqlRowSet loop1Srs = jdbcTemplate.queryForRowSet(consoleSqlLoop1);
        SqlRowSet loop2Srs = jdbcTemplate.queryForRowSet(consoleSqlLoop2);
        while (loop1Srs.next()) {
            mapL1RowToDavis(loop1Srs, currentWeather);
        }
        while (loop2Srs.next()) {
            mapL2RowToDavis(loop2Srs, currentWeather);
        }
        return currentWeather;
    }

    public void createRecord(DataRecord dataRecord) {
        if (dataRecord.getPacketType() == 0) {
            createLoop1Record((Loop1Reading) dataRecord);
            createLoop1Alarms((Loop1Reading) dataRecord);
        } else if (dataRecord.getPacketType() == 1) {
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
                "leaf_wetness4, forecast_icon, transmitter_battery_status, console_battery_voltage, time_of_sunrise, " +
                "time_of_sunset, inside_alarms, rain_alarms, outside_alarms1, outside_alarms2)" +
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
                "?,?,?,?,?," +
                "?,?,?,?,?)";

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
                l1.getLeafWetness4(), l1.getForecastIcons(), l1.getTransmitterBatteryStatus(),
                l1.getConsoleBatteryVoltage(), l1.getTimeOfSunrise(),
                l1.getTimeOfSunset(), l1.getInsideAlarms(), l1.getRainAlarms(), l1.getOutsideAlarms1(),
                l1.getOutsideAlarms2());

    }


    private void createLoop1Alarms(Loop1Reading l1) {
        String loop1SqlAlarms = "INSERT INTO alarm (entry_date, entry_time, for_export, data_source, " +
                "inside, rain, outside_alarms1, outside_alarms2, outside_humidity_alarms, " +
                "extra_temp_hum_alarms1, extra_temp_hum_alarms2, extra_temp_hum_alarms3, extra_temp_hum_alarms4, " +
                "extra_temp_hum_alarms5, " +
                "extra_temp_hum_alarms6, extra_temp_hum_alarms7, soil_leaf_alarms1, soil_leaf_alarms2, soil_leaf_alarms3, " +
                "soil_leaf_alarms4) " +
                "VALUES (?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?)";

        jdbcTemplate.update(loop1SqlAlarms, getDatestamp(), getTimestamp(), true, "DavisVP2L1",
                l1.getInsideAlarms(), l1.getRainAlarms(), l1.getOutsideAlarms1(), l1.getOutsideAlarms2(),
                l1.getOutsideHumidityAlarms(),
                l1.getExtraTempHumAlarms1(), l1.getExtraTempHumAlarms2(), l1.getExtraTempHumAlarms3(),
                l1.getExtraTempHumAlarms4(), l1.getExtraTempHumAlarms5(),
                l1.getExtraTempHumAlarms6(), l1.getExtraTempHumAlarms7(), l1.getSoilLeafAlarms1(),
                l1.getSoilLeafAlarms2(), l1.getSoilLeafAlarms3(),
                l1.getSoilLeafAlarms4());
    }

    private void createLoop2Record(Loop2Reading l2) {
        String loop2Sql = "INSERT INTO record (entry_date, entry_time, for_export, data_source, bar_trend," +
                "barometer, inside_temperature, inside_humidity, outside_temperature, wind_speed," +
                "wind_direction, ten_min_avg_wind_speed, two_min_avg_wind_speed, ten_min_wind_gust, wind_direction_for_the_ten_minute_wind_gust," +
                "dew_point, outside_humidity, heat_index, wind_chill, thsw_index," +
                "rain_rate, uv, solar_radiation, storm_rain, start_date_of_current_storm, " +
                "daily_rain, last_fifteen_min_rain, last_hour_rain, day_ET, last_24_hour_rain," +
                "packet_type, barometric_reduction_method, user_entered_barometric_offset, barometric_calibration_number, " +
                "absolute_barometric_pressure, " +
                "altimeter_setting, next_ten_min_wind_speed_graph_pointer, next_fifteen_min_wind_speed_graph_pointer," +
                "next_hourly_wind_speed_graph_pointer, next_daily_wind_speed_graph_pointer, " +
                "next_minute_rain_graph_pointer, index_to_the_minute_within_an_hour, next_monthly_rain, " +
                "next_yearly_rain, next_seasonal_rain, " +
                "next_rain_storm_graph_pointer) " +
                "VALUES (?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
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
                l2.getDayRain(), l2.getLast15MinRain(), l2.getLastHourRain(), l2.getDailyET(), l2.getLast24HourRain(),
                l2.getPacketType(), l2.getBarometricReductionMethod(), l2.getUserEnteredBarometricOffset(),
                l2.getBarometricCalibrationNumber(), l2.getAbsoluteBarometricPressure(),
                l2.getAltimeterSetting(), l2.getNextTenMinWindSpeedGraphPointer(), l2.getNextFifteenMinWindSpeedGraphPointer(),
                l2.getNextHourlyWindSpeedGraphPointer(), l2.getNextDailyWindSpeedGraphPointer(),
                l2.getNextMinuteRainGraphPointer(), l2.getIndexToTheMinuteWithinAnHour(), l2.getNextMonthlyRain(),
                l2.getNextYearlyRain(), l2.getNextSeasonalRain(),
                l2.getNextRainStormGraphPointer()
        );

    }


    @Override
    public LocalDate getDatestamp() {
        return LocalDate.now();
    }

    @Override
    public LocalTime getTimestamp() {
        return now();
    }

    private void mapL1RowToDavis(SqlRowSet l1srs, CurrentWeather currentWeather) {
        currentWeather.setBarTrend(l1srs.getInt("bar_trend"));
        currentWeather.setBarometer(l1srs.getDouble("barometer"));
        currentWeather.setInsideTemperature(l1srs.getDouble("inside_temperature"));
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
        if (l1srs.getString("start_date_of_current_storm") != null) {
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

    }

    private void mapL2RowToDavis(SqlRowSet l2srs, CurrentWeather currentWeather) {
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


    /**
     * Returns an AggregateWeather object containing high/low/avg weather data of one
     * day, identified by the days offset from today.
     *
     * @param daysOffset
     * @return
     */
    public AggregateWeather getPreviousWeather(int daysOffset) {
        AggregateWeather aggregateWeather = new AggregateWeather();
        aggregateWeather.setTemperatureHigh(getPreviousTemperatureHigh(daysOffset));
        aggregateWeather.setTemperatureLow(getPreviousTemperatureLow(daysOffset));
        aggregateWeather.setTemperatureAvg(getPreviousTemperatureAvg(daysOffset));
        aggregateWeather.setTemperatureChange(getPreviousTemperatureChange(daysOffset));
        aggregateWeather.setBarometerHigh(getPreviousBarometerHigh(daysOffset));
        aggregateWeather.setBarometerLow(getPreviousBarometerLow(daysOffset));
        aggregateWeather.setHumidityHigh(getPreviousHumidityHigh(daysOffset));
        aggregateWeather.setHumidityLow(getPreviousHumidityLow(daysOffset));
        aggregateWeather.setHumidityAvg(getPreviousHumidityAvg(daysOffset));
        aggregateWeather.setTotalRain(getPreviousTotalRain(daysOffset));
        aggregateWeather.setWindAvg(getAvgWindSpeed(daysOffset));
        aggregateWeather.setWindHigh(getPreviousWindHigh(daysOffset));
        aggregateWeather.setWindGust(getPreviousWindGust(daysOffset));
        aggregateWeather.setAccumulatedRain(getAccumulatedRainByDays(daysOffset));
        return aggregateWeather;
    }


    /**
     * Returns accumulated rain (each day's rain total, summed) from a day offset from now();
     *
     * @param days number of days offset from today's date
     * @return double, sum of all days' rain since offset.
     */
    public double getAccumulatedRainByDays(int days) {
        double accumulatedRain = 0;
        for (int i = 0; i <= days; i++) {
            accumulatedRain += getPreviousTotalRain(i);
        }
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.valueOf(df.format(accumulatedRain));
    }

    private double getPreviousWindGust(int daysOffset) {
        double windGust = 0;
        String previousTempHighSql = " SELECT MAX(ten_min_wind_gust)\n" +
                "                FROM record\n" +
                "                WHERE     for_export = 'TRUE'\n" +
                "        AND entry_date = '" +
                getDatestamp().minusDays(daysOffset) + "'";
        SqlRowSet previousTempHighSrs = jdbcTemplate.queryForRowSet(previousTempHighSql);
        while (previousTempHighSrs.next()) {
            windGust = previousTempHighSrs.getDouble("max");
        }
        return windGust;
    }

    private double getPreviousTemperatureHigh(int daysOffset) {
        double temperatureHigh = 0;
        String previousTempHighSql = " SELECT MAX(outside_temperature)\n" +
                "                FROM record\n" +
                "                WHERE     for_export = 'TRUE'\n" +
                "        AND entry_date = '" +
                getDatestamp().minusDays(daysOffset) + "'";
        SqlRowSet previousTempHighSrs = jdbcTemplate.queryForRowSet(previousTempHighSql);
        while (previousTempHighSrs.next()) {
            temperatureHigh = previousTempHighSrs.getDouble("max");
        }
        return temperatureHigh;
    }

    private double getPreviousTemperatureLow(int daysOffset) {
        double temperatureLow = 0;
        String previousTempLowSql = " SELECT MIN(outside_temperature)\n" +
                "                FROM record\n" +
                "                WHERE     for_export = 'TRUE'\n" +
                "        AND entry_date = '" +
                getDatestamp().minusDays(daysOffset) + "'";
        SqlRowSet previousTempLowSrs = jdbcTemplate.queryForRowSet(previousTempLowSql);
        while (previousTempLowSrs.next()) {
            temperatureLow = previousTempLowSrs.getDouble("min");
        }
        return temperatureLow;
    }

    private double getPreviousTemperatureAvg(int daysOffset) {
        double temperatureAvg = 0;
        String previousTempSql = " SELECT ROUND(AVG(outside_temperature),1)\n" +
                "                FROM record\n" +
                "                WHERE     for_export = 'TRUE'\n" +
                "        AND entry_date = '" +
                getDatestamp().minusDays(daysOffset) + "'";
        SqlRowSet previousTemperatureAvgSrs = jdbcTemplate.queryForRowSet(previousTempSql);
        while (previousTemperatureAvgSrs.next()) {
            temperatureAvg = previousTemperatureAvgSrs.getDouble("round");
        }
        return temperatureAvg;
    }

    private double getPreviousTemperatureChange(int daysOffset) {
        double temperatureChange = 0;
        String previousTempChange =
                "SELECT(SELECT MAX(outside_temperature) " +
                        "FROM record " +
                        "WHERE for_export = 'TRUE' " +
                        "AND entry_date = '" + getDatestamp().minusDays(daysOffset) +
                        "')-(SELECT MIN(outside_temperature) " +
                        "FROM record " +
                        "WHERE for_export = 'TRUE' " +
                        "AND entry_date = '" +
                        getDatestamp().minusDays(daysOffset) + "') AS difference";
        SqlRowSet previousTempChangeSrs = jdbcTemplate.queryForRowSet(previousTempChange);
        while (previousTempChangeSrs.next()) {
            temperatureChange = previousTempChangeSrs.getDouble("difference");
        }
        return temperatureChange;
    }

    private double getPreviousHumidityAvg(int daysOffset) {
        double humidityAvg = 0;
        String previousHumAvgSql = " SELECT ROUND(AVG(outside_humidity),0) " +
                "                FROM record " +
                "                WHERE for_export = 'TRUE' " +
                "        AND entry_date = '" +
                getDatestamp().minusDays(daysOffset) + "'";
        SqlRowSet previousHumidityAvgSrs = jdbcTemplate.queryForRowSet(previousHumAvgSql);
        while (previousHumidityAvgSrs.next()) {
            humidityAvg = previousHumidityAvgSrs.getDouble("round");
        }
        return humidityAvg;
    }

    private double getPreviousHumidityHigh(int daysOffset) {
        double humidityHigh = 0;
        String previousHumHighSql = " SELECT MAX(outside_humidity) " +
                "                FROM record " +
                "                WHERE for_export = 'TRUE' " +
                "        AND entry_date = '" +
                getDatestamp().minusDays(daysOffset) + "'";
        SqlRowSet previousHumHighSrs = jdbcTemplate.queryForRowSet(previousHumHighSql);
        while (previousHumHighSrs.next()) {
            humidityHigh = previousHumHighSrs.getDouble("max");
        }
        return humidityHigh;
    }

    private double getPreviousHumidityLow(int daysOffset) {
        double humidityLow = 0;
        String previousHumLowSql = " SELECT MIN(outside_humidity) " +
                "                FROM record " +
                "                WHERE for_export = 'TRUE' " +
                "        AND entry_date = '" +
                getDatestamp().minusDays(daysOffset) + "'";
        SqlRowSet previousHumLowSrs = jdbcTemplate.queryForRowSet(previousHumLowSql);
        while (previousHumLowSrs.next()) {
            humidityLow = previousHumLowSrs.getDouble("min");
        }
        return humidityLow;
    }

    public double getPreviousTotalRain(int daysOffset) {
        double previousRain = 0;
        String previousRainSql = "SELECT MAX(day_rain) FROM record WHERE for_export = 'TRUE'" +
                "        AND entry_date = '" +
                getDatestamp().minusDays(daysOffset) + "'";
        SqlRowSet previousRainSrs = jdbcTemplate.queryForRowSet(previousRainSql);
        while (previousRainSrs.next()) {
            previousRain = previousRainSrs.getDouble("max");
        }

        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(df.format(previousRain));
    }

    private double getPreviousBarometerHigh(int daysOffset) {
        double barHigh = 0;
        String previousBarHighSql = " SELECT MAX(barometer) " +
                "                FROM record " +
                "                WHERE for_export = 'TRUE' " +
                "        AND entry_date = '" +
                getDatestamp().minusDays(daysOffset) + "'";

        SqlRowSet previousBarHighSrs = jdbcTemplate.queryForRowSet(previousBarHighSql);
        while (previousBarHighSrs.next()) {
            barHigh = previousBarHighSrs.getDouble("max");
        }
        return barHigh;
    }

    private double getPreviousBarometerLow(int daysOffset) {
        double barLow = 0;
        String previousBarLowSql = " SELECT MIN(barometer) " +
                "                FROM record " +
                "                WHERE for_export = 'TRUE' " +
                "        AND entry_date = '" +
                getDatestamp().minusDays(daysOffset) + "'";
        SqlRowSet previousBarLowSrs = jdbcTemplate.queryForRowSet(previousBarLowSql);
        while (previousBarLowSrs.next()) {
            barLow = previousBarLowSrs.getDouble("min");
        }
        return barLow;
    }

    private double getPreviousWindHigh(int daysOffset) {
        double windHigh = 0;
        String previousWindHighSql = " SELECT MAX(wind_speed) " +
                "                FROM record " +
                "                WHERE for_export = 'TRUE' " +
                "        AND entry_date = '" +
                getDatestamp().minusDays(daysOffset) + "'";
        SqlRowSet previousWindHighSrs = jdbcTemplate.queryForRowSet(previousWindHighSql);
        while (previousWindHighSrs.next()) {
            windHigh = previousWindHighSrs.getDouble("max");
        }
        return windHigh;
    }

    private double getAvgWindSpeed(int daysOffset) {
        double windAvg = 0;
        String previousAvgWindSpeedSql = "SELECT ROUND(AVG(wind_speed),2) " +
                " FROM record WHERE entry_date = '" +
                getDatestamp().minusDays(daysOffset) + "'";
        ;
        SqlRowSet previousAvgWindSpeedSrs = jdbcTemplate.queryForRowSet(previousAvgWindSpeedSql);
        while (previousAvgWindSpeedSrs.next()) {
            windAvg = previousAvgWindSpeedSrs.getDouble("round");
        }
        return windAvg;
    }

    /**
     * Given the name of a table header, returns the column's first entry of each hour of the past 24 hours.
     *
     * @param tableHeader valid name of a table header
     * @return Hashmap of entry time as key, corresponding value of tableheader at entry time as value
     */
    public HashMap<LocalDateTime, String> getMapOfTimeAndHeaderValue(String tableHeader) {
        HashMap<LocalDateTime, String> headerMap = new HashMap<>();
        LocalDateTime rightNow = LocalDateTime.now();
        if (validateHeaderName(tableHeader) == false) return headerMap;
        for (int i = 0; i < 25; i++) {
            LocalDateTime backThen = rightNow.minusHours(i);
            String headerValByHour = getSqlDataByHeader(backThen, tableHeader);
            if (tableHeader.equalsIgnoreCase("barometer")) {
                headerValByHour += getBarTrendDescription(backThen);
            }
            if (tableHeader.equalsIgnoreCase("wind_speed")) {
                headerValByHour += getWindDirections(backThen);
            }
            LocalDateTime adjustedTime = backThen.with(ChronoField.MINUTE_OF_HOUR, 0).truncatedTo(ChronoUnit.MINUTES);
            headerMap.put(adjustedTime, headerValByHour);
        }
        return headerMap;
    }

    /**
     * Returns a value of a table header when given a time. Because some table headers may contain
     * blank rows, sql query specifies whether the table header is in LOOP1 or LOOP2 data.
     *
     * @param dateTime
     * @param headerName
     * @return table header value
     */
    private String getSqlDataByHeader(LocalDateTime dateTime, String headerName) {
        LocalDate searchDate = LocalDate.from(dateTime);
        LocalTime searchTime = LocalTime.from(dateTime);
        int searchHour = searchTime.getHour();
        String headerVal = "";
        StringBuilder previousHeaderDataSql = new StringBuilder(
                "SELECT " + headerName + ", entry_time " +
                        "FROM record " +
                        "WHERE (EXTRACT (hour FROM entry_time))= " + searchHour + " " +
                        "AND entry_date = '" + searchDate + "' ");
        if (headerNameInLoop2(headerName)) {
            previousHeaderDataSql.append("AND packet_type = '1' ");
        } else {
            previousHeaderDataSql.append("AND packet_type = '0' ");
        }
        previousHeaderDataSql.append("ORDER by entry_time " +
                "LIMIT 1");

        SqlRowSet previousHeaderDataSrs = jdbcTemplate.queryForRowSet(String.valueOf(previousHeaderDataSql));


        while (previousHeaderDataSrs.next()) {
            headerVal = previousHeaderDataSrs.getString(1);
        }
        return headerVal;
    }

    private String getBarTrendDescription(LocalDateTime dateTime) {
        String barTrendStr = getSqlDataByHeader(dateTime, "bar_trend");
        if(barTrendStr.isEmpty()){
            return "no data";
        }
        int barTrendInt = (int) Double.parseDouble(barTrendStr);
        String friendlyBarTrend;
        switch (barTrendInt) {
            case -60:
                friendlyBarTrend = "Falling Rapidly";
                break;
            case -20:
                friendlyBarTrend = "Falling Slowly";
                break;
            case 0:
                friendlyBarTrend = "Steady";
                break;
            case 20:
                friendlyBarTrend = "Rising Slowly";
                break;
            case 60:
                friendlyBarTrend = "Rising Rapidly";
                break;
            default:
                friendlyBarTrend = "No info";
                break;
        }
        return "  " + friendlyBarTrend;
    }

    /**
     * Gets wind direction in degrees from database, converts degrees to closest 16 point compass direction;
     * @param dateTime of windDirection data
     * @return windDirection data converted to closest 16 point compass direction
     */
    private String getWindDirections(LocalDateTime dateTime) {
        String windDirection = getSqlDataByHeader(dateTime, "wind_direction");
        if(windDirection.isEmpty()){
            return "null";
        }
        Double windDirectionInt = Double.parseDouble(windDirection);
        String[] dirArray = new String[]{"N","NNE","NE","ENE","E","ESE","SE","SSE","S","SSW","SW","WSW","W","WNW",
                "NW","NNW","N"};
        int indexOfDirection = (int)Math.floor((windDirectionInt + 11.25)/22.5);
        return "  " + dirArray[indexOfDirection];
        }


    /**
     * Contains a list of header names that are not duplicates of Loop1 header names
     * @param headerName
     * @return boolean
     */
    private boolean headerNameInLoop2(String headerName) {
        List<String> L2HeaderNames = Arrays.asList(
                "ten_min_avg_wind_speed",
                "two_min_avg_wind_speed",
                "ten_min_wind_gust",
                "wind_direction_for_the_ten_minute_wind_gust",
                "dew_point",
                "heat_index",
                "wind_chill",
                "thsw_index",
                "daily_rain",
                "last_fifteen_min_rain",
                "last_hour_rain",
                "last_24_hour_rain",
                "daily_ET",
                "month_ET",
                "year_ET",
                "barometric_reduction_method",
                "user_entered_barometric_offset",
                "barometric_calibration_number",
                "absolute_barometric_pressure",
                "barometric_sensor_raw_reading",
                "absolute_barometric_pressure",
                "altimeter_setting",
                "next_ten_min_wind_speed_graph_pointer",
                "next_fifteen_min_wind_speed_graph_pointer",
                "next_hourly_wind_speed_graph_pointer",
                "next_daily_wind_speed_graph_pointer",
                "next_minute_rain_graph_pointer",
                "next_rain_storm_graph_pointer",
                "index_to_the_minute_within_an_hour",
                "next_monthly_rain",
                "next_yearly_rain",
                "next_seasonal_rain"
        );
        return L2HeaderNames.contains(headerName);
    }


    private boolean validateHeaderName(String headerName) {
        boolean headerIsValid = false;
        String[] headerNames = {
                "entry_date",
                "entry_time",
                "for_export",
                "data_source",
                "bar_trend",
                "packet_type",
                "next_record",
                "barometer",
                "inside_temperature",
                "inside_humidity",
                "outside_temperature",
                "wind_speed",
                "ten_min_avg_wind_speed",
                "two_min_avg_wind_speed",
                "ten_min_wind_gust",
                "wind_direction",
                "wind_direction_for_the_ten_minute_wind_gust",
                "dew_point",
                "heat_index",
                "wind_chill",
                "thsw_index",
                "extra_temperature1",
                "extra_temperature2",
                "extra_temperature3",
                "extra_temperature4",
                "extra_temperature5",
                "extra_temperature6",
                "extra_temperature7",
                "soil_temperature1",
                "soil_temperature2",
                "soil_temperature3",
                "soil_temperature4",
                "leaf_temperature1",
                "leaf_temperature2",
                "leaf_temperature3",
                "leaf_temperature4",
                "outside_humidity",
                "extra_humidity1",
                "extra_humidity2",
                "extra_humidity3",
                "extra_humidity4",
                "extra_humidity5",
                "extra_humidity6",
                "extra_humidity7",
                "rain_rate",
                "uv",
                "solar_radiation",
                "storm_rain",
                "start_date_of_current_storm",
                "daily_rain",
                "day_rain",
                "month_rain",
                "year_rain",
                "last_fifteen_min_rain",
                "last_hour_rain",
                "last_24_hour_rain",
                "day_ET",
                "daily_ET",
                "month_ET",
                "year_ET",
                "soil_moisture1",
                "soil_moisture2",
                "soil_moisture3",
                "soil_moisture4",
                "leaf_wetness1",
                "leaf_wetness2",
                "leaf_wetness3",
                "leaf_wetness4",
                "forecast_icon",
                "transmitter_battery_status",
                "console_battery_voltage",
                "barometric_reduction_method",
                "user_entered_barometric_offset",
                "barometric_calibration_number",
                "barometric_sensor_raw_reading",
                "absolute_barometric_pressure",
                "altimeter_setting",
                "next_ten_min_wind_speed_graph_pointer",
                "next_fifteen_min_wind_speed_graph_pointer",
                "next_hourly_wind_speed_graph_pointer",
                "next_daily_wind_speed_graph_pointer",
                "next_minute_rain_graph_pointer",
                "next_rain_storm_graph_pointer",
                "index_to_the_minute_within_an_hour",
                "next_monthly_rain",
                "next_yearly_rain",
                "next_seasonal_rain",
                "inside_alarms",
                "rain_alarms",
                "outside_alarms1",
                "outside_alarms2",
                "outside_humidity_alarms",
                "extra_temp_hum_alarms1",
                "extra_temp_hum_alarms2",
                "extra_temp_hum_alarms3",
                "extra_temp_hum_alarms4",
                "extra_temp_hum_alarms5",
                "extra_temp_hum_alarms6",
                "extra_temp_hum_alarms7",
                "soil_leaf_alarms1",
                "soil_leaf_alarms2",
                "soil_leaf_alarms3",
                "soil_leaf_alarms4",
                "forecast_rule_number",
                "time_of_sunrise",
                "time_of_sunset",

        };
        for (String header : headerNames) {
            if (header.equals(headerName)) {
                headerIsValid = true;
            }
        }
        return headerIsValid;
    }
}


