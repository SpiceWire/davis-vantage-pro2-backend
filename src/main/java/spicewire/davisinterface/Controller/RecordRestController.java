package spicewire.davisinterface.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spicewire.davisinterface.Model.AggregateWeather;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * This class maps frontend requests for weather records. It can handle
 * requests for a specific day's weather or a header's value for each of the past
 * 24 hours.
 */

@CrossOrigin
@RestController
@RequestMapping("/record")
public class RecordRestController {
    //todo implement ResponseEntity
    private ConsoleController consoleController;

    public RecordRestController(ConsoleController consoleController) {
        this.consoleController = consoleController;
    }

    /**
     * Returns an AggregateWeather object for a day offset from now().
     * @param offset number of days offset from now()
     * @return AggregateWeather object serialized
     */
    @GetMapping(value= "/day/{offset}")
    private AggregateWeather previousWeather(@PathVariable Integer offset){
        System.out.println("Console received request for past weather.");
        return consoleController.getPreviousWeatherbyDay(offset);
    }


    /**
     * Returns an AggregateWeather object for a day offset from now().
     * AggregateWeather object contains a field for cumulative rain since the
     * offset day.
     * @param offset number of days offset from now()
     * @return AggregateWeather object serialized
     */
    @GetMapping(value= "/rain/{val}")
    private AggregateWeather previousRain(@PathVariable Integer offset){
        System.out.println("Console received request for past weather.");
        return consoleController.getPreviousWeatherbyDay(offset);

    }

    /**
     * Returns a hashmap paired values: (now() -i hours, header value at now() -i hours)
     * for the past 24 hours, given a valid header.
     * @param headerName valid name for header
     * @return serialized hashmap of past 24 hours and header value at each hour
     */
    @GetMapping(value= "/header/{headerName}")
    private ResponseEntity headerData(@PathVariable String headerName){
        HashMap<LocalDateTime, String> headerMap = consoleController.get24HoursOfHeaderData(headerName);
        if(headerMap.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body("There was no table header named " + headerName);
        }
        return new ResponseEntity(headerMap, HttpStatus.OK);
    }
    
}
