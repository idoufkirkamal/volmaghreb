# Prompt pour Générer le Diagramme d'Architecture Azure - VolMaghreb

## Contexte du Projet

**Nom**: VolMaghreb Flight Reservation System  
**Type**: Application web de réservation de vols pour le Maroc  
**Stack technique**: Spring Boot 3.2.0 (Java 17), Thymeleaf, Spring Security 6, Spring Data JPA, MySQL 8.0  
**Build**: Maven  
**Déploiement**: Docker, CI/CD GitHub Actions → Azure

---

## Description de l'Application

**VolMaghreb** est une plateforme de réservation de vols complète avec deux interfaces principales :

### Interface Client (Customer)
- Recherche de vols (origine, destination, date, classe)
- Réservation de vols pour plusieurs voyageurs
- Gestion des réservations personnelles
- Authentification utilisateur sécurisée
- Gestion de profil (informations personnelles, mot de passe)

### Interface Administrateur (Admin)
- Tableau de bord avec statistiques système
- Gestion des vols (CRUD)
- Gestion des aéroports (CRUD)
- Gestion de la flotte d'avions (CRUD)
- Gestion des utilisateurs
- Supervision de toutes les réservations

---

## Architecture Applicative (Couches)

### 1. **Couche Présentation** (Frontend)
- **Technologie**: Thymeleaf (Server-Side Rendering)
- **Ressources statiques**: HTML, CSS, JavaScript, Bootstrap 5.3.2, jQuery 3.7.1
- **Templates**: 
  - Pages client: `index.html`, `flight-list.html`, `reservation-booking.html`, `my-reservations.html`, `profile.html`
  - Pages admin: `dashboard.html`, `flights.html`, `airports.html`, `airplanes.html`, `reservations.html`, `users.html`
  - Pages auth: `sign-in.html`, `sign-up.html`
  - Fragments réutilisables: `navbar.html`, `footer.html`, `head.html`

### 2. **Couche Controllers** (MVC)
- `HomeController`: Affichage page d'accueil, À propos, Contact
- `AuthController`: Inscription, connexion, déconnexion
- `FlightWebController`: Recherche et affichage des vols
- `ReservationViewController`: Gestion des réservations côté client
- `UserController`: Gestion du profil utilisateur
- `AdminController`: Tableau de bord et gestion administrative

### 3. **Couche Services** (Business Logic)
- `AuthService`: Authentification et enregistrement
- `FlightService`: Logique métier des vols
- `ReservationService`: Logique métier des réservations
- `AirportService`: Gestion des aéroports
- `AirplaneService`: Gestion des avions
- `UserService`: Gestion des utilisateurs
- `SeatService`: Gestion des sièges
- `DashboardService`: Statistiques et métriques
- `CustomUserDetailsService`: Chargement des détails utilisateur pour Spring Security

### 4. **Couche Repositories** (Data Access - JPA)
- `UserRepository`
- `FlightRepository`
- `ReservationRepository`
- `AirportRepository`
- `AirplaneRepository`
- `SeatRepository`
- `TravelerRepository`
- `PaymentRepository`

### 5. **Modèle de Données** (Entités JPA)
- **User**: utilisateurs (clients et admins) avec rôles (ADMIN, CLIENT)
- **Flight**: vols (origine, destination, dates, prix, statut)
- **Reservation**: réservations liées à un utilisateur et un vol
- **Traveler**: voyageurs associés à une réservation
- **Seat**: sièges des avions et leur disponibilité
- **Airplane**: avions (modèle, capacité, compagnie)
- **Airport**: aéroports (nom, code IATA, ville, pays)
- **Payment**: paiements (montant, méthode, statut)

### 6. **Sécurité**
- **Spring Security 6**: authentification et autorisation basée sur les rôles
- **Password Encoding**: BCrypt pour les mots de passe
- **Session Management**: Remember-me, CSRF protection
- **Access Control**: 
  - `/admin/**` → nécessite rôle ADMIN
  - `/user/**` → nécessite rôle CLIENT ou ADMIN
  - `/auth/**` → accès public

### 7. **Autres Fonctionnalités**
- **Email Notifications**: Spring Mail (SMTP Gmail configuré)
- **PDF Generation**: iText7 pour générer des billets/reçus
- **Data Initialization**: Création automatique du compte admin au premier démarrage
- **Logging**: Logs applicatifs et Spring Security (DEBUG level)

