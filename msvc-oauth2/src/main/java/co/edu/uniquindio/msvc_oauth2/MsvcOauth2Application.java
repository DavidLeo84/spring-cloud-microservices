package co.edu.uniquindio.msvc_oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsvcOauth2Application {

	public static void main(String[] args) {
		SpringApplication.run(MsvcOauth2Application.class, args);
	}
}
