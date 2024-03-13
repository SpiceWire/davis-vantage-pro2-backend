package spicewire.davisinterface.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.hateoas.UriTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import spicewire.davisinterface.Model.CensusAddress;
import spicewire.davisinterface.Model.CensusGeolookup;
import spicewire.davisinterface.Model.StreetAddress;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLOutput;
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
    //todo generic forecast should pull location from a local file return the default with the forecast
    //todo needs method to accept a lat/lon, method to accept an address, both return the location with the forecast
    //todo needs option to choose which forecast to use
    //todo needs to accept geolocation, address by different methods, then return a forecast.
    //todo both address and geolocation will ultimately get the geoPoint, then return a forecast.



    @GetMapping(value="/{nonce}")
    public String getPreloadedForecast(@PathVariable String nonce) throws JsonProcessingException {
        System.out.println("\nController received unique request for Forecast with id= \n" + nonce);
        String forecastString = "none";
        try {
            JsonNode node = mapper.readTree(new URI(testForecastURL).toURL());
            forecastString= node.toString();
//            CensusAddress firstCensusAddress=mapper.readValue(new URI(URL_ADDRESS).toURL(), CensusAddress.class);
//            System.out.println("result is " + firstCensusAddress.new Result().gimmieString());
//            return firstCensusAddress.toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return forecastString;
    }

    public String getForecastFromURL(String forecastURL) throws JsonProcessingException {
        System.out.println("\nController received unique request for ForecastFromURL with URL= \n" + forecastURL);
        String forecastString = "none";
        try {
            JsonNode node = mapper.readTree(new URI(forecastURL).toURL());
            forecastString= node.toString();
//            CensusAddress firstCensusAddress=mapper.readValue(new URI(URL_ADDRESS).toURL(), CensusAddress.class);
//            System.out.println("result is " + firstCensusAddress.new Result().gimmieString());
//            return firstCensusAddress.toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return forecastString;
    }

    @GetMapping(value="/address/")
    public void getLatLonFromAddress(@PathVariable  StreetAddress address) {

        System.out.println("Received unique get for forecast at an address: " + address.toString());
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> addressVars = new HashMap<String, String>();
        addressVars.put("theStreet", address.getStreetAddress());
        addressVars.put("theCity", address.getCity());
        addressVars.put("theState", address.getState());
        addressVars.put("theZip", address.getZip());
        String rawApiResult = restTemplate.getForObject("https://geocoding.geo.census.gov/geocoder/locations/" +
                "{theStreet}/{theCity}/{theState}/{theZip}/&benchmark=2020&format=json", String.class, addressVars);
        try {
            JsonNode jsonNode = objectMapper.readTree(rawApiResult);
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("rawAPIresult = " + rawApiResult);

    }

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

    @PostMapping(value="/address/")
    public void postLatLonFromAddress(@RequestBody  StreetAddress address) {

        System.out.println("Received unique post for forecast at an address: " + address.toString());
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> addressVars = new HashMap<String, String>();
        addressVars.put("theStreet", address.getStreetAddress());
        addressVars.put("theCity", address.getCity());
        addressVars.put("theState", address.getState());
        addressVars.put("theZip", address.getZip());
        String rawApiResult = restTemplate.getForObject("https://geocoding.geo.census.gov/geocoder/locations/" +
                "{theStreet}/{theCity}/{theState}/{theZip}/&benchmark=2020&format=json", String.class, addressVars);
        try {
            JsonNode jsonNode = objectMapper.readTree(rawApiResult);
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("rawAPIresult = " + rawApiResult);

    }

    @PostMapping(value="/latLon/{latLon}")
    public String makeForecastPointFromLatLon(@PathVariable String latLon) throws JsonProcessingException {
        System.out.println("Received unique post for forecast at a latLon: " + latLon);
        ObjectMapper objectMapper = new ObjectMapper();
        String gridx = "";
        String gridy = "";
        String forecastURL="";
        String forecastHourlyURL;
        String forecastGridDataURL;
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

