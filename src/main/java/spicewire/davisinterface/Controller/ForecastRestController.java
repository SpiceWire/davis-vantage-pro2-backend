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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
//todo add validation for lat/lon
//todo use ResponseEntity instead

@CrossOrigin
@Configuration
@RestController
@RequestMapping("/forecast")
public class ForecastRestController {
    private String geocodingURL = "https://geocoding.geo.census.gov/geocoder/locations/";
    private ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate() ;
    private String testForecastURL = "https://api.weather.gov/points/39.7456,-97.0892";
    private String URL_ADDRESS = "https://geocoding.geo.census.gov/geocoder/locations/address?street=417+Hampton+Rd&c" +
            "ity=King+of+Prussia&state=PA&zip=19406&benchmark=2020&format=json";

    String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    String addressPath = rootPath + "address.properties";

    //todo generic forecast should pull location from a local file return the default with the forecast
    //todo needs method to accept a lat/lon, method to accept an address, both return the location with the forecast
    //todo needs option to choose which forecast to use
    //todo needs to accept geolocation, address by different methods, then return a forecast.
    //todo both address and geolocation will ultimately get the geoPoint, then return a forecast.
    //todo item returned should have address and forecast
    //todo return an object with extended forecast, address, and a detailed forecast

//    @Autowired
//    StreetAddress streetAddress;

    /**
     * Returns address and forecasts for default location in properties file.
     * @param nonce random number
     * @return ResponseEntity with StreetAddress object and forecast array
     */
    @GetMapping(value="/{nonce}")
    public ResponseEntity getDefaultForecast(@PathVariable String nonce)   {
        System.out.println("\nController received unique default request for Forecast with id= \n" + nonce);
        StreetAddress defaultAddress = getAddressFromAddressProperties();
        Map<String, Object> forecastAndAddress = makeForecastFromAddressObj(defaultAddress);
        System.out.println("forecastAndAddress = " + forecastAndAddress);
        return new ResponseEntity(forecastAndAddress,HttpStatus.OK);
    }

    @PostMapping(value="/latLon")
    public ResponseEntity postForecastOfLatLon(@RequestBody Map<String, String> addressMap){
        String latLon = addressMap.get("latLon");
        System.out.println("Recieved post request for forecast with body lat/lon of " + latLon);
        StreetAddress address = new StreetAddress();
        //adds latLon to address
        address.setLatLon(latLon);
        //address is populated with weather URL's
        address = makeForecastPointFromStreetAddressObj(address);
        //address is saved as default
//        saveInfoToAddressProperties(convertAddressToMap(address));
        //get forecast from StreetAddress obj
        Map<String, Object> forecastAndAddress = makeForecastFromAddressObj(address);

//        return ResponseEntity.ok(HttpStatus.OK);
        System.out.println("forecastAndAddress = " + forecastAndAddress);
        return new ResponseEntity(forecastAndAddress,HttpStatus.OK);
    }


