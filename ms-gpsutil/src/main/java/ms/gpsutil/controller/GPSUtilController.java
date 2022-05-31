package ms.gpsutil.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ms.gpsutil.controller.exception.AttractionListIsEmpty;
import ms.gpsutil.controller.exception.NearByAttractionError;
import ms.gpsutil.controller.exception.UserLocationError;
import ms.gpsutil.model.Attraction;
import ms.gpsutil.model.VisitedLocation;
import ms.gpsutil.service.IGPSUtilService;

@RestController
public class GPSUtilController {

    @Autowired
    IGPSUtilService gpsUtilService;
    

    @GetMapping("/getLocation")
    public VisitedLocation getLocation(@RequestParam UUID id) {
	    VisitedLocation visitedLocation = gpsUtilService.getUserLocation(id);
	    if(visitedLocation == null)
		throw new UserLocationError(
			"An error occurred while searching for the location of the user with the id " + id);
	    return visitedLocation;
	}
    
    @PostMapping("/getLocation")
    public VisitedLocation getLocationProxy(@RequestBody UUID id) {
	    VisitedLocation visitedLocation = gpsUtilService.getUserLocation(id);
	    if(visitedLocation == null)
		throw new UserLocationError(
			"An error occurred while searching for the location of the user with the id " + id);
	    return visitedLocation;
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
    
    @PostMapping("/getNearByAttraction")
    public List<Attraction> getNearByAttractionProxy(@RequestBody UUID id) {
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
    
    @GetMapping("/getAllAttractions")
    public List<Attraction> getAllAttractions() {
	List<Attraction> listAttraction = new ArrayList<>();
	listAttraction = gpsUtilService.getAllAttractions();
	
	if(listAttraction.isEmpty())
	    throw new AttractionListIsEmpty("The attraction list is empty");
	
	return listAttraction;
    }

}
