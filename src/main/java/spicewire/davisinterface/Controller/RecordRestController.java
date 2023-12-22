package spicewire.davisinterface.Controller;


import org.springframework.web.bind.annotation.*;
import spicewire.davisinterface.Model.AggregateWeather;

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

    @GetMapping(value= "/rain/{val}")
    private AggregateWeather previousRain(@PathVariable Integer offset){
        System.out.println("Console received request for past weather.");
        return consoleController.getPreviousWeatherbyDay(offset);

    }
}
