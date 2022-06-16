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
import tourGuide.model.Provider;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;
import tourGuide.proxies.IMicroServiceTripPricerProxy;
import tourGuide.repositories.UserRepository;

@SpringBootTest
public class TripPricerServiceTest {
    
    @Autowired
    ITripPricerService tripPricerService;
    
    @Autowired
    UserRepository userRepository;
    
    @MockBean
    IMicroServiceTripPricerProxy microServiceTripPricerProxyMock;
    
    @Test
    public void getTripDealsUserTest() {
	//GIVEN
	User user = userRepository.getUser("internalUser50");
	List<Provider> resultProxy = new ArrayList<>();
	HashMap<String, Object> mapParams = new HashMap<>();
	mapParams.put("userId", user.getUserId());
	mapParams.put("numberOfAdult", 1);
	mapParams.put("numberOfChildren", 0);
	mapParams.put("duration", 1);
	mapParams.put("cumulRewardPoint", 100);
	mapParams.put("tripPricerApiKey", "test-server-api-key");
	Provider provider = new Provider();
	provider.setName("Holiday Travels");
	provider.setPrice(200);
	UUID uuid = UUID.randomUUID();
	provider.setTripId(uuid);
	resultProxy.add(provider);
	VisitedLocation visitedLocation = new VisitedLocation();
	visitedLocation.setUserId(user.getUserId());
	Location location = new Location(34.817595D, -120.922008D);
	visitedLocation.setLocation(location);
	Attraction attraction = new Attraction(UUID.randomUUID(), "Disneyland", "Anaheim", "CA", 33.817595D, -117.922008D);
	UserReward userReward = new UserReward(visitedLocation, attraction, 100);
	user.addUserReward(userReward);
	List<Provider> result = new ArrayList<>();
	//WHEN
	when(microServiceTripPricerProxyMock.getTripDeals(mapParams)).thenReturn(resultProxy);
	result = tripPricerService.getTripDealsUser("internalUser50", "test-server-api-key");
	//THEN
	assertEquals(result.isEmpty(), false);
    }
    
    @Test
    public void getTripDealsUserErrorTest() {
	//GIVEN
	List<Provider> result = new ArrayList<>();
	//WHEN
	result = tripPricerService.getTripDealsUser("internalUser500", "test-server-api-key");
	//THEN
	assertEquals(result.isEmpty(), true);
    }
    
    @Test
    public void getTripDealsUserNotAvailableTest() {
	//GIVEN
	User user = userRepository.getUser("internalUser60");
	List<Provider> resultProxy = new ArrayList<>();
	HashMap<String, Object> mapParams = new HashMap<>();
	mapParams.put("userId", user.getUserId());
	mapParams.put("numberOfAdult", 1);
	mapParams.put("numberOfChildren", 0);
	mapParams.put("duration", 1);
	mapParams.put("cumulRewardPoint", 100);
	mapParams.put("tripPricerApiKey", "test-server-api-key");
	VisitedLocation visitedLocation = new VisitedLocation();
	visitedLocation.setUserId(user.getUserId());
	Location location = new Location(34.817595D, -120.922008D);
	visitedLocation.setLocation(location);
	Attraction attraction = new Attraction(UUID.randomUUID(), "Disneyland", "Anaheim", "CA", 33.817595D, -117.922008D);
	UserReward userReward = new UserReward(visitedLocation, attraction, 100);
	user.addUserReward(userReward);
	List<Provider> result = new ArrayList<>();
	//WHEN
	when(microServiceTripPricerProxyMock.getTripDeals(mapParams)).thenReturn(resultProxy);
	result = tripPricerService.getTripDealsUser("internalUser60", "test-server-api-key");
	//THEN
	assertEquals(result.isEmpty(), true);
    }

}
