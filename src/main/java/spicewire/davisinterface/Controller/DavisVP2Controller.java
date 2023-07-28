package spicewire.davisinterface.Controller;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spicewire.davisinterface.Dao.JdbcWeatherRecord;
import spicewire.davisinterface.Model.DavisVP2;



@RequestMapping("/davisvp2")//base path for handlers
public class DavisVP2Controller {
    private static DavisVP2 davisVP2;
    private static BasicDataSource datasource;
    private ConsoleController consoleController;

    //    private RestTemplate restTemplate = new RestTemplate(); //This throws an error on run;
    public DavisVP2Controller(DavisVP2 davisVP2, BasicDataSource dataSource) {
        DavisVP2Controller.davisVP2 = davisVP2;
        DavisVP2Controller.datasource = dataSource;


    }


    @RequestMapping(path = "", method = RequestMethod.GET)
    public DavisVP2.DisplayWeather getLastReadings(){
        return new JdbcWeatherRecord(datasource).getDavisConsoleWeather();
    }

    @RequestMapping(path = "/comportsettings", method = RequestMethod.GET)
    public String getComPortSettings(){
        return ConsoleController.getComPortSettings();
    }

    /*
    @RequestMapping(path = "/davisvp2/comportsettings", method = RequestMethod.PUT)
    public void*/
    //how to make key-value pairs?

}
