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

/**
 * La classe GPSUtilServiceImpl est l'implémentation de l'interface IGPSUtilService.
 * 
 * @see IGPSUtilService
 * @author Dylan
 *
 */
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

	/*
	 * On essaie de récupérer la location de l'utilisateur via gpsUtil, la date,
	 * et nous ajoutons ces informations à notre model VisitedLocation avec l'id de
	 * l'utilisateur.
	 * On capture l'exception Throwable en cas de NumberFormatException liée au formatage de
	 * la date.
	 */
	if(userId != null) {
	    try {
		Double latitude = gpsUtil.getUserLocation(userId).location.latitude;
		Double longitude = gpsUtil.getUserLocation(userId).location.longitude;
		Location location = new Location(longitude, latitude);
		Date date = new Date();
		visitedLocation.setUserId(userId);
		visitedLocation.setLocation(location);
		visitedLocation.setTimeVisited(date);
	    } catch (Throwable e) {
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
	    /*
	     * On utilise une boucle forEach sur la liste d'attractions depuis gpsUtil,
	     * on récupère la localisation de celle-ci, on calcul la distance entre l'utilisateur
	     * et l'attraction, on l'ajoute à notre treemap qui classera par ordre croissant les
	     * distances, ainsi lorsque la treemap à déjà 5 attractions, on vérifie si la distance
	     * de la nouvelle attraction est inférieur à la dernière enregistrée dans la map, si telle
	     * est le cas nous supprimons la dernière valeur pour ajouter la nouvelle.
	     */
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
		} else if (distanceList.lastKey() > distance) {
		    Double value = distanceList.lastKey();
		    distanceList.remove(value);
		    distanceList.put(distance, attractionName);
		}
	    });

	    /*
	     * On  récupère le nom des 5 attractions présent dans la treemap, pour les ajouter
	     * à notre liste de model Attraction qui sera retourner.
	     */
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
	    }
	} else {
	    logger.error("An error occurred while searching for the location of the user");
	}
	return nearByAttractions;
    }

    @Override
    public List<Attraction> getAllAttractions() {
	List<Attraction> listAttraction = new ArrayList<>();
	logger.debug("Search the list of attractions");
	/*
	 * On utilise une boucle forEach pour récupérer toutes les attractions depuis gpsUtil.
	 */
	gpsUtil.getAttractions().forEach(attraction -> {
	    Attraction addAttraction = new Attraction();
	    addAttraction.setAttractionId(attraction.attractionId);
	    addAttraction.setAttractionName(attraction.attractionName);
	    addAttraction.setCity(attraction.city);
	    addAttraction.setState(attraction.state);
	    addAttraction.setLatitude(attraction.latitude);
	    addAttraction.setLongitude(attraction.longitude);
	    listAttraction.add(addAttraction);
	});
	if(listAttraction.isEmpty()) {
	logger.info("The attraction list is empty");
	} else {
	    logger.info("The attraction list were successfully found");
	}
	return listAttraction;
    }

}
