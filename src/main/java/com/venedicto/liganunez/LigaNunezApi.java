package com.venedicto.liganunez;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.venedicto.liganunez.configuration.LocalDateConverter;
import com.venedicto.liganunez.configuration.LocalDateTimeConverter;

import springfox.documentation.oas.annotations.EnableOpenApi;

@SuppressWarnings("deprecation")
@SpringBootApplication
@EnableOpenApi
@ComponentScan(basePackages = { "com.venedicto.liganunez", "com.venedicto.liganunez.api" , "com.venedicto.liganunez.configuration"})
public class LigaNunezApi implements CommandLineRunner {

    @Override
    public void run(String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
    }

    public static void main(String[] args) throws Exception {
        new SpringApplication(LigaNunezApi.class).run(args);
    }

    /*~~(Unable to find runtime dependencies beginning with: 'spring-webmvc')~~>*/@Configuration
    static class CustomDateConfig extends WebMvcConfigurerAdapter {
        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(new LocalDateConverter("yyyy-MM-dd"));
            registry.addConverter(new LocalDateTimeConverter("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        }
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}
