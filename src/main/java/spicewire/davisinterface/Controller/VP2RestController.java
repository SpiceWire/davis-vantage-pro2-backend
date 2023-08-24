package spicewire.davisinterface.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spicewire.davisinterface.Model.CurrentWeather;
import spicewire.davisinterface.Controller.ConsoleController;

@RestController
public class VP2RestController {

    private CurrentWeather currentWeather;
    private ConsoleController consoleController ;

    public VP2RestController(ConsoleController consoleController){
        this.consoleController = consoleController;
    }

@RequestMapping(method= RequestMethod.GET)
public CurrentWeather get() {
        //return new CurrentWeather();
    return consoleController.getCurrentWeather();
    //what do i need at first? getweather. Implement that.
    //@RequestBody deserializes incoming data into a java object
    //@ResponseBody maps an object into Json
}
}
