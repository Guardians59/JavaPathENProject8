package ms.gpsutil.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import ms.gpsutil.model.Attraction;
import ms.gpsutil.model.Location;
import ms.gpsutil.model.VisitedLocation;
import ms.gpsutil.service.IDistanceCalculService;
import ms.gpsutil.service.IGPSUtilService;

@Service
public class GPSUtilServiceImpl implements IGPSUtilService {

    @Autowired
    IDistanceCalculService distanceCalculService;

    private Logger logger = LoggerFactory.getLogger(GPSUtilServiceImpl.class);
    private GpsUtil gpsUtil = new GpsUtil();

    @Override
    public VisitedLocation getUserLocation(UUID userId) {
	Locale.setDefault(Locale.ENGLISH);
	VisitedLocation visitedLocation = new VisitedLocation();
	logger.debug("Search the user location");

	if (userId != null) {
	    try {
		Double latitude = gpsUtil.getUserLocation(userId).location.latitude;
		Double longitude = gpsUtil.getUserLocation(userId).location.longitude;
		Location location = new Location(longitude, latitude);
		Date date = new Date();
		visitedLocation.setUserId(userId);
		visitedLocation.setLocation(location);
		visitedLocation.setTimeVisited(date);
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
	List<Attraction> attractions = new ArrayList<>();
	List<Attraction> nearByAttractions = new ArrayList<>();
	TreeMap<Double, String> distanceList = new TreeMap<Double, String>();
	logger.debug("Search for the five nearest attractions to the user");

	if(visitedLocation.getUserId() != null && visitedLocation.getLocation() != null) {
	
	    gpsUtil.getAttractions().forEach(attraction -> {
		   Attraction addAttraction = new Attraction();
		   addAttraction.setAttractionId(attraction.attractionId);
		   addAttraction.setAttractionName(attraction.attractionName);
		   addAttraction.setCity(attraction.city);
		   addAttraction.setState(attraction.state);
		   addAttraction.setLatitude(attraction.latitude);
		   addAttraction.setLongitude(attraction.longitude);
		   attractions.add(addAttraction);
		   
	    Location locationAttraction = new Location(addAttraction.longitude, addAttraction.latitude);
	    Double distance = distanceCalculService.getDistance(locationAttraction, visitedLocation.location);
	    String attractionName = addAttraction.getAttractionName();

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
		if(attractionList.getAttractionName().equals(key)) {
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
