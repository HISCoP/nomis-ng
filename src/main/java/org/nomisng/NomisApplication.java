package org.nomisng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@EnableScheduling
@SpringBootApplication
public class NomisApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(NomisApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(NomisApplication.class, args);
		browse("http://localhost:8080/swagger-ui.html");
	}

	//starting the default browser
	public static void browse(String url) {
		if(Desktop.isDesktopSupported()){
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.browse(new URI(url));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}else{
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