---

## Infrastructure Azure Déployée (7 Services)

Vous avez déployé l'application VolMaghreb sur **Microsoft Azure** en utilisant les **7 services managés** suivants :

### 1. **Plateforme d'Hébergement Web Managé (PaaS)**
**Service Azure**: **Azure App Service (Web App)**

- **Rôle**: Héberge l'application Spring Boot packagée en JAR exécutable
- **Configuration**:
  - Nom de l'application: `SystemeReservationVOl`
  - Runtime: Java 17 (Eclipse Temurin ou Microsoft OpenJDK)
  - OS: Linux (recommandé pour conteneurs)
  - Plan App Service: B1, S1 ou P1V2 (selon la charge)
  - Mise à l'échelle automatique: activée pour gérer le trafic
  - Continuous Deployment: via GitHub Actions (CI/CD)
- **Points d'entrée**:
  - Endpoint public: `https://systemereservationvol.azurewebsites.net/volmaghreb`
  - Health check: `https://systemereservationvol.azurewebsites.net/volmaghreb/actuator/health` (si Spring Boot Actuator activé)

### 2. **Base de Données Relationnelle Managée (DBaaS)**
**Service Azure**: **Azure Database for MySQL - Flexible Server**

- **Rôle**: Stocke toutes les données relationnelles de l'application (utilisateurs, vols, réservations, paiements, etc.)
- **Configuration**:
  - Version MySQL: 8.0
  - Tier: Burstable (B1ms) ou General Purpose (selon le volume de données)
  - Stockage: 20-100 GB avec auto-growth activé
  - Sauvegarde automatique: rétention 7-35 jours
  - Haute disponibilité: Zone-redundant (si critique)
  - SSL/TLS enforcement: activé pour la sécurité
  - Firewall Rules: autoriser Azure App Service et IPs développeurs
- **Schéma de base de données**: 
  - Tables: `users`, `flights`, `reservations`, `traveler`, `seats`, `airports`, `airplanes`, `payments`
  - Relations: clés étrangères (User ↔ Reservation ↔ Flight ↔ Airplane, etc.)
- **Connexion depuis App Service**: 
  - String de connexion stockée dans **Azure Key Vault** (secret)
  - Variables d'environnement: `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`

### 3. **Gestion de l'Identité Utilisateur**
**Service Azure**: **Azure Active Directory B2C** (ou **Entra ID B2C**)

- **Rôle**: Authentification et gestion des identités utilisateurs (clients et admins)
- **Fonctionnalités**:
  - Sign-up / Sign-in flows personnalisés
  - Authentification multi-facteurs (MFA) optionnelle
  - Intégration avec Spring Security via OAuth2/OpenID Connect
  - Self-service password reset
  - Social logins (optionnel): Google, Facebook, Microsoft
- **Configuration**:
  - Tenant Azure AD B2C: `volmaghreb.onmicrosoft.com`
  - User flows: Sign-up/Sign-in combiné, Password reset
  - Claims mapping: email, firstName, lastName, role (ADMIN/CLIENT)
  - API Permissions: délégation pour l'application Spring Boot
- **Intégration avec l'application**:
  - Remplacement de l'authentification Spring Security locale par Azure AD B2C
  - Token JWT validé côté serveur
  - Stockage des rôles dans une table locale ou comme custom attributes dans Azure AD

### 4. **Gestion Sécurisée des Secrets**
**Service Azure**: **Azure Key Vault**

- **Rôle**: Stockage centralisé et sécurisé des secrets, clés API, certificats et identifiants
- **Secrets stockés**:
  - `DB-USERNAME`: nom d'utilisateur MySQL
  - `DB-PASSWORD`: mot de passe MySQL
  - `DB-CONNECTION-STRING`: chaîne de connexion complète
  - `MAIL-PASSWORD`: mot de passe SMTP Gmail
  - `AZURE-AD-B2C-CLIENT-SECRET`: secret pour l'authentification B2C
  - `JWT-SECRET-KEY`: clé de signature JWT (si utilisé)
