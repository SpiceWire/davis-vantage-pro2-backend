package spicewire.davisinterface.Controller;


import org.springframework.web.bind.annotation.*;
import spicewire.davisinterface.Model.AggregateWeather;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * This class maps frontend requests for weather records. It can handle
 * requests for a specific day's weather,
 */

@CrossOrigin
@RestController
@RequestMapping("/record")
public class RecordRestController {
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
//    @GetMapping(value= "/date/{offset}")
//    private AggregateWeather previousWeather(@PathVariable String[] offset){
//        System.out.println("Console received request for past weather.");
//        return consoleController.getPreviousWeatherbyDay(offset);
//    }

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
    @GetMapping(value= "/header/{val}")
    private HashMap<LocalDateTime, String> headerData24Hours(@PathVariable String headerName){
        System.out.println("Console received request for 24 hours of hourly data in a category " +
                "called " + headerName);
        return consoleController.get24HoursOfHeaderData(headerName);
    }
}
