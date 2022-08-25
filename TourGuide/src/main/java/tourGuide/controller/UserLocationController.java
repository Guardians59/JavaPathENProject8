package tourGuide.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import tourGuide.controller.exception.AllCurrentUsersLocationsIsEmpty;
import tourGuide.controller.exception.CurrentUserLocationIsEmpty;
import tourGuide.controller.exception.ThreadTimeOut;
import tourGuide.controller.exception.UserLocationError;
import tourGuide.controller.exception.UsersLocationsHistoricalIsEmpty;
import tourGuide.model.VisitedLocation;
import tourGuide.service.IUserLocationService;
import tourGuide.service.impl.UserLocationServiceImpl;

/**
 * La classe UserLocationController est le controller qui permet de g�rer les URL des
 * localisations des utilisateurs.
 * @author Dylan
 *
 */
@RestController
public class UserLocationController {
    
    @Autowired
    IUserLocationService userLocationService;
    
    @Autowired
    UserLocationServiceImpl userLocationServiceImpl;
    
    /**
     * La m�thode getUserLocation permet de r�cup�rer la localisation de l'utilisateur.
     * @param userName le nom de l'utilisateur.
     * @return VisitedLocation les informations de localisation.
     */
    @ApiOperation(value = "R�cup�re la localisation de l'utilisateur.")
    @GetMapping("getLocation/{userName}")
    public VisitedLocation getUserLocation(@PathVariable String userName) {
	VisitedLocation visitedLocation = new VisitedLocation();
	visitedLocation = userLocationService.getUserLocation(userName);
	if(visitedLocation.location == null)
	    throw new UserLocationError(
		    "An error occured while searching for the location of the user " + userName);
	
	return visitedLocation;
    }
    
    /**
     * La m�thode getCurrentUserLocation permet de r�cup�rer la derni�re localisation enregistr�e
     * de l'utilisateur.
     * @param userName le nom de l'utilisateur.
     * @return HashMap avec l'id de l'utilisateur et le model VisitedLocation pour avoir les
     * informations de localisation.
     */
    @ApiOperation(value = "R�cup�re la derni�re localisation enregistr�e de l'utilisateur.")
    @GetMapping("getCurrentLocation/{userName}")
    public HashMap<Object, Object> getCurrentUserLocation(@PathVariable String userName) {
	HashMap<Object, Object> result = new HashMap<>();
	result = userLocationService.getCurrentUserLocation(userName);
	if(result.isEmpty())
	    throw new CurrentUserLocationIsEmpty(
		    "An error occurred while searching for the current location of the user " + userName);
	
	return result;
    }
    /**
     * La m�thode getCurrentLocationsOfUsers permet de r�cup�rer la derni�re localisation enregistr�e
     * de plusieurs utilisateurs.
     * @param userNames les noms des utilisateurs.
     * @return HashMap avec les id des utilisateurs et le model VisitedLocation pour avoir les
     * informations des localisations.
     */
    @ApiOperation(value = "R�cup�re la derni�re localisation enregistr�e des utilisateurs.")
    @GetMapping("getCurrentLocationsOfUsers/{userNames}")
    public HashMap<Object, Object> getCurrentLocationsOfUsers(@PathVariable List<String> userNames) {
	HashMap<Object, Object> result = new HashMap<>();
	result = userLocationService.getCurrentLocationOfUsers(userNames);
	if(result.isEmpty())
	    throw new AllCurrentUsersLocationsIsEmpty(
		    "An error occurred while searching for the current locations of the users");
	
	return result;
    }
    /**
     * La m�thode getAllCurrentLocations permet de r�cup�rer la derni�re localisation enregistr�e
     * de tous les utilisateurs.
     * @return HashMap avec les id des utilisateurs et le model VisitedLocation pour avoir les
     * informations des localisations.
     */
    @ApiOperation(value = "R�cup�re la derni�re localisation enregistr�e de tous les utilisateurs.")
    @GetMapping("getAllCurrentLocations")
    public HashMap<Object, Object> getAllCurrentLocations() {
	HashMap<Object, Object> result = new HashMap<>();
	result = userLocationService.getAllCurrentLocations();
	if(result.isEmpty())
	    throw new AllCurrentUsersLocationsIsEmpty(
		    "An error occurred while searching for the current locations of the users");
	
	return result;
    }
    /**
     * La m�thode getUsersLocationsHistorical permet de r�cup�rer l'historique des localisations
     * enregistr�es des utilisateurs.
     * @param userNames le nom des utilisateurs.
     * @return HashMap avec le nom de l'utilisateur et une liste du model VisitedLocation.
     */
    @ApiOperation(value = "R�cup�re l'historique de localisation des utilisateurs.")
    @GetMapping("getUsersLocationsHistorical/{userNames}")
    public HashMap<String, Object> getUsersLocationsHistorical(@PathVariable List<String> userNames) {
	HashMap<String, Object> result = new HashMap<>();
	result = userLocationService.getUsersLocationsHistorical(userNames);
	if(result.isEmpty())
	    throw new UsersLocationsHistoricalIsEmpty(
		    "An error occurred while searching for the historical locations of the users");
	
	return result;
    }
    /**
     * La m�thode getAllLocationsThread permet de r�cup�rer la localisation de tous les utilisateurs
     * en utilisant le multithreading pour plus de rapidit�.
     * @return HashMap avec les id des utilisateurs et le model VisitedLocation.
     */
    @ApiOperation(value = "R�cup�re la localisation de tous les utilisateurs.")
    @GetMapping("getAllLocations")
    public HashMap<Object, Object> getAllLocationsThread() {
	HashMap<Object, Object> result = userLocationService.getAllLocationsThread();
	if(!result.isEmpty()) {
	    return result;
	} else {
	    throw new ThreadTimeOut("Thread execution timed out, more than 15 minutes of searching");
	}
	
    }

}
