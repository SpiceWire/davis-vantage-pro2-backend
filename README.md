Backend interface for Davis Vantage Pro 2 Weather Station.

This project allows owners of a Davis Vantage Pro 2 to access, store, view and use weather data collected
by the DavisVP2. It aligns with the technical specifications and protocols of the manufacturer's  [Serial Communication Reference Manual](https://support.davisinstruments.com/article/rbzgl0rh6k-vantage-pro-pro-2-and-vue-communications-reference-2-6-1-any-os).
 It is designed to run locally on a secured network. 

It implements:
* A custom binary parser to convert binary weather data to human-readable information
* CRC (Cyclic Redundancy Check) of binary information whenever applicable
* Scheduled, regular polling of the VP2 to collect weather data
* JDBC interface for a persistent PostgreSQL database of all weather station data
* Apache Tomcat webserver for API
* Custom REST API with endpoints
* Transactions with the United States Census Bureau Geocoder API to get the latitude and longitude of a given address
* Transactions with the United States Weather Service API for latitude and longitude-based forecasts
* Local cache of default address for faster forecast delivery
* SpringBoot and Spring annotations
* Separate database of weather alarms
* [JSerialComm](https://fazecast.github.io/jSerialComm/) library for serial communications

  ## API Endpoints
  ### Weather
  The weather endpoint uses GET. See complete list of weather data sent by this endpoint.
  | Endpoint name | Description |
  | --- | --- |
  | /weather |  Delivers all current weather data including but not limited to wind (speed, direction gust, average, high), humidity, temperature, rain (rate, last hour, total) |
  | /weather/new |  Delivers most recent weather data from the database |

  ### Record
  The record endpoint uses GET and delivers weather info from the database.
    | Endpoint name | Description |
  | --- | --- |
  | /record/day/{offset} | Gives aggregate weather information of a day offset from today. |
  | /record/header/{headerName} | Returns a hashmap of (key: each hour of the past 24 hours) and (value: header's value at the time) |

  ### Forecast
  The forecast endpoint provides the 5 day day/night forecast from the United States Weather Service for a geolocation. (United States of America only)
    | Endpoint name | Description |
  | --- | --- |
  | /forecast | GET method. Gives forecasts for the default address |
  | /forecast/latLon | POST method. Gives forecasts for a latitude and longitude coordinate String. (e.g "latLon":"33.9764, -118.2164") |
  | /forecast/address | POST method. Gives forecasts for a vald USA residential street address. |
  | /forecast/default/address | POST method. Gives forecasts for a valid USA residential street address, and sets the address as default for forecasts. |
  | /forecast/default/latLon | POST method. Gives forecasts for a latitude and longitude coordinate String, and sets the latLon as default for forecasts. |

    ### CurrentData
  With each GET call, the currentData endpoint also delivers a user-friendly text response, confirmation of the command used, and any error messages
  | Endpoint name | Description |
  | --- | --- |
  | /vp2/currentData/LOOP | Raw binary LOOP data |
  | /vp2/currentData/LPS | Raw binary LOOP2 data |

  ### Testing
  The testing endpoint also delivers a user-friendly text response, description of the test, confirmation of the command sent to the console, and any error messages. Uses GET.
    | Endpoint name | Description |
  | --- | --- |
  | /vp2/testing/TEST | Responds with "TEST" |
  | /vp2/testing/RXCHECK | Provides total packets received, total packets missed, number of resynchronizations, the largest number of packets received in a row., and the number of CRC errors detected |
  | /vp2/testing/RXTEST | Responds with "OK" |
  | /vp2/testing/VER | Responds with current VP2 firmware date |
  | /vp2/testing/RECEIVERS | Binary of "Stations Received" |
  | /vp2/testing/NVER | Responds with current VP2 firmware version number |

  ### Settings
  The settings endpoint gets and sets serial port settings, includng selection of serial port.
   | Endpoint name | Description |
  | --- | --- |
  |/vp2/settings/ | GET method. COM port info (system name, path, description), baud rate, data bits, stop bits, parity, write timeout, read timeout |
  |/vp2/settings/ | POST method. Sets baud rate, timeout mode, read timeout, write timeout, COM port, then replies with GET method response. |

  ## Weather Variables Delivered with Current Weather

| Temperature | Humidity | Wind |
| --- | --- | --- |
| inside_temperature | inside_humidity | wind_speed |
| outside_temperature | outside_humidity | wind_direction |
| extra_temperature1 | extra_humidity1 | ten_min_avg_wind_speed |
| extra_temperature2 | extra_humidity2 | two_min_avg_wind_speed |
| extra_temperature3 | extra_humidity3 | ten_min_wind_gust |
| extra_temperature4 | extra_humidity4 | wind_direction_for_the_ten_minute_wind_gust |
| extra_temperature5 | extra_humidity5 | |
| extra_temperature6 | extra_humidity6 | |
| extra_temperature7 | extra_humidity7 | |


| Environmental | Rain | Agriculture |
| --- | --- | --- | 
| dew_point | daily_rain | extra_temperature (1-7) |
| heat_index | rain_rate | soil_temperature (1-4) |
| wind_chill | last_fifteen_min_rain | leaf_temperature (1-4) |
| thsw_index | last_hour_rain | extra_humidity (1-7) |
| uv | storm_rain | soil_moisture (1-4) |
| day_ET | start_date_of_current_storm | leaf_wetness (1-4) |
| month_ET | month_rain | |
| year_ET|  year_rain | |
| solar_radiation |  |
	  	

| Misc |
| --- |
| barometer |
| bar_trend |
| forecast_icon |

 ## Weather Variables Delivered with Aggreggate Weather
| Variable Name | Additional Description |
| --- | --- |
 temperatureHigh |
 temperatureLow | 
 temperatureAvg | 
 temperatureChange | highest temperature minus lowest temperature
 humidityAvg |
 humidityLow |
 humidityHigh |
 totalRain | total rain for the day
 barometerHigh |
 barometerLow |
 windHigh |
 windAvg |
 windGust | distinct from windHigh
 accumulatedRain | sum of all rain since this day, including this day


  ## Weather Variables Stored in Database  
All variables related to Current Weather (listed above) are stored in the database. In addition, the following are stored:

| Console | Alarms | Calbration |
| --- | --- | --- | 
| time_of_sunrise | rain_alarms | barometric_reduction_method |
| time_of_sunset | inside_alarms  | user_entered_barometric_offset |
| transmitter_battery_status | outside_alarms (1-2) | barometric_calibration_number |
| console_battery_voltage | soil_leaf_alarms (1-4) | barometric_sensor_raw_reading |
| forecast_rule_number  | extra_temp_hum_alarms (1-7) | altimeter_setting |
| index_to_the_minute_within_an_hour | outside_humidity_alarms |   absolute_barometric_pressure  |

  
| Display |
| --- |
| next_ten_min_wind_speed_graph_pointer  | 
| next_fifteen_min_wind_speed_graph_pointer | 
| next_hourly_wind_speed_graph_pointer | 
| next_daily_wind_speed_graph_pointer |
| next_minute_rain_graph_pointer |
| next_rain_storm_graph_pointer | 
| next_monthly_rain | 
| next_yearly_rain |
| next_seasonal_rain | 

  
  
  
