# 📋 Plan de Répartition du Travail - Système de Réservation de Vols MVC
## 👥 Équipe de 4 Développeurs - VolMaghreb

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1-green.svg)](https://www.thymeleaf.org/)

---

## 📊 **Vue d'ensemble du Projet**

**Nom du Projet** : VolMaghreb - Système de Réservation de Vols  
**Architecture** : Model-View-Controller (MVC) Monolithique  
**Technologie principale** : Spring Boot 3.2.0  
**Durée estimée** : 3 semaines (15 jours ouvrables)  
**Date de début** : 30 Mai 2025  
**Date de livraison prévue** : 20 Juin 2025  

### 🎯 **Objectifs du Projet**
- Développer une plateforme de réservation de vols simple et efficace
- Implémenter une architecture MVC robuste et maintenable
- Assurer une expérience utilisateur moderne et responsive
- Garantir la sécurité des données et des transactions
- Faciliter la collaboration en équipe avec Git

---

## 🏗️ **Architecture du Système**

### **Pattern MVC Détaillé**
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│      VIEW       │    │   CONTROLLER    │    │      MODEL      │
│   (Thymeleaf)   │◄───┤  (Spring MVC)   │───►│   (JPA/Hibernate)│
│                 │    │                 │    │                 │
│ • Templates     │    │ • @Controller   │    │ • Entities      │
│ • HTML/CSS/JS   │    │ • @RequestMapping│    │ • Repositories  │
│ • Static Assets │    │ • @Service      │    │ • Services      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### **Stack Technologique**
| Couche | Technologie | Version | Usage |
|--------|-------------|---------|-------|
| **Framework** | Spring Boot | 3.2.0 | Framework principal |
| **Sécurité** | Spring Security | 6.2.0 | Authentification/Autorisation |
| **ORM** | Spring Data JPA | 3.2.0 | Accès aux données |
| **Base de données** | MySQL | 8.0 | Persistance des données |
| **Template Engine** | Thymeleaf | 3.1.0 | Rendu des vues |
| **Frontend** | Bootstrap | 5.3.0 | CSS Framework |
| **Build Tool** | Maven | 3.9.0 | Gestion des dépendances |
| **Conteneurisation** | Docker | Latest | Environnement de développement |

---

## 👥 **Répartition de l'Équipe & Responsabilités**

### 📊 **Matrice de Responsabilités (RACI)**
| Tâche | Dev 1 | Dev 2 | Dev 3 | Dev 4 |
|-------|-------|-------|-------|-------|
| Configuration projet | **R** | A | C | I |
| Authentification | **R** | C | I | C |
| Gestion vols | C | **R** | I | C |
| Réservations | I | C | **R** | C |
| Interface utilisateur | C | I | C | **R** |
| Base de données | C | C | C | **R** |
| Tests | C | C | C | **R** |
| Déploiement | I | I | I | **R** |

*R=Responsable, A=Approbateur, C=Consulté, I=Informé*

---

## 🎯 **DÉVELOPPEUR 1 : TECH LEAD & BACKEND SENIOR**
### 🔐 *Spécialité : Architecture, Authentification & Sécurité*

**Profil requis** : 3+ ans d'expérience Spring Boot, connaissance approfondie Spring Security  
**Temps alloué** : 100% sur le projet (40h/semaine)  
**Budget estimé** : 15 jours-homme  

### **🎯 Objectifs Principaux**
- Mettre en place l'architecture solide du projet
- Garantir la sécurité de l'application
- Encadrer l'équipe technique
- Assurer la qualité du code

### **📋 Responsabilités détaillées**

#### **🔧 Phase 1 : Architecture & Configuration (Jours 1-3)**
- [ ] **Setup du projet Spring Boot**
  - ✅ Configuration Maven avec toutes les dépendances
  - ✅ Structure des packages selon les bonnes pratiques
  - ✅ Configuration multi-environnements (dev, test, prod)
  - ✅ Configuration des logs avec Logback
  - ⏱️ Estimation : 1 jour

- [ ] **Configuration Base de Données**
  - Configuration des connexions MySQL
  - Setup des pools de connexions (HikariCP)
  - Configuration JPA/Hibernate optimisée
  - Scripts de migration avec Flyway
  - ⏱️ Estimation : 0.5 jour

- [ ] **Architecture de sécurité**
  - Configuration Spring Security complète
  - Gestion des sessions et CSRF
  - Configuration CORS pour les API
  - ⏱️ Estimation : 1.5 jour

#### **👤 Phase 2 : Système d'Authentification (Jours 4-7)**
- [ ] **Modèle Utilisateur**
  ```java
  @Entity
  public class User {
      // Champs : id, email, password, firstName, lastName, role, etc.
      // Relations : OneToMany avec Reservation
  }
  ```
  - Entité User avec validation
  - Énumération des rôles (USER, ADMIN, MANAGER)
  - Hashage des mots de passe avec BCrypt
  - ⏱️ Estimation : 1 jour

- [ ] **Services d'Authentification**
  ```java
  @Service
  public class UserService {
      // Méthodes : register, authenticate, findByEmail, etc.
  }
  ```
  - UserService avec toute la logique métier
  - Validation des données d'inscription
  - Gestion des erreurs d'authentification
  - ⏱️ Estimation : 1.5 jour

- [ ] **Contrôleurs d'Authentification**
  ```java
  @Controller
  public class AuthController {
      // Endpoints : /login, /register, /logout
  }
  ```
  - Contrôleur pour login/register/logout
  - Gestion des redirections après authentification
  - Messages d'erreur utilisateur-friendly
  - ⏱️ Estimation : 1.5 jour

#### **🔒 Phase 3 : Sécurité Avancée (Jours 8-10)**
- [ ] **Configuration Spring Security**
  ```java
  @Configuration
  @EnableWebSecurity
  public class SecurityConfig {
      // Configuration des règles d'accès
  }
  ```
  - Matrice d'autorisation par URL
  - Protection contre les attaques communes (XSS, CSRF)
  - Configuration des headers de sécurité
  - ⏱️ Estimation : 1 jour

- [ ] **Tests de Sécurité**
  - Tests unitaires pour l'authentification
  - Tests d'intégration Spring Security
  - Tests de non-régression
  - ⏱️ Estimation : 1 jour

- [ ] **Documentation & Code Review**
  - Documentation technique de la sécurité
  - Revue de code des autres développeurs
  - Formation équipe sur les bonnes pratiques
  - ⏱️ Estimation : 1 jour

### **📁 Livrables & Fichiers**
```
src/main/java/com/volmaghreb/reservation/
├── FlightReservationApplication.java        ⭐ CRITICAL
├── config/
│   ├── SecurityConfig.java                 ⭐ CRITICAL  
│   ├── DatabaseConfig.java                 🔧 IMPORTANT
│   └── WebConfig.java                      🔧 IMPORTANT
├── model/
│   └── User.java                           ⭐ CRITICAL
├── repository/
│   └── UserRepository.java                 ⭐ CRITICAL
├── service/
│   ├── UserService.java                    ⭐ CRITICAL
│   └── impl/UserServiceImpl.java           ⭐ CRITICAL
├── controller/
│   └── AuthController.java                 ⭐ CRITICAL
├── dto/
│   ├── UserDTO.java                        🔧 IMPORTANT
│   ├── LoginForm.java                      🔧 IMPORTANT
│   └── RegisterForm.java                   🔧 IMPORTANT
└── exception/
    ├── AuthenticationException.java        🔧 IMPORTANT
    └── SecurityException.java              🔧 IMPORTANT

src/main/resources/
├── application.properties                   ⭐ CRITICAL
├── application-dev.properties              🔧 IMPORTANT
├── application-prod.properties             🔧 IMPORTANT
└── templates/
    ├── login.html                          ⭐ CRITICAL
    ├── register.html                       ⭐ CRITICAL
    └── layout/
        ├── header.html                     🔧 IMPORTANT
        └── base.html                       🔧 IMPORTANT
```

### **🎯 Critères de Validation**
- [ ] ✅ Les utilisateurs peuvent s'inscrire avec validation email
- [ ] ✅ L'authentification fonctionne (login/logout)
- [ ] ✅ Les routes sont protégées selon les rôles
- [ ] ✅ Les sessions sont gérées sécurisement
- [ ] ✅ Protection contre les attaques (CSRF, XSS)
- [ ] ✅ Tests de sécurité passent à 100%
- [ ] ✅ Documentation technique complète

### **⚠️ Points d'Attention & Risques**
| Risque | Impact | Probabilité | Mitigation |
|--------|---------|-------------|------------|
| Problème de configuration Spring Security | 🔴 Élevé | 🟡 Moyen | Documentation détaillée, tests |
| Retard sur l'authentification | 🔴 Élevé | 🟡 Moyen | Prioriser les fonctionnalités core |
| Problème de performance | 🟡 Moyen | 🟢 Faible | Monitoring, tests de charge |

---

---

## 🎯 **DÉVELOPPEUR 2 : BACKEND SPECIALIST**
### ✈️ *Spécialité : Gestion des Vols, Aéroports & APIs*

**Profil requis** : 2+ ans d'expérience Spring Boot, JPA/Hibernate, API REST  
**Temps alloué** : 100% sur le projet (40h/semaine)  
**Budget estimé** : 15 jours-homme  

### **🎯 Objectifs Principaux**
- Développer le cœur métier de l'application (vols)
- Créer des APIs performantes et bien documentées
- Implémenter la logique de recherche complexe
- Assurer l'intégrité des données

### **📋 Responsabilités détaillées**

#### **🏢 Phase 1 : Fondations Métier (Jours 1-4)**
- [ ] **Modèles de Données Vols**
  ```java
  @Entity
  public class Airport {
      // Champs : code IATA, nom, ville, pays, timezone
  }
  
  @Entity
  public class Flight {
      // Champs : numéro, aéroports, horaires, prix, sièges
      // Relations : ManyToOne avec Airport
  }
  ```
  - Entité Airport avec codes IATA/ICAO
  - Entité Flight avec toutes les relations
  - Contraintes de validation métier
  - Index de performance sur colonnes clés
  - ⏱️ Estimation : 2 jours

- [ ] **Repositories & Requêtes**
  ```java
  @Repository
  public interface FlightRepository extends JpaRepository<Flight, Long> {
      @Query("SELECT f FROM Flight f WHERE...")
      List<Flight> findFlightsByCriteria(...);
  }
  ```
  - Repositories JPA avec requêtes personnalisées
  - Requêtes optimisées pour la recherche
  - Pagination et tri des résultats
  - ⏱️ Estimation : 1.5 jour

- [ ] **Données Initiales**
  ```java
  @Component
  public class DataLoader implements ApplicationRunner {
      // Chargement des aéroports et vols de test
  }
  ```
  - DataLoader pour aéroports marocains et internationaux
  - Vols de démonstration réalistes
  - Scripts SQL d'initialisation
  - ⏱️ Estimation : 0.5 jour

#### **🔍 Phase 2 : Logique de Recherche (Jours 5-8)**
- [ ] **Service de Recherche Avancée**
  ```java
  @Service
  public class FlightSearchService {
      public Page<Flight> searchFlights(FlightSearchCriteria criteria);
      public List<Flight> findAlternativeFlights(...);
  }
  ```
  - Recherche multi-critères (dates, villes, prix)
  - Filtrage par compagnie, horaires, escales
  - Suggestions de vols alternatifs
  - Cache des résultats fréquents
  - ⏱️ Estimation : 2 jours

- [ ] **Algorithmes de Pricing**
  ```java
  @Component
  public class PricingEngine {
      public BigDecimal calculateDynamicPrice(Flight flight, LocalDate bookingDate);
  }
  ```
  - Calcul de prix dynamique selon demande
  - Gestion des promotions et réductions
  - Tarifs selon la classe (Économique, Business)
  - ⏱️ Estimation : 1.5 jour

- [ ] **Gestion des Stocks**
  ```java
  @Service
  public class SeatInventoryService {
      public boolean reserveSeats(Long flightId, int seatCount);
      public void releaseSeats(Long flightId, int seatCount);
  }
  ```
  - Gestion concurrentielle des sièges
  - Système de réservation temporaire
  - Alertes de surbooking
  - ⏱️ Estimation : 0.5 jour

#### **🌐 Phase 3 : APIs & Performance (Jours 9-12)**
- [ ] **Contrôleurs REST**
  ```java
  @RestController
  @RequestMapping("/api/flights")
  public class FlightApiController {
      @GetMapping("/search")
      public ResponseEntity<Page<FlightDTO>> searchFlights(...);
  }
  ```
  - API REST complète pour les vols
  - Documentation OpenAPI/Swagger
  - Gestion des erreurs HTTP appropriées
  - Validation des paramètres d'entrée
  - ⏱️ Estimation : 2 jours

- [ ] **Contrôleurs Web MVC**
  ```java
  @Controller
  public class FlightController {
      @GetMapping("/flights/search")
      public String searchPage(Model model);
  }
  ```
  - Contrôleurs pour les vues Thymeleaf
  - Gestion des formulaires de recherche
  - Pagination des résultats
  - ⏱️ Estimation : 1 jour

- [ ] **Optimisation & Performance**
  - Cache Redis pour recherches fréquentes
  - Optimisation des requêtes JPA
  - Compression des réponses JSON
  - Monitoring des performances
  - ⏱️ Estimation : 1 jour

#### **🧪 Phase 4 : Tests & Documentation (Jours 13-15)**
- [ ] **Tests Complets**
  ```java
  @DataJpaTest
  class FlightRepositoryTest {
      // Tests des requêtes JPA
  }
  
  @WebMvcTest
  class FlightControllerTest {
      // Tests des contrôleurs
  }
  ```
  - Tests unitaires des services (90%+ couverture)
  - Tests d'intégration des APIs
  - Tests de performance des recherches
  - ⏱️ Estimation : 2 jours

- [ ] **Documentation Technique**
  - Documentation API avec Swagger
  - Guide d'utilisation des services
  - Schémas de base de données
  - ⏱️ Estimation : 1 jour

### **📁 Livrables & Fichiers**
```
src/main/java/com/volmaghreb/reservation/
├── model/
│   ├── Airport.java                        ⭐ CRITICAL
│   ├── Flight.java                         ⭐ CRITICAL
│   ├── FlightClass.java (enum)             🔧 IMPORTANT
│   └── FlightStatus.java (enum)            🔧 IMPORTANT
├── repository/
│   ├── AirportRepository.java              ⭐ CRITICAL
│   └── FlightRepository.java               ⭐ CRITICAL
├── service/
│   ├── FlightService.java                  ⭐ CRITICAL
│   ├── FlightSearchService.java            ⭐ CRITICAL
│   ├── PricingService.java                 🔧 IMPORTANT
│   └── impl/
│       ├── FlightServiceImpl.java          ⭐ CRITICAL
│       ├── FlightSearchServiceImpl.java    ⭐ CRITICAL
│       └── PricingServiceImpl.java         🔧 IMPORTANT
├── controller/
│   ├── FlightController.java               ⭐ CRITICAL
│   ├── FlightApiController.java            🔧 IMPORTANT
│   └── HomeController.java                 🔧 IMPORTANT
├── dto/
│   ├── FlightDTO.java                      ⭐ CRITICAL
│   ├── FlightSearchCriteria.java           ⭐ CRITICAL
│   ├── AirportDTO.java                     🔧 IMPORTANT
│   └── FlightSearchForm.java               🔧 IMPORTANT
└── config/
    ├── DataLoader.java                     ⭐ CRITICAL
    └── CacheConfig.java                    📊 NICE_TO_HAVE

src/main/resources/
├── data/
│   ├── airports.sql                        ⭐ CRITICAL
│   └── sample-flights.sql                  🔧 IMPORTANT
└── static/js/
    └── flight-search.js                    🔧 IMPORTANT
```

### **🎯 Critères de Validation**
- [ ] ✅ La recherche de vols fonctionne avec tous les filtres
- [ ] ✅ Les données d'aéroports sont correctement chargées
- [ ] ✅ La pagination et le tri fonctionnent parfaitement
- [ ] ✅ Les APIs REST répondent en moins de 500ms
- [ ] ✅ La gestion des sièges évite les conflits
- [ ] ✅ Les prix sont calculés correctement
- [ ] ✅ Tests de couverture > 85%

### **📊 Métriques de Performance**
| Métrique | Objectif | Méthode de mesure |
|----------|----------|-------------------|
| Temps de recherche | < 500ms | Tests de performance |
| Disponibilité API | > 99.5% | Monitoring Actuator |
| Précision des résultats | 100% | Tests fonctionnels |
| Couverture de tests | > 85% | JaCoCo |

### **⚠️ Points d'Attention & Risques**
| Risque | Impact | Probabilité | Mitigation |
|--------|---------|-------------|------------|
| Performance recherche lente | 🔴 Élevé | 🟡 Moyen | Cache, index DB, optimisation requêtes |
| Conflit sièges concurrent | 🔴 Élevé | 🟡 Moyen | Locks optimistes, transactions |
| Données incohérentes | 🟡 Moyen | 🟢 Faible | Validation rigoureuse, contraintes DB |

---

---

## 🎯 **DÉVELOPPEUR 3 : BACKEND BUSINESS**
### 🎫 *Spécialité : Réservations, Paiements & Notifications*

**Profil requis** : 2+ ans d'expérience Spring Boot, logique métier complexe, intégrations API  
**Temps alloué** : 100% sur le projet (40h/semaine)  
**Budget estimé** : 15 jours-homme  

### **🎯 Objectifs Principaux**
- Développer le processus complet de réservation
- Implémenter le système de paiement sécurisé
- Gérer les notifications multi-canal
- Assurer la cohérence des données transactionnelles

### **📋 Responsabilités détaillées**

#### **🎫 Phase 1 : Système de Réservation (Jours 1-5)**
- [ ] **Modèles de Réservation**
  ```java
  @Entity
  public class Reservation {
      // Champs : numéro, utilisateur, vol, passagers, statut, prix
      // Relations : ManyToOne User, ManyToOne Flight
      // Validations : dates, nombre passagers, etc.
  }
  
  @Entity
  public class Passenger {
      // Champs : nom, prénom, passeport, âge, type (adulte/enfant)
  }
  ```
  - Entité Reservation avec workflow complet
  - Entité Passenger pour gestion multi-passagers
  - Énumérations pour statuts (PENDING, CONFIRMED, CANCELLED)
  - Contraintes business et validation
  - ⏱️ Estimation : 2 jours

- [ ] **Service de Réservation**
  ```java
  @Service
  @Transactional
  public class ReservationService {
      public Reservation createReservation(ReservationRequest request);
      public void confirmReservation(String reservationNumber);
      public void cancelReservation(Long id, String reason);
  }
  ```
  - Logique métier complète de réservation
  - Gestion des états et transitions
  - Validation de disponibilité en temps réel
  - Calcul automatique des prix avec taxes
  - Gestion des annulations et remboursements
  - ⏱️ Estimation : 2.5 jours

- [ ] **Génération des Références**
  ```java
  @Component
  public class ReservationNumberGenerator {
      public String generateReservationNumber();
      // Format : VM-YYYYMMDD-XXXXX (VM = VolMaghreb)
  }
  ```
  - Génération unique des numéros de réservation
  - Codes de confirmation sécurisés
  - QR codes pour les billets électroniques
  - ⏱️ Estimation : 0.5 jour

#### **💳 Phase 2 : Système de Paiement (Jours 6-9)**
- [ ] **Modèles de Paiement**
  ```java
  @Entity
  public class Payment {
      // Champs : montant, méthode, statut, transaction_id
      // Relations : OneToOne avec Reservation
      // Audit : dates création/modification
  }
  
  public enum PaymentMethod {
      CREDIT_CARD, PAYPAL, BANK_TRANSFER, APPLE_PAY
  }
  ```
  - Entité Payment avec audit complet
  - Support multi-méthodes de paiement
  - Gestion des devises (MAD, EUR, USD)
  - Historique des transactions
  - ⏱️ Estimation : 1.5 jour

- [ ] **Service de Paiement**
  ```java
  @Service
  public class PaymentService {
      public Payment processPayment(PaymentRequest request);
      public void refundPayment(Long paymentId);
      public PaymentStatus checkPaymentStatus(String transactionId);
  }
  ```
  - Intégration simulée avec gateway de paiement
  - Gestion des échecs et retry automatique
  - Système de remboursement
  - Validation des cartes de crédit
  - Logs de sécurité pour audit
  - ⏱️ Estimation : 2 jours

- [ ] **Sécurité des Paiements**
  ```java
  @Component
  public class PaymentSecurityService {
      public boolean validateCreditCard(String cardNumber);
      public String encryptSensitiveData(String data);
  }
  ```
  - Encryption des données sensibles
  - Validation des numéros de carte
  - Détection de fraudes basique
  - Compliance PCI DSS (simulation)
  - ⏱️ Estimation : 0.5 jour

#### **📧 Phase 3 : Système de Notifications (Jours 10-12)**
- [ ] **Service de Notifications**
  ```java
  @Service
  public class NotificationService {
      public void sendBookingConfirmation(Reservation reservation);
      public void sendPaymentReceipt(Payment payment);
      public void sendFlightReminder(Reservation reservation);
  }
  ```
  - Notifications email avec templates HTML
  - SMS notifications (simulation)
  - Notifications push (préparation)
  - Templates personnalisables par langue
  - Queue asynchrone pour performance
  - ⏱️ Estimation : 2 jours

- [ ] **Templates d'Emails**
  ```html
  <!-- Confirmation de réservation -->
  <!-- Reçu de paiement -->
  <!-- Rappel de vol -->
  <!-- Annulation -->
  ```
  - Templates HTML responsives
  - Support multi-langues (FR, AR, EN)
  - Branding VolMaghreb
  - QR codes et liens utiles
  - ⏱️ Estimation : 1 jour

#### **🔧 Phase 4 : Contrôleurs & Intégration (Jours 13-15)**
- [ ] **Contrôleurs de Réservation**
  ```java
  @Controller
  public class ReservationController {
      @GetMapping("/reservations/create")
      @PostMapping("/reservations")
      @GetMapping("/reservations/{id}")
  }
  ```
  - Interface web complète de réservation
  - Workflow étape par étape
  - Gestion des erreurs utilisateur
  - ⏱️ Estimation : 1.5 jour

- [ ] **Contrôleurs de Paiement**
  ```java
  @Controller
  public class PaymentController {
      @GetMapping("/payment/checkout")
      @PostMapping("/payment/process")
  }
  ```
  - Interface de paiement sécurisée
  - Intégration avec frontend JavaScript
  - Gestion des callbacks de paiement
  - ⏱️ Estimation : 1 jour

- [ ] **Tests & Finalisation**
  - Tests unitaires complets (>90% couverture)
  - Tests d'intégration des workflows
  - Tests de charge pour les transactions
  - Documentation technique
  - ⏱️ Estimation : 0.5 jour

### **📁 Livrables & Fichiers**
```
src/main/java/com/volmaghreb/reservation/
├── model/
│   ├── Reservation.java                    ⭐ CRITICAL
│   ├── Passenger.java                      ⭐ CRITICAL
│   ├── Payment.java                        ⭐ CRITICAL
│   ├── Notification.java                   🔧 IMPORTANT
│   ├── ReservationStatus.java (enum)       ⭐ CRITICAL
│   ├── PaymentMethod.java (enum)           ⭐ CRITICAL
│   └── PaymentStatus.java (enum)           ⭐ CRITICAL
├── repository/
│   ├── ReservationRepository.java          ⭐ CRITICAL
│   ├── PassengerRepository.java            🔧 IMPORTANT
│   ├── PaymentRepository.java              ⭐ CRITICAL
│   └── NotificationRepository.java         📊 NICE_TO_HAVE
├── service/
│   ├── ReservationService.java             ⭐ CRITICAL
│   ├── PaymentService.java                 ⭐ CRITICAL
│   ├── NotificationService.java            ⭐ CRITICAL
│   ├── ReservationNumberGenerator.java     🔧 IMPORTANT
│   └── impl/
│       ├── ReservationServiceImpl.java     ⭐ CRITICAL
│       ├── PaymentServiceImpl.java         ⭐ CRITICAL
│       └── NotificationServiceImpl.java    ⭐ CRITICAL
├── controller/
│   ├── ReservationController.java          ⭐ CRITICAL
│   ├── PaymentController.java              ⭐ CRITICAL
│   └── UserDashboardController.java        🔧 IMPORTANT
├── dto/
│   ├── ReservationDTO.java                 ⭐ CRITICAL
│   ├── ReservationRequest.java             ⭐ CRITICAL
│   ├── PaymentDTO.java                     ⭐ CRITICAL
│   ├── PaymentRequest.java                 ⭐ CRITICAL
│   └── PassengerDTO.java                   🔧 IMPORTANT
└── config/
    ├── EmailConfig.java                    🔧 IMPORTANT
    └── AsyncConfig.java                    📊 NICE_TO_HAVE

src/main/resources/
├── templates/
│   ├── email/
│   │   ├── booking-confirmation.html       ⭐ CRITICAL
│   │   ├── payment-receipt.html            ⭐ CRITICAL
│   │   └── flight-reminder.html            🔧 IMPORTANT
│   ├── reservations/
│   │   ├── create.html                     ⭐ CRITICAL
│   │   ├── summary.html                    ⭐ CRITICAL
│   │   ├── confirmation.html               ⭐ CRITICAL
│   │   └── list.html                       🔧 IMPORTANT
│   └── payment/
│       ├── checkout.html                   ⭐ CRITICAL
│       └── success.html                    🔧 IMPORTANT
└── static/js/
    ├── reservation.js                      🔧 IMPORTANT
    └── payment.js                          🔧 IMPORTANT
```

### **🎯 Critères de Validation**
- [ ] ✅ Les réservations sont créées et sauvegardées correctement
- [ ] ✅ Le workflow de paiement fonctionne de bout en bout
- [ ] ✅ Les emails de confirmation sont envoyés automatiquement
- [ ] ✅ La gestion des sièges évite les doublons
- [ ] ✅ Les numéros de réservation sont uniques
- [ ] ✅ Les annulations libèrent les sièges
- [ ] ✅ L'historique des transactions est complet

### **💰 Règles Business Critiques**
| Règle | Description | Validation |
|-------|-------------|------------|
| Unicité réservation | Un siège ne peut être réservé qu'une fois | Contrainte DB + Service |
| Timeout paiement | Réservation expire après 15 minutes | Job scheduler |
| Validation passagers | Données passeport obligatoires | Validation Bean |
| Remboursement | Conditions selon timing annulation | Service métier |

### **⚠️ Points d'Attention & Risques**
| Risque | Impact | Probabilité | Mitigation |
|--------|---------|-------------|------------|
| Double réservation même siège | 🔴 Élevé | 🟡 Moyen | Locks pessimistes, transactions |
| Échec paiement non géré | 🔴 Élevé | 🟡 Moyen | Retry logic, monitoring |
| Emails non délivrés | 🟡 Moyen | 🟡 Moyen | Queue + retry, logs détaillés |
| Performance lors pics | 🟡 Moyen | 🟡 Moyen | Cache, async processing |

---

---

## 🎯 **DÉVELOPPEUR 4 : FRONTEND & UI/UX SPECIALIST**
### 🎨 *Spécialité : Interface Utilisateur, Expérience & Design*

**Profil requis** : 2+ ans d'expérience Frontend, HTML/CSS/JS, UX/UI Design  
**Temps alloué** : 100% sur le projet (40h/semaine)  
**Budget estimé** : 15 jours-homme  

### **🎯 Objectifs Principaux**
- Créer une interface moderne et responsive
- Optimiser l'expérience utilisateur (UX)
- Implémenter des interactions fluides
- Assurer l'accessibilité et la performance frontend

### **📋 Responsabilités détaillées**

#### **🎨 Phase 1 : Design System & Foundation (Jours 1-4)**
- [ ] **Architecture Frontend**
  ```html
  <!-- Structure de base Thymeleaf -->
  <!DOCTYPE html>
  <html lang="fr" xmlns:th="http://www.thymeleaf.org">
  <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <!-- Bootstrap 5 + Custom CSS -->
  </head>
  ```
  - Template de base responsive avec Bootstrap 5
  - Architecture CSS modulaire (SCSS recommandé)
  - Variables CSS pour thème VolMaghreb
  - Grid system personnalisé
  - ⏱️ Estimation : 1.5 jour

- [ ] **Design System VolMaghreb**
  ```css
  /* Theme Colors VolMaghreb */
  :root {
      --vm-primary: #2C5F41;      /* Vert Marocain */
      --vm-secondary: #D4AF37;    /* Or */
      --vm-accent: #C41E3A;       /* Rouge */
      --vm-neutral: #F5F5F5;      /* Gris clair */
  }
  ```
  - Palette de couleurs cohérente
  - Typographie (Google Fonts + Arabic support)
  - Iconographie (Font Awesome + custom icons)
  - Composants UI réutilisables
  - Guide de style visuel
  - ⏱️ Estimation : 1.5 jour

- [ ] **Layout Principal**
  ```html
  <!-- layout/base.html -->
  <div class="wrapper">
      <header th:fragment="header" class="navbar-custom">
      <main class="main-content">
      <footer th:fragment="footer" class="footer-custom">
  ```
  - Header avec navigation responsive
  - Footer informatif avec liens utiles
  - Sidebar pour filtres (pages recherche)
  - Navigation mobile (hamburger menu)
  - ⏱️ Estimation : 1 jour

#### **🏠 Phase 2 : Pages Publiques & Navigation (Jours 5-8)**
- [ ] **Page d'Accueil Moderne**
  ```html
  <!-- index.html -->
  <section class="hero-section">
      <div class="search-widget">
          <!-- Widget de recherche rapide -->
      </div>
  </section>
  <section class="features-grid">
      <!-- Avantages VolMaghreb -->
  </section>
  ```
  - Hero section avec widget de recherche
  - Sections promotionnelles animées
  - Testimonials clients
  - Stats en temps réel (vols disponibles)
  - Call-to-action strategiques
  - ⏱️ Estimation : 2 jours

- [ ] **Pages Informatives**
  ```html
  <!-- about.html, contact.html, faq.html -->
  <div class="page-header">
  <div class="content-sections">
  <div class="contact-form">
  ```
  - Page À propos avec histoire VolMaghreb
  - Page Contact avec formulaire fonctionnel
  - FAQ interactive avec accordéons
  - Pages légales (CGV, Politique confidentialité)
  - ⏱️ Estimation : 1.5 jour

- [ ] **Navigation & Accessibilité**
  ```css
  /* Navigation accessible */
  .navbar-nav .nav-link:focus {
      outline: 2px solid var(--vm-primary);
  }
  
  @media (prefers-reduced-motion: reduce) {
      * { animation: none !important; }
  }
  ```
  - Navigation clavier complète
  - Support lecteurs d'écran (ARIA)
  - Contrastes conformes WCAG 2.1
  - Mode sombre optionnel
  - ⏱️ Estimation : 0.5 jour

#### **✈️ Phase 3 : Interface de Recherche (Jours 9-11)**
- [ ] **Formulaire de Recherche Avancé**
  ```html
  <!-- flights/search.html -->
  <form class="flight-search-form" th:object="${searchForm}">
      <div class="form-row">
          <div class="form-group col-md-6">
              <label for="departure">Départ</label>
              <input type="text" class="form-control airport-autocomplete">
          </div>
      </div>
  </form>
  ```
  - Autocomplete pour aéroports avec Ajax
  - Sélecteur de dates avec calendrier
  - Filtres avancés (classe, escales, horaires)
  - Validation côté client en temps réel
  - Sauvegarde des préférences utilisateur
  - ⏱️ Estimation : 1.5 jour

- [ ] **Interface Résultats de Recherche**
  ```html
  <!-- flights/list.html -->
  <div class="flights-container">
      <div class="filters-sidebar">
          <!-- Filtres dynamiques -->
      </div>
      <div class="flights-results">
          <div class="flight-card" th:each="flight : ${flights}">
              <!-- Card vol avec détails -->
          </div>
      </div>
  </div>
  ```
  - Cards de vols avec informations claires
  - Système de filtrage en temps réel
  - Tri par prix, durée, horaires
  - Pagination Ajax pour performance
  - Comparaison de vols (modal)
  - ⏱️ Estimation : 2 jours

- [ ] **Page Détails Vol**
  ```html
  <!-- flights/details.html -->
  <div class="flight-details-container">
      <div class="flight-info-card">
      <div class="pricing-options">
      <div class="seat-map"> <!-- Si temps disponible -->
  ```
  - Informations détaillées du vol
  - Options de tarifs (Économique, Business)
  - Carte interactive des sièges (bonus)
  - Informations aéroports et services
  - ⏱️ Estimation : 0.5 jour

#### **🎫 Phase 4 : Processus de Réservation (Jours 12-15)**
- [ ] **Formulaire de Réservation Multi-étapes**
  ```html
  <!-- reservations/create.html -->
  <div class="reservation-wizard">
      <div class="wizard-steps">
          <div class="step active">1. Passagers</div>
          <div class="step">2. Services</div>
          <div class="step">3. Paiement</div>
      </div>
      <div class="step-content">
          <!-- Contenu dynamique par étape -->
      </div>
  </div>
  ```
  - Wizard à 3 étapes avec progression visuelle
  - Formulaires passagers avec validation
  - Sélection services additionnels
  - Résumé commande avant paiement
  - Sauvegarde automatique (localStorage)
  - ⏱️ Estimation : 2 jours

- [ ] **Interface de Paiement**
  ```html
  <!-- payment/checkout.html -->
  <div class="payment-container">
      <div class="payment-methods">
          <div class="method-card" data-method="card">
              <!-- Formulaire carte de crédit -->
          </div>
      </div>
      <div class="payment-summary">
          <!-- Récapitulatif commande -->
      </div>
  </div>
  ```
  - Interface sécurisée de paiement
  - Support multi-méthodes (carte, PayPal)
  - Validation cartes avec Luhn algorithm
  - Loading states et feedback utilisateur
  - Page de confirmation avec QR code
  - ⏱️ Estimation : 1.5 jour

- [ ] **Dashboard Utilisateur**
  ```html
  <!-- user/dashboard.html -->
  <div class="user-dashboard">
      <div class="dashboard-nav">
      <div class="dashboard-content">
          <div class="reservations-list">
              <!-- Liste des réservations -->
          </div>
      </div>
  </div>
  ```
  - Liste des réservations avec filtres
  - Actions rapides (annuler, modifier)
  - Téléchargement billets PDF
  - Historique des paiements
  - ⏱️ Estimation : 0.5 jour

### **📁 Livrables & Fichiers**
```
src/main/resources/
├── static/
│   ├── css/
│   │   ├── bootstrap.min.css                   ⭐ CRITICAL
│   │   ├── main.css                           ⭐ CRITICAL
│   │   ├── components.css                     ⭐ CRITICAL
│   │   ├── responsive.css                     🔧 IMPORTANT
│   │   └── print.css                          📊 NICE_TO_HAVE
│   ├── js/
│   │   ├── bootstrap.min.js                   ⭐ CRITICAL
│   │   ├── jquery-3.7.1.min.js               ⭐ CRITICAL
│   │   ├── app.js                             ⭐ CRITICAL
│   │   ├── flight-search.js                   ⭐ CRITICAL
│   │   ├── reservation-wizard.js              ⭐ CRITICAL
│   │   ├── payment.js                         🔧 IMPORTANT
│   │   └── utils.js                           🔧 IMPORTANT
│   ├── images/
│   │   ├── logo/
│   │   │   ├── volmaghreb-logo.svg            ⭐ CRITICAL
│   │   │   └── volmaghreb-logo-white.svg      🔧 IMPORTANT
│   │   ├── icons/
│   │   │   ├── plane.svg                      🔧 IMPORTANT
│   │   │   └── payment-methods/               🔧 IMPORTANT
│   │   └── backgrounds/
│   │       ├── hero-bg.jpg                    🔧 IMPORTANT
│   │       └── pattern-bg.svg                 📊 NICE_TO_HAVE
│   └── fonts/                                  📊 NICE_TO_HAVE
├── templates/
│   ├── layout/
│   │   ├── base.html                          ⭐ CRITICAL
│   │   ├── header.html                        ⭐ CRITICAL
│   │   ├── footer.html                        ⭐ CRITICAL
│   │   └── meta.html                          🔧 IMPORTANT
│   ├── index.html                             ⭐ CRITICAL
│   ├── about.html                             🔧 IMPORTANT
│   ├── contact.html                           🔧 IMPORTANT
│   ├── faq.html                               📊 NICE_TO_HAVE
│   ├── auth/
│   │   ├── login.html                         ⭐ CRITICAL
│   │   ├── register.html                      ⭐ CRITICAL
│   │   └── forgot-password.html               🔧 IMPORTANT
│   ├── flights/
│   │   ├── search.html                        ⭐ CRITICAL
│   │   ├── list.html                          ⭐ CRITICAL
│   │   ├── details.html                       ⭐ CRITICAL
│   │   └── compare.html                       📊 NICE_TO_HAVE
│   ├── reservations/
│   │   ├── create.html                        ⭐ CRITICAL
│   │   ├── summary.html                       ⭐ CRITICAL
│   │   ├── confirmation.html                  ⭐ CRITICAL
│   │   └── list.html                          🔧 IMPORTANT
│   ├── payment/
│   │   ├── checkout.html                      ⭐ CRITICAL
│   │   ├── success.html                       ⭐ CRITICAL
│   │   └── failed.html                        🔧 IMPORTANT
│   ├── user/
│   │   ├── dashboard.html                     🔧 IMPORTANT
│   │   ├── profile.html                       🔧 IMPORTANT
│   │   └── settings.html                      📊 NICE_TO_HAVE
│   └── error/
│       ├── 404.html                           🔧 IMPORTANT
│       ├── 500.html                           🔧 IMPORTANT
│       └── maintenance.html                   📊 NICE_TO_HAVE
```

### **🎯 Critères de Validation**
- [ ] ✅ Interface responsive sur tous les écrans (mobile, tablet, desktop)
- [ ] ✅ Performance : Page load < 3 secondes
- [ ] ✅ Accessibilité WCAG 2.1 AA compliance
- [ ] ✅ Compatibilité cross-browser (Chrome, Firefox, Safari, Edge)
- [ ] ✅ Formulaires avec validation client + feedback en temps réel
- [ ] ✅ Navigation intuitive et logique
- [ ] ✅ Design cohérent avec branding VolMaghreb

### **📊 Standards de Performance**
| Métrique | Objectif | Outil de mesure |
|----------|----------|-----------------|
| Page Speed Score | > 90 | Google PageSpeed |
| First Contentful Paint | < 1.5s | Chrome DevTools |
| Cumulative Layout Shift | < 0.1 | Core Web Vitals |
| Accessibility Score | > 95 | axe DevTools |

### **🎨 Guidelines Design**
```css
/* VolMaghreb Design System */
:root {
    /* Couleurs principales */
    --vm-primary: #2C5F41;          /* Vert Marocain */
    --vm-secondary: #D4AF37;        /* Or */
    --vm-accent: #C41E3A;           /* Rouge */
    --vm-neutral-100: #F8F9FA;      /* Très clair */
    --vm-neutral-900: #212529;      /* Très foncé */
    
    /* Typographie */
    --font-primary: 'Inter', sans-serif;
    --font-arabic: 'Cairo', sans-serif;
    
    /* Spacing */
    --spacing-xs: 0.25rem;
    --spacing-sm: 0.5rem;
    --spacing-md: 1rem;
    --spacing-lg: 1.5rem;
    --spacing-xl: 3rem;
    
    /* Shadows */
    --shadow-sm: 0 1px 2px rgba(0,0,0,0.05);
    --shadow-md: 0 4px 6px rgba(0,0,0,0.1);
    --shadow-lg: 0 10px 15px rgba(0,0,0,0.1);
    
    /* Border radius */
    --radius-sm: 0.25rem;
    --radius-md: 0.5rem;
    --radius-lg: 1rem;
}
```

### **⚠️ Points d'Attention & Risques**
| Risque | Impact | Probabilité | Mitigation |
|--------|---------|-------------|------------|
| Performance mobile lente | 🔴 Élevé | 🟡 Moyen | Optimisation images, lazy loading |
| Incompatibilité navigateurs | 🟡 Moyen | 🟡 Moyen | Tests cross-browser, polyfills |
| Surcharge visuelle | 🟡 Moyen | 🟢 Faible | Design system strict, reviews UX |
| Accessibilité insuffisante | 🔴 Élevé | 🟢 Faible | Tests automatisés, audit manuel |

### **🔧 Technologies & Librairies**
| Technologie | Version | Usage | Priorité |
|-------------|---------|-------|----------|
| Bootstrap | 5.3.0 | CSS Framework | ⭐ CRITICAL |
| jQuery | 3.7.1 | DOM manipulation | ⭐ CRITICAL |
| Font Awesome | 6.4.0 | Iconographie | 🔧 IMPORTANT |
| Swiper.js | 10.0.0 | Carousels/Sliders | 📊 NICE_TO_HAVE |
| AOS | 2.3.4 | Animations scroll | 📊 NICE_TO_HAVE |
| Chart.js | 4.3.0 | Graphiques admin | 📊 NICE_TO_HAVE |

---

---

## 📅 **PLANNING GÉNÉRAL (3 SEMAINES)**

### **Semaine 1 : Configuration et Modèles**
- **Tous** : Configuration de l'environnement de développement
- **Personne 1** : Configuration du projet et base de données
- **Personne 2** : Création des entités Flight et Airport
- **Personne 3** : Création des entités Reservation et Payment  
- **Personne 4** : Préparation des assets et templates de base

### **Semaine 2 : Services et Logique Métier**
- **Personne 1** : Implémentation de l'authentification
- **Personne 2** : Services de gestion des vols
- **Personne 3** : Services de réservation et paiement
- **Personne 4** : Interfaces de recherche et vols

### **Semaine 3 : Contrôleurs et Finalisation**
- **Personne 1** : Tests d'authentification et sécurité
- **Personne 2** : API REST et tests des vols
- **Personne 3** : Contrôleurs de réservation et notifications
- **Personne 4** : Interface de réservation et tests d'intégration

---

## 🔧 **OUTILS DE COLLABORATION**

### **Gestion de version :**
```bash
# Structure des branches Git
main
├── feature/auth-system (Personne 1)
├── feature/flight-management (Personne 2)
├── feature/reservation-system (Personne 3)
└── feature/frontend-ui (Personne 4)
```

### **Standards de développement :**
- **Naming conventions** : CamelCase pour Java, kebab-case pour CSS
- **Format des commits** : `[TYPE] Description courte`
  - `[FEAT]` : Nouvelle fonctionnalité
  - `[FIX]` : Correction de bug
  - `[REFACTOR]` : Refactoring
  - `[TEST]` : Ajout de tests

### **Points de synchronisation :**
- **Daily Stand-up** : 15 min chaque matin
- **Code Review** : Avant chaque merge
- **Démo hebdomadaire** : Vendredi après-midi

---

## ✅ **CRITÈRES DE VALIDATION**

### **Personne 1 (Auth) :**
- [ ] Les utilisateurs peuvent s'inscrire et se connecter
- [ ] Les routes sont protégées selon les rôles
- [ ] Les sessions sont gérées correctement

### **Personne 2 (Vols) :**
- [ ] La recherche de vols fonctionne avec filtres
- [ ] Les données d'aéroports sont correctement affichées
- [ ] La pagination fonctionne

### **Personne 3 (Réservations) :**
- [ ] Les réservations sont créées et sauvegardées
- [ ] Les paiements sont simulés correctement
- [ ] Les emails de confirmation sont envoyés

### **Personne 4 (Frontend) :**
- [ ] L'interface est responsive et moderne
- [ ] La navigation est intuitive
- [ ] Les formulaires sont validés côté client

---

## 🚀 **COMMANDES DE DÉMARRAGE**

```bash
# Cloner le projet
git clone <repositories-url>
cd flight-reservation-mvc

# Installer les dépendances
mvn clean install

# Lancer l'application
mvn spring-boot:run

# Accès à l'application
http://localhost:8080
```

---

**Note :** Ce plan peut être ajusté selon l'expérience de chaque développeur et les priorités du projet.