- **Accès depuis App Service**:
  - **Managed Identity** (System-assigned ou User-assigned) activée sur l'App Service
  - Politique d'accès Key Vault: GET secrets pour la Managed Identity
  - Références Key Vault dans App Service Configuration:
    ```
    @Microsoft.KeyVault(SecretUri=https://volmaghreb-kv.vault.azure.net/secrets/DB-PASSWORD/)
    ```
- **Rotation des secrets**: automatique (via Azure Key Vault ou manuel)

### 5. **Stockage d'Objets**
**Service Azure**: **Azure Blob Storage**

- **Rôle**: Stockage hautement évolutif pour les fichiers statiques (images, vidéos, PDFs, sauvegardes)
- **Conteneurs (Containers)**:
  - `static-assets`: images de l'application (logos, icônes, screenshots)
  - `user-uploads`: photos de profil, documents d'identité des voyageurs
  - `flight-images`: images des avions, aéroports
  - `pdf-tickets`: billets électroniques générés (iText7)
  - `backups`: sauvegardes de la base de données
- **Configuration**:
  - Redundancy: LRS (Locally Redundant Storage) ou GRS (Geo-Redundant)
  - Access Tier: Hot (pour les fichiers fréquemment accédés)
  - Public Access: Blob (lecture publique pour les assets statiques) ou Private avec SAS tokens
  - Lifecycle Management: déplacer les vieux PDFs vers Cool/Archive après 90 jours
- **Intégration avec l'application**:
  - SDK Azure Storage Blob pour Java (Spring Boot)
  - Upload des fichiers depuis l'interface admin/client
  - Génération de SAS tokens pour téléchargement sécurisé des billets PDF

### 6. **Réseau de Diffusion de Contenu (CDN)**
**Service Azure**: **Azure CDN** (ou **Azure Front Door**)

- **Rôle**: Accélérer la livraison des contenus statiques (CSS, JS, images, vidéos) via un réseau distribué global
- **Configuration**:
  - Origine CDN: Azure Blob Storage (`static-assets` container) et/ou App Service
  - Profil CDN: Standard Microsoft, Standard Akamai, ou Premium Verizon
  - Points de présence (PoPs): répartis mondialement (Europe, Afrique, Moyen-Orient prioritaires pour le Maroc)
  - Règles de cache: TTL de 7 jours pour CSS/JS/images, 1 jour pour les vidéos
  - Compression: Gzip/Brotli activé pour les fichiers textuels
  - HTTPS: certificat SSL/TLS gratuit via Azure CDN
- **Endpoints**:
  - CDN Endpoint: `https://volmaghreb-cdn.azureedge.net`
  - Fichiers servis: `/assets/css/style.css`, `/assets/images/logo.png`, etc.
- **Intégration avec l'application**:
  - Modification des URLs dans les templates Thymeleaf pour pointer vers le CDN
  - Fallback vers App Service si le CDN est indisponible

### 7. **Surveillance et Performance Applicative (APM)**
**Service Azure**: **Azure Application Insights** (partie de **Azure Monitor**)

- **Rôle**: Collecte de métriques, logs et traces pour surveiller la performance, détecter les anomalies et diagnostiquer les problèmes
- **Métriques surveillées**:
  - **Performance**: temps de réponse HTTP, latence des requêtes DB, throughput
  - **Disponibilité**: uptime de l'application, résultats des tests de disponibilité (pings)
  - **Erreurs**: exceptions Java, erreurs 500/404, stack traces
  - **Utilisation**: nombre de sessions utilisateur, pages vues, flux utilisateurs
  - **Dépendances**: appels vers MySQL, Blob Storage, Key Vault, services externes (SMTP)
- **Configuration**:
  - Application Insights Instrumentation Key (ou Connection String) injecté dans l'App Service
  - SDK Application Insights pour Java (auto-instrumentation via Java agent)
  - Collecte automatique des logs Spring Boot (Logback/SLF4J)
  - Custom Events/Metrics pour les événements métier (réservation créée, paiement effectué)
- **Alertes configurées**:
  - Alerte si le temps de réponse > 2 secondes
  - Alerte si le taux d'erreurs > 5%
  - Alerte si l'uptime < 99%
  - Alerte si la base de données est inaccessible > 1 minute
- **Dashboards**:
  - Tableau de bord Azure Portal avec graphiques en temps réel
  - KPIs: nombre de réservations/heure, utilisateurs actifs, revenus générés
