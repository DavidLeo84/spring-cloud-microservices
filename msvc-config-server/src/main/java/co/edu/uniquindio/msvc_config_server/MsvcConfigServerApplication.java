package co.edu.uniquindio.msvc_config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class MsvcConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcConfigServerApplication.class, args);
	}

}
