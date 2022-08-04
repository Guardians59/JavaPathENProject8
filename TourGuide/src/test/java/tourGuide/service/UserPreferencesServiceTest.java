package tourGuide.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;

import tourGuide.model.User;
import tourGuide.model.UserPreferences;
import tourGuide.repositories.UserRepository;

@SpringBootTest
public class UserPreferencesServiceTest {
    
    @Autowired
    IUserPreferencesService userPreferencesService;
    
    @Autowired
    UserRepository userRepository;
    
    @Test
    public void updateUserPreferencesTest() throws JsonProcessingException {
	//GIVEN
	int result;
	User user = userRepository.getUser("internalUser52");
	UserPreferences oldUserPreferences = user.getUserPreferences();
	UserPreferences newUserPreferences = new UserPreferences();
	newUserPreferences.setNumberOfAdults(3);
	newUserPreferences.setNumberOfChildren(2);
	newUserPreferences.setTripDuration(2);
	newUserPreferences.setTicketQuantity(5);
	newUserPreferences.setAttractionProximity(user.getUserPreferences().getAttractionProximity());
	newUserPreferences.setHighPricePoint(user.getUserPreferences().getHighPricePoint());
	newUserPreferences.setLowerPricePoint(user.getUserPreferences().getLowerPricePoint());
	newUserPreferences.setCurrency(user.getUserPreferences().getCurrency());
	//WHEN
	result = userPreferencesService.updateUserPreferences("internalUser52", newUserPreferences);
	//THEN
	assertEquals(result, 2);
	assertEquals(user.getUserPreferences().equals(oldUserPreferences), false);
    }
    
    @Test
    public void updateSameUserPreferencesTest() throws JsonProcessingException {
	//GIVEN
	int result;
	User user = userRepository.getUser("internalUser53");
	UserPreferences oldUserPreferences = user.getUserPreferences();
	UserPreferences newUserPreferences = new UserPreferences();
	newUserPreferences.setNumberOfAdults(1);
	newUserPreferences.setNumberOfChildren(0);
	newUserPreferences.setTripDuration(1);
	newUserPreferences.setTicketQuantity(1);
	newUserPreferences.setAttractionProximity(user.getUserPreferences().getAttractionProximity());
	newUserPreferences.setHighPricePoint(user.getUserPreferences().getHighPricePoint());
	newUserPreferences.setLowerPricePoint(user.getUserPreferences().getLowerPricePoint());
	newUserPreferences.setCurrency(user.getUserPreferences().getCurrency());
	//WHEN
	result = userPreferencesService.updateUserPreferences("internalUser53", newUserPreferences);
	//THEN
	assertEquals(result, 1);
	assertEquals(user.getUserPreferences().equals(oldUserPreferences), true);
    }
    
    @Test
    public void updateUserPreferencesErrorNumberAdultTest() throws JsonProcessingException {
	//GIVEN
	int result;
	User user = userRepository.getUser("internalUser13");
	UserPreferences oldUserPreferences = user.getUserPreferences();
	UserPreferences newUserPreferences = new UserPreferences();
	newUserPreferences.setNumberOfAdults(0);
	newUserPreferences.setNumberOfChildren(2);
	newUserPreferences.setTripDuration(2);
	newUserPreferences.setTicketQuantity(5);
	newUserPreferences.setAttractionProximity(user.getUserPreferences().getAttractionProximity());
	newUserPreferences.setHighPricePoint(user.getUserPreferences().getHighPricePoint());
	newUserPreferences.setLowerPricePoint(user.getUserPreferences().getLowerPricePoint());
	newUserPreferences.setCurrency(user.getUserPreferences().getCurrency());
	//WHEN
	result = userPreferencesService.updateUserPreferences("internalUser13", newUserPreferences);
	//THEN
	assertEquals(result, -1);
	assertEquals(user.getUserPreferences().equals(oldUserPreferences), true);
    }
    
    @Test
    public void updateUserPreferencesErrorDurationTest() throws JsonProcessingException {
	//GIVEN
	int result;
	User user = userRepository.getUser("internalUser14");
	UserPreferences oldUserPreferences = user.getUserPreferences();
	UserPreferences newUserPreferences = new UserPreferences();
	newUserPreferences.setNumberOfAdults(3);
	newUserPreferences.setNumberOfChildren(2);
	newUserPreferences.setTripDuration(0);
	newUserPreferences.setTicketQuantity(5);
	newUserPreferences.setAttractionProximity(user.getUserPreferences().getAttractionProximity());
	newUserPreferences.setHighPricePoint(user.getUserPreferences().getHighPricePoint());
	newUserPreferences.setLowerPricePoint(user.getUserPreferences().getLowerPricePoint());
	newUserPreferences.setCurrency(user.getUserPreferences().getCurrency());
	//WHEN
	result = userPreferencesService.updateUserPreferences("internalUser14", newUserPreferences);
	//THEN
	assertEquals(result, -1);
	assertEquals(user.getUserPreferences().equals(oldUserPreferences), true);
    }
    
    @Test
    public void updateUserPreferencesErrorTest() throws JsonProcessingException {
	//GIVEN
	int result;
	UserPreferences newUserPreferences = new UserPreferences();
	newUserPreferences.setNumberOfAdults(3);
	newUserPreferences.setNumberOfChildren(2);
	newUserPreferences.setTripDuration(2);
	newUserPreferences.setTicketQuantity(5);
	newUserPreferences.setAttractionProximity(Integer.MAX_VALUE);
	//WHEN
	result = userPreferencesService.updateUserPreferences("internalUser102000", newUserPreferences);
	//THEN
	assertEquals(result, -1);
    }

}