- **Logs centralisés**:
  - Log Analytics Workspace: requêtes KQL pour analyser les logs
  - Exemples de requêtes: 
    - `traces | where message contains "Reservation created"`
    - `requests | summarize avg(duration) by name | order by avg_duration desc`

---

## Architecture Réseau et Sécurité

### **Réseau Virtuel (optionnel mais recommandé)**
**Service Azure**: **Azure Virtual Network (VNet)**

- **Rôle**: Isoler les ressources Azure dans un réseau privé
- **Configuration**:
  - VNet Integration sur l'App Service pour accéder aux ressources backend via IPs privées
  - Private Endpoints pour MySQL, Key Vault, Blob Storage (trafic interne uniquement)
  - Network Security Groups (NSG): règles de pare-feu pour filtrer le trafic

### **Identité Managée (Managed Identity)**
- **System-assigned Managed Identity** activée sur l'App Service
- Permet l'accès sans mot de passe à:
  - Azure Key Vault (récupération des secrets)
  - Azure Blob Storage (upload/download de fichiers)
  - Azure Database for MySQL (authentification Azure AD optionnelle)

### **Firewall et Règles d'accès**
- **MySQL Firewall**: autoriser uniquement l'App Service et les IPs administrateurs
- **Key Vault Access Policies**: GET secrets uniquement pour la Managed Identity
- **Blob Storage**: accès public pour les assets statiques, SAS tokens pour les fichiers privés

---

## Pipeline CI/CD (GitHub Actions → Azure)

### **Workflow GitHub Actions** (`.github/workflows/deploy.yml`)

**Étapes du pipeline**:
1. **Checkout code**: récupérer le code source depuis GitHub
2. **Setup JDK 17**: installer Java 17 (Temurin distribution)
3. **Cache Maven dependencies**: mettre en cache les dépendances Maven pour accélérer les builds
4. **Build with Maven**: 
   - `mvn clean package` → génère `target/app.jar`
   - Tests unitaires et d'intégration exécutés (JUnit, Spring Boot Test)
5. **Deploy to Azure Web App**:
   - Action: `azure/webapps-deploy@v2`
   - Authentification: `publish-profile` (secret GitHub `AZURE_WEBAPP_PUBLISH_PROFILE`)
   - Package: `target/app.jar`
   - App Name: `SystemeReservationVOl`

**Déclencheur**: Push sur la branche `master`

**Sécurité**:
- Secret GitHub `AZURE_WEBAPP_PUBLISH_PROFILE` stocké dans les Repository Secrets
- Alternative recommandée: OIDC (OpenID Connect) avec Federated Identity Credential pour éviter les secrets longue durée

---

## Diagramme d'Architecture Azure à Générer

**Votre tâche**: Générer un **diagramme d'architecture détaillé** représentant l'infrastructure Azure du projet VolMaghreb avec les 7 services déployés.

### **Éléments à inclure dans le diagramme**:

#### **1. Utilisateurs et Trafic Entrant**
- 🌐 **Utilisateurs finaux** (clients et admins) → navigateur web
- 🔒 **HTTPS** → trafic chiffré vers Azure CDN

#### **2. Couche CDN et Réseau**
- ☁️ **Azure CDN** (Front Door)
  - Livraison des assets statiques (CSS, JS, images)
  - Points de présence (PoPs) globaux
  - Flèche vers → Azure Blob Storage (origine des assets)
  - Flèche vers → Azure App Service (origine des pages HTML dynamiques)

#### **3. Couche Application**
- 🖥️ **Azure App Service (Web App)** `SystemeReservationVOl`
  - Runtime: Java 17, Spring Boot 3.2.0
  - Managed Identity activée
  - Variables d'environnement chargées depuis Key Vault
  - Flèches vers:
    - ➡️ Azure Database for MySQL (requêtes SQL via JPA)
    - ➡️ Azure Key Vault (récupération secrets)
    - ➡️ Azure Blob Storage (upload/download fichiers)
    - ➡️ Azure AD B2C (validation tokens JWT)
    - ➡️ Azure Application Insights (envoi métriques/logs)

#### **4. Couche Authentification**
- 🔐 **Azure Active Directory B2C** (Entra ID B2C)
  - User Flows: Sign-up/Sign-in, Password Reset
  - Flèche bidirectionnelle avec App Service (OAuth2/OIDC)
  - Stockage des utilisateurs dans Azure AD B2C (ou synchronisation avec MySQL)

