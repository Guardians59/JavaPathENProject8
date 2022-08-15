package ms.gpsutil.service;

import ms.gpsutil.model.Location;
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

}
