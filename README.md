# MDD (Monde De Dev)

Application web Full-Stack permettant aux développeurs de s'abonner à des thématiques, consulter et créer des posts, et interagir via des commentaires.

## 🔧 Prérequis

Avant de commencer, assurez-vous d'avoir installé :

- **Java 17** ([Télécharger](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))
- **Maven 3.6+** ([Télécharger](https://maven.apache.org/download.cgi))
- **MySQL 8.0+** ([Télécharger](https://dev.mysql.com/downloads/mysql/))
- **Node.js 16.x+** et **npm 8.x+** ([Télécharger](https://nodejs.org/))
- **Angular CLI 14.x** (installé globalement)

### Vérifier les installations

```bash
java -version          # Doit afficher Java 17
mvn -version           # Doit afficher Maven 3.6+
mysql --version        # Doit afficher MySQL 8.0+
node -v                # Doit afficher v16.x ou supérieur
npm -v                 # Doit afficher 8.x ou supérieur
ng version             # Doit afficher Angular CLI 14.x
```

Si Angular CLI n'est pas installé :
```bash
npm install -g @angular/cli@14
```

## 🚀 Installation et Lancement

### Étape 1 : Cloner le projet

```bash
git clone <url-du-repository>
cd Projet6
```

### Étape 2 : Configuration de la base de données

1. Démarrez le serveur MySQL
2. La base de données `mdd` sera créée automatiquement au premier lancement
3. Des données de test seront insérées automatiquement

### Étape 3 : Backend (Spring Boot)

#### 3.1 Configurer les variables d'environnement

**Windows PowerShell :**
```powershell
cd back
$env:DB_URL="jdbc:mysql://localhost:3306/mdd?createDatabaseIfNotExist=true"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="rootroot"
$env:JWT_SECRET="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"
$env:JPA_DDL_AUTO="update"
$env:SHOW_SQL="false"
```

**Linux/macOS :**
```bash
cd back
export DB_URL="jdbc:mysql://localhost:3306/mdd?createDatabaseIfNotExist=true"
export DB_USERNAME="root"
export DB_PASSWORD="rootroot"
export JWT_SECRET="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"
export JPA_DDL_AUTO="update"
export SHOW_SQL="false"
```

> ⚠️ **Important :** 
> - Remplacez `root` et `rootroot` par vos identifiants MySQL
> - Le `JWT_SECRET` doit être une clé de 256 bits (64 caractères hexadécimaux)
> - Si votre MySQL utilise un autre port, modifiez `3306` dans `DB_URL`

#### 3.2 Démarrer l'application

```bash
mvn clean install
mvn spring-boot:run
```

✅ **Le backend est démarré quand vous voyez :**
```
Started MddApiApplication in X.XXX seconds
```

#### 3.3 Vérifier le démarrage

Ouvrir dans le navigateur : http://localhost:8080/swagger-ui/index.html

### Étape 4 : Frontend (Angular)

**Ouvrir un nouveau terminal** et exécuter :

```bash
cd front
npm install
npm start
```

✅ **Le frontend est démarré quand vous voyez :**
```
** Angular Live Development Server is listening on localhost:4200 **
✔ Compiled successfully.
```

#### 4.2 Accéder à l'application

Ouvrir dans le navigateur : http://localhost:4200

## 👤 Comptes de test

Trois utilisateurs sont disponibles pour tester l'application :

| Email | Mot de passe |
|-------|--------------|
| `john@gmail.com` | `Test!1234` |
| `alice@gmail.com` | `Test!1234` |
| `bob@gmail.com` | `Test!1234` |

## 📡 API Documentation

Une fois le backend démarré, accédez à la documentation Swagger :

**URL :** http://localhost:8080/swagger-ui/index.html

### Tester les endpoints sécurisés

1. Utilisez l'endpoint `/api/auth/login` avec un compte de test
2. Copiez le token JWT retourné
3. Cliquez sur "Authorize" (🔒 en haut à droite)
4. Collez le token dans le champ "Value"
5. Cliquez sur "Authorize" puis "Close"
6. Vous pouvez maintenant tester tous les endpoints

## 🛠️ Technologies

**Backend :**
- Java 17
- Spring Boot 3.2.5
- Spring Security + JWT
- MySQL 8
- MapStruct 1.6.3
- Lombok 1.18.32
- Swagger/OpenAPI 3

**Frontend :**
- Angular 14
- Angular Material 14
- TypeScript 4.7
- RxJS 7.5

## 📝 Commandes utiles

### Backend
```bash
# Compiler sans tests
mvn clean install -DskipTests

# Lancer les tests
mvn test

# Créer le JAR
mvn clean package

# Lancer le JAR
java -jar target/mdd-api-0.0.1-SNAPSHOT.jar
```

### Frontend
```bash
# Lancer en mode développement
npm start

# Lancer les tests
npm test

# Build de production
npm run build

# Build avec optimisations
ng build --configuration production
```
