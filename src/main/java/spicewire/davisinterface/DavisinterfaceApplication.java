package spicewire.davisinterface;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

import spicewire.davisinterface.View.ComsPanes;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class DavisinterfaceApplication extends JFrame {

	@ConfigurationProperties
	public static BasicDataSource getDatasource(){
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/WeatherDB");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres");
		return dataSource;
	}






	public static void main(String[] args) {
		var ctx = new SpringApplicationBuilder(DavisinterfaceApplication.class)
				.headless(true).run(args);

		EventQueue.invokeLater(()->{
			var ex = ctx.getBean(DavisinterfaceApplication.class);
			ex.setVisible(true);
		});


	}
}