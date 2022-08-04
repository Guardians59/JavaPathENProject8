package tourGuide.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import tourGuide.model.VisitedLocation;
import tourGuide.proxies.IMicroServiceGPSUtilProxy;
import tourGuide.repositories.UserRepository;

@SpringBootTest
public class UserLocationServiceTest {
    
    @Autowired
    IUserLocationService userLocationService;
    
    @Autowired
    UserRepository userRepository;
    
    @MockBean
    IMicroServiceGPSUtilProxy microServiceGPSUtilProxyMock;
    
    @Test
    public void getCurrentUserLocationTest() {
	//GIVEN
	HashMap<Object, Object> result = new HashMap<>();
	UUID id = userRepository.getUser("internalUser1").getUserId();
	//WHEN
	result = userLocationService.getCurrentUserLocation("internalUser1");
	//THEN
	assertEquals(result.isEmpty(), false);
	assertEquals(result.containsKey(id), true);
    }
    
    @Test
    public void getCurrentUserLocationErrorTest() {
	//GIVEN
	HashMap<Object, Object> result = new HashMap<>();
	//WHEN
	result = userLocationService.getCurrentUserLocation("internalUser101000");
	//THEN
	assertEquals(result.isEmpty(), true);
    }
    
    @Test
    public void getCurrentLocationsOfUsersTest() {
	//GIVEN
	HashMap<Object, Object> result = new HashMap<>();
	List<String> userNames = new ArrayList<>();
	userNames.add("internalUser2");
	userNames.add("internalUser3");
	userNames.add("internalUser4");
	UUID id = userRepository.getUser("internalUser2").getUserId();
	UUID id2 = userRepository.getUser("internalUser3").getUserId();
	UUID id3 = userRepository.getUser("internalUser4").getUserId();
	//WHEN
	result = userLocationService.getCurrentLocationOfUsers(userNames);
	//THEN
	assertEquals(result.isEmpty(), false);
	assertEquals(result.containsKey(id), true);
	assertEquals(result.containsKey(id2), true);
	assertEquals(result.containsKey(id3), true);
    }
    
    @Test
    public void getCurrentLocationsOfUsersOneUserValidTest() {
	//GIVEN
	HashMap<Object, Object> result = new HashMap<>();
	List<String> userNames = new ArrayList<>();
	userNames.add("internalUser2");
	userNames.add("internalUser300000");
	userNames.add("internalUser400000");
	UUID id = userRepository.getUser("internalUser2").getUserId();
	//WHEN
	result = userLocationService.getCurrentLocationOfUsers(userNames);
	//THEN
	assertEquals(result.isEmpty(), false);
	assertEquals(result.containsKey(id), true);
	assertEquals(result.size(), 1);
    }
    
    @Test
    public void getCurrentLocationsOfUsersErrorTest() {
	//GIVEN
	HashMap<Object, Object> result = new HashMap<>();
	List<String> userNames = new ArrayList<>();
	userNames.add("internalUser200000");
	userNames.add("internalUser300000");
	//WHEN
	result = userLocationService.getCurrentLocationOfUsers(userNames);
	//THEN
	assertEquals(result.isEmpty(), true);
    }
    
    @Test
    public void getAllCurrentLocationsTest() {
	//GIVEN
	HashMap<Object, Object> result = new HashMap<>();
	UUID id = userRepository.getUser("internalUser60").getUserId();
	UUID id2 = userRepository.getUser("internalUser61").getUserId();
	UUID id3 = userRepository.getUser("internalUser40").getUserId();
	//WHEN
	result = userLocationService.getAllCurrentLocations();
	//THEN
	assertEquals(result.isEmpty(), false);
	assertEquals(result.containsKey(id), true);
	assertEquals(result.containsKey(id2), true);
	assertEquals(result.containsKey(id3), true);
    }
    
    @Test
    public void getAllLocationsHistoricalTest() {
	//GIVEN
	HashMap<String, Object> result = new HashMap<>();
	List<String> userNames = new ArrayList<>();
	userNames.add("internalUser5");
	userNames.add("internalUser6");
	userNames.add("internalUser7");
	//WHEN
	result = userLocationService.getUsersLocationsHistorical(userNames);
	//THEN
	assertEquals(result.isEmpty(), false);
	assertEquals(result.containsKey("internalUser5"), true);
	assertEquals(result.containsKey("internalUser6"), true);
	assertEquals(result.containsKey("internalUser7"), true);
    }
    
    @Test
    public void getAllLocationsHistoricalOneUserValidTest() {
	//GIVEN
	HashMap<String, Object> result = new HashMap<>();
	List<String> userNames = new ArrayList<>();
	userNames.add("internalUser5");
	userNames.add("internalUser600000");
	userNames.add("internalUser700000");
	//WHEN
	result = userLocationService.getUsersLocationsHistorical(userNames);
	//THEN
	assertEquals(result.isEmpty(), false);
	assertEquals(result.containsKey("internalUser5"), true);
	assertEquals(result.containsKey("internalUser600000"), false);
	assertEquals(result.containsKey("internalUser700000"), false);
    }
    
    @Test
    public void getAllLocationsHistoricalErrorTest() {
	//GIVEN
	HashMap<String, Object> result = new HashMap<>();
	List<String> userNames = new ArrayList<>();
	userNames.add("internalUser500000");
	userNames.add("internalUser600000");
	//WHEN
	result = userLocationService.getUsersLocationsHistorical(userNames);
	//THEN
	assertEquals(result.isEmpty(), true);
    }
    
    @Test
    public void getLocationErrorUsernameTest() {
	//GIVEN
	VisitedLocation result = new VisitedLocation();
	//WHEN
	result = userLocationService.getUserLocation("john");
	//THEN
	assertEquals(result.location, null);
    }

}
