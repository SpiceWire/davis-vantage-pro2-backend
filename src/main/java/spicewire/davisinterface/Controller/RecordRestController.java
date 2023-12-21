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

    @GetMapping(value= "/rain/{val}")
    private AggregateWeather yesterdayWeather(@PathVariable int val){
        System.out.println("Console received request for past weather.");
        return consoleController.getTotalRain(val);

    }
}
