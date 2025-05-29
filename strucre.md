# Plan de Répartition du Travail - Système de Réservation de Vols MVC
## Équipe de 4 Développeurs

---

## 🎯 **PERSONNE 1 : DÉVELOPPEUR BACKEND SENIOR (Authentification & Sécurité)**

### **Responsabilités principales :**
- Configuration du projet Spring Boot principal
- Système d'authentification et autorisation
- Configuration de la sécurité
- Gestion des utilisateurs

### **Tâches détaillées :**

#### **Phase 1 : Configuration du projet (Semaine 1)**
- [ ] Créer le projet Spring Boot principal avec Maven
- [ ] Configurer les dépendances (Spring Boot, Security, JPA, Thymeleaf)
- [ ] Configurer la base de données MySQL
- [ ] Mettre en place la structure MVC de base
- [ ] Créer le fichier `application.properties` principal

#### **Phase 2 : Authentification (Semaine 2)**
- [ ] Créer l'entité `User` avec JPA
- [ ] Implémenter `UserRepository`
- [ ] Développer `UserService` et `UserServiceImpl`
- [ ] Créer `AuthController` pour login/register
- [ ] Configurer Spring Security (`SecurityConfig`)

#### **Phase 3 : Interface d'authentification (Semaine 3)**
- [ ] Créer les templates Thymeleaf pour login (`login.html`)
- [ ] Créer le template pour l'inscription (`register.html`)
- [ ] Implémenter la gestion des sessions
- [ ] Ajouter la validation des formulaires
- [ ] Tests unitaires pour l'authentification

#### **Fichiers à créer/modifier :**
```
src/main/java/com/volmaghreb/reservation/
├── FlightReservationApplication.java
├── config/
│   ├── SecurityConfig.java
│   └── DatabaseConfig.java
├── model/User.java
├── repository/UserRepository.java
├── service/
│   ├── UserService.java
│   └── impl/UserServiceImpl.java
├── controller/AuthController.java
└── dto/UserDTO.java

src/main/resources/
├── application.properties
└── templates/
    ├── login.html
    ├── register.html
    └── layout/header.html
```

---

## 🎯 **PERSONNE 2 : DÉVELOPPEUR BACKEND (Gestion des Vols & Aéroports)**

### **Responsabilités principales :**
- Gestion des vols et aéroports
- Recherche et filtrage des vols
- APIs pour les vols

### **Tâches détaillées :**

#### **Phase 1 : Modèles de données (Semaine 1)**
- [ ] Créer l'entité `Airport` avec JPA
- [ ] Créer l'entité `Flight` avec relations
- [ ] Configurer les repositories (`AirportRepository`, `FlightRepository`)
- [ ] Ajouter les données initiales des aéroports (DataLoader)

#### **Phase 2 : Services métier (Semaine 2)**
- [ ] Implémenter `FlightService` et `FlightServiceImpl`
- [ ] Développer la logique de recherche de vols
- [ ] Ajouter les filtres (prix, horaires, compagnies)
- [ ] Implémenter la gestion des sièges disponibles

#### **Phase 3 : Contrôleurs (Semaine 3)**
- [ ] Créer `FlightController` pour les APIs
- [ ] Implémenter les endpoints de recherche
- [ ] Ajouter la pagination des résultats
- [ ] Créer `HomeController` pour la page d'accueil
- [ ] Tests unitaires pour les services

#### **Fichiers à créer/modifier :**
```
src/main/java/com/volmaghreb/reservation/
├── model/
│   ├── Airport.java
│   └── Flight.java
├── repository/
│   ├── AirportRepository.java
│   └── FlightRepository.java
├── service/
│   ├── FlightService.java
│   └── impl/FlightServiceImpl.java
├── controller/
│   ├── FlightController.java
│   └── HomeController.java
├── dto/
│   ├── FlightDTO.java
│   └── FlightSearchForm.java
└── config/DataLoader.java
```

---

