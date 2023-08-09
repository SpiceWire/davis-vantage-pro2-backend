package spicewire.davisinterface.Controller;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import spicewire.davisinterface.Dao.JdbcWeatherRecord;
import spicewire.davisinterface.Model.DavisVP2;

import javax.sql.DataSource;


//@CrossOrigin( maxAge = 3600, origins = "*", allowedHeaders = "*")
@RestController
public class DavisVP2Controller  {


    private static DavisVP2 davisVP2;
    private static DataSource datasource;

    private ConsoleController consoleController;


    public DavisVP2Controller(DavisVP2 davisVP2, DataSource dataSource) {
        DavisVP2Controller.davisVP2 = davisVP2;
        DavisVP2Controller.datasource = dataSource;
    }

//    @CrossOrigin( maxAge = 3600)@RequestMapping("/davisvp2")//base path for handlers
    @GetMapping(path = "/davisvp2")
    public DavisVP2.DisplayWeather getLastReadings(){

        System.out.println("Weather record sent from server.");
//        return new JdbcWeatherRecord(datasource).getDavisConsoleWeather();
        return JdbcWeatherRecord.get
    }


    @GetMapping(path = "/portSettings")
    public String getComPortSettings(){
        return ConsoleController.getComPortSettings();
    }

    /*
    @RequestMapping(path = "/davisvp2/comportsettings", method = RequestMethod.PUT)
    public void*/
    //how to make key-value pairs?

}
