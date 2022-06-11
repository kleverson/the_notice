package br.com.thenotice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TheNoticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TheNoticeApplication.class, args);
    }

}
