package tourGuide.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.Provider;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;
import tourGuide.proxies.IMicroServiceTripPricerProxy;
import tourGuide.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class TripPricerControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    @Autowired
    UserRepository userRepository;
    
    @MockBean
    IMicroServiceTripPricerProxy tripPricerProxyMock;
    
    @BeforeEach
    public void setUp() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void getTripDealsUserTest() throws Exception {
	//GIVEN
	User user = userRepository.getUser("internalUser52");
	List<Provider> result = new ArrayList<>();
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
	result.add(provider);
	VisitedLocation visitedLocation = new VisitedLocation();
	visitedLocation.setUserId(user.getUserId());
	Location location = new Location(34.817595D, -120.922008D);
	visitedLocation.setLocation(location);
	Attraction attraction = new Attraction(UUID.randomUUID(), "Disneyland", "Anaheim", "CA", 33.817595D, -117.922008D);
	UserReward userReward = new UserReward(visitedLocation, attraction, 100);
	user.addUserReward(userReward);
	//WHEN
	when(tripPricerProxyMock.getTripDeals(mapParams)).thenReturn(result);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getTripDeals/internalUser52?apiKey=test-server-api-key")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.[0].name").value("Holiday Travels"))
		.andExpect(jsonPath("$.[0].price").value(200))
		.andExpect(jsonPath("$.[0].tripId").value(uuid.toString()))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void getTripDealsUserNotAvailableTest() throws Exception {
	//GIVEN
	User user = userRepository.getUser("internalUser53");
	List<Provider> result = new ArrayList<>();
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
	//WHEN
	when(tripPricerProxyMock.getTripDeals(mapParams)).thenReturn(result);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getTripDeals/internalUser53?apiKey=test-server-api-key")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.[0].name").doesNotExist())
		.andExpect(status().isNotFound());
    }

}
