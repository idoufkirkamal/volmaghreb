package com.volmaghreb.reservation.config;

import com.volmaghreb.reservation.entities.Airplane;
import com.volmaghreb.reservation.entities.Airport;
import com.volmaghreb.reservation.repositories.AirplaneRepository;
import com.volmaghreb.reservation.repositories.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private AirportRepository airportRepository;
    
    @Autowired
    private AirplaneRepository airplaneRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only seed data if the airport table is empty
        if (airportRepository.count() == 0) {
            seedAirports();
            System.out.println("✅ Sample airport data seeded successfully!");
        } else {
            System.out.println("ℹ️ Airport data already exists, skipping seeding.");
        }
        
        // Only seed airplane data if the airplane table is empty
        if (airplaneRepository.count() == 0) {
            seedAirplanes();
            System.out.println("✅ Sample airplane data seeded successfully!");
        } else {
            System.out.println("ℹ️ Airplane data already exists, skipping seeding.");
        }
    }

    private void seedAirports() {
        // Create sample airports
        createAirportIfNotExists("JFK", "John F. Kennedy International Airport", "New York", "United States");
        createAirportIfNotExists("LAX", "Los Angeles International Airport", "Los Angeles", "United States");
        createAirportIfNotExists("LHR", "London Heathrow Airport", "London", "United Kingdom");
        createAirportIfNotExists("CDG", "Charles de Gaulle Airport", "Paris", "France");
        createAirportIfNotExists("DXB", "Dubai International Airport", "Dubai", "United Arab Emirates");
        
        // Moroccan airports
        createAirportIfNotExists("CMN", "Mohammed V International Airport", "Casablanca", "Morocco");
        createAirportIfNotExists("RAK", "Marrakech Menara Airport", "Marrakech", "Morocco");
        createAirportIfNotExists("RBA", "Rabat–Salé Airport", "Rabat", "Morocco");
        createAirportIfNotExists("FEZ", "Fès–Saïs Airport", "Fès", "Morocco");
        createAirportIfNotExists("TNG", "Ibn Battouta Airport", "Tangier", "Morocco");
        createAirportIfNotExists("AGA", "Al Massira Airport", "Agadir", "Morocco");
        createAirportIfNotExists("OUZ", "Ouarzazate Airport", "Ouarzazate", "Morocco");
        createAirportIfNotExists("NDR", "Nador International Airport", "Nador", "Morocco");
        createAirportIfNotExists("OUJ", "Angads Airport", "Oujda", "Morocco");
        createAirportIfNotExists("ESS", "Essaouira-Mogador Airport", "Essaouira", "Morocco");
    }

    private void createAirportIfNotExists(String iataCode, String name, String city, String country) {
        if (!airportRepository.existsByIataCode(iataCode)) {
            Airport airport = new Airport();
            airport.setIataCode(iataCode);
            airport.setName(name);
            airport.setCity(city);
            airport.setCountry(country);
            airportRepository.save(airport);
        }
    }
    
    private void seedAirplanes() {
        // Wide-body aircraft for long-haul flights
        createAirplaneIfNotExists("Boeing 777-300ER", 8, 42, 310);
        createAirplaneIfNotExists("Boeing 787-9 Dreamliner", 6, 36, 290);
        createAirplaneIfNotExists("Airbus A350-900", 12, 45, 253);
        createAirplaneIfNotExists("Airbus A330-300", 10, 36, 222);
        createAirplaneIfNotExists("Boeing 777-200LR", 8, 40, 280);
        
        // Narrow-body aircraft for regional and medium-haul flights
        createAirplaneIfNotExists("Boeing 737-800", 8, 20, 150);
        createAirplaneIfNotExists("Airbus A320-200", 8, 24, 144);
        createAirplaneIfNotExists("Boeing 737 MAX 8", 8, 20, 162);
        createAirplaneIfNotExists("Airbus A321neo", 12, 28, 165);
        createAirplaneIfNotExists("Boeing 737-700", 8, 12, 124);
        
        // Premium aircraft configurations
        createAirplaneIfNotExists("Boeing 787-8 Dreamliner", 4, 28, 186);
        createAirplaneIfNotExists("Airbus A350-1000", 16, 56, 293);
        createAirplaneIfNotExists("Boeing 777-300", 8, 52, 358);
        createAirplaneIfNotExists("Airbus A340-600", 12, 42, 267);
        
        // Regional jets
        createAirplaneIfNotExists("Embraer E190", 0, 8, 92);
        createAirplaneIfNotExists("Bombardier CRJ-900", 0, 12, 64);
        createAirplaneIfNotExists("Embraer E175", 0, 12, 64);
        
        // Additional configurations for variety
        createAirplaneIfNotExists("Airbus A319-100", 0, 16, 124);
        createAirplaneIfNotExists("Boeing 757-200", 6, 24, 180);
        createAirplaneIfNotExists("Airbus A380-800", 14, 76, 427);
    }
    
    private void createAirplaneIfNotExists(String model, int firstClassCapacity, int businessClassCapacity, int economyClassCapacity) {
        // Check if airplane with this exact configuration already exists
        boolean exists = airplaneRepository.findAll().stream()
            .anyMatch(airplane -> airplane.getModel().equals(model) &&
                     airplane.getFirstClassCapacity() == firstClassCapacity &&
                     airplane.getBusinessClassCapacity() == businessClassCapacity &&
                     airplane.getEconomyClassCapacity() == economyClassCapacity);
        
        if (!exists) {
            Airplane airplane = new Airplane();
            airplane.setModel(model);
            airplane.setFirstClassCapacity(firstClassCapacity);
            airplane.setBusinessClassCapacity(businessClassCapacity);
            airplane.setEconomyClassCapacity(economyClassCapacity);
            airplaneRepository.save(airplane);
        }
    }
}
