package tourGuide.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import tourGuide.controller.exception.AllCurrentUsersLocationsIsEmpty;
import tourGuide.controller.exception.CurrentUserLocationIsEmpty;
import tourGuide.controller.exception.UserLocationError;
import tourGuide.controller.exception.UsersLocationsHistoricalIsEmpty;
import tourGuide.model.VisitedLocation;
import tourGuide.service.IUserLocationService;

@RestController
public class UserLocationController {
    
    @Autowired
    IUserLocationService userLocationService;
    
    @GetMapping("getLocation/{userName}")
    public VisitedLocation getUserLocation(@PathVariable String userName) {
	VisitedLocation visitedLocation = new VisitedLocation();
	visitedLocation = userLocationService.getUserLocation(userName);
	if(visitedLocation.location == null)
	    throw new UserLocationError(
		    "An error occured while searching for the location of the user " + userName);
	
	return visitedLocation;
    }
    
    @GetMapping("getCurrentLocation/{userName}")
    public HashMap<Object, Object> getCurrentUserLocation(@PathVariable String userName) {
	HashMap<Object, Object> result = new HashMap<>();
	result = userLocationService.getCurrentUserLocation(userName);
	if(result.isEmpty())
	    throw new CurrentUserLocationIsEmpty(
		    "An error occurred while searching for the current location of the user " + userName);
	
	return result;
    }
    
    @GetMapping("getAllCurrentLocations/{userNames}")
    public HashMap<Object, Object> getAllCurrentUsersLocations(@PathVariable List<String> userNames) {
	HashMap<Object, Object> result = new HashMap<>();
	result = userLocationService.getAllCurrentLocation(userNames);
	if(result.isEmpty())
	    throw new AllCurrentUsersLocationsIsEmpty(
		    "An error occurred while searching for the current locations of the users");
	
	return result;
    }
    
    @GetMapping("getUsersLocationsHistorical/{userNames}")
    public HashMap<String, Object> getUsersLocationsHistorical(@PathVariable List<String> userNames) {
	HashMap<String, Object> result = new HashMap<>();
	result = userLocationService.getUsersLocationsHistorical(userNames);
	if(result.isEmpty())
	    throw new UsersLocationsHistoricalIsEmpty(
		    "An error occurred while searching for the historical locations of the users");
	
	return result;
    }

}
