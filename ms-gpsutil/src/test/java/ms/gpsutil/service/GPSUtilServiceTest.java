package ms.gpsutil.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ms.gpsutil.model.Attraction;
import ms.gpsutil.model.Location;
import ms.gpsutil.model.User;
import ms.gpsutil.model.VisitedLocation;

@SpringBootTest
public class GPSUtilServiceTest {

    @Autowired
    IGPSUtilService gpsUtilService;

    @Test
    public void getUserLocationTest() {
	// GIVEN
	User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
	// WHEN
	VisitedLocation visitedLocation = gpsUtilService.getUserLocation(user.getUserId());
	// THEN
	assertTrue(visitedLocation.userId.equals(user.getUserId()));
	assertTrue(visitedLocation.getTimeVisited() != null);
    }
    @Test
    public void getErrorUserLocationTest() {
	//GIVEN
	User user = new User();
	//WHEN
	VisitedLocation visitedLocation = gpsUtilService.getUserLocation(user.getUserId());
	//THEN
	assertEquals(visitedLocation.getLocation(), null);
    }

    @Test
    public void getNearbyAttractionsTest() {
	//GIVEN
	User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
	
	VisitedLocation visitedLocation = new VisitedLocation();
	Location location = new Location(34.817595D, -120.922008D);
	visitedLocation.setLocation(location);
	visitedLocation.setUserId(user.getUserId());
	//WHEN
	List<Attraction> attractions = gpsUtilService.getNearByAttractions(visitedLocation);
	//THEN
	assertEquals(5, attractions.size());
    }
    
    @Test
    public void getErrorNearbyAttractionsTest() {
	//GIVEN
	User user = new User();
	VisitedLocation visitedLocation = new VisitedLocation();
	Location location = new Location(34.817595D, -120.922008D);
	visitedLocation.setLocation(location);
	visitedLocation.setUserId(user.getUserId());
	//WHEN
	List<Attraction> attractions = gpsUtilService.getNearByAttractions(visitedLocation);
	//THEN
	assertEquals(attractions.isEmpty(), true);
    }
    
    @Test
    public void getAllAttractions() {
	//GIVEN
	List<Attraction> listAttraction = new ArrayList<>();
	//WHEN
	listAttraction = gpsUtilService.getAllAttractions();
	//THEN
	assertEquals(listAttraction.isEmpty(), false);
    }

}
