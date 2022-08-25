![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)

# Projet 8 Openclassroom

TourGuide est une application permettant aux utilisateurs de découvrir les attractions touristiques, et propose également des réductions sur des séjours et spectacles.

# Technologies

- Java 8
- Spring Boot
- Feign
- Docker

# Lancement de l'application

Avant de démarrer l'application, en invite de commandes veuillez vous rendre dans le dossier TourGuide et entrée cette ligne de commande :
docker-compose up -d

Ceci permettra de démarrer les conteneurs des microServices sachant que :
- L'API GPSUtil utilise le port 8081
- L'API RewardCentral utilise le port 8082
- L'API TripPricer utilise le port 8083

Vous pouvez ensuite démarrer l'application TourGuide

Pour arrêter les conteneurs, utiliser la commande suivante :
docker-compose stop

# Documentation API

Une fois l'application démarrée, vous trouverez la documentation de l'API TourGuide à l'url :
http://localhost:8080/swagger-ui.html

La documentation de l'API du microService GPS : 
http://localhost:8081/swagger-ui.html

La documentation de l'API du microService RewardCentral :
http://localhost:8082/swagger-ui.html

La documentation de l'API du microService TripPricer :
http://localhost:8083/swagger-ui.html
