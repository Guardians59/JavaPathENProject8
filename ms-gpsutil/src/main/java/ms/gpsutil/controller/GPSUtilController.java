package ms.gpsutil.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import ms.gpsutil.controller.exception.ListOfUsersError;
import ms.gpsutil.controller.exception.NearByAttractionError;
import ms.gpsutil.controller.exception.UserLocationError;
import ms.gpsutil.service.IGPSUtilService;

@RestController
public class GPSUtilController {

    @Autowired
    IGPSUtilService gpsUtilService;
    

    @GetMapping("/getLocation")
    public Location getLocation(@RequestParam UUID id) {
	    VisitedLocation visitedLocation = gpsUtilService.getUserLocation(id);
	    if(visitedLocation == null)
		throw new UserLocationError(
			"An error occurred while searching for the location of the user with the id " + id);
	    return visitedLocation.location;
	}

    @GetMapping("/getNearByAttraction")
    public List<Attraction> getNearByAttraction(@RequestParam UUID id) {
	List<Attraction> listAttraction = new ArrayList<>();
	VisitedLocation visitedLocation = gpsUtilService.getUserLocation(id);

	if(visitedLocation == null) {
	    throw new UserLocationError(
		    "An error occurred while searching for the location of the user with the id " + id);
	} else {
	    listAttraction = gpsUtilService.getNearByAttractions(visitedLocation);

	    if(listAttraction.isEmpty())
		throw new NearByAttractionError("An error occurred while searching for the nearest attractions");
	}

	return listAttraction;
    }
    
    @GetMapping("/getAllLocations")
    public List<VisitedLocation> getAllLocations(@RequestParam List<UUID> userId) {
	List<VisitedLocation> result = new ArrayList<>();
	if(userId.isEmpty()) {
	    throw new ListOfUsersError("An error occurred while searching for user locations");
	} else {
	    result = gpsUtilService.getAllLocationsUsers(userId);
	}
	return result;
    }

}
