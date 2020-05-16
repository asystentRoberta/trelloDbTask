package pl.com.bohdziewicz.trelloDbTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TrelloDbTaskApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(TrelloDbTaskApplication.class, args);
    }

    @Override protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {

        return applicationBuilder.sources(TrelloDbTaskApplication.class);
    }
}
