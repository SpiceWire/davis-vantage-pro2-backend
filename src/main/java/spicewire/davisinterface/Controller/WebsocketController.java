package spicewire.davisinterface.Controller;

//import org.apache.commons.dbcp2.BasicDataSource;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Controller;
//import spicewire.davisinterface.Dao.JdbcWeatherRecord;
//import spicewire.davisinterface.Model.CurrentWeather;
//
//import java.util.concurrent.TimeUnit;
//
//@Configuration
//@EnableScheduling
//@Controller
//public class WebsocketController {
//    private static BasicDataSource dataSource = new BasicDataSource();
//
//    private static JdbcWeatherRecord jdbcWeatherRecord= new JdbcWeatherRecord(dataSource);
//
//    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.MINUTES)
//    @SendTo("/weather/mostRecent")
//    public CurrentWeather mostRecentWeather() {
//        System.out.println("\nWebsocket triggered\n");
//        return jdbcWeatherRecord.getWeather();
//    }
//
//
//}
