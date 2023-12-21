package spicewire.davisinterface.Controller;


import org.springframework.web.bind.annotation.*;
import spicewire.davisinterface.Model.AggregateWeather;

@CrossOrigin
@RestController
@RequestMapping("/record")
public class RecordRestController {
    private ConsoleController consoleController;


    @GetMapping(value= "/rain/{val}")
    private AggregateWeather yesterdayWeather(@PathVariable int val){
        return consoleController.getTotalRain(val);

    }
}
