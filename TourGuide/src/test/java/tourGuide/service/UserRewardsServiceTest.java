package tourGuide.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;
import tourGuide.proxies.IMicroServiceGPSUtilProxy;
import tourGuide.proxies.IMicroServiceRewardCentralProxy;
import tourGuide.repositories.UserRepository;

@SpringBootTest
public class UserRewardsServiceTest {
   
    
    @Autowired
    UserRepository userRepository;
    
    @MockBean
    IMicroServiceRewardCentralProxy rewardCentralProxy;
    
    @MockBean
    IMicroServiceGPSUtilProxy gpsUtilProxy;
    
    @Autowired
    IUserRewardsService userRewardsService;
    
    
    @Test
    public void calculRewardTest() {
	//GIVEN
	User user = userRepository.getUser("internalUser2");
	Double latitude = user.getListVisitedLocations().get(0).location.latitude;
	Double longitude = user.getListVisitedLocations().get(0).location.longitude;
	List<Attraction> list = new ArrayList<>();
	UUID idDisney = UUID.randomUUID();
	UUID idJackson = UUID.randomUUID();
	UUID idMojave = UUID.randomUUID();
	UUID idJoshua = UUID.randomUUID();
	UUID idBuffalo = UUID.randomUUID();
	list.add(new Attraction(idDisney, "Disneyland", "Anaheim", "CA", latitude, longitude));
	list.add(new Attraction(idJackson, "Jackson Hole", "Jackson Hole", "WY", (latitude + 0.000005), longitude));
	list.add(new Attraction(idMojave,"Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
	list.add(new Attraction(idJoshua,"Joshua Tree National Park", "Joshua Tree National Park", "CA", 33.881866D, -115.90065D));
	list.add(new Attraction(idBuffalo,"Buffalo National River", "St Joe", "AR", 35.985512D, -92.757652D));
	HashMap<String, Object> mapId = new HashMap<>();
	mapId.put("attractionId", idDisney);
	mapId.put("userId", user.getUserId());
	HashMap<String, Object> mapIdSecond = new HashMap<>();
	mapIdSecond.put("attractionId", idJackson);
	mapIdSecond.put("userId", user.getUserId());
	HashMap<String, Integer> rewardDisney = new HashMap<>();
	rewardDisney.put("reward", 150);
	HashMap<String, Integer> rewardJackson = new HashMap<>();
	rewardJackson.put("reward", 50);
	HashMap<String, Object> result = new HashMap<>();
	//WHEN
	when(gpsUtilProxy.getAllAttractions()).thenReturn(list);
	when(rewardCentralProxy.getRewardPoints(mapId)).thenReturn(rewardDisney);
	when(rewardCentralProxy.getRewardPoints(mapIdSecond)).thenReturn(rewardJackson);
	result = userRewardsService.calculReward("internalUser2");
	//THEN
	assertEquals(result.containsKey("internalUser2"), true);
	
    }
    
    @Test
    public void calculRewardErrorUserTest() {
	//GIVEN
	List<Attraction> list = new ArrayList<>();
	UUID idDisney = UUID.randomUUID();
	UUID idJackson = UUID.randomUUID();
	UUID idMojave = UUID.randomUUID();
	UUID idJoshua = UUID.randomUUID();
	UUID idBuffalo = UUID.randomUUID();
	list.add(new Attraction(idDisney, "Disneyland", "Anaheim", "CA", 40.153788, 120.243566D));
	list.add(new Attraction(idJackson, "Jackson Hole", "Jackson Hole", "WY", 80.768775, -70.811325));
	list.add(new Attraction(idMojave,"Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
	list.add(new Attraction(idJoshua,"Joshua Tree National Park", "Joshua Tree National Park", "CA", 33.881866D, -115.90065D));
	list.add(new Attraction(idBuffalo,"Buffalo National River", "St Joe", "AR", 35.985512D, -92.757652D));
	HashMap<String, Object> result = new HashMap<>();
	//WHEN
	when(gpsUtilProxy.getAllAttractions()).thenReturn(list);
	result = userRewardsService.calculReward("internalUser101");
	//THEN
	assertEquals(result.isEmpty(), true);
	
    }
    
    @Test
    public void calculRewardErrorListAttractionTest() {
	//GIVEN
	User user = userRepository.getUser("internalUser1");
	List<Attraction> list = new ArrayList<>();
	UUID idDisney = UUID.randomUUID();
	UUID idJackson = UUID.randomUUID();
	HashMap<String, Object> mapId = new HashMap<>();
	mapId.put("attractionId", idDisney);
	mapId.put("userId", user.getUserId());
	HashMap<String, Object> mapIdSecond = new HashMap<>();
	mapIdSecond.put("attractionId", idJackson);
	mapIdSecond.put("userId", user.getUserId());
	HashMap<String, Integer> rewardDisney = new HashMap<>();
	rewardDisney.put("reward", 150);
	HashMap<String, Integer> rewardJackson = new HashMap<>();
	rewardJackson.put("reward", 50);
	HashMap<String, Object> result = new HashMap<>();
	//WHEN
	when(gpsUtilProxy.getAllAttractions()).thenReturn(list);
	when(rewardCentralProxy.getRewardPoints(mapId)).thenReturn(rewardDisney);
	when(rewardCentralProxy.getRewardPoints(mapIdSecond)).thenReturn(rewardJackson);
	result = userRewardsService.calculReward("internalUser1");
	//THEN
	assertEquals(result.isEmpty(), true);	
    }
    
    @Test
    public void calculRewardOneAlreadyExistTest() {
	//GIVEN
	User user = userRepository.getUser("internalUser26");
	Double latitude = user.getListVisitedLocations().get(0).location.latitude;
	Double longitude = user.getListVisitedLocations().get(0).location.longitude;
	List<Attraction> list = new ArrayList<>();
	UUID idDisney = UUID.randomUUID();
	UUID idJackson = UUID.randomUUID();
	UUID idMojave = UUID.randomUUID();
	UUID idJoshua = UUID.randomUUID();
	UUID idBuffalo = UUID.randomUUID();
	list.add(new Attraction(idJoshua,"Joshua Tree National Park", "Joshua Tree National Park", "CA", 33.881866D, -115.90065D));
	list.add(new Attraction(idDisney, "Disneyland", "Anaheim", "CA", latitude, longitude));
	list.add(new Attraction(idJackson, "Jackson Hole", "Jackson Hole", "WY", (latitude + 0.000005), longitude));
	list.add(new Attraction(idMojave,"Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
	list.add(new Attraction(idBuffalo,"Buffalo National River", "St Joe", "AR", 35.985512D, -92.757652D));
	UserReward userReward = new UserReward(user.getListVisitedLocations().get(0),list.get(2) , 10);
	user.addUserReward(userReward);
	HashMap<String, Object> mapId = new HashMap<>();
	mapId.put("attractionId", idDisney);
	mapId.put("userId", user.getUserId());
	HashMap<String, Integer> rewardDisney = new HashMap<>();
	rewardDisney.put("reward", 150);
	HashMap<String, Object> result = new HashMap<>();
	//WHEN
	when(gpsUtilProxy.getAllAttractions()).thenReturn(list);
	when(rewardCentralProxy.getRewardPoints(mapId)).thenReturn(rewardDisney);
	result = userRewardsService.calculReward("internalUser26");
	//THEN
	assertEquals(user.getUserRewards().size(), 2);
	
    }
    
    @Test
    public void calculAllRewardTest() {
	//GIVEN
	User user = userRepository.getUser("internalUser3");
	Double latitude = user.getListVisitedLocations().get(0).location.latitude;
	Double longitude = user.getListVisitedLocations().get(0).location.longitude;
	User user2 = userRepository.getUser("internalUser4");
	Double latitude2 = user2.getListVisitedLocations().get(0).location.latitude;
	Double longitude2 = user2.getListVisitedLocations().get(0).location.longitude;
	List<Attraction> list = new ArrayList<>();
	UUID idDisney = UUID.randomUUID();
	UUID idJackson = UUID.randomUUID();
	UUID idMojave = UUID.randomUUID();
	UUID idJoshua = UUID.randomUUID();
	UUID idBuffalo = UUID.randomUUID();
	list.add(new Attraction(idDisney, "Disneyland", "Anaheim", "CA", latitude, longitude));
	list.add(new Attraction(idJackson, "Jackson Hole", "Jackson Hole", "WY", (latitude + 0.000005), longitude));
	list.add(new Attraction(idMojave,"Mojave National Preserve", "Kelso", "CA", latitude2, longitude2));
	list.add(new Attraction(idJoshua,"Joshua Tree National Park", "Joshua Tree National Park", "CA", 33.881866D, -115.90065D));
	list.add(new Attraction(idBuffalo,"Buffalo National River", "St Joe", "AR", 35.985512D, -92.757652D));
	HashMap<String, Object> mapId = new HashMap<>();
	mapId.put("attractionId", idDisney);
	mapId.put("userId", user.getUserId());
	HashMap<String, Object> mapIdSecond = new HashMap<>();
	mapIdSecond.put("attractionId", idJackson);
	mapIdSecond.put("userId", user.getUserId());
	HashMap<String, Object> mapIdTree = new HashMap<>();
	mapIdTree.put("attractionId", idMojave);
	mapIdTree.put("userId", user2.getUserId());
	HashMap<String, Integer> rewardDisney = new HashMap<>();
	rewardDisney.put("reward", 150);
	HashMap<String, Integer> rewardJackson = new HashMap<>();
	rewardJackson.put("reward", 50);
	HashMap<String, Integer> rewardMojave = new HashMap<>();
	rewardMojave.put("reward", 30);
	HashMap<String, Object> result = new HashMap<>();
	//WHEN
	when(gpsUtilProxy.getAllAttractions()).thenReturn(list);
	when(rewardCentralProxy.getRewardPoints(mapId)).thenReturn(rewardDisney);
	when(rewardCentralProxy.getRewardPoints(mapIdSecond)).thenReturn(rewardJackson);
	when(rewardCentralProxy.getRewardPoints(mapIdTree)).thenReturn(rewardMojave);
	result = userRewardsService.calculateAllRewardsOfUsers();
	System.out.println(result);
	//THEN
	assertEquals(result.containsKey("internalUser3"), true);
	assertEquals(result.containsKey("internalUser4"), true);
	
    }
    
    @Test
    public void cumulativeRewardPointsTest() {
	User user = userRepository.getUser("internalUser30");
	Location location = new Location(40.153788, 120.243566D);
	VisitedLocation visitedLocation = new VisitedLocation();
	visitedLocation.setLocation(location);
	Attraction attraction = new Attraction(UUID.randomUUID(), "Buffalo National River", "St Joe", "AR", 35.985512D, -92.757652D);
	UserReward userReward = new UserReward(visitedLocation, attraction, 50);
	user.addUserReward(userReward);
	attraction = new Attraction (UUID.randomUUID(), "Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D);
	location = new Location(45.153788, 120.243566D);
	visitedLocation.setLocation(location);
	UserReward userReward2 = new UserReward(visitedLocation, attraction, 100);
	user.addUserReward(userReward2);
	int result = 0;
	//WHEN
	result = userRewardsService.cumulativeRewardPoints("internalUser30");
	//THEN
	assertEquals(result, 150);
    }
    
    @Test
    public void cumulativeZeroRewardPointsTest() {
	//GIVEN
	int result;
	//WHEN
	result = userRewardsService.cumulativeRewardPoints("internalUser8");
	//THEN
	assertEquals(result, 0);
    }

}
