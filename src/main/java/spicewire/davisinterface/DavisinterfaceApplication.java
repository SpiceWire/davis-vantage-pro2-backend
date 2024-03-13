package spicewire.davisinterface;

import org.apache.catalina.connector.Connector;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import spicewire.davisinterface.Model.StreetAddress;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class DavisinterfaceApplication extends JFrame {
	private static final Logger log = LoggerFactory.getLogger(DavisinterfaceApplication.class);

	@Bean
	public RestTemplate forecastRestTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public StreetAddress initialStreetAddress(){ return new StreetAddress();}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
			}
		};
	}
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/").allowedOrigins("http://localhost:5173");
//				corsConfigurer().
//			}
//		};
//	}
//	@Bean
//	public ConfigurableServletWebServerFactory webServerFactory() {
//		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//		factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
//			@Override
//			public void customize(Connector connector) {
//				connector.setProperty("relaxedQueryChars", "|{}[]");
//			}
//		});
//		return factory;
//	}

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
			ex.setVisible(false);
		});


	}
}