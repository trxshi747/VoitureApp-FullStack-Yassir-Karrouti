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

---

##  Guide de Démarrage Rapide (Docker)

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

##  Guide de Déploiement Kubernetes (Minikube)

Si vous souhaitez déployer l'application sur un cluster Kubernetes local, suivez ces étapes :

### Prérequis
- [Minikube](https://minikube.sigs.k8s.io/docs/start/) installé.
- `kubectl` installé.

### Lancement étape par étape

1. **Démarrer et préparer le cluster** :
   Lancez Minikube et connectez votre terminal au daemon Docker de Minikube (à exécuter dans PowerShell).
   ```powershell
   minikube start
   minikube -p minikube docker-env --shell powershell | Invoke-Expression
   ```

2. **Construire les images Docker locales** :
   Construisez les images du Backend et du Frontend directement dans le cluster Minikube.
   ```powershell
   docker build -t springboot-crud-k8s:1.0 .
   docker build -t react-frontend-k8s:1.0 .\src\main\webapp\reactjs
   ```

3. **Déployer la configuration et les services** :
   Appliquez les manifestes Kubernetes dans cet ordre précis.
   ```powershell
   kubectl apply -f mysql-configMap.yaml
   kubectl apply -f mysql-secrets.yaml
   kubectl apply -f db-deployment.yaml
   kubectl apply -f ollama-deployment.yaml
   kubectl apply -f app-deployment.yaml
   kubectl apply -f frontend-deployment.yaml
   ```
   *(Attendez quelques minutes que les pods soient au statut `Running`. Vous pouvez vérifier l'état avec `kubectl get pods`)*

4. **Accéder à l'application** :
   Une fois les pods déployés, ouvrez l'accès a l'applis :
   ```powershell
   kubectl port-forward service/react-frontend-svc 3000:3000
   kubectl port-forward service/springboot-crud-svc 9090:9090
   kubectl port-forward service/ollama 11434:11434
   ```
   Vous pouvez maintenant ouvrir l'application : [http://localhost:3000](http://localhost:3000)

*(Optionnel) Ouvrir les dans chaque terminal*
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

---
**Développé par** : Yassir Karrouti
