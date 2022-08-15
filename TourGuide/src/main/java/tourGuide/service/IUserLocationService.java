package tourGuide.service;

import java.util.HashMap;
import java.util.List;

import tourGuide.model.VisitedLocation;
/**
 * L'interface IUserLocationService est le service qui récupére les localisations des
 * utilisateurs.
 * @author Dylan
 *
 */
public interface IUserLocationService {
    /**
     * La méthode getCurrentUserLocation permet de récupérer la dernière localisation enregistrée
     * de l'utilisateur.
     * @param userName le nom de l'utilisateur.
     * @return HashMap avec l'id de l'utilisateur et le model VisitedLocation pour avoir les
     * informations de localisation.
     */
    public HashMap<Object, Object> getCurrentUserLocation(String userName);
    /**
     * La méthode getCurrentLocationsOfUsers permet de récupérer la dernière localisation enregistrée
     * de plusieurs utilisateurs.
     * @param userNames les noms des utilisateurs.
     * @return HashMap avec les id des utilisateurs et le model VisitedLocation pour avoir les
     * informations des localisations.
     */
    public HashMap<Object, Object> getCurrentLocationOfUsers(List<String> userNames);
    /**
     * La méthode getAllCurrentLocations permet de récupérer la dernière localisation enregistrée
     * de tous les utilisateurs.
     * @return HashMap avec les id des utilisateurs et le model VisitedLocation pour avoir les
     * informations des localisations.
     */
    public HashMap<Object, Object> getAllCurrentLocations();
    /**
     * La méthode getUsersLocationsHistorical permet de récupérer l'historique des localisations
     * enregistrées des utilisateurs.
     * @param userNames les noms des utilisateurs.
     * @return HashMap avec le nom de l'utilisateur et une liste du model VisitedLocation.
     */
    public HashMap<String, Object> getUsersLocationsHistorical(List<String> userNames);
    /**
     * La méthode getUserLocation permet de récupérer la localisation actuelle de l'utilisateur.
     * @param userName le nom de l'utilisateur.
     * @return VisitedLocation les informations de localisation.
     */
    public VisitedLocation getUserLocation(String userName);
    /**
     * La méthode getAllLocationsThread permet de récupérer la localisation actuelle de tous
     * les utilisateurs, en utilisant le multithreading pour plus de rapidité.
     * @return HashMap avec les id des utilisateurs et le model VisitedLocation.
     */
    public HashMap<Object, Object> getAllLocationsThread();

}
