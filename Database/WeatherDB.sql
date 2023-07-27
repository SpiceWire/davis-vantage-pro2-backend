

BEGIN TRANSACTION;
DROP TABLE IF EXISTS station, record, alarms;

CREATE TABLE station(
    station_id serial NOT NULL ,
    type_number int,
    type_name varchar,
	low_temp_alarm_activated boolean,
	high_temp_alarm_activated boolean,
	low_hum_alarm_activated boolean,
	high_hum_alarm_activated boolean,  --timestamp?
    constraint PK_station PRIMARY KEY (station_id)
);
COMMIT;


BEGIN TRANSACTION;

CREATE TABLE alarm(
    station_id serial PRIMARY KEY,
    type_number int,
    type_name varchar,
    active boolean,
    indoor boolean,
	humidity boolean,
	temperature boolean,
	time_alarm boolean,
	rain boolean,
	soil boolean,
	leaf boolean,
	falling_bar boolean,
	rising_bar boolean,
	low_inside_temp boolean,
	high_inside_temp boolean,
	low_inside_humidity boolean,
	high_inside_humidity boolean,
	high_rain_rate boolean,
	fifteen_min_rain boolean,
	twenty_four_hour_rain boolean,
	storm_total_rain boolean,
	daily_ET boolean,
	low_outside_temp boolean,
	high_outside_temp boolean,
	wind_speed boolean,
	ten_min_avg_speed boolean,
	low_dewpoint boolean,
	high_dewpoint boolean,
	high_heat boolean,
	low_wind_chill boolean,
	high_thsw boolean,
	high_solar_rad boolean,
	uv_dose boolean,
	uv_dose_enabled boolean,
	low_humidity boolean,
	high_humidity boolean,
	temp_hum1_low_temp boolean,  --new temp/hum alarm 1
	temp_hum1_high_temp boolean,
	temp_hum1_low_hum boolean,
	temp_hum1_high_hum boolean,
	temp_hum2_low_temp boolean,  --new temp/hum alarm 2
	temp_hum2_high_temp boolean,
	temp_hum2_low_hum boolean,
	temp_hum2_high_hum boolean,
	temp_hum3_low_temp boolean,  --new temp/hum alarm 3
	temp_hum3_high_temp boolean,
	temp_hum3_low_hum boolean,
	temp_hum3_high_hum boolean,
	temp_hum4_low_temp boolean,  --new temp/hum alarm 4
	temp_hum4_high_temp boolean,
	temp_hum4_low_hum boolean,
	temp_hum4_high_hum boolean,
	temp_hum5_low_temp boolean,  --new temp/hum alarm 5
	temp_hum5_high_temp boolean,
	temp_hum5_low_hum boolean,
	temp_hum5_high_hum boolean,
	temp_hum6_low_temp boolean,  --new temp/hum alarm 6
	temp_hum6_high_temp boolean,
	temp_hum6_low_hum boolean,
	temp_hum6_high_hum boolean,
	temp_hum7_low_temp boolean,  --new temp/hum alarm 7
	temp_hum7_high_temp boolean,
	temp_hum7_low_hum boolean,
	temp_hum7_high_hum boolean,
	leaf_soil1_low_leaf_wetness boolean, --new leaf/soil alarm 1
	leaf_soil1_high_leaf_wetness boolean,
	leaf_soil1_low_soil_moisture boolean,
	leaf_soil1_high_soil_moisture boolean,
	leaf_soil1_low_leaf_temp boolean,
	leaf_soil1_high_leaf_temp boolean,	
	leaf_soil1_low_soil_temp boolean,
	leaf_soil1_high_soil_temp boolean,	
	leaf_soil2_low_leaf_wetness boolean, --new leaf/soil alarm 2
	leaf_soil2_high_leaf_wetness boolean,
	leaf_soil2_low_soil_moisture boolean,
	leaf_soil2_high_soil_moisture boolean,
	leaf_soil2_low_leaf_temp boolean,
	leaf_soil2_high_leaf_temp boolean,	
	leaf_soil2_low_soil_temp boolean,
	leaf_soil2_high_soil_temp boolean,
	leaf_soil3_low_leaf_wetness boolean, --new leaf/soil alarm 3
	leaf_soil3_high_leaf_wetness boolean,
	leaf_soil3_low_soil_moisture boolean,
	leaf_soil3_high_soil_moisture boolean,
	leaf_soil3_low_leaf_temp boolean,
	leaf_soil3_high_leaf_temp boolean,	
	leaf_soil3_low_soil_temp boolean,
	leaf_soil3_high_soil_temp boolean,	
	leaf_soil4_low_leaf_wetness boolean, --new leaf/soil alarm 4
	leaf_soil4_high_leaf_wetness boolean,
	leaf_soil4_low_soil_moisture boolean,
	leaf_soil4_high_soil_moisture boolean,
	leaf_soil4_low_leaf_temp boolean,
	leaf_soil4_high_leaf_temp boolean,	
	leaf_soil4_low_soil_temp boolean,
	leaf_soil4_high_soil_temp boolean,
	forecast_icon int
   --L1: page 24
);

