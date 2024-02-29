package spicewire.davisinterface.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLOutput;
import java.util.Map;
import java.util.Objects;
@CrossOrigin
@Configuration
@RestController
@RequestMapping("/forecast")
public class ForecastRestController {
//    @Bean
//    private RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder.build();
//    }
    ObjectMapper objectMapper = new ObjectMapper();
    private RestTemplate restTemplate = new RestTemplate() ;

    private String URL_ADDRESS = "https://geocoding.geo.census.gov/geocoder/locations/address?street=413+Hampton+Rd&c" +
            "ity=King+of+Prussia&state=PA&zip=19406&benchmark=2020&format=json";

    @GetMapping(value="/{nonce}")
    public String getPreloadedForecast(@PathVariable String nonce) throws JsonProcessingException {
        System.out.println("\nController received unique request for Forecast with id= \n" + nonce);
        jsonMap();
        return restTemplate.getForObject(
                URL_ADDRESS,
                String.class);
    }

    public void jsonMap () throws JsonProcessingException {
        try {
            String jsonString = new String (restTemplate.getForObject(
                    URL_ADDRESS,
                    String.class));
            Map<String, Object> mapObj= objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>(){});
            System.out.println(mapObj.toString());
        }
        catch (JsonProcessingException e){
            System.out.println("jsaon not good");
        }


    }


//    @GetMapping(value="/{cmdWord}/{nonce}")
//    public String getForecast(){
//        return new String(Objects.requireNonNull(restTemplate.getForObject(
//                URL_ADDRESS,
//                String.class)));
//    }
}
