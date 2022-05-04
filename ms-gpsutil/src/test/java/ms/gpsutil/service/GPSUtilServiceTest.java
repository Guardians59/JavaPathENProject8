package ms.gpsutil.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
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
    public void getUserLocationTest() {
	// GIVEN
	User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
	// WHEN
	VisitedLocation visitedLocation = gpsUtilService.getUserLocation(user.getUserId());
	// THEN
	assertTrue(visitedLocation.userId.equals(user.getUserId()));
    }
    @Test
    public void getErrorUserLocationTest() {
	//GIVEN
	User user = new User();
	//WHEN
	VisitedLocation visitedLocation = gpsUtilService.getUserLocation(user.getUserId());
	//THEN
	assertEquals(visitedLocation, null);
    }

    @Test
    public void getNearbyAttractionsTest() {
	//GIVEN
	User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
	VisitedLocation visitedLocation = gpsUtilService.getUserLocation(user.getUserId());
	//WHEN
	List<Attraction> attractions = gpsUtilService.getNearByAttractions(visitedLocation);
	//THEN
	assertEquals(5, attractions.size());
    }
    
    @Test
    public void getErrorNearbyAttractionsTest() {
	//GIVEN
	User user = new User();
	VisitedLocation visitedLocation = gpsUtilService.getUserLocation(user.getUserId());
	//WHEN
	List<Attraction> attractions = gpsUtilService.getNearByAttractions(visitedLocation);
	//THEN
	assertEquals(attractions.isEmpty(), true);
    }
    
    @Test
    public void getAllLocationsUsersTest() {
	//GIVEN
	User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
	User user2 = new User(UUID.randomUUID(), "fred", "000", "fred@tourGuide.com");
	User user3 = new User(UUID.randomUUID(), "joe", "000", "joe@tourGuide.com");
	List<UUID> list = new ArrayList<>();
	list.add(user.getUserId());
	list.add(user2.getUserId());
	list.add(user3.getUserId());
	List<VisitedLocation> result = new ArrayList<>();
	//WHEN
	result = gpsUtilService.getAllLocationsUsers(list);
	//THEN
	assertEquals(result.size(), 3);
    }

}