#### **5. Couche Données**
- 🗄️ **Azure Database for MySQL - Flexible Server**
  - Version: MySQL 8.0
  - Tables: users, flights, reservations, airports, airplanes, seats, traveler, payments
  - Sauvegarde automatique activée
  - Firewall: accès restreint à App Service via Private Endpoint (optionnel)

#### **6. Couche Sécurité**
- 🔑 **Azure Key Vault**
  - Secrets stockés: DB credentials, SMTP password, Azure AD B2C client secret, JWT key
  - Access Policy: Managed Identity de l'App Service avec permission GET secrets
  - Flèche depuis App Service (lecture secrets via SDK)

#### **7. Couche Stockage**
- 📦 **Azure Blob Storage**
  - Containers:
    - `static-assets` (CSS, JS, images) → servi via Azure CDN
    - `user-uploads` (photos de profil, documents)
    - `pdf-tickets` (billets électroniques générés)
    - `backups` (sauvegardes DB)
  - Accès: Public (assets statiques) + SAS tokens (fichiers privés)
  - Flèche depuis App Service (upload/download via SDK)
  - Flèche vers Azure CDN (origine CDN)

#### **8. Couche Monitoring**
- 📊 **Azure Application Insights** (Azure Monitor)
  - Collecte:
    - Métriques de performance (temps de réponse, throughput)
    - Logs applicatifs (Spring Boot logs)
    - Traces distribuées (dépendances MySQL, Blob Storage, etc.)
    - Exceptions et erreurs
  - Dashboards: Azure Portal, Workbooks personnalisés
  - Alertes: notification par email/SMS si anomalies détectées
  - Flèche depuis App Service (Java agent auto-instrumentation)

#### **9. CI/CD Pipeline**
- 🚀 **GitHub Actions**
  - Workflow: `.github/workflows/deploy.yml`
  - Étapes: Checkout → Build (Maven) → Deploy (Azure App Service)
  - Authentification: Publish Profile ou OIDC Federated Identity
  - Flèche depuis GitHub → App Service (déploiement automatique)

#### **10. Services Annexes (optionnels à représenter)**
- 📧 **SMTP Gmail**: envoi d'emails de notification (intégré dans l'application via Spring Mail)
- 🌐 **Azure Virtual Network (VNet)**: réseau privé pour isoler les ressources
- 🔒 **Private Endpoints**: connexions privées pour MySQL, Key Vault, Blob Storage

---

## Flux de Données Principaux

### **Flux 1: Utilisateur recherche un vol**
1. Client → Azure CDN → récupère CSS/JS/images depuis Blob Storage
2. Client → Azure App Service → requête GET `/volmaghreb/flights/search?from=Casablanca&to=Paris`
3. App Service → Azure AD B2C → valide le token JWT de l'utilisateur
4. App Service → Azure Database for MySQL → requête SQL `SELECT * FROM flights WHERE origin='CMN' AND destination='CDG'`
5. App Service → renvoie résultats au client (template Thymeleaf)
6. App Service → Azure Application Insights → log métrique "flight search executed in 250ms"

### **Flux 2: Utilisateur effectue une réservation**
1. Client → App Service → POST `/volmaghreb/reservations/book`
2. App Service → MySQL → INSERT INTO reservations, traveler, payments
3. App Service → Blob Storage → génère PDF du billet (iText7) et upload dans `pdf-tickets`
4. App Service → SMTP Gmail → envoie email de confirmation avec lien SAS vers le PDF
5. App Service → Application Insights → log événement "reservation created" + montant payé
6. App Service → renvoie page de confirmation au client

### **Flux 3: Admin consulte le tableau de bord**
1. Admin → App Service → GET `/volmaghreb/admin/dashboard`
2. App Service → Azure AD B2C → vérifie rôle ADMIN
3. App Service → MySQL → requêtes agrégées (COUNT reservations, SUM payments, AVG ratings, etc.)
4. App Service → Application Insights → requêtes KQL pour afficher métriques en temps réel
5. App Service → renvoie dashboard HTML avec statistiques
6. Admin → CDN → charge les graphiques JS (Chart.js, etc.)

### **Flux 4: Déploiement automatique (CI/CD)**
1. Développeur → GitHub → push commit sur branche `master`
2. GitHub Actions → déclenche workflow `.github/workflows/deploy.yml`
3. GitHub Actions → build Maven → génère `target/app.jar`
4. GitHub Actions → Azure App Service → déploie le JAR via API REST (publish-profile)
5. App Service → redémarre avec la nouvelle version
6. App Service → Application Insights → log "deployment successful"

---

## Exigences pour le Diagramme

### **Format attendu**:
- **Outil recommandé**: Draw.io, Lucidchart, Microsoft Visio, PlantUML, ou Azure Architecture Diagram (Azure Portal)
- **Style**: utiliser les icônes officielles Azure (téléchargeables depuis Microsoft)
- **Légende**: 
  - Flèches pleines → flux de données synchrones (HTTP, SQL)
  - Flèches pointillées → flux asynchrones (logs, métriques)
  - Couleurs: 
    - Bleu → services Azure
    - Vert → utilisateurs/clients
    - Orange → CI/CD pipeline
    - Rouge → sécurité (Key Vault, AD B2C)

### **Détails à afficher**:
- Nom de chaque service Azure
- Nom des ressources (ex: `SystemeReservationVOl`, `volmaghreb-kv`, `volmaghreb-cdn`)
- Protocoles de communication (HTTPS, JDBC, OAuth2, SDK)
- Ports exposés (8080 pour App Service)
- Zones de disponibilité (si HA activée)
- Relations entre services (flèches avec labels: "read secrets", "query DB", "upload files", "send logs", etc.)

### **Sections du diagramme**:
1. **User Layer**: navigateurs web (clients + admins)
2. **Edge Layer**: Azure CDN (Front Door)
3. **Application Layer**: Azure App Service
4. **Security Layer**: Azure AD B2C + Azure Key Vault
5. **Data Layer**: Azure Database for MySQL + Azure Blob Storage
6. **Monitoring Layer**: Azure Application Insights
7. **CI/CD Layer**: GitHub Actions → App Service

---

## Exemple de Prompt pour un Outil de Génération de Diagrammes (Draw.io, PlantUML, etc.)

**Si vous utilisez PlantUML**, voici un prompt à affiner:

```plantuml
@startuml VolMaghreb Azure Architecture

!define AzurePuml https://raw.githubusercontent.com/plantuml-stdlib/Azure-PlantUML/release/2-2/dist
!includeurl AzurePuml/AzureCommon.puml
!includeurl AzurePuml/Web/AzureAppService.puml
!includeurl AzurePuml/Databases/AzureDatabaseForMySQL.puml
!includeurl AzurePuml/Identity/AzureActiveDirectoryB2C.puml
!includeurl AzurePuml/Security/AzureKeyVault.puml
!includeurl AzurePuml/Storage/AzureBlobStorage.puml
!includeurl AzurePuml/Networking/AzureCDN.puml
!includeurl AzurePuml/DevOps/AzureApplicationInsights.puml

actor "Clients & Admins" as Users

AzureCDN(cdn, "Azure CDN", "volmaghreb-cdn")
AzureAppService(app, "App Service", "SystemeReservationVOl\nJava 17, Spring Boot 3.2")
AzureActiveDirectoryB2C(adb2c, "Azure AD B2C", "volmaghreb.onmicrosoft.com")
AzureKeyVault(kv, "Key Vault", "volmaghreb-kv")
AzureDatabaseForMySQL(db, "Azure DB for MySQL", "volmaghreb-db\nMySQL 8.0")
AzureBlobStorage(blob, "Blob Storage", "static-assets, pdf-tickets, backups")
AzureApplicationInsights(insights, "Application Insights", "Monitoring & APM")

Users -down-> cdn : HTTPS (assets statiques)
Users -down-> app : HTTPS (pages dynamiques)

cdn -down-> blob : Origine CDN (CSS, JS, images)
cdn -down-> app : Origine CDN (HTML)

app -right-> adb2c : OAuth2/OIDC\n(valider tokens JWT)
app -down-> db : JDBC\n(requêtes SQL via JPA)
app -left-> kv : REST API\n(récupérer secrets)
app -down-> blob : SDK\n(upload/download fichiers)
app -right-> insights : Java Agent\n(logs, métriques, traces)

note right of app
  **Managed Identity**
  - Accès Key Vault
  - Accès Blob Storage
end note

note bottom of db
  **Tables**: users, flights,
  reservations, airports,
  airplanes, seats, traveler, payments
end note

note bottom of kv
  **Secrets**: DB credentials,
  SMTP password, Azure AD B2C
  client secret, JWT key
end note

note bottom of blob
  **Containers**: static-assets,
  user-uploads, pdf-tickets, backups
end note

@enduml
```

