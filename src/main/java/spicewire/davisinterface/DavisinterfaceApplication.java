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

	public DavisinterfaceApplication(){
		initSwingGUI();
	}

	private void initSwingGUI(){
			System.out.println("frame called on start");
			JFrame frame = new JFrame("Settings");
			ComsPanes view = new spicewire.davisinterface.View.ComsPanes();
			frame.setContentPane(view.getPanelMainJPanel());
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setSize(700, 600);
			frame.setVisible(true);
	}


	public static void main(String[] args) {
		var ctx = new SpringApplicationBuilder(DavisinterfaceApplication.class)
				.headless(false).run(args);

		EventQueue.invokeLater(()->{
			var ex = ctx.getBean(DavisinterfaceApplication.class);
			ex.setVisible(true);
		});


	}
}