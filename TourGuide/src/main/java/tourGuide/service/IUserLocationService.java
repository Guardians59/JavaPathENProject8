package tourGuide.service;

import java.util.HashMap;
import java.util.List;

import tourGuide.model.VisitedLocation;
/**
 * L'interface IUserLocationService est le service qui r�cup�re les localisations des
 * utilisateurs.
 * @author Dylan
 *
 */
public interface IUserLocationService {
    /**
     * La m�thode getCurrentUserLocation permet de r�cup�rer la derni�re localisation enregistr�e
     * de l'utilisateur.
     * @param userName le nom de l'utilisateur.
     * @return HashMap avec l'id de l'utilisateur et le model VisitedLocation pour avoir les
     * informations de localisation.
     */
    public HashMap<Object, Object> getCurrentUserLocation(String userName);
    /**
     * La m�thode getCurrentLocationsOfUsers permet de r�cup�rer la derni�re localisation enregistr�e
     * de plusieurs utilisateurs.
     * @param userNames les noms des utilisateurs.
     * @return HashMap avec les id des utilisateurs et le model VisitedLocation pour avoir les
     * informations des localisations.
     */
    public HashMap<Object, Object> getCurrentLocationOfUsers(List<String> userNames);
    /**
     * La m�thode getAllCurrentLocations permet de r�cup�rer la derni�re localisation enregistr�e
     * de tous les utilisateurs.
     * @return HashMap avec les id des utilisateurs et le model VisitedLocation pour avoir les
     * informations des localisations.
     */
    public HashMap<Object, Object> getAllCurrentLocations();
    /**
     * La m�thode getUsersLocationsHistorical permet de r�cup�rer l'historique des localisations
     * enregistr�es des utilisateurs.
     * @param userNames les noms des utilisateurs.
     * @return HashMap avec le nom de l'utilisateur et une liste du model VisitedLocation.
     */
    public HashMap<String, Object> getUsersLocationsHistorical(List<String> userNames);
    /**
     * La m�thode getUserLocation permet de r�cup�rer la localisation actuelle de l'utilisateur.
     * @param userName le nom de l'utilisateur.
     * @return VisitedLocation les informations de localisation.
     */
    public VisitedLocation getUserLocation(String userName);
    /**
     * La m�thode getAllLocationsThread permet de r�cup�rer la localisation actuelle de tous
     * les utilisateurs, en utilisant le multithreading pour plus de rapidit�.
     * @return HashMap avec les id des utilisateurs et le model VisitedLocation.
     */
    public HashMap<Object, Object> getAllLocationsThread();

}
