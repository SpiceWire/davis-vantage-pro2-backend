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
import org.springframework.stereotype.Service;
import spicewire.davisinterface.Controller.ConsoleController;
import spicewire.davisinterface.Controller.DavisVP2Controller;
import spicewire.davisinterface.Controller.VP2RestController;
import spicewire.davisinterface.Dao.JdbcWeatherRecord;
import spicewire.davisinterface.Model.DavisVP2;
import spicewire.davisinterface.Model.Seriall;
import spicewire.davisinterface.View.ComsPanes;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class DavisinterfaceApplication extends JFrame {
	private static final String url = "jdbc:postgresql://localhost:5432/WeatherDB";
	private static final String user = "postgres";
	private static final String password = "postgres";


//	public static Connection connect() {
//		Connection conn = null;
//		try {
//			conn = DriverManager.getConnection(url, user, password);
//			System.out.println("Connected to the PostgreSQL server successfully.");
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//		}
//
//		return conn;
//	}

	public static void main(String[] args) {
		SpringApplication.run(DavisinterfaceApplication.class, args);
//		Seriall serialModel = new Seriall();
//		ComsPanes view = new spicewire.davisinterface.View.ComsPanes();
//
//		BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setUrl("jdbc:postgresql://localhost:5432/WeatherDB");
//		dataSource.setUsername("postgres");
//		dataSource.setPassword("postgres");
//
//		JdbcWeatherRecord jdbcWeatherRecord = new JdbcWeatherRecord(dataSource);
//		ConsoleController consoleController = new ConsoleController(serialModel, view, jdbcWeatherRecord);
//		VP2RestController vp2RestController = new VP2RestController(consoleController);

		//

//		DavisVP2 davisVP2 = new DavisVP2();
//		DavisVP2Controller davisVP2Controller = new DavisVP2Controller();

//
//
//			System.out.println("frame called on start");
//			JFrame frame = new JFrame("Settings");
//			frame.setContentPane(view.getPanelMainJPanel());
//			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//			frame.setSize(700, 600);
//			frame.setVisible(true);


//		@Configuration
//		 class CustomContainer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
//			public void customize(ConfigurableServletWebServerFactory factory){
//				factory.setPort(8080);
//			}
//		}
		//DavisinterfaceApplication.connect();
			//SpringApplication.run(DavisinterfaceApplication.class, args);

	}
}