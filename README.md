#  Magasin de Voitures Connecté (Spring Boot + React + Ollama AI)

Une application Full-Stack moderne pour la gestion de stock automobile, enrichie par l'Intelligence Artificielle locale (Ollama).

##  Fonctionnalités
- **Gestion CRUD** : Opérations complètes sur les véhicules (Marque, Modèle, Couleur, Année, Prix).
- **Assistant IA (Ollama)** : 
  -  **Résumés Intelligents** : Analyse automatique des caractéristiques du véhicule.
  -  **Quiz Interactifs** : Génération de quiz sur les véhicules avec plusieurs niveaux de difficulté.
  -  **Chat Dédié** : Discutez en direct avec l'IA à propos d'une voiture spécifique ou de manière générale.
- **Documentation API** : Interface Swagger UI complète.
- **Architecture Conteneurisée** : Déploiement simplifié via Docker.

##  Stack Technique
- **Backend** : Java 21, Spring Boot 3.4.13, Spring Data JPA, Spring AI.
- **Frontend** : React 18, React-Bootstrap, Axios, FontAwesome.
- **Base de données** : MySQL / MariaDB.
- **IA** : Ollama (Modèle Llama2).
- **Déploiement** : Docker & Docker Compose.

---

##  Guide de Démarrage Rapide

### Prérequis
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) installé et démarré.

### Installation et Lancement

1. **Cloner le repository** :
   ```bash
   git clone <url-du-repo>
   cd SpringDataRest
   ```

2. **Lancer l'application complète** :
   Une seule commande suffit pour compiler le code et démarrer tous les services :
   ```bash
   docker-compose up --build
   ```

   > **Note importante** : Lors du premier lancement, Docker téléchargera le modèle d'IA (environ 3.8 Go). Cette étape peut prendre 5 à 10 minutes selon votre connexion. L'application sera prête une fois que vous verrez `react-frontend  | Compiled successfully!`.

---

##  Accès aux Services

Une fois l'application lancée, vous pouvez accéder aux interfaces suivantes :

| Service | URL | Description |
| :--- | :--- | :--- |
| **Frontend** | [http://localhost:3000](http://localhost:3000) | Interface utilisateur React (Gestion & IA) |
| **Backend API** | [http://localhost:9090/api/](http://localhost:9090/api/) | Point d'entrée de l'API Spring Boot |
| **Swagger UI** | [http://localhost:9090/swagger-ui.html](http://localhost:9090/swagger-ui.html) | Documentation interactive de l'API |
| **IA Ollama** | [http://localhost:11434](http://localhost:11434) | Moteur d'IA local |

---

**Développé par** : Yassir Karrouti
