package br.com.sizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RestApplication {

	public String PORT = System.getenv("PORT");

	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);

		// Exemplo de como imprimir variáveis de ambiente
		String databaseUrl = System.getenv("DATABASE_URL");
		String databaseUsername = System.getenv("DATABASE_USERNAME");
		String databasePassword = System.getenv("DATABASE_PASSWORD");

		System.out.println("DATABASE_URL: " + databaseUrl);
		System.out.println("DATABASE_USERNAME: " + databaseUsername);
		System.out.println("DATABASE_PASSWORD: " + databasePassword);
	}

}
