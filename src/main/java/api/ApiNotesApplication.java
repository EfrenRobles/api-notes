package api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import api.shared.infrastructure.config.EnvConfig;

@SpringBootApplication
public class ApiNotesApplication {

    public static void main(String[] args) {
        EnvConfig.setProperty(
            SpringApplication.run(ApiNotesApplication.class, args).getEnvironment()
        );
    }

}
