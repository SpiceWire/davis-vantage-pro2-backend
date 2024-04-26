-- Database: WeatherDB

-- DROP DATABASE IF EXISTS "WeatherDB";

CREATE DATABASE "WeatherDB"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

COMMENT ON DATABASE "WeatherDB"
    IS 'Weather data captured from DavisVP2';

GRANT ALL ON DATABASE "WeatherDB" TO PUBLIC;

GRANT ALL ON DATABASE "WeatherDB" TO postgres WITH GRANT OPTION;

ALTER DEFAULT PRIVILEGES FOR ROLE postgres
GRANT ALL ON TABLES TO pg_checkpoint;

-- Table: public.record

-- DROP TABLE IF EXISTS public.record;

CREATE TABLE IF NOT EXISTS public.record
(
    entry integer NOT NULL DEFAULT nextval('record_entry_seq'::regclass),
    entry_date date NOT NULL,
    entry_time time without time zone NOT NULL,
    for_export boolean NOT NULL,
    data_source character varying(50) COLLATE pg_catalog."default" NOT NULL,
    bar_trend integer,
    packet_type integer,
    next_record integer,
    barometer numeric(4,2),
    inside_temperature numeric(5,2),
    inside_humidity integer,
    outside_temperature numeric(5,2),
    wind_speed integer,
    ten_min_avg_wind_speed integer,
    two_min_avg_wind_speed integer,
    ten_min_wind_gust numeric(5,2),
    wind_direction integer,
    wind_direction_for_the_ten_minute_wind_gust integer,
    dew_point integer,
    heat_index integer,
    wind_chill integer,
    thsw_index integer,
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
    outside_humidity integer,
    extra_humidity1 integer,
    extra_humidity2 integer,
    extra_humidity3 integer,
    extra_humidity4 integer,
    extra_humidity5 integer,
    extra_humidity6 integer,
    extra_humidity7 integer,
    rain_rate numeric(6,2),
    uv integer,
    solar_radiation integer,
    storm_rain numeric(6,2),
    start_date_of_current_storm date,
    day_rain numeric(6,2),
    month_rain numeric(7,2),
    year_rain numeric(7,2),
    last_fifteen_min_rain numeric(6,2),
    last_hour_rain numeric(6,2),
    last_24_hour_rain numeric(6,2),
    day_et numeric(8,4),
    month_et numeric(8,4),
    year_et numeric(8,4),
    soil_moisture1 numeric(5,1),
    soil_moisture2 numeric(5,1),
    soil_moisture3 numeric(5,1),
    soil_moisture4 numeric(5,1),
    leaf_wetness1 integer,
    leaf_wetness2 integer,
    leaf_wetness3 integer,
    leaf_wetness4 integer,
    forecast_icon integer,
    barometric_reduction_method numeric(1,0),
    user_entered_barometric_offset numeric(7,0),
    absolute_barometric_pressure numeric(7,0),
    altimeter_setting numeric(7,0),
    next_ten_min_wind_speed_graph_pointer numeric(5,0),
    next_fifteen_min_wind_speed_graph_pointer numeric(5,0),
    next_hourly_wind_speed_graph_pointer numeric(5,0),
    next_daily_wind_speed_graph_pointer numeric(5,0),
    next_minute_rain_graph_pointer numeric(5,0),
    next_rain_storm_graph_pointer numeric(5,0),
    index_to_the_minute_within_an_hour numeric(3,0),
    next_monthly_rain numeric(4,0),
    next_yearly_rain numeric(4,0),
    next_seasonal_rain numeric(4,0),
    inside_alarms numeric(3,0),
    rain_alarms numeric(3,0),
    outside_alarms1 numeric(3,0),
    outside_alarms2 numeric(3,0),
    outside_humidity_alarms numeric(3,0),
    extra_temp_hum_alarms1 numeric(3,0),
    extra_temp_hum_alarms2 numeric(3,0),
    extra_temp_hum_alarms3 numeric(3,0),
    extra_temp_hum_alarms4 numeric(3,0),
    extra_temp_hum_alarms5 numeric(3,0),
    extra_temp_hum_alarms6 numeric(3,0),
    extra_temp_hum_alarms7 numeric(3,0),
    soil_leaf_alarms1 numeric(3,0),
    soil_leaf_alarms2 numeric(3,0),
    soil_leaf_alarms3 numeric(3,0),
    soil_leaf_alarms4 numeric(3,0),
    transmitter_battery_status numeric(3,0),
    console_battery_voltage numeric(3,0),
    forecast_rule_number numeric(3,0),
    time_of_sunset time with time zone,
    time_of_sunrise time with time zone,
    daily_rain numeric(6,2),
    barometric_calibration_number numeric(5,0),
    CONSTRAINT record_pkey PRIMARY KEY (entry)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.record
    OWNER to postgres;

GRANT ALL ON TABLE public.record TO PUBLIC;

GRANT ALL ON TABLE public.record TO pg_checkpoint;

GRANT ALL ON TABLE public.record TO pg_read_all_data;

GRANT ALL ON TABLE public.record TO pg_read_server_files;

GRANT ALL ON TABLE public.record TO pg_write_all_data;

GRANT ALL ON TABLE public.record TO pg_write_server_files;

GRANT ALL ON TABLE public.record TO postgres;

-- Table: public.alarm

-- DROP TABLE IF EXISTS public.alarm;

CREATE TABLE IF NOT EXISTS public.alarm
(
    station_id integer NOT NULL DEFAULT nextval('alarm_station_id_seq'::regclass),
    type_number integer,
    type_name character varying COLLATE pg_catalog."default",
    active boolean,
    temperature boolean,
    time_alarm boolean,
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
    daily_et boolean,
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
    temp_hum1_low_temp boolean,
    temp_hum1_high_temp boolean,
    temp_hum1_low_hum boolean,
    temp_hum1_high_hum boolean,
    temp_hum2_low_temp boolean,
    temp_hum2_high_temp boolean,
    temp_hum2_low_hum boolean,
    temp_hum2_high_hum boolean,
    temp_hum3_low_temp boolean,
    temp_hum3_high_temp boolean,
    temp_hum3_low_hum boolean,
    temp_hum3_high_hum boolean,
    temp_hum4_low_temp boolean,
    temp_hum4_high_temp boolean,
    temp_hum4_low_hum boolean,
    temp_hum4_high_hum boolean,
    temp_hum5_low_temp boolean,
    temp_hum5_high_temp boolean,
    temp_hum5_low_hum boolean,
    temp_hum5_high_hum boolean,
    temp_hum6_low_temp boolean,
    temp_hum6_high_temp boolean,
    temp_hum6_low_hum boolean,
    temp_hum6_high_hum boolean,
    temp_hum7_low_temp boolean,
    temp_hum7_high_temp boolean,
    temp_hum7_low_hum boolean,
    temp_hum7_high_hum boolean,
    leaf_soil1_low_leaf_wetness boolean,
    leaf_soil1_high_leaf_wetness boolean,
    leaf_soil1_low_soil_moisture boolean,
    leaf_soil1_high_soil_moisture boolean,
    leaf_soil1_low_leaf_temp boolean,
    leaf_soil1_high_leaf_temp boolean,
    leaf_soil1_low_soil_temp boolean,
    leaf_soil1_high_soil_temp boolean,
    leaf_soil2_low_leaf_wetness boolean,
    leaf_soil2_high_leaf_wetness boolean,
    leaf_soil2_low_soil_moisture boolean,
    leaf_soil2_high_soil_moisture boolean,
    leaf_soil2_low_leaf_temp boolean,
    leaf_soil2_high_leaf_temp boolean,
    leaf_soil2_low_soil_temp boolean,
    leaf_soil2_high_soil_temp boolean,
    leaf_soil3_low_leaf_wetness boolean,
    leaf_soil3_high_leaf_wetness boolean,
    leaf_soil3_low_soil_moisture boolean,
    leaf_soil3_high_soil_moisture boolean,
    leaf_soil3_low_leaf_temp boolean,
    leaf_soil3_high_leaf_temp boolean,
    leaf_soil3_low_soil_temp boolean,
    leaf_soil3_high_soil_temp boolean,
    leaf_soil4_low_leaf_wetness boolean,
    leaf_soil4_high_leaf_wetness boolean,
    leaf_soil4_low_soil_moisture boolean,
    leaf_soil4_high_soil_moisture boolean,
    leaf_soil4_low_leaf_temp boolean,
    leaf_soil4_high_leaf_temp boolean,
    leaf_soil4_low_soil_temp boolean,
    leaf_soil4_high_soil_temp boolean,
    inside numeric(4,0),
    outside_humidity numeric(4,0),
    extra_temp_hum_alarms1 numeric(4,0),
    extra_temp_hum_alarms2 numeric(4,0),
    extra_temp_hum_alarms3 numeric(4,0),
    extra_temp_hum_alarms4 numeric(4,0),
    extra_temp_hum_alarms5 numeric(4,0),
    extra_temp_hum_alarms6 numeric(4,0),
    extra_temp_hum_alarms7 numeric(4,0),
    soil_leaf_alarms1 numeric(4,0),
    soil_leaf_alarms2 numeric(4,0),
    soil_leaf_alarms3 numeric(4,0),
    soil_leaf_alarms4 numeric(4,0),
    outside_alarms1 numeric(4,0),
    outside_alarms2 numeric(4,0),
    entry_date date,
    entry_time time with time zone,
    for_export boolean,
    data_source character varying(50) COLLATE pg_catalog."default",
    outside_humidity_alarms numeric(4,0),
    entry_serial integer NOT NULL DEFAULT nextval('alarm_entry_serial_seq'::regclass),
    rain numeric(4,0),
    CONSTRAINT alarm_pkey PRIMARY KEY (entry_serial)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.alarm
    OWNER to postgres;

GRANT ALL ON TABLE public.alarm TO PUBLIC;

GRANT ALL ON TABLE public.alarm TO pg_checkpoint;

GRANT ALL ON TABLE public.alarm TO pg_read_all_data;

GRANT ALL ON TABLE public.alarm TO pg_read_server_files;

GRANT ALL ON TABLE public.alarm TO pg_write_all_data;

GRANT ALL ON TABLE public.alarm TO pg_write_server_files;

GRANT ALL ON TABLE public.alarm TO postgres;
