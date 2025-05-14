# Volmaghreb
A flight reservation system built with a microservices architecture using Spring Boot, MySQL, and Thymeleaf.

## Project Structure
- **authentication-service/**: Handles user authentication
- **reservation-service/**: Manages bookings and email notifications
- **flight-service/**: Manages flight operations 
- **notification-service/**: Sends email notifications
- **user-service/**: Manages user profiles
- **payment-service/**: Handles payment processing
- **airport-service/**: Manages airport data

## Setup Instructions
1. Clone the repository: `git clone <url>`
2. Ensure MySQL is running and create a database named "volmaghreb_db"
3. Run all services with Docker: `docker-compose up`
4. Access each service via its port (e.g., Authentication Service at `http://localhost:8081`).

## Technologies
- Spring Boot
- MySQL
- Thymeleaf
- Docker