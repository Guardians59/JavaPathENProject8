package tourGuide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import tourGuide.controller.exception.UserPreferencesError;
import tourGuide.model.UserPreferences;
import tourGuide.service.IUserPreferencesService;
/**
 * La classe UserPreferencesController permet de gérer les URL de gestion des préférences
 * utilisateur.
 * @author Dylan
 *
 */
@RestController
public class UserPreferencesController {
    
    @Autowired
    IUserPreferencesService userPreferencesService;
    
    /**
     * La méthode updateUserPreferences permet de modifier les préférences de l'utilisateur pour
     * ses séjours.
     * @param userName le nom de l'utilisateur.
     * @param userPreferences les nouvelles préférences de l'utilisateur.
     * @return Status OK si la modification à réussi ou notFound si une erreur est rencontrée.
     * @throws JsonProcessingException si une erreur avec JSON est rencontrée.
     */
    @PutMapping("updateUserPreferences/{userName}")
    public ResponseEntity<?> updateUserPreferences(@PathVariable String userName, @RequestBody UserPreferences userPreferences) throws JsonProcessingException {
	int result;
	result = userPreferencesService.updateUserPreferences(userName, userPreferences);
	if(result == -1) {
	    return ResponseEntity.notFound().build();
	} else if (result == 1){
	    return ResponseEntity.status(HttpStatus.OK)
		    .body("Preferences of user " + userName + " haven't been updated, because no information has been changed");
	} else {
	    return ResponseEntity.status(HttpStatus.OK)
		    .body("Preferences of user " + userName + " updated with success");
	}
    }
    /**
     * La méthode getUserPreferences permet de récupérer les préférences de l'utilisateur pour
     * ses séjours.
     * @param userName le nom de l'utilisateur.
     * @return UserPreferences le model des préférences de l'utilisateur.
     */
    @GetMapping("getUserPreferences/{userName}")
    public UserPreferences getUserPreferences(@PathVariable String userName) {
	UserPreferences result = userPreferencesService.getUserPreferences(userName);
	if(result.getNumberOfAdults() == 0)
	    throw new UserPreferencesError("An error occurred while finding the user " + userName);
	
	return result;
    }

}
