package tourGuide.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import tourGuide.model.User;
import tourGuide.model.UserPreferences;
import tourGuide.repositories.UserRepository;
import tourGuide.service.IUserPreferencesService;

public class UserPreferencesServiceImpl implements IUserPreferencesService{
    
    @Autowired
    UserRepository userRepository;
    
    private Logger logger = LoggerFactory.getLogger(UserPreferencesServiceImpl.class);

    @Override
    public boolean updateUserPreferences(String userName, int numberOfAdult, int numberOfChildren, int duration) {
	User user = userRepository.getUser(userName);
	boolean result = false;
	logger.debug("Update preferences for user " + userName);
	if(user != null) {
	    UserPreferences userPreference = new UserPreferences();
	    userPreference.setNumberOfAdults(numberOfAdult);
	    userPreference.setNumberOfChildren(numberOfChildren);
	    userPreference.setTripDuration(duration);
	    userPreference.setTicketQuantity((numberOfAdult + numberOfChildren)); 
	    user.setUserPreferences(userPreference);
	    result = true;
	    logger.info("Preferences for user " + userName + " have been updated successfully");
	} else {
	    logger.error("An error occurred while finding the user " + userName);
	}
	return result;
    }

}