    @PostMapping(value="/address")
    public ResponseEntity postWeatherFromAddress(@RequestBody Map<String, String> addressMap)  {
        System.out.println("rec'd forecast request with attached address " + addressMap);
        StreetAddress address = getLatitudeAndLongitudeFromAddress(addressMap);
        StreetAddress forecastAddress = makeForecastPointFromStreetAddressObj(address);
        Map<String, Object> forecastAndAddress = makeForecastFromAddressObj(forecastAddress);
        System.out.println("forecastAndAddress = " + forecastAndAddress);
        return new ResponseEntity(forecastAndAddress,HttpStatus.OK);
    }


//
//todo get county code?
    /**
     * When given a Map of a street address, uses an API get latLon of street address,
     * returns a StreetAddress object containing the address and latLon coordinate.
     * @param addressMap Map of USA address. Must contain key names:
     *                   streetAddress, city, state, zip.
     * @return StreetAddress obj with street address and latLon.
     */
    private StreetAddress getLatitudeAndLongitudeFromAddress(Map<String, String> addressMap){
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
            latLon = latitude+","+longitude;
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
     * @param address StreetAddress obj, minimum params is latLon only.
     * @return
     */
    private StreetAddress makeForecastPointFromStreetAddressObj(StreetAddress address)  {
        String latLon = address.getLatLon();
        System.out.println("Received post for forecast at a latLon: " + latLon);
        ObjectMapper objectMapper = new ObjectMapper();
        String city = "";
        String state = "";
        String gridx = "";
        String gridy = "";
        String forecastURL="";
        String forecastHourlyURL="";
        String forecastGridDataURL="";
        String gridpointsURL = "https://api.weather.gov/points/" +latLon;
        System.out.println("gridpointsURL = " + gridpointsURL);
        String alertsURL = "https://api.weather.gov/alerts/active?point=" + latLon;
        try {
        JsonNode jsonNode = objectMapper.readTree(new URI(gridpointsURL).toURL());
         gridx = jsonNode.get("properties").findValue("gridX").asText();
         gridy = jsonNode.get("properties").findValue("gridY").asText();
         city = jsonNode.get("properties").findValue("city").asText();
         state = jsonNode.get("properties").findValue("state").asText();
         forecastURL = jsonNode.get("properties").findValue("forecast").asText();
            System.out.println("ThisForecastURL = " + forecastURL);
         forecastHourlyURL = jsonNode.get("properties").findValue("forecastHourly").asText();
         forecastGridDataURL= jsonNode.get("properties").findValue("forecastGridData").asText();
         address.setForecastURL(forecastURL);
         address.setForecastHourlyURL(forecastHourlyURL);
         address.setForecastGridDataURL(forecastGridDataURL);
         address.setActiveAlertsByPointURL(alertsURL);
         address.setGridpointsURL(gridpointsURL);
         address.setState(state);
         address.setCity(city);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        catch (MalformedURLException e){
            System.out.println("Improperly formatted URL");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("gridx and grid y are " + gridx + ", " + gridy );
        return address;
    }

    /**
     * When given a URL of a forecast, returns a String containing an array of forecasts.
     * @param forecastURL
     * @return Array of forecasts as a String.
     */
    private String getForecastFromURL(String forecastURL)   {
        System.out.println("\nController received unique request for ForecastFromURL with URL= \n" + forecastURL);
        String forecastString = "none";
        try {
            JsonNode node = new ObjectMapper().readTree(new URI(forecastURL).toURL());
            forecastString = node.get("properties").get("periods").toString();
            System.out.println("node is " + node.toString() );
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

//    private Map<String, String> makeForecastForDefaultAddress() {
//
//    }

    /**
     * Given a StreetAddress object, returns a Map of the object's parameters.
     * @param address
     * @return
     */
    private Map<String, Object> convertAddressToMap(StreetAddress address){
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
     * @param address StreetAddress with URLs that will provide forecasts
     * @return Map of forecasts as strings and StreetAddress as object.
     */
    private Map<String, Object> makeForecastFromAddressObj(StreetAddress address){
        String forecast = getForecastFromURL(address.getForecastURL());
        String hourlyForecast= getForecastFromURL(address.getForecastHourlyURL());
        Map<String, Object> forecastMap = new HashMap<>();
        forecastMap.put("forecast", forecast);
//        forecastMap.put("hourlyForecast", hourlyForecast);
        forecastMap.put("address", address);
        return forecastMap;
    }

    public String getInfoFromAddressProperties(){
        Properties addressProps = new Properties();
        String value = "";
        try {
            addressProps.load(new FileInputStream(addressPath));
            value = addressProps.getProperty("streetAddress");
        }
        catch (IOException e){
        }
        return value;
    }

    /**
     * Reads the properties file, returns a StreetAddress instance from its contents
     * @return StreetAddress object
     */
    private StreetAddress getAddressFromAddressProperties(){
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
        }
        catch (IOException e){
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
        return address;
    }

    /**
     * Given an address Map, saves it to properties file.
     * @param propMap
     */
    private void saveInfoToAddressProperties(Map<String, Object> propMap) {
        Properties addressProps = new Properties();
        try {
            addressProps.putAll(propMap);
            File propsFile = new File(addressPath);
            if (propsFile.createNewFile()) {
                System.out.println("Properties file created: " + propsFile.getName());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (OutputStream outputStream = Files.newOutputStream(Path.of(addressPath))) {
            addressProps.store(outputStream, null);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

//todo add alerts
    private String getForecastURLFromLatLon(String latLon) {
        System.out.println("Received unique post for forecast at a latLon: " + latLon);
        ObjectMapper objectMapper = new ObjectMapper();
        String gridx = "";
        String gridy = "";
        String forecastURL="";
        String forecastHourlyURL="";
        String forecastGridDataURL="";
        String gridpointsURL = "https://api.weather.gov/points/" +latLon;
        System.out.println("gridpointsURL = " + gridpointsURL);
        //String alertsURL = "https://api.weather.gov/alerts/active?point=" + latitude + "," + longitude;
        try {
            JsonNode jsonNode = objectMapper.readTree(new URI(gridpointsURL).toURL());
            gridx = jsonNode.get("properties").findValue("gridX").asText();
            gridy = jsonNode.get("properties").findValue("gridY").asText();
            forecastURL = jsonNode.get("properties").findValue("forecast").asText();
            System.out.println("ThisForecastURL = " + forecastURL);
            forecastHourlyURL = jsonNode.get("properties").findValue("forecastHourly").asText();
            forecastGridDataURL= jsonNode.get("properties").findValue("forecastGridData").asText();

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);

        }
        catch (MalformedURLException e){
            System.out.println("Improperly formatted URL");
            return "Improperly formatted url";
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("gridx and grid y are " + gridx + ", " + gridy );
        return getForecastFromURL(forecastURL);
    }
//    @GetMapping(value="/address/")
//    public void getLatLonFromAddress(@PathVariable  StreetAddress address) {
//
//        System.out.println("Received unique get for forecast at an address: " + address.toString());
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        Map<String, String> addressVars = new HashMap<String, String>();
//        addressVars.put("theStreet", address.getStreetAddress());
//        addressVars.put("theCity", address.getCity());
//        addressVars.put("theState", address.getState());
//        addressVars.put("theZip", address.getZip());
//        String rawApiResult = restTemplate.getForObject("https://geocoding.geo.census.gov/geocoder/locations/" +
//                "{theStreet}/{theCity}/{theState}/{theZip}/&benchmark=2020&format=json", String.class, addressVars);
//        try {
//            JsonNode jsonNode = objectMapper.readTree(rawApiResult);
//        }  catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("rawAPIresult = " + rawApiResult);
//
//    }
//        CensusGeolookup.Result result = jsonCensusGeolookup.new Result();
//        System.out.println("Result is: " + result.gimmieCity());
//        try {
//            String jsonString = (restTemplate.getForObject(
//                    URL_ADDRESS,
//                    CensusGeolookup.class).toString());
//            System.out.println("result is "+ CensusGeolookup.Result.toString());
//            System.out.println("address is: " + CensusGeolookup.Address.class);
//            Map<String, Object> mapObj= objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>(){});
//            System.out.println("map is: " + mapObj.toString());
//            System.out.println("keysize is: " + mapObj.keySet().size());
//            for(String s:mapObj.keySet()){
//                System.out.println("Key is:" + s);
//            }
//
//        }
//        catch (Exception e){
//            System.out.println("jsaon not good");
//        }


    }
//
//    public static void getKey(JSONObject json, String key) {
//        boolean exists = json.has(key);
//        Iterator < ? > keys;
//        String nextKeys;
//        if (!exists) {
//            keys = json.keys();
//            while (keys.hasNext()) {
//                nextKeys = (String) keys.next();
//                try {
//                    if (json.get(nextKeys) instanceof JSONObject) {
//                        if (exists == false) {
//                            getKey(json.getJSONObject(nextKeys), key);
//                        }
//                    } else if (json.get(nextKeys) instanceof JSONArray) {
//                        JSONArray jsonarray = json.getJSONArray(nextKeys);
//                        for (int i = 0; i < jsonarray.length(); i++) {
//                            String jsonarrayString = jsonarray.get(i).toString();
//                            JSONObject innerJSOn = new JSONObject(jsonarrayString);
//                            if (exists == false) {
//                                getKey(innerJSOn, key);
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                }
//            }
//        } else {
//            parseObject(json, key);
//        }
//    }
//    @GetMapping(value="/{cmdWord}/{nonce}")
//    public String getForecast(){
//        return new String(Objects.requireNonNull(restTemplate.getForObject(
//                URL_ADDRESS,
//                String.class)));
//    }

//    @GetMapping(value="/address/")
//    public String tokenGetResponse() {
//        System.out.println("Received get request for forecast at an unspecified address");
//        return "Backend rec'd forecast get with unspecified address";
//    }

//    @PostMapping(value="/address/")
//    public String tokenPostResponse() {
//        System.out.println("Received post request for forecast at an unspecified address");
//        return "Backend rec'd forecast post with unspecified address";
//    }

//    @PostMapping(value="/address")
//    public ResponseEntity postLatLonFromAddress(@RequestBody StreetAddress address) throws JsonProcessingException {

//    @PostMapping(value="/address")
//    public ResponseEntity postLatLonFromAddress(@RequestBody StreetAddress address) throws JsonProcessingException {
//
//        System.out.println("Received unique post for forecast at an address: " + address.toString());
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        Map<String, String> addressVars = new HashMap<String, String>();
//        addressVars.put("theStreet", address.getStreetAddress());
//        addressVars.put("theCity", address.getCity());
//        addressVars.put("theState", address.getState());
//        addressVars.put("theZip", address.getZip());
//        System.out.println("addressVars is: " + addressVars);
//        String rawApiResult = restTemplate.getForObject("https://geocoding.geo.census.gov/geocoder/locations/" +
//                "{theStreet}/{theCity}/{theState}/{theZip}/&benchmark=2020&format=json", String.class, addressVars);
//        try {
//            JsonNode jsonNode = objectMapper.readTree(rawApiResult);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("rawAPIresult = " + rawApiResult);
//        return ResponseEntity.ok(HttpStatus.OK);
//
//    }

//    @PostMapping(value="/latLon/{latLon}")
//    public ResponseEntity postForecastFromLatLon(@PathVariable String latLon){
//        System.out.println("Recieved post request for forecast with lat/lon of " + latLon);
//        StreetAddress address = new StreetAddress();
//        //adds latLon to address
//        address.setLatLon(latLon);
//        //address is populated with weather URL's
//        address = makeForecastPointFromStreetAddressObj(address);
//        //address is saved as default
////        saveInfoToAddressProperties(convertAddressToMap(address));
//        //get forecast from StreetAddress obj
//        Map<String, Object> forecastAndAddress = makeForecastFromAddressObj(address);
//
////        return ResponseEntity.ok(HttpStatus.OK);
//        System.out.println("forecastAndAddress = " + forecastAndAddress);
//        return new ResponseEntity(forecastAndAddress,HttpStatus.OK);
//    }