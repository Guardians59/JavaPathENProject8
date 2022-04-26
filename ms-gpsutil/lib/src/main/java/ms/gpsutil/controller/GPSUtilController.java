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
import ms.gpsutil.service.IGPSUtilService;

@RestController
public class GPSUtilController {

    @Autowired
    IGPSUtilService gpsUtilService;

    @GetMapping("/getLocation")
    public Location getLocation(@RequestParam UUID id) {

	VisitedLocation visitedLocation = gpsUtilService.getUserLocation(id);
	return visitedLocation.location;
    }

    @GetMapping("/getNearByAttraction")
    public List<Attraction> getNearByAttraction(@RequestParam UUID id) {

	List<Attraction> listAttraction = new ArrayList<>();
	VisitedLocation visitedLocation = gpsUtilService.getUserLocation(id);
	listAttraction = gpsUtilService.getNearByAttractions(visitedLocation);
	return listAttraction;
    }

}
