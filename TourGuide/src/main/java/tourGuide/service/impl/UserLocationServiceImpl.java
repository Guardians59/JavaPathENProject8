package tourGuide.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;
import tourGuide.repositories.UserRepository;
import tourGuide.service.IUserLocationService;

@Service
public class UserLocationServiceImpl implements IUserLocationService {
    
    @Autowired
    UserRepository userRepository;
    
    private Logger logger = LoggerFactory.getLogger(UserLocationServiceImpl.class);

    @Override
    public HashMap<Object, Object> getCurrentUserLocation(String userName) {
	User user = userRepository.getUser(userName);
	HashMap<Object, Object> result = new HashMap<>();
	logger.debug("Search the last current location");
	
	if(user != null) {
	Double longitude = user.getLastVisitedLocation().location.longitude;
	Double latitude = user.getLastVisitedLocation().location.latitude;
	Location location = new Location(longitude, latitude);
	result.put(user.getUserId(), location);
	logger.info("The last current location was successfully retrieved");
	} else {
	    logger.error("An error occurred while finding the user's location for " + userName);
	}
	return result;
    }

    @Override
    public HashMap<Object, Object> getAllCurrentLocation(List<String> userNames) {
	HashMap<Object, Object> result = new HashMap<>();
	logger.info("Find the last current location of each users");
	userNames.forEach(userName -> {
	    User user = new User();
	    user = userRepository.getUser(userName);
	    if(user != null) {
		Double longitude = user.getLastVisitedLocation().location.longitude;
		Double latitude = user.getLastVisitedLocation().location.latitude;
		Location location = new Location(longitude, latitude);
		result.put(user.getUserId(), location);
	    } else {
		logger.error("An error occurred while finding the user's location for " + userName);
	    }
	});
	if(!result.isEmpty()) {
	    logger.info("The location of the users has been successfully found");
	} else {
	    logger.info("No location found");
	}
	return result;
    }

    @Override
    public HashMap<String, Object> getAllLocationsHistorical(List<String> userNames) {
	HashMap<String, Object> result = new HashMap<>();
	List<VisitedLocation> locationHistorical = new ArrayList<>();
	logger.debug("Search the historical location");
	userNames.forEach(userName -> {
	    User user = new User();
	    user = userRepository.getUser(userName);
	    if(user != null) {
		user.getListVisitedLocations().forEach(listVisitedLocation -> {
		    VisitedLocation visitedLocation = new VisitedLocation();
		    Location location = new Location(listVisitedLocation.location.longitude, listVisitedLocation.location.latitude);
		    visitedLocation.setUserId(listVisitedLocation.userId);
		    visitedLocation.setLocation(location);
		    visitedLocation.setTimeVisited(listVisitedLocation.timeVisited);
		    locationHistorical.add(visitedLocation);
		});
		result.put(userName, locationHistorical);
	    } else {
		logger.error("An error occurred while finding the user's location historical for " + userName);
	    }
	});
	if(!result.isEmpty()) {
	    logger.info("The historicals location of the users has been successfully found");
	} else {
	    logger.info("No location history found");
	}
	return result;
    }

}
