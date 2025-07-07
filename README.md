# VolMaghreb Flight Reservation System

A comprehensive flight reservation system built with Spring Boot and modern web technologies, designed for managing flight bookings and airline operations.

## 🚀 Features

### For Customers
- **Flight Search**: Search flights by origin, destination, date, and travel class
- **Flight Booking**: Book flights with multiple travelers
- **Reservation Management**: View and manage personal reservations
- **User Authentication**: Secure user registration and login
- **Profile Management**: Update personal information and passwords

### For Administrators
- **Dashboard**: Overview of system statistics and metrics
- **Flight Management**: Create, update, and delete flights
- **Airport Management**: Manage airport information
- **Airplane Management**: Manage aircraft fleet
- **User Management**: View and manage user accounts
- **Reservation Oversight**: Monitor all reservations in the system

## � Screenshots

### Customer Interface

<table>
  <tr>
    <td align="center">
      <img src="src/main/resources/static/assets/images/Screenshots/1.png" width="400px" alt="Flight Search"/>
      <br><b>Flight Search</b>
    </td>
    <td align="center">
      <img src="src/main/resources/static/assets/images/Screenshots/2.png" width="400px" alt="Flight Results"/>
      <br><b>Flight Results</b>
    </td>
  </tr>
  <tr>
    <td align="center">
      <img src="src/main/resources/static/assets/images/Screenshots/3.png" width="400px" alt="Flight Booking"/>
      <br><b>Flight Booking</b>
    </td>
    <td align="center">
      <img src="src/main/resources/static/assets/images/Screenshots/4.png" width="400px" alt="User Reservations"/>
      <br><b>User Reservations</b>
    </td>
  </tr>
  <tr>
    <td align="center" colspan="2">
      <img src="src/main/resources/static/assets/images/Screenshots/5.png" width="400px" alt="User Profile"/>
      <br><b>User Profile</b>
    </td>
  </tr>
</table>

### Admin Interface

<table>
  <tr>
    <td align="center">
      <img src="src/main/resources/static/assets/images/Screenshots/6.png" width="400px" alt="Admin Dashboard"/>
      <br><b>Admin Dashboard</b>
    </td>
    <td align="center">
      <img src="src/main/resources/static/assets/images/Screenshots/7.png" width="400px" alt="Flight Management"/>
      <br><b>Flight Management</b>
    </td>
  </tr>
  <tr>
    <td align="center">
      <img src="src/main/resources/static/assets/images/Screenshots/8.png" width="400px" alt="Airport Management"/>
      <br><b>Airport Management</b>
    </td>
    <td align="center">
      <img src="src/main/resources/static/assets/images/Screenshots/9.png" width="400px" alt="User Management"/>
      <br><b>User Management</b>
    </td>
  </tr>
  <tr>
    <td align="center">
      <img src="src/main/resources/static/assets/images/Screenshots/10.png" width="400px" alt="Airplane Management"/>
      <br><b>Airplane Management</b>
    </td>
    <td align="center">
      <img src="src/main/resources/static/assets/images/Screenshots/11.png" width="400px" alt="Reservation Management"/>
      <br><b>Reservation Management</b>
    </td>
  </tr>
</table>

## �🛠️ Technology Stack

- **Backend**: Spring Boot 3.2.0, Java 17
- **Frontend**: Thymeleaf, Bootstrap 5.3.2, jQuery 3.7.1
- **Database**: MySQL 8.0
- **Security**: Spring Security 6
- **Build Tool**: Maven
- **Additional**: Lombok, Apache Commons Lang3

## 📋 Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## ⚙️ Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd volmaghreb
```

### 2. Database Setup
Create a MySQL database:
```sql
CREATE DATABASE volmaghreb_db;
```

### 3. Environment Configuration
Create a `.env` file in the root directory:
```env
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password
```

Alternatively, copy and configure the application properties:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Update the database credentials in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/volmaghreb_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

### 4. Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

The application will be available at: `http://localhost:8080/volmaghreb`

## 🔐 Default Credentials

### Admin Account
- **Email**: admin@volmaghreb.com
- **Password**: admin123

*Note: The admin account is automatically created on first startup.*

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/volmaghreb/reservation/
│   │   ├── controllers/          # Web and REST controllers
│   │   ├── services/             # Business logic layer
│   │   ├── repositories/         # Data access layer
│   │   ├── entities/             # JPA entities
│   │   ├── dtos/                 # Data transfer objects
│   │   ├── enums/                # Enumeration classes
│   │   ├── mappers/              # Entity-DTO mappers
│   │   └── config/               # Configuration classes
│   ├── resources/
│   │   ├── templates/            # Thymeleaf templates
│   │   ├── static/               # Static web assets
│   │   └── application.properties
```

## 🌐 API Endpoints

### Public Endpoints
- `GET /` - Home page
- `GET /auth/sign-in` - Login page
- `GET /auth/sign-up` - Registration page
- `GET /about` - About page
- `GET /contact` - Contact page

### Client Endpoints
- `GET /flights` - Flight search
- `GET /flights/search` - Search results
- `GET /reservations` - User reservations
- `POST /reservations/book` - Book a flight
- `GET /user/profile` - User profile

### Admin Endpoints
- `GET /admin/dashboard` - Admin dashboard
- `GET /admin/flights` - Flight management
- `GET /admin/airports` - Airport management
- `GET /admin/airplanes` - Airplane management
- `GET /admin/users` - User management
- `GET /admin/reservations` - Reservation management

### REST API
- `GET /api/flights` - Get all flights
- `POST /api/flights` - Create flight
- `GET /api/airports` - Get all airports
- `POST /api/reservations` - Create reservation

## 🔧 Configuration

### Server Configuration
```properties
server.port=8080
server.servlet.context-path=/volmaghreb
```

### Database Configuration
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### Security Configuration
- Form-based authentication
- Role-based access control (ADMIN, CLIENT)
- Session management with remember-me functionality

## 🎨 UI Features

- **Responsive Design**: Bootstrap-based responsive layout
- **Modern Interface**: Clean and professional design
- **Interactive Elements**: Dynamic form validation and feedback
- **Search Functionality**: Advanced flight search with filters
- **User Dashboard**: Personalized user experience

## 📊 Database Schema

The system uses the following main entities:
- **User**: System users (clients and admins)
- **Flight**: Flight information
- **Airport**: Airport details
- **Airplane**: Aircraft information
- **Reservation**: Booking records
- **Seat**: Seat assignments

## 🧪 Testing

Run tests with:
```bash
mvn test
```

## 📝 Development

### Adding New Features
1. Create entities in the `entities` package
2. Add repositories in the `repositories` package
3. Implement services in the `services` package
4. Create controllers in the `controllers` package
5. Add templates in `src/main/resources/templates`

### Code Style
- Follow Spring Boot best practices
- Use Lombok annotations for boilerplate code
- Implement proper error handling
- Add appropriate validation

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is proprietary software developed for VolMaghreb.

## 📞 Support

For support and questions, please contact the development team.

---

**VolMaghreb Flight Reservation System** - Streamlining air travel in Morocco and beyond.