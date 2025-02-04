package ssii;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;

@SpringBootApplication
public class WebApp {

	public static void main(String[] args) {
		SpringApplication.run(WebApp.class, args);
	}

}
