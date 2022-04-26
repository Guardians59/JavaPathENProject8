package ms.gpsutil.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import ms.gpsutil.model.User;
import ms.gpsutil.service.IDistanceCalculService;
import ms.gpsutil.service.IGPSUtilService;

@Service
public class GPSUtilServiceImpl implements IGPSUtilService{
    
    @Autowired
    IDistanceCalculService distanceCalculService;

    private Logger logger = LoggerFactory.getLogger(GPSUtilServiceImpl.class);
    private GpsUtil gpsUtil = new GpsUtil();

    @Override
    public VisitedLocation getUserLocation(UUID userId) {
	Locale.setDefault(Locale.ENGLISH);
	VisitedLocation visitedLocation = null;
	User user = new User();
	logger.debug("Search the user location");

	if(userId != null) {
	    try {
		visitedLocation = gpsUtil.getUserLocation(userId);
		user.addToVisitedLocations(visitedLocation);
	    } catch (NumberFormatException e) {
		logger.error("An error has occurred in the format of the position");
	    }
	    logger.info("The user's location was found successfully");
	} else {
	    logger.error("An error occurred while searching for the location of the user");
	}
	return visitedLocation;
    }

    @Override
    public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation) {
	List<Attraction> attractions = gpsUtil.getAttractions();
	List<Attraction> nearByAttractions = new ArrayList<>();
	TreeMap<Double, String> distanceList = new TreeMap<Double, String>();
	logger.debug("Search for the five nearest attractions to the user");

	if(visitedLocation != null) {
	attractions.forEach(attraction -> {
	    Double distance = distanceCalculService.getDistance(attraction, visitedLocation.location);
	    String attractionName = attraction.attractionName;

	    if(distanceList.size() < 5) {
		distanceList.put(distance, attractionName);
	    } else if(distanceList.lastKey() > distance) {
		Double value = distanceList.lastKey();
		distanceList.remove(value);
		distanceList.put(distance, attractionName);
	    }
	});

	Set<Entry<Double, String>> set = distanceList.entrySet();
	set.stream().forEach(attractionDistance -> {
	    String key = attractionDistance.getValue();

	    attractions.forEach(attractionList -> {
		if(attractionList.attractionName.equals(key)) {
		    nearByAttractions.add(attractionList);
		}
	    });
	});
	if(nearByAttractions.isEmpty() || nearByAttractions.size() < 5) {
	    logger.error("An error occurred while searching for the nearest attractions");
	} else {
	    logger.info("The list of nearest attractions were successfully found");
	} } else {
	    logger.error("An error occurred while searching for the location of the user");
	}
	return nearByAttractions;
    }

}