COMMIT;

BEGIN TRANSACTION;
INSERT INTO alarm (station_id) VALUES  --creates eight sensor stations, including ISS
(1), (2), (3),(4),(5),(6),(7),(8);
COMMIT;

BEGIN TRANSACTION;
CREATE TABLE record (
    entry serial NOT NULL PRIMARY KEY,
    entry_date date NOT NULL,
    entry_time time NOT NULL,
	for_export boolean NOT NULL,
    data_source varchar(50) NOT NULL,
    bar_trend int,
    packet_type int,
    next_record int,
    barometer numeric(4,2),
    inside_temperature numeric(5,2),
    inside_humidity int,
    outside_temperature numeric(5,2),
    wind_speed int,
    ten_min_avg_wind_speed int,
    two_min_avg_wind_speed int,
    ten_min_wind_gust numeric(5,2),
    wind_direction int,
    wind_direction_for_the_ten_minute_wind_gust int,
    dew_point int,
    heat_index int,
    wind_chill int,
    thsw_index int,
    extra_temperature1 numeric(5,2),
    extra_temperature2 numeric(5,2),
    extra_temperature3 numeric(5,2),
    extra_temperature4 numeric(5,2),
    extra_temperature5 numeric(5,2),
    extra_temperature6 numeric(5,2),
    extra_temperature7 numeric(5,2),
    soil_temperature1 numeric(5,2),
    soil_temperature2 numeric(5,2),
    soil_temperature3 numeric(5,2),
    soil_temperature4 numeric(5,2),
    leaf_temperature1 numeric(5,2),
    leaf_temperature2 numeric(5,2),
    leaf_temperature3 numeric(5,2),
    leaf_temperature4 numeric(5,2),
    outside_humidity int,
    extra_humidity1 int,
    extra_humidity2 int,
    extra_humidity3 int,
    extra_humidity4 int,
    extra_humidity5 int,
    extra_humidity6 int,
    extra_humidity7 int,
    rain_rate numeric(6,2),
    uv int,
    solar_radiation int,
    storm_rain numeric(6,2),
    start_date_of_current_storm date,
    day_rain numeric(6,2),
    month_rain numeric(7,2),
    year_rain numeric(7,2),
    last_fifteen_min_rain numeric(6,2),
    last_hour_rain numeric(6,2),
    last_24_hour_rain numeric(6,2),
    day_ET numeric(8,4),
    month_ET numeric(8,4),
    year_ET numeric(8,4),
    soil_moisture1 numeric(5,1),
    soil_moisture2 numeric(5,1),
    soil_moisture3 numeric(5,1),
    soil_moisture4 numeric(5,1),
    leaf_wetness1 int,
    leaf_wetness2 int,
    leaf_wetness3 int,
    leaf_wetness4 int,
    forecast_icon int
  );
  
 COMMIT;

  
-- CREATE TABLE console(
--     setting serial,
--     setting_name varchar,
--     setting_value varchar,
-- --    L1: icons, battery voltage, transmitter battery status, sunrise and sunset, CRC,
-- --   L2:
--   both: get version number and date, time, date,
--   version number and date are found (boolean)
--   com port settings are set (boolean)
--    barometric_reduction_method int, L2 p26
--   altimeter

-- )

-- CREATE TABLE calibration(
--     --   barometric calibration number
--     --   barometric sensor raw reading
--     --   absolute barometric pressure
-- )



