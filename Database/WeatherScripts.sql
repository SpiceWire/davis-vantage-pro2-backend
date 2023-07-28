
BEGIN TRANSACTION;
DROP TABLE IF EXISTS station, record, alarm;
--ALTER TABLE record ADD COLUMN IF NOT EXISTS ten_min_wind_gust NUMERIC (5,2);
SELECT outside_temperature, outside_humidity, wind_speed, wind_direction,
                bar_trend, barometer, inside_temperature,inside_humidity,
                 forecast_icon, day_rain, storm_rain, rain_rate,
                 heat_index FROM record
                WHERE for_export = 'TRUE' AND data_source = 'DavisVP2L1'
                ORDER BY entry DESC LIMIT 1

SELECT
    wind_chill, heat_index
FROM
    record
WHERE
    (for_export = 'TRUE' AND packet_type = 1)
ORDER BY
    entry DESC
LIMIT
    1


 INSERT INTO record (entry_date, entry_time, for_export, data_source, bar_trend,
                packet_type, next_record, barometer, inside_temperature, inside_humidity,
                outside_temperature, wind_speed, ten_min_avg_wind_speed, wind_direction, extra_temperature1,
                extra_temperature2, extra_temperature3, extra_temperature4, extra_temperature5, extra_temperature6,
                extra_temperature7, soil_temperature1, soil_temperature2, soil_temperature3, soil_temperature4,
                leaf_temperature1, leaf_temperature2, leaf_temperature3, leaf_temperature4, outside_humidity,
                extra_humidity1, extra_humidity2, extra_humidity3, extra_humidity4, extra_humidity5,
                extra_humidity6, extra_humidity7, rain_rate, uv, solar_radiation,
                storm_rain, start_date_of_current_storm, day_rain, month_rain, year_rain,
                day_ET, month_ET, year_ET, soil_moisture1, soil_moisture2,
                soil_moisture3, soil_moisture4, leaf_wetness1, leaf_wetness2, leaf_wetness3,
                leaf_wetness4, forecast_icon)
                values ('2023-07-01','11:12:13', 'TRUE', 'DavisVP2L1', '20',
                '0', '223', '30.00', '73.4', '66',
                '80', '10', '15', '180', '0',
                '0', '0', '0', '0', '0',
                '0', '0', '0', '0', '0',
                '0', '0', '0', '0', '0',
                '0', '0', '0', '0', '0',
                '0', '0', '0.5', '0', '0',
                '0.75', '2023-07-01', '1.0', '2.0', '3.0',
                '0', '0', '0', '0', '0',
                '0', '0', '0', '0', '0',
                '0','8');

INSERT INTO record (entry_date, entry_time, for_export, data_source, bar_trend,
                barometer, inside_temperature, inside_humidity, outside_temperature, wind_speed,
                wind_direction, ten_min_avg_wind_speed, two_min_avg_wind_speed, ten_min_wind_gust, wind_direction_for_the_ten_minute_wind_gust,
                dew_point, outside_humidity, heat_index, wind_chill, thsw_index,
                rain_rate, uv, solar_radiation, storm_rain, start_date_of_current_storm,
                day_rain, last_fifteen_min_rain, last_hour_rain, day_ET, last_24_hour_rain,
                packet_type)
                VALUES ('2023-06-02','07:08:09', 'TRUE', 'DavisVP2L2', '-60',
                '29.00','75','50','90','17',
                '90','7','13','20','110',
                '50','60','105','0','0',
                '1.1','0','0','0.5','2023-07-01',
                '1.5','0.4','0.8','0','1.0',
                '1');
ALTER TABLE record
    ALTER COLUMN barometer [SET DATA] TYPE numeric(4,2);
--ALTER TABLE record ADD COLUMN IF NOT EXISTS ten_min_wind_gust NUMERIC (5,2);

-- BEGIN TRANSACTION;
-- SELECT entry, entry_date, entry_time,data_source, outside_temperature, outside_humidity, wind_speed, wind_direction,
--                 bar_trend, barometer, inside_temperature,inside_humidity,
--                  forecast_icon, day_rain, storm_rain, rain_rate,
--                  heat_index FROM record
--                 WHERE for_export = 'TRUE' 
--                 ORDER BY  entry DESC LIMIT 4
