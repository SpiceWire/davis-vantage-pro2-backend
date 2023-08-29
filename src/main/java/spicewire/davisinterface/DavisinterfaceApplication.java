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
//	private static final String url = "jdbc:postgresql://localhost:5432/WeatherDB";
//	private static final String user = "postgres";
//	private static final String password = "postgres";


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
		var ctx = new SpringApplicationBuilder(DavisinterfaceApplication.class)
				.headless(false).run(args);

		EventQueue.invokeLater(()->{
			var ex = ctx.getBean(DavisinterfaceApplication.class);
			ex.setVisible(true);
		});
		//SpringApplication.run(DavisinterfaceApplication.class, args);
//		Seriall serialModel = new Seriall();
//		ComsPanes view = new spicewire.davisinterface.View.ComsPanes();

//
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