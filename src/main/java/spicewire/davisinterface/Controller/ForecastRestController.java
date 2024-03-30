package spicewire.davisinterface.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import spicewire.davisinterface.Model.StreetAddress;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.*;


@CrossOrigin
@Configuration
@RestController
@RequestMapping("/forecast")
public class ForecastRestController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private final String addressPath = rootPath + "address.properties";

    //todo implement hourly forecast and weather alerts

    /**
     * Returns address and forecasts for default location in properties file.
     *
     * @param nonce random number
     * @return ResponseEntity with StreetAddress object and forecast array
     */
    @GetMapping(value = "/{nonce}")
    private ResponseEntity getDefaultForecast(@PathVariable String nonce) {
        System.out.println("\nController received unique default request for Forecast with id= \n" + nonce);
        StreetAddress defaultAddress = getAddressFromAddressProperties();
        return makeResponseFromAddress(defaultAddress);
    }

    @PostMapping(value = "/latLon")
    private ResponseEntity postForecastOfLatLon(@RequestBody Map<String, String> addressMap) {
        String latLon = addressMap.get("latLon");
        System.out.println("Recieved post request for forecast with body lat/lon of " + latLon);
        StreetAddress address = new StreetAddress();
        address.setLatLon(latLon);
        return makeResponseFromAddress(address);
    }


    @PostMapping(value = "/address")
    private ResponseEntity postForecastOfAddress(@RequestBody Map<String, String> addressMap) {
        System.out.println("rec'd forecast request with attached address " + addressMap);
        StreetAddress address = getLatitudeAndLongitudeFromAddress(addressMap);
        return makeResponseFromAddress(address);
    }

    @PostMapping(value = "/default/address")
    private ResponseEntity makeDefaultAddress(@RequestBody Map<String, String> addressMap) {
        System.out.println("rec'd request to make default address: " + addressMap);
        StreetAddress address = getLatitudeAndLongitudeFromAddress(addressMap);
        makeForecastPointFromStreetAddressObj(address);
        Map<String, Object> defaultAddressMap = convertAddressToMap(address);
        saveMapToAddressProperties(defaultAddressMap);
        System.out.println("Default address properties saved as: " + defaultAddressMap);
        return makeResponseFromAddress(address);
    }

    @PostMapping(value = "/default/latLon")
    private ResponseEntity makeDefaultLatLon(@RequestBody Map<String, String> addressMap) {
        String latLon = addressMap.get("latLon");
        System.out.println("Received post request to set default with body lat/lon of " + latLon);
        StreetAddress address = new StreetAddress();
        address.setLatLon(latLon);
        makeForecastPointFromStreetAddressObj(address);
        Map<String, Object> defaultAddressMap = convertAddressToMap(address);
        saveMapToAddressProperties(defaultAddressMap);
        return makeResponseFromAddress(address);
    }


