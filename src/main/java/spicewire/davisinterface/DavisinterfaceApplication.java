package spicewire.davisinterface;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import spicewire.davisinterface.Controller.ConsoleController;
import spicewire.davisinterface.Controller.DavisVP2Controller;
import spicewire.davisinterface.Dao.JdbcWeatherRecord;
import spicewire.davisinterface.Model.DavisVP2;
import spicewire.davisinterface.Model.Seriall;
import spicewire.davisinterface.View.ComsPanes;

import javax.swing.*;
@SpringBootApplication
public class DavisinterfaceApplication extends JFrame {

	public static void main(String[] args) {

		Seriall serialModel = new Seriall();
		ComsPanes view = new spicewire.davisinterface.View.ComsPanes();
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/WeatherDB");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres");
		JdbcWeatherRecord jdbcWeatherRecord = new JdbcWeatherRecord(dataSource);
		ConsoleController consoleController = new ConsoleController(serialModel, view, jdbcWeatherRecord);
		DavisVP2 davisVP2 = new DavisVP2();
		DavisVP2Controller davisVP2Controller = new DavisVP2Controller(davisVP2, dataSource);
		consoleController.listenerFromController();


			System.out.println("frame called on start");
			JFrame frame = new JFrame("Settings");
			frame.setContentPane(view.getPanelMainJPanel());
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setSize(700, 600);
			frame.setVisible(true);

		@Configuration
		 class CustomContainer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
			public void customize(ConfigurableServletWebServerFactory factory){
				factory.setPort(8080);
			}
		}

			SpringApplication.run(DavisinterfaceApplication.class, args);

	}
}