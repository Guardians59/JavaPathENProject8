package tourGuide.service;

import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.VisitedLocation;
/**
 * L'interface IDistanceCalculService est le service de calcul des distances, entre les
 * utilisateurs et les attractions.
 * @author Dylan
 *
 */
public interface IDistanceCalculService {
    /**
     * La méthode getDistance permet de calculer la distance entre une attraction et
     * un utilisateur.
     * @param attractionLocation la localisation de l'attraction.
     * @param userLocation la localisation de l'utilisateur.
     * @return double, la distance selon un calcul par défaut choisit pour l'application.
     */
    public double getDistance(Location attractionLocation, Location userLocation);
    /**
     * La méthode isWithinAttractionProximity permet de vérifier si l'utilisateur est proche d'une
     * attraction selon un calcul par défaut choisit pour l'application.
     * @param attraction l'attraction.
     * @param location la localisation de l'utilisateur.
     * @return boolean, true si la distance correspond ou false si c'est trop éloigné.
     */
    public boolean isWithinAttractionProximity(Attraction attraction, Location location);
    /**
     * La méthode nearAttraction permet de vérifier si l'utilisateur est proche d'une attraction
     * selon un calcul par défaut choisit pour l'application.
     * @param visitedLocation les informations de localisation de l'utilisateur.
     * @param attraction l'attraction.
     * @return boolean, true si la distance correspond ou false si c'est trop éloigné.
     */
    public boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction);

}
