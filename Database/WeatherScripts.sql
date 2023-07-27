
BEGIN TRANSACTION;
DROP TABLE IF EXISTS station, record, alarms;
--ALTER TABLE record ADD COLUMN IF NOT EXISTS ten_min_wind_gust NUMERIC (5,2);
SELECT outside_temperature, outside_humidity, wind_speed, wind_direction,
                bar_trend, barometer, inside_temperature,inside_humidity,
                 forecast_icon, day_rain, storm_rain, rain_rate,
                 heat_index FROM record
                WHERE for_export = 'TRUE' AND source = 'DavisVP2'
                ORDER BY entry DESC LIMIT 1

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
