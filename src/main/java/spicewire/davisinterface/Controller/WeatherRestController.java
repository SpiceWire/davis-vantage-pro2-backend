package spicewire.davisinterface.Controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spicewire.davisinterface.Model.CurrentWeather;

@CrossOrigin(origins = {"http://localhost:5173","http://localhost:8080"})
@RestController
@RequestMapping("/weather")
public class WeatherRestController {
    //@RequestBody deserializes incoming data into a java object
    //@ResponseBody maps an object into Json
    private CurrentWeather currentWeather;
    private ConsoleController consoleController ;

    public WeatherRestController(ConsoleController consoleController){
        this.consoleController = consoleController;
    }

@RequestMapping(method= RequestMethod.GET)
public CurrentWeather get() {
    return consoleController.getMostRecentWeather();
}

@RequestMapping(method = RequestMethod.GET, path = "/new")
    public CurrentWeather make(){
        return consoleController.getCurrentWeatherReadings();
}
}
