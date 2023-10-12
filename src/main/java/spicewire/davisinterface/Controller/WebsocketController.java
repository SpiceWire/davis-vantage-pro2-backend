package spicewire.davisinterface.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import spicewire.davisinterface.Dao.JdbcWeatherRecord;
import spicewire.davisinterface.Model.CurrentWeather;

@Controller
public class WebsocketController {

    private static JdbcWeatherRecord jdbcWeatherRecord;

    @SendTo("/weather/mostRecent")
    public CurrentWeather mostRecentWeather() {
        return jdbcWeatherRecord.getWeather();
    }
}
