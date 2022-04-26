package ms.gpsutil.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import ms.gpsutil.model.User;

@SpringBootTest
public class GPSUtilServiceTest {

    @Autowired
    IGPSUtilService gpsUtilService;

    @Test
    public void getUserLocation() {
	// GIVEN
	User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
	// WHEN
	VisitedLocation visitedLocation = gpsUtilService.getUserLocation(user.getUserId());
	// THEN
	assertTrue(visitedLocation.userId.equals(user.getUserId()));
    }
    @Test
    public void getErrorUserLocation() {
	//GIVEN
	User user = new User();
	//WHEN
	VisitedLocation visitedLocation = gpsUtilService.getUserLocation(user.getUserId());
	//THEN
	assertEquals(visitedLocation, null);
    }

    @Test
    public void getNearbyAttractions() {
	//GIVEN
	User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
	VisitedLocation visitedLocation = gpsUtilService.getUserLocation(user.getUserId());
	//WHEN
	List<Attraction> attractions = gpsUtilService.getNearByAttractions(visitedLocation);
	//THEN
	assertEquals(5, attractions.size());
    }
    
    @Test
    public void getErrorNearbyAttractions() {
	//GIVEN
	User user = new User();
	VisitedLocation visitedLocation = gpsUtilService.getUserLocation(user.getUserId());
	//WHEN
	List<Attraction> attractions = gpsUtilService.getNearByAttractions(visitedLocation);
	//THEN
	assertEquals(attractions.isEmpty(), true);
    }

}
