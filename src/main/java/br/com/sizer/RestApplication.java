package br.com.sizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@EnableFeignClients
@SpringBootApplication
@OpenAPIDefinition(servers = { @Server(url = "/", description = "Default Server URL") })
public class RestApplication {
	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);

		// // Exemplo de como imprimir variáveis de ambiente
		// String databaseUrl = System.getenv("DATABASE_URL");
		// String databaseUsername = System.getenv("DATABASE_USERNAME");
		// String databasePassword = System.getenv("DATABASE_PASSWORD");

		// System.out.println("DATABASE_URL: " + databaseUrl);
		// System.out.println("DATABASE_USERNAME: " + databaseUsername);
		// System.out.println("DATABASE_PASSWORD: " + databasePassword);
	}

}