//
//todo get county code?

    /**
     * When given a Map of a street address, uses an API to get latLon of street address,
     * returns a StreetAddress object containing the address and latLon coordinate.
     *
     * @param addressMap Map of USA address. Must contain key names:
     *                   streetAddress, city, state, zip.
     * @return StreetAddress obj with street address and latLon.
     */
    private StreetAddress getLatitudeAndLongitudeFromAddress(Map<String, String> addressMap) {
        StreetAddress address;
        System.out.println("Received unique post for forecast at an address: " + addressMap.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        String latitude = "";
        String longitude = "";
        String latLon = "";
        String rawApiResult = restTemplate.getForObject("https://geocoding.geo.census.gov/geocoder/locations/" +
                        "address?street={streetAddress}&city={city}&state={state}&zip={zip}&benchmark=2020&format=json",
                String.class, addressMap);
        System.out.println("rawApiResult is " + rawApiResult);
        try {
            JsonNode jsonNode = objectMapper.readTree(rawApiResult);
            longitude = jsonNode.get("result").findValue("x").asText();
            latitude = jsonNode.get("result").findValue("y").asText();
            System.out.println("latitude is " + latitude);
            System.out.println("longitude is " + longitude);
            latLon = latitude + "," + longitude;
            addressMap.put("latLon", latLon);
            addressMap.put("latitude", latitude);
            addressMap.put("longitude", longitude);
            address = new StreetAddress(addressMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("rawAPIresult = " + rawApiResult);
        return address;
    }


    /**
     * Populates a StreetAddress obj with URLs for alerts and weather.
     *
     * @param address StreetAddress obj, minimum params is latLon only.
     * @return StreetAddress obj with URLs for alerts and weather forecasts, and city, state if needed
     */
    private StreetAddress makeForecastPointFromStreetAddressObj(StreetAddress address) {
        String latLon = address.getLatLon();
        System.out.println("Received post for forecast at a latLon: " + latLon);
        ObjectMapper objectMapper = new ObjectMapper();
        String city = "";
        String state = "";
        String gridx = "";
        String gridy = "";
        String forecastURL = "";
        String forecastHourlyURL = "";
        String forecastGridDataURL = "";
        String gridpointsURL = "https://api.weather.gov/points/" + latLon;
        System.out.println("gridpointsURL = " + gridpointsURL);
        String alertsURL = "https://api.weather.gov/alerts/active?point=" + latLon;
        try {
            JsonNode jsonNode = objectMapper.readTree(new URI(gridpointsURL).toURL());
            gridx = jsonNode.get("properties").findValue("gridX").asText();
            gridy = jsonNode.get("properties").findValue("gridY").asText();
            if (address.getCity() != null || address.getState() != null) {
                if (address.getCity().isBlank() || address.getState().isBlank()) {
                    city = jsonNode.get("properties").findValue("city").asText();
                    state = jsonNode.get("properties").findValue("state").asText();
                    address.setState(state);
                    address.setCity(city);
                    address.setZip("");
                    address.setStreetAddress("");
                }
            } else {
                city = jsonNode.get("properties").findValue("city").asText();
                state = jsonNode.get("properties").findValue("state").asText();
                address.setState(state);
                address.setCity(city);
                address.setZip("");
                address.setStreetAddress("");
            }

            forecastURL = jsonNode.get("properties").findValue("forecast").asText();
            System.out.println("ThisForecastURL = " + forecastURL);
            forecastHourlyURL = jsonNode.get("properties").findValue("forecastHourly").asText();
            forecastGridDataURL = jsonNode.get("properties").findValue("forecastGridData").asText();
            address.setForecastURL(forecastURL);
            address.setForecastHourlyURL(forecastHourlyURL);
            address.setForecastGridDataURL(forecastGridDataURL);
            address.setActiveAlertsByPointURL(alertsURL);
            address.setGridpointsURL(gridpointsURL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            System.out.println("Improperly formatted URL");
        } catch (IOException e) {
            //todo rerun if error
            throw new RuntimeException(e);
        }
        System.out.println("gridx and grid y are " + gridx + ", " + gridy);
        System.out.println("address is " + address);
        return address;
    }

    /**
     * Given a StreetAddress object with at least latLon, returns a ResponseEntity with the
     * address and forecast
     *
     * @param givenAddress StreetAddress object with at least latLon.
     * @return
     */
    private ResponseEntity makeResponseFromAddress(StreetAddress givenAddress) {
        StreetAddress populatedAddress = makeForecastPointFromStreetAddressObj(givenAddress);
        System.out.println("populated address = " + populatedAddress);
        Map<String, Object> forecastAndAddress = makeForecastFromAddressObj(populatedAddress);
        System.out.println("Response: " + forecastAndAddress);
        return new ResponseEntity(forecastAndAddress, HttpStatus.OK);
    }

    /**
     * When given a URL of a forecast, returns a String containing an array of forecasts.
     *
     * @param forecastURL
     * @return Array of forecasts as a String.
     */
    private String getForecastFromURL(String forecastURL) {
        System.out.println("\nController received unique request for ForecastFromURL with URL= \n" + forecastURL);
        String forecastString = "none";
        try {
            JsonNode node = new ObjectMapper().readTree(new URI(forecastURL).toURL());
            forecastString = node.get("properties").get("periods").toString();
            System.out.println("node is " + node.toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        System.out.println("forecastString is " + forecastString);
        return forecastString;
    }


    /**
     * Given a StreetAddress object, returns a Map of the object's parameters.
     *
     * @param address
     * @return
     */
    private Map<String, Object> convertAddressToMap(StreetAddress address) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> addressMap = objectMapper
                .convertValue(address, new TypeReference<>() {
                });
        return addressMap;
    }

//todo add hourly forecast, alerts

    /**
     * Creates two Strings of forecasts when given a StreetAddress object. StreetAddress obj must already contain
     * URLs that will provide forecasts.
     *
     * @param address StreetAddress with URLs that will provide forecasts
     * @return Map of forecasts as strings and StreetAddress as object.
     */
    private Map<String, Object> makeForecastFromAddressObj(StreetAddress address) {
        String forecast = getForecastFromURL(address.getForecastURL());
        String hourlyForecast = getForecastFromURL(address.getForecastHourlyURL());
        Map<String, Object> forecastMap = new HashMap<>();
        forecastMap.put("forecast", forecast);
        forecastMap.put("address", address);
        return forecastMap;
    }


    /**
     * Reads the properties file, returns a StreetAddress instance from its contents
     *
     * @return StreetAddress object
     */
    private StreetAddress getAddressFromAddressProperties() {
        Properties addressProps = new Properties();
        StreetAddress address = new StreetAddress();
        Map<String, String> addressMap = new HashMap<>();
        try {
            addressProps.load(new FileInputStream(addressPath));
            for (String key : addressProps.stringPropertyNames()) {
                String value = addressProps.getProperty(key);
                addressMap.put(key, value);
            }
            address = new StreetAddress(addressMap);
            if(address.getLatLon()==null){
                throw new IOException();
            }
            System.out.println("root path is " + rootPath);
            System.out.println("getAddressFromAddressProperties first run says address is " + address.toString());
        } catch (IOException e) {
            System.out.println("exception in getAddressFromAddressProperties");
            address.setStreetAddress("7251 S. South Shore Drive");
            address.setCity("Chicago");
            address.setState("IL");
            address.setZip("60649");
            address.setForecastURL("https://api.weather.gov/gridpoints/LOT/78,68/forecast");
            address.setForecastHourlyURL("https://api.weather.gov/gridpoints/LOT/78,68/forecast/hourly");
            address.setLatLon("41.7770,-87.5802");
            address.setLatitude("41.7770");
            address.setLongitude("-87.5802");
            address.setGridpointsURL("https://api.weather.gov/points/41.7648,-87.5613");
            address.setActiveAlertsByPointURL("https://api.weather.gov/alerts/active?point=41.7648,-87.5613");
        }
        System.out.println("getAddressFromAddressProperties says address is " + address.toString());
        return address;
    }

    /**
     * Given an address Map, saves it to properties file.
     *
     * @param propMap
     */
    private void saveMapToAddressProperties(Map<String, Object> propMap) {
        Properties addressProps = new Properties();
        System.out.println("propmap is " + propMap);
        try {
            addressProps.putAll(propMap);
            File propsFile = new File(addressPath);
            if (propsFile.createNewFile()) {
                System.out.println("Properties file created: " + propsFile.getName());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{
            addressProps.store(new FileWriter(addressPath), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//todo add alerts
}