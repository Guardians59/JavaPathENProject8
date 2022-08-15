package ms.gpsutil.service;

import java.util.List;
import java.util.UUID;

import ms.gpsutil.model.Attraction;
import ms.gpsutil.model.VisitedLocation;

/**
 * L'interface IGPSUtilService est le service qui nous permet de r�cup�rer la positions d'un
 * utilisateur, la liste d'attractions les plus proches, ainsi que la liste de toutes les attractions.
 * @author Dylan
 *
 */
public interface IGPSUtilService {

    /**
     * La m�thode getLocation permet de r�cup�rer la localisation de l'utilisateur.
     * @param userId l'id de l'utilisateur.
     * @return VisitedLocation la localisation de l'utilisateur avec les informations
     * (Id, Location, Date).
     */
    public VisitedLocation getUserLocation(UUID userId);

    /**
     * La m�thode getNearByAttraction permet de r�cup�rer la liste des 5 attractions les plus
     * proches de l'utilisateur.
     * @param visitedLocation les informations de localisation de l'utilisateur.
     * @return List d'attractions, les attractions les plus proches.
     */
    public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation);
    
    /**
     * La m�thode getAllAttractions permet de r�cup�rer la liste des attractions.
     * @return List d'attractions, les attractions enregistr�es.
     */
    public List<Attraction> getAllAttractions();

}
