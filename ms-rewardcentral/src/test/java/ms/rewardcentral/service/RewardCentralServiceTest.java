package ms.rewardcentral.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RewardCentralServiceTest {
    
    @Autowired
    IRewardCentralService rewardCentralService;
    
    @Test
    public void getRewardPointTest() {
	//GIVEN
	HashMap<String, Integer> firstReward = new HashMap<>();
	//WHEN
	firstReward = rewardCentralService.getRewardPoints(UUID.randomUUID(), UUID.randomUUID());
	//THEN
	assertEquals(firstReward.get("reward").intValue() > 0, true);
    }
    
    @Test
    public void getRewardPointErrorTest() {
	//GIVEN
	HashMap<String, Integer> firstReward = new HashMap<>();
	//WHEN
	firstReward = rewardCentralService.getRewardPoints(null, UUID.randomUUID());
	//THEN
	assertEquals(firstReward.containsKey("reward"), false);
    }
    
    /*@Test
    public void calculateRewardTest() {
	//GIVEN
	User user = new User(UUID.randomUUID(), "jon");
	VisitedLocation visitedLocation = new VisitedLocation();
	Location location = new Location(34.817595D, -120.922008D);
	visitedLocation.setLocation(location);
	visitedLocation.setUserId(user.getUserId());
	user.addToListVisitedLocations(visitedLocation);
	List<Attraction> list = new ArrayList<>();
	UUID idDisney = UUID.randomUUID();
	UUID idJackson = UUID.randomUUID();
	UUID idMojave = UUID.randomUUID();
	list.add(new Attraction(idDisney, "Disneyland", "Anaheim", "CA", 34.817595D, -120.922009D));
	list.add(new Attraction(idJackson, "Jackson Hole", "Jackson Hole", "WY", 34.817590D, -120.922009D));
	list.add(new Attraction(idMojave,"Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
	HashMap<String, Object> result = new HashMap<>();
	//WHEN
	result = rewardCentralService.calculateReward(list, user);
	//THEN
	assertEquals(result.isEmpty(), false);
    }
    
    @Test
    public void calculateRewardErrorUserTest() {
	//GIVEN
	User user = new User();
	VisitedLocation visitedLocation = new VisitedLocation();
	Location location = new Location(34.817595D, -120.922008D);
	visitedLocation.setLocation(location);
	visitedLocation.setUserId(user.getUserId());
	user.addToListVisitedLocations(visitedLocation);
	List<Attraction> list = new ArrayList<>();
	UUID idDisney = UUID.randomUUID();
	UUID idJackson = UUID.randomUUID();
	UUID idMojave = UUID.randomUUID();
	list.add(new Attraction(idDisney, "Disneyland", "Anaheim", "CA", 34.817595D, -120.922009D));
	list.add(new Attraction(idJackson, "Jackson Hole", "Jackson Hole", "WY", 34.817590D, -120.922009D));
	list.add(new Attraction(idMojave,"Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
	HashMap<String, Object> result = new HashMap<>();
	//WHEN
	result = rewardCentralService.calculateReward(list, user);
	//THEN
	assertEquals(result.isEmpty(), true);
    }
    
    @Test
    public void calculateRewardErrorLocationTest() {
	//GIVEN
	User user = new User(UUID.randomUUID(), "jon");
	List<Attraction> list = new ArrayList<>();
	UUID idDisney = UUID.randomUUID();
	UUID idJackson = UUID.randomUUID();
	UUID idMojave = UUID.randomUUID();
	list.add(new Attraction(idDisney, "Disneyland", "Anaheim", "CA", 34.817595D, -120.922009D));
	list.add(new Attraction(idJackson, "Jackson Hole", "Jackson Hole", "WY", 34.817590D, -120.922009D));
	list.add(new Attraction(idMojave,"Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
	HashMap<String, Object> result = new HashMap<>();
	//WHEN
	result = rewardCentralService.calculateReward(list, user);
	//THEN
	assertEquals(result.isEmpty(), true);
    }
    
    @Test
    public void calculateRewardErrorListAttractionTest() {
	//GIVEN
	User user = new User(UUID.randomUUID(), "jon");
	VisitedLocation visitedLocation = new VisitedLocation();
	Location location = new Location(34.817595D, -120.922008D);
	visitedLocation.setLocation(location);
	visitedLocation.setUserId(user.getUserId());
	user.addToListVisitedLocations(visitedLocation);
	List<Attraction> list = new ArrayList<>();
	HashMap<String, Object> result = new HashMap<>();
	//WHEN
	result = rewardCentralService.calculateReward(list, user);
	//THEN
	assertEquals(result.isEmpty(), true);
    }
    
    @Test
    public void calculateAllRewardsOfUsersTest() {
	//GIVEN
	User user = new User(UUID.randomUUID(), "jon");
	User user2 = new User(UUID.randomUUID(), "eddy");
	User user3 = new User();
	User user4 = new User(UUID.randomUUID(), "jack");
	User user5 = new User(UUID.randomUUID(), "tom");
	VisitedLocation visitedLocation = new VisitedLocation();
	Location location = new Location(34.817595D, -120.922008D);
	visitedLocation.setLocation(location);
	visitedLocation.setUserId(user.getUserId());
	user.addToListVisitedLocations(visitedLocation);
	VisitedLocation visitedLocation2 = new VisitedLocation();
	Location location2 = new Location(41.141689D, -115.510396D);
	visitedLocation2.setLocation(location2);
	visitedLocation2.setUserId(user2.getUserId());
	user2.addToListVisitedLocations(visitedLocation2);
	VisitedLocation visitedLocation3 = new VisitedLocation();
	Location location3 = new Location(50.817595D, -120.922008D);
	visitedLocation3.setLocation(location3);
	visitedLocation3.setUserId(user5.getUserId());
	user5.addToListVisitedLocations(visitedLocation3);
	List<User> listUsers = new ArrayList<>();
	listUsers.add(user);
	listUsers.add(user2);
	listUsers.add(user3);
	listUsers.add(user4);
	listUsers.add(user5);
	List<Attraction> list = new ArrayList<>();
	UUID idDisney = UUID.randomUUID();
	UUID idJackson = UUID.randomUUID();
	UUID idMojave = UUID.randomUUID();
	list.add(new Attraction(idDisney, "Disneyland", "Anaheim", "CA", 34.817595D, -120.922009D));
	list.add(new Attraction(idJackson, "Jackson Hole", "Jackson Hole", "WY", 34.817590D, -120.922009D));
	list.add(new Attraction(idMojave,"Mojave National Preserve", "Kelso", "CA", 41.141689D, -115.510399D));
	HashMap<String, Object> result = new HashMap<>();
	//WHEN
	result = rewardCentralService.calculateAllRewardsOfUsers(list, listUsers);
	//THEN
	assertEquals(result.containsKey("jon"), true);
	assertEquals(result.containsKey("eddy"), true);
	assertEquals(result.containsKey("jack"), false);
	assertEquals(result.containsKey("tom"), false);
    }
    
    @Test
    public void calculateAllRewardsOfUsersErrorTest() {
	//GIVEN
	List<User> listUsers = new ArrayList<>();
	List<Attraction> list = new ArrayList<>();
	UUID idDisney = UUID.randomUUID();
	UUID idJackson = UUID.randomUUID();
	UUID idMojave = UUID.randomUUID();
	list.add(new Attraction(idDisney, "Disneyland", "Anaheim", "CA", 34.817595D, -120.922009D));
	list.add(new Attraction(idJackson, "Jackson Hole", "Jackson Hole", "WY", 34.817590D, -120.922009D));
	list.add(new Attraction(idMojave,"Mojave National Preserve", "Kelso", "CA", 41.141689D, -115.510399D));
	HashMap<String, Object> result = new HashMap<>();
	//WHEN
	result = rewardCentralService.calculateAllRewardsOfUsers(list, listUsers);
	//THEN
	assertEquals(result.isEmpty(), true);
    }*/

}
