package spicewire.davisinterface.Controller;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import spicewire.davisinterface.Dao.JdbcWeatherRecord;
import spicewire.davisinterface.Model.DavisVP2;

import javax.sql.DataSource;

@RestController
@CrossOrigin( maxAge = 3600, origins = "*", allowedHeaders = "*")
@RequestMapping("/davisvp2")//base path for handlers
public class DavisVP2Controller implements WebMvcConfigurer {

//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**");
//    }
    private static DavisVP2 davisVP2;
    private static DataSource datasource;

    private ConsoleController consoleController;


    public DavisVP2Controller(DavisVP2 davisVP2, DataSource dataSource) {
        DavisVP2Controller.davisVP2 = davisVP2;
        DavisVP2Controller.datasource = dataSource;
    }

    @CrossOrigin( maxAge = 3600)
    @RequestMapping(path = "", method = RequestMethod.GET)
    public DavisVP2.DisplayWeather getLastReadings(){
        consoleController.getCurrentWeather();
        System.out.println("Weather record sent from server.");
        return new JdbcWeatherRecord(datasource).getDavisConsoleWeather();
    }


    @RequestMapping(path = "/portSettings", method = RequestMethod.GET)
    public String getComPortSettings(){
        return ConsoleController.getComPortSettings();
    }

    /*
    @RequestMapping(path = "/davisvp2/comportsettings", method = RequestMethod.PUT)
    public void*/
    //how to make key-value pairs?

}
