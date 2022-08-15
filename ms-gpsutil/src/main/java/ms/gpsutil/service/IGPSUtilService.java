package ms.gpsutil.service;

import java.util.List;
import java.util.UUID;

import ms.gpsutil.model.Attraction;
import ms.gpsutil.model.VisitedLocation;

/**
 * L'interface IGPSUtilService est le service qui nous permet de récupérer la positions d'un
 * utilisateur, la liste d'attractions les plus proches, ainsi que la liste de toutes les attractions.
 * @author Dylan
 *
 */
public interface IGPSUtilService {

    /**
     * La méthode getLocation permet de récupérer la localisation de l'utilisateur.
     * @param userId l'id de l'utilisateur.
     * @return VisitedLocation la localisation de l'utilisateur avec les informations
     * (Id, Location, Date).
     */
    public VisitedLocation getUserLocation(UUID userId);

    /**
     * La méthode getNearByAttraction permet de récupérer la liste des 5 attractions les plus
     * proches de l'utilisateur.
     * @param visitedLocation les informations de localisation de l'utilisateur.
     * @return List d'attractions, les attractions les plus proches.
     */
    public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation);
    
    /**
     * La méthode getAllAttractions permet de récupérer la liste des attractions.
     * @return List d'attractions, les attractions enregistrées.
     */
    public List<Attraction> getAllAttractions();

}
