package spicewire.davisinterface.Controller;


import org.springframework.web.bind.annotation.*;
import spicewire.davisinterface.Model.CurrentWeather;

@CrossOrigin
@RestController
@RequestMapping("/weather")
public class WeatherRestController {
    //@RequestBody deserializes incoming data into a java object
    //@ResponseBody maps an object into Json
    private CurrentWeather currentWeather;
    private ConsoleController consoleController;

    public WeatherRestController(ConsoleController consoleController) {
        this.consoleController = consoleController;
    }


    @GetMapping(value= "/{val}")
    public CurrentWeather getUnique(@PathVariable String val) {
        System.out.println("\nController received unique request for weather with id= \n" + val);
        return consoleController.getMostRecentWeather();
    }

    @GetMapping()
    public CurrentWeather get() {
        System.out.println("\nController received request for weather.\n");
        return consoleController.getMostRecentWeather();
    }

    @GetMapping(path = "/new")
    public CurrentWeather make() {
        return consoleController.getCurrentWeatherReadings();
    }
}