**Si vous utilisez Draw.io**, voici les étapes:
1. Télécharger les icônes Azure depuis: https://learn.microsoft.com/en-us/azure/architecture/icons/
2. Créer les formes suivantes:
   - **Utilisateurs**: icône "User" ou "Web Browser"
   - **Azure CDN**: icône "Azure Front Door" ou "Azure CDN"
   - **App Service**: icône "Azure App Service"
   - **Azure AD B2C**: icône "Azure Active Directory"
   - **Key Vault**: icône "Azure Key Vault"
   - **MySQL**: icône "Azure Database for MySQL"
   - **Blob Storage**: icône "Azure Blob Storage"
   - **Application Insights**: icône "Azure Monitor" ou "Application Insights"
   - **GitHub Actions**: icône "GitHub" avec flèche vers App Service
3. Connecter les formes avec des flèches et ajouter des labels (ex: "HTTPS", "JDBC", "OAuth2", "SDK", etc.)
4. Ajouter une légende et des notes pour expliquer les flux de données

---

## Résumé des 7 Services Azure et leur Rôle

| # | Service Azure | Rôle dans l'Architecture | Ressource Nom |
|---|---------------|--------------------------|---------------|
| 1 | **Azure App Service** | Hébergement de l'application Spring Boot (PaaS) | `SystemeReservationVOl` |
| 2 | **Azure Database for MySQL** | Stockage relationnel des données (DBaaS) | `volmaghreb-db` |
| 3 | **Azure Active Directory B2C** | Authentification et gestion des identités utilisateurs | `volmaghreb.onmicrosoft.com` |
| 4 | **Azure Key Vault** | Stockage sécurisé des secrets et identifiants | `volmaghreb-kv` |
| 5 | **Azure Blob Storage** | Stockage d'objets (fichiers statiques, PDFs, images) | `volmaghrebstorage` |
| 6 | **Azure CDN** | Accélération de la livraison des contenus statiques | `volmaghreb-cdn` |
| 7 | **Azure Application Insights** | Surveillance, métriques, logs et APM | `volmaghreb-insights` |

---

## Checklist Avant de Générer le Diagramme

- [ ] Identifier clairement les 7 services Azure et leur interconnexion
- [ ] Représenter les flux de données entre les services (flèches avec labels)
- [ ] Inclure les utilisateurs (clients + admins) comme point d'entrée
- [ ] Afficher le pipeline CI/CD (GitHub Actions → App Service)
- [ ] Ajouter la couche sécurité (Managed Identity, Key Vault, Azure AD B2C)
- [ ] Montrer la séparation entre assets statiques (CDN + Blob) et pages dynamiques (App Service)
- [ ] Inclure la couche monitoring (Application Insights)
- [ ] Utiliser les icônes officielles Azure pour la clarté
- [ ] Ajouter des notes explicatives pour chaque service
- [ ] Valider que les 7 services requis sont bien présents et correctement reliés

---

## Utilisation du Prompt

**Pour générer le diagramme**, copiez ce prompt et:
1. **PlantUML**: adaptez le code PlantUML ci-dessus et compilez-le avec PlantUML Server ou VS Code (extension PlantUML)
2. **Draw.io**: suivez les étapes manuelles décrites (télécharger icônes, créer formes, connecter)
3. **Azure Architecture Center**: utilisez l'outil Azure Architecture Diagram (https://learn.microsoft.com/en-us/azure/architecture/browse/)
4. **IA générative (ChatGPT, Copilot)**: fournissez ce prompt et demandez "Génère un diagramme d'architecture Azure en PlantUML/Mermaid/SVG représentant cette infrastructure"

---

**Fin du Prompt d'Architecture Azure - VolMaghreb**

*Ce document peut être utilisé comme référence complète pour dessiner, comprendre et communiquer l'architecture Azure du projet VolMaghreb.*
