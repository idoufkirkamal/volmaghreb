# 🛫 VolMaghreb - Système de Réservation de Vols (Architecture MVC)

## 📋 Description du Projet

**VolMaghreb** est un système de réservation de vols développé en architecture **Model-View-Controller (MVC)** avec Spring Boot. Ce projet permet aux utilisateurs de rechercher, réserver et gérer leurs vols vers et depuis le Maroc de manière simple et sécurisée.

## 🏗️ Architecture

Le projet utilise une **architecture MVC monolithique** avec Spring Boot, offrant :
- ✅ Simplicité de développement et de déploiement
- ✅ Performance optimisée pour les équipes de taille moyenne
- ✅ Maintenance facilitée
- ✅ Tests d'intégration simplifiés

### Technologies Utilisées

- **Backend** : Spring Boot 3.2.0, Spring Security, Spring Data JPA
- **Frontend** : Thymeleaf, Bootstrap 5, JavaScript
- **Base de données** : MySQL 8.0
- **Build** : Maven
- **Conteneurisation** : Docker & Docker Compose

## 🚀 Démarrage Rapide

### Prérequis
- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- Git

### Installation

1. **Cloner le projet**
```bash
git clone https://github.com/ouchgoutmohamed/volmaghreb.git
cd volmaghreb
```

2. **Démarrer la base de données**
```bash
docker-compose up -d mysql
```

3. **Compiler et lancer l'application**
```bash
mvn clean install
mvn spring-boot:run
```

4. **Accéder à l'application**
- Application : http://localhost:8080/volmaghreb
- Adminer (DB Admin) : http://localhost:8081

### Configuration par défaut
- **URL Application** : `http://localhost:8080/volmaghreb`
- **Base de données** : `volmaghreb_db`
- **Admin par défaut** : admin / admin123

## 👥 Organisation de l'Équipe (4 Développeurs)

| Développeur | Rôle | Responsabilités |
|-------------|------|-----------------|
| **Développeur 1** | Lead & Backend Core | Configuration, Entités, Services principaux, Sécurité |
| **Développeur 2** | Backend Services & API | Contrôleurs, Services métier, APIs, Tests |
| **Développeur 3** | Frontend & UI/UX | Templates Thymeleaf, CSS/JS, Design responsive |
| **Développeur 4** | Database & DevOps | Base de données, Docker, Tests, Déploiement |

📋 **Voir le plan détaillé** : [PLAN_REPARTITION_TRAVAIL.md](PLAN_REPARTITION_TRAVAIL.md)

## 📁 Structure du Projet

```
volmaghreb/
├── src/
│   ├── main/
│   │   ├── java/com/volmaghreb/reservation/
│   │   │   ├── FlightReservationApplication.java
│   │   │   ├── config/          # Configuration Spring
│   │   │   ├── controller/      # Contrôleurs MVC
│   │   │   ├── model/           # Entités JPA
│   │   │   ├── repository/      # Repositories Spring Data
│   │   │   ├── service/         # Services métier
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   └── exception/       # Gestion des exceptions
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/          # CSS, JS, Images
│   │       └── templates/       # Templates Thymeleaf
│   └── test/                    # Tests unitaires et d'intégration
├── docker-compose.yml           # Configuration Docker
├── pom.xml                      # Configuration Maven
└── README.md
```

## 🎯 Fonctionnalités

### ✅ Fonctionnalités Principales
- **Authentification** : Connexion/Inscription sécurisée
- **Recherche de vols** : Par destination, date, nombre de passagers
- **Réservation** : Processus de réservation complet
- **Paiement** : Simulation de paiement sécurisé
- **Gestion des réservations** : Consultation, modification, annulation
- **Interface responsive** : Compatible mobile et desktop

### 🔄 Fonctionnalités Optionnelles
- **Notifications par email** : Confirmations et rappels
- **Panel d'administration** : Gestion des vols et utilisateurs
- **API REST** : Pour intégrations futures
- **Rapports et statistiques** : Tableaux de bord

## 🛠️ Commandes Utiles

### Développement
```bash
# Démarrer en mode développement
mvn spring-boot:run

# Tests
mvn test

# Build complet
mvn clean package

# Nettoyer le projet
mvn clean
```

### Docker
```bash
# Démarrer tous les services
docker-compose up -d

# Voir les logs
docker-compose logs -f

# Arrêter les services
docker-compose down

# Rebuild les images
docker-compose up --build
```

## 📊 Planning de Développement

| Phase | Durée | Objectifs |
|-------|-------|-----------|
| **Phase 1** | Semaines 1-2 | Configuration & Architecture |
| **Phase 2** | Semaines 3-5 | Développement Core |
| **Phase 3** | Semaines 6-7 | Fonctionnalités Avancées |
| **Phase 4** | Semaine 8 | Tests & Optimisation |
| **Phase 5** | Semaines 9-10 | Finalisation & Livraison |

## 🧪 Tests

```bash
# Tests unitaires
mvn test

# Tests d'intégration
mvn verify

# Couverture de code
mvn jacoco:report
```

## 📝 Configuration

### Base de données
La configuration par défaut utilise MySQL. Pour modifier :

```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/volmaghreb_db
spring.datasource.username=root
spring.datasource.password=password
```

### Profils d'environnement
- **Développement** : `application.properties`
- **Production** : `application-prod.properties`
- **Tests** : `application-test.properties`

## 🤝 Contribution

1. Fork le projet
2. Créer une branche pour votre fonctionnalité (`git checkout -b feature/AmazingFeature`)
3. Commit vos changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 📞 Support

- **Documentation** : Consultez le [Plan de Répartition](PLAN_REPARTITION_TRAVAIL.md)
- **Issues** : Ouvrez une issue sur GitHub
- **Wiki** : Documentation détaillée sur le wiki du projet

## 📄 Licence

Ce projet est sous licence MIT. Voir `LICENSE` pour plus de détails.

## 🔄 Migration depuis Microservices

Ce projet a été migré d'une architecture microservices vers MVC pour :
- ✅ Simplifier le développement
- ✅ Réduire la complexité opérationnelle
- ✅ Optimiser les performances
- ✅ Faciliter le travail en équipe

---

**Version** : 1.0.0  
**Date de création** : 30 Mai 2025  
**Équipe** : VolMaghreb Development Team  

🛫 **Votre passerelle vers le Maroc - Réservez facilement et en toute sécurité !**