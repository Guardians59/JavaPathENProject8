package ms.gpsutil.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import ms.gpsutil.model.User;
import ms.gpsutil.service.IGPSUtilService;
import ms.gpsutil.service.IUserService;

@RestController
public class GPSUtilController {
    
    @Autowired
    IGPSUtilService gpsUtilService;
    
    @Autowired
    IUserService userService;
    
    @GetMapping("/getLocation")
    public String getLocation(@RequestParam String userName) {
	User user = userService.getUser(userName);
	if (user != null) {
	    VisitedLocation visitedLocation = gpsUtilService.getUserLocation(user);
	    return JsonStream.serialize(visitedLocation.location);
	} else {
	    return JsonStream.serialize("An error occurred while searching for the location of the user");
	}
    }

    @GetMapping("/getNearByAttraction")
    public String getNearByAttraction(@RequestParam String userName) {
	User user = userService.getUser(userName);
	List<Attraction> listAttraction = new ArrayList<>();
	if (user != null) {
	    VisitedLocation visitedLocation = gpsUtilService.getUserLocation(user);
	    listAttraction = gpsUtilService.getNearByAttractions(visitedLocation);
	    return JsonStream.serialize(listAttraction);
	} else {
	    return JsonStream.serialize("An error occurred while searching for the location of the user");
	}
    }

}
