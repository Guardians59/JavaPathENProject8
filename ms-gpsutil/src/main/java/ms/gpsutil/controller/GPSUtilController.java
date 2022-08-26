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

import io.swagger.annotations.ApiOperation;
import ms.gpsutil.controller.exception.AttractionListIsEmpty;
import ms.gpsutil.controller.exception.NearByAttractionError;
import ms.gpsutil.controller.exception.UserLocationError;
import ms.gpsutil.model.Attraction;
import ms.gpsutil.model.VisitedLocation;
import ms.gpsutil.service.IGPSUtilService;

/**
 * La classe GPSUtilController est le controller qui permet de gérer les URL
 * du micro-service GPSUtil.
 * 
 * @author Dylan
 *
 */
@RestController
public class GPSUtilController {

    @Autowired
    IGPSUtilService gpsUtilService;
    
    /**
     * La méthode getLocation permet de récupérer la localisation de l'utilisateur.
     * @param id l'id de l'utilisateur.
     * @return VisitedLocation la localisation de l'utilisateur avec les informations
     * (Id, Location, Date).
     */
    @ApiOperation(value = "Recupere la localisation de l'utilisateur.")
    @GetMapping("/getLocation")
    public VisitedLocation getLocation(@RequestParam UUID id) {
	    VisitedLocation visitedLocation = gpsUtilService.getUserLocation(id);
	    if(visitedLocation == null)
		throw new UserLocationError(
			"An error occurred while searching for the location of the user with the id " + id);
	    return visitedLocation;
	}
    
    /**
     * La méthode getLocationProxy permet d'envoyer la localisation de l'utilisateur à
     * TourGuide via Feign.
     * @param id l'id de l'utilisateur.
     * @return VisitedLocation la localisation de l'utilisateur avec les informations
     * (Id, Location, Date).
     */
    @ApiOperation(value = "Envoie la position de l'utilisateur au proxy.")
    @PostMapping("/getLocation")
    public VisitedLocation getLocationProxy(@RequestBody UUID id) {
	    VisitedLocation visitedLocation = gpsUtilService.getUserLocation(id);
	    if(visitedLocation == null)
		throw new UserLocationError(
			"An error occurred while searching for the location of the user with the id " + id);
	    return visitedLocation;
	}

    /**
     * La méthode getNearByAttraction permet de récupérer la liste des 5 attractions les plus
     * proches de l'utilisateur.
     * @param id l'id de l'utilisateur.
     * @return List d'attractions, les attractions les plus proches.
     */
    @ApiOperation(value = "Recupere la liste des 5 attractions les plus proches de l'utilisateur.")
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
    
    /**
     * La méthode getNearByAttractionProxy permet d'envoyer la liste des 5 attractions les plus
     * proches de l'utilisateur à TourGuide via Feign.
     * @param id l'id de l'utilisateur.
     * @return List d'attractions, les attractions les plus proches de l'utilisateur.
     */
    @ApiOperation(value = "Envoie la liste des 5 attractions les plus proches de l'utilisateur au proxy.")
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
    
    /**
     * La méthode getAllAttractions permet de récupérer la liste des attractions.
     * @return List d'attractions, les attractions enregistrées.
     */
    @ApiOperation(value = "Recupere la liste des attractions.")
    @GetMapping("/getAllAttractions")
    public List<Attraction> getAllAttractions() {
	List<Attraction> listAttraction = new ArrayList<>();
	listAttraction = gpsUtilService.getAllAttractions();
	
	if(listAttraction.isEmpty())
	    throw new AttractionListIsEmpty("The attraction list is empty");
	
	return listAttraction;
    }

}
