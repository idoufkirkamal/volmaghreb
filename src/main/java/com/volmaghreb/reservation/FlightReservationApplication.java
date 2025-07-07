package com.volmaghreb.reservation;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlightReservationApplication {

    public static void main(String[] args) {
        try {
            // Try to load .env file if it exists
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            
            // Only set system properties if the environment variables are not already set
            // and if they exist in the .env file
            if (System.getenv("DB_USERNAME") == null && dotenv.get("DB_USERNAME") != null) {
                System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
            }
            if (System.getenv("DB_PASSWORD") == null && dotenv.get("DB_PASSWORD") != null) {
                System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
            }
        } catch (Exception e) {
            // If .env loading fails, continue without it - environment variables might be set directly
            System.out.println("Note: .env file not found or could not be loaded. Using system environment variables if available.");
        }

        SpringApplication.run(FlightReservationApplication.class, args);
    }
}
