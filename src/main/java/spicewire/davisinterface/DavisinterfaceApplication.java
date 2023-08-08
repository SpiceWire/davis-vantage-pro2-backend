package spicewire.davisinterface;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;
import spicewire.davisinterface.View.ComsPanes;

import javax.swing.*;


@SpringBootApplication

public class DavisinterfaceApplication extends JFrame {

	public static void main(String[] args) {
		SpringApplication.run(DavisinterfaceApplication.class, args);
	}


	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public BasicDataSource dataSource(){
		return new BasicDataSource();
		}

//	public BasicDataSource dataSource() {
//		return new BasicDataSource();}

//	@Configuration
//	public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/davisvp2").allowedOrigins("http://localhost:9000/");
//
//	}

//	@Configuration
//			public class databaseConfig {
//
//		@Bean
//				public BasicDataSource dataSource(){
//			DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//			dataSourceBuilder.url("jdbc:postgresql://localhost:5432/WeatherRecordsDB");
//			dataSourceBuilder.username("postgres");
//			dataSourceBuilder.password("postgres1");
//			return (BasicDataSource) dataSourceBuilder.build();
//		}


//	@Autowired
//
//	public BasicDataSource dataSource = new BasicDataSource() {
//	};


//	Seriall serialModel = new Seriall();
	@Autowired
	ComsPanes view = new spicewire.davisinterface.View.ComsPanes();
/*	BasicDataSource dataSource = new BasicDataSource();

            datasource.setUrl("jdbc:postgresql://localhost:5432/WeatherRecordsDB");
            dataSource.setUsername("postgres");
            dataSource.setPassword("postgres1");*/
//	JdbcWeatherRecord jdbcWeatherRecord = new JdbcWeatherRecord(dataSource);
//	ConsoleController consoleController = new ConsoleController(serialModel, view, jdbcWeatherRecord);
//	DavisVP2 davisVP2 = new DavisVP2();

//	DavisVP2Controller davisVP2Controller = new DavisVP2Controller(davisVP2, dataSource);
        //consoleController.listenerFromController();

	private void createFrame() {
		System.out.println("frame called on start");
		JFrame frame= new JFrame("Settings");
		frame.setContentPane(view.getPanelMainJPanel());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(700,600);
		frame.setVisible(true);
	}

//	private void setDataSource() {
//
//		dataSource.setUrl("jdbc:postgresql://localhost:5432/WeatherRecordsDB");
//		dataSource.setUsername("postgres");
//		dataSource.setPassword("postgres1");
//	}
}
