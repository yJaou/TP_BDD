package ssii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.h2.tools.Server;
import java.sql.SQLException;

@SpringBootApplication
public class WebApp {

	public static void main(String[] args) {
		SpringApplication.run(WebApp.class, args);
	}

	// Optionnel : Démarre un serveur H2 en mode TCP
	// Permet d'accéder à la base de données H2 depuis un client externe (comme DBeaver ou IntelliJ IDEA)
	// URL de connexion : jdbc:h2:tcp://localhost:9092/mem:testdb
	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server h2Server() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
	}
}
