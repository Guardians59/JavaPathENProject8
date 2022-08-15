package tourGuide.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zalando.jackson.datatype.money.MoneyModule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tourGuide.model.User;
import tourGuide.model.UserPreferences;
import tourGuide.repositories.UserRepository;
import tourGuide.service.IUserPreferencesService;
/**
 * La classe UserPreferencesServiceImpl est l'implémentation de l'interface IUserPreferencesService.
 * 
 * @see IUserPreferencesService
 * @author Dylan
 *
 */
@Service
public class UserPreferencesServiceImpl implements IUserPreferencesService {

    @Autowired
    UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(UserPreferencesServiceImpl.class);

    @Override
    public int updateUserPreferences(String userName, UserPreferences userPreferences) throws JsonProcessingException  {
	User user = userRepository.getUser(userName);
	int result = -1;
	logger.debug("Update preferences for user " + userName);
	/*
	 * On initie un ObjectMapper avec moneyModule pour la conversion de Money.
	 */
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.registerModule(new MoneyModule());
	
	if(user != null) {
	    String json = objectMapper.writeValueAsString(userPreferences);
	    String userPreferencesRegister = objectMapper.writeValueAsString(user.getUserPreferences());
	    if(userPreferences.getNumberOfAdults() > 0 && userPreferences.getTripDuration() > 0) {
		/*
		 * Si aucune informations n'a été modifiée on renvoie 1, sans rien modifier.
		 * Si une modification est apportée on renvoie 2 tout en sauvegardant les nouvelles
		 * préférences de l'utilisateur.
		 * Si une erreur est rencontrée le resultat retourné sera -1.
		 */
		if(json.equals(userPreferencesRegister)) {
		    result = 1;
		    logger.info("Preferences for user " + userName + " haven't been updated, "
			    + "because no information has been changed");

		} else {
		    UserPreferences newUserPreference = new UserPreferences();
		    newUserPreference.setNumberOfAdults(userPreferences.getNumberOfAdults());
		    newUserPreference.setNumberOfChildren(userPreferences.getNumberOfChildren());
		    newUserPreference.setTripDuration(userPreferences.getTripDuration());
		    newUserPreference.setTicketQuantity(userPreferences.getTicketQuantity());
		    newUserPreference.setAttractionProximity(userPreferences.getAttractionProximity());
		    newUserPreference.setCurrency(userPreferences.getCurrency());
		    newUserPreference.setHighPricePoint(userPreferences.getHighPricePoint());
		    newUserPreference.setLowerPricePoint(userPreferences.getLowerPricePoint());
		    user.setUserPreferences(newUserPreference);
		    result = 2;
		    logger.info("Preferences for user " + userName + " have been updated successfully");
		}
	    } else {
		logger.error("An error occurred, the number of adults and the duration must be greater than zero");
	    }
	} else {
	    logger.error("An error occurred while finding the user " + userName);
	}
	return result;
    }

    @Override
    public UserPreferences getUserPreferences(String userName) {
	User user = userRepository.getUser(userName);
	UserPreferences userPreferences = new UserPreferences();
	
	if(user != null) {
	    //On récupère les préférences de l'utilisateur.
	    userPreferences = user.getUserPreferences();
	    logger.info("Preferences for user " + userName + " has been successfully found");
	}else {
	    userPreferences.setNumberOfAdults(0);
	    logger.error("An error occurred while finding the user " + userName);
	}
	return userPreferences;
    }

}