## 🎯 **PERSONNE 3 : DÉVELOPPEUR BACKEND (Réservations & Paiements)**

### **Responsabilités principales :**
- Système de réservation
- Gestion des paiements
- Notifications par email

### **Tâches détaillées :**

#### **Phase 1 : Modèles de réservation (Semaine 1)**
- [ ] Créer l'entité `Reservation` avec relations
- [ ] Créer l'entité `Payment`
- [ ] Configurer `ReservationRepository` et `PaymentRepository`
- [ ] Définir les énumérations (statuts, méthodes de paiement)

#### **Phase 2 : Services de réservation (Semaine 2)**
- [ ] Implémenter `ReservationService` et `ReservationServiceImpl`
- [ ] Développer `PaymentService` et `PaymentServiceImpl`
- [ ] Ajouter la logique de validation des réservations
- [ ] Implémenter la gestion des stocks de sièges

#### **Phase 3 : Notifications et contrôleurs (Semaine 3)**
- [ ] Configurer le service d'email (Spring Mail)
- [ ] Implémenter `NotificationService`
- [ ] Créer `ReservationController` et `PaymentController`
- [ ] Ajouter la génération de numéros de réservation
- [ ] Tests unitaires pour les réservations

#### **Fichiers à créer/modifier :**
```
src/main/java/com/volmaghreb/reservation/
├── model/
│   ├── Reservation.java
│   ├── Payment.java
│   └── Notification.java
├── repository/
│   ├── ReservationRepository.java
│   └── PaymentRepository.java
├── service/
│   ├── ReservationService.java
│   ├── PaymentService.java
│   ├── NotificationService.java
│   └── impl/
│       ├── ReservationServiceImpl.java
│       ├── PaymentServiceImpl.java
│       └── NotificationServiceImpl.java
├── controller/
│   ├── ReservationController.java
│   └── PaymentController.java
└── dto/
    ├── ReservationDTO.java
    └── PaymentDTO.java
```

---

## 🎯 **PERSONNE 4 : DÉVELOPPEUR FRONTEND (Interface Utilisateur)**

### **Responsabilités principales :**
- Développement des interfaces utilisateur
- Templates Thymeleaf
- CSS/JavaScript et UX

### **Tâches détaillées :**

#### **Phase 1 : Templates de base (Semaine 1)**
- [ ] Adapter et organiser les assets CSS/JS existants
- [ ] Créer le template de base (`layout.html`)
- [ ] Développer la page d'accueil (`index.html`)
- [ ] Créer la page "À propos" (`about.html`)

#### **Phase 2 : Interface de recherche et vols (Semaine 2)**
- [ ] Créer le formulaire de recherche de vols
- [ ] Développer la page de résultats de recherche (`flights/list.html`)
- [ ] Créer la page de détails d'un vol (`flights/details.html`)
- [ ] Ajouter l'interactivité JavaScript pour les filtres

#### **Phase 3 : Interface de réservation (Semaine 3)**
- [ ] Créer le formulaire de réservation (`reservations/create.html`)
- [ ] Développer la page de confirmation de réservation
- [ ] Créer l'interface de paiement (`payment/checkout.html`)
- [ ] Ajouter la page de gestion des réservations utilisateur
- [ ] Tests d'intégration frontend

#### **Fichiers à créer/modifier :**
```
src/main/resources/
├── static/
│   ├── css/
│   │   ├── style.css
│   │   └── custom.css
│   ├── js/
│   │   ├── functions.js
│   │   └── app.js
│   └── images/
└── templates/
    ├── layout/
    │   ├── layout.html
    │   ├── header.html
    │   └── footer.html
    ├── index.html
    ├── about.html
    ├── contact.html
    ├── flights/
    │   ├── search.html
    │   ├── list.html
    │   └── details.html
    ├── reservations/
    │   ├── create.html
    │   ├── list.html
    │   └── details.html
    └── payment/
        └── checkout.html
```

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
git clone <repository-url>
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
