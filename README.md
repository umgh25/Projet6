# MDD (Monde De Dev)

MDD est un réseau social pour les développeurs. Il permet aux développeurs de s'abonner à des thématiques, et depuis leur tableau de bord d'accéder aux posts liés aux thématiques auxquelles ils sont abonnés. Ils peuvent également créer des posts sur les thématiques de leur choix et laisser des commentaires sous les différents posts. Le backend de l'application est développé en JAVA avec Spring Boot et le frontend avec Angular.

## Utiliser MDD

Pour utiliser MDD, clonez le projet et suivez les étapes ci-dessous :

## BACK-END

### Prérequis
- Java 17
- MySQL 8.x.x
- IDE comme IntelliJ IDEA ou Eclipse
- Maven 3.x.x

### Base de données :
Après avoir installé MySQL et configuré votre nom d'utilisateur et mot de passe, assurez-vous que votre serveur de base de données est actif.

La base de données sera créée automatiquement au lancement du backend et des données de test y seront insérées.

Vous pouvez utiliser les trois utilisateurs suivants ou en créer de nouveaux :

- **Email** : `john@gmail.com`  
  **Mot de passe** : `Test!1234`

- **Email** : `alice@gmail.com`  
  **Mot de passe** : `Test!1234`

- **Email** : `bob@gmail.com`  
  **Mot de passe** : `Test!1234`

### Configuration du projet :
Ouvrez le projet dans votre IDE. Ouvrez le fichier [application.properties](back/src/main/resources/application.properties) et éditez les lignes suivantes :

```properties
# JWT secret key
jwt.secret=Your256BitSecretHere
```
⚠️ Utilisez une clé de 256 bits, vous pouvez utiliser un générateur de clé en ligne pour cela.

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/mdd?createDatabaseIfNotExist=true
```
⚠️ Changez le port MySQL s'il n'est pas 3306.

### Lancer le projet

Dans votre terminal, allez dans le dossier "back" et suivez les instructions :

**Définissez les variables d'environnement avec ces commandes :**

```bash
$env:DB_URL="jdbc:mysql://localhost:3306/mdd?createDatabaseIfNotExist=true"
$env:DB_USERNAME="your_username"
$env:DB_PASSWORD="your_password"
$env:JWT_SECRET="Your256BitSecretHere"
$env:JPA_DDL_AUTO="update"
$env:SHOW_SQL="false"
```
⚠️ Remplacez `your_username` par votre nom d'utilisateur de base de données et `your_password` par votre mot de passe.

**Lancez l'application avec ces commandes :**

```bash
mvn clean install
java -jar target/mdd-api-0.0.1-SNAPSHOT.jar
```

Ou simplement :

```bash
mvn spring-boot:run
```

### Documentation API

Après avoir lancé l'application, ouvrez votre navigateur et utilisez le lien suivant :

```
http://localhost:8080/swagger-ui/index.html
```

Vous pouvez maintenant explorer toutes les routes disponibles de l'API, y compris leurs descriptions, paramètres, et même tester les endpoints directement.

Pour tester certaines routes, vous devrez fournir un token d'authentification :

1. Obtenez-le en utilisant l'endpoint `/api/auth/login` avec les identifiants d'un utilisateur
2. Cliquez sur le bouton "Authorize" et collez la valeur du token
3. Vous pouvez maintenant tester les endpoints sécurisés

## FRONT-END

### Prérequis
- Node.js 16.x.x ou supérieur
- Angular CLI 14.x.x
- IDE comme VS Code ou WebStorm

### Lancer le projet

1. Ouvrez le projet dans votre IDE
2. Dans votre terminal, placez-vous dans le répertoire "front"

**Installez les dépendances :**

```bash
npm install
```

**Lancez le serveur de développement :**

```bash
ng serve
```

ou

```bash
npm start
```

**Ouvrez votre navigateur et naviguez vers :**

```
http://localhost:4200
```

L'application se rechargera automatiquement si vous modifiez les fichiers source.

### Build de production

Pour construire le projet pour la production :

```bash
ng build
```

Les fichiers de build seront stockés dans le répertoire `dist/`.

## Technologies utilisées

### Backend
- Java 17
- Spring Boot 3.2.5
- Spring Security avec JWT
- MySQL 8
- MapStruct pour le mapping d'objets
- Lombok
- Swagger/OpenAPI pour la documentation

### Frontend
- Angular 14
- Angular Material pour les composants UI
- RxJS pour la programmation réactive
