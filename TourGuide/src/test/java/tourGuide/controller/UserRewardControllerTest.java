package tourGuide.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import tourGuide.model.FeignRewardPointsModel;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;
import tourGuide.proxies.IMicroServiceGPSUtilProxy;
import tourGuide.proxies.IMicroServiceRewardCentralProxy;
import tourGuide.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRewardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    UserRepository userRepository;

    @MockBean
    IMicroServiceGPSUtilProxy microServiceGPSUtilProxyMock;

    @MockBean
    IMicroServiceRewardCentralProxy microServiceRewardCentralProxyMock;

    @BeforeEach
    public void setUp() {
	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void calculateRewardPointTest() throws Exception {
	// GIVEN
	User user = userRepository.getUser("internalUser10");
	Double latitude = user.getListVisitedLocations().get(0).location.latitude;
	Double longitude = user.getListVisitedLocations().get(0).location.longitude;
	List<Attraction> list = new ArrayList<>();
	UUID idDisney = UUID.randomUUID();
	UUID idJackson = UUID.randomUUID();
	UUID idMojave = UUID.randomUUID();
	UUID idJoshua = UUID.randomUUID();
	list.add(new Attraction(idDisney, "Disneyland", "Anaheim", "CA", latitude, longitude));
	list.add(new Attraction(idJackson, "Jackson Hole", "Jackson Hole", "WY", (latitude + 0.000005), longitude));
	list.add(new Attraction(idMojave, "Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
	list.add(new Attraction(idJoshua, "Joshua Tree National Park", "Joshua Tree National Park", "CA", 33.881866D,
		-115.90065D));
	FeignRewardPointsModel feignReward = new FeignRewardPointsModel(idDisney, user.getUserId());
	FeignRewardPointsModel feignReward2 = new FeignRewardPointsModel(idJackson, user.getUserId());
	HashMap<String, Integer> rewardDisney = new HashMap<>();
	rewardDisney.put("reward", 150);
	HashMap<String, Integer> rewardJackson = new HashMap<>();
	rewardJackson.put("reward", 50);
	// WHEN
	when(microServiceGPSUtilProxyMock.getAllAttractions()).thenReturn(list);
	when(microServiceRewardCentralProxyMock.getRewardPoints(feignReward)).thenReturn(rewardDisney);
	when(microServiceRewardCentralProxyMock.getRewardPoints(feignReward2)).thenReturn(rewardJackson);
	// THEN
	mockMvc.perform(
		MockMvcRequestBuilders.get("/calculateRewardPoint/internalUser10")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.Disneyland").exists())
		.andExpect(jsonPath("$.Disneyland").value(150))
		.andExpect(jsonPath("$.['Jackson Hole']").exists())
		.andExpect(jsonPath("$.['Jackson Hole']").value(50))
		.andExpect(jsonPath("$.['Mojave National Preserve']").doesNotExist())
		.andExpect(jsonPath("$.['Joshua Tree National Park']").doesNotExist())
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void calculRewardPointErrorUserTest() throws Exception {
	// GIVEN
	List<Attraction> list = new ArrayList<>();
	UUID idDisney = UUID.randomUUID();
	UUID idJackson = UUID.randomUUID();
	list.add(new Attraction(idDisney, "Disneyland", "Anaheim", "CA", 40.153788, 120.243566D));
	list.add(new Attraction(idJackson, "Jackson Hole", "Jackson Hole", "WY", 80.768775, -70.811325));
	// WHEN
	when(microServiceGPSUtilProxyMock.getAllAttractions()).thenReturn(list);
	// THEN
	mockMvc.perform(
		MockMvcRequestBuilders.get("/calculateRewardPoint/internalUser110")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$").doesNotExist())
		.andExpect(status().isNotFound());
    }
    
    @Test
    public void calculRewardPointErrorListAttractionTest() throws Exception {
	//GIVEN
	User user = userRepository.getUser("internalUser20");
	List<Attraction> list = new ArrayList<>();
	UUID idDisney = UUID.randomUUID();
	UUID idJackson = UUID.randomUUID();
	FeignRewardPointsModel feignReward = new FeignRewardPointsModel(idDisney, user.getUserId());
	FeignRewardPointsModel feignReward2 = new FeignRewardPointsModel(idJackson, user.getUserId());
	HashMap<String, Integer> rewardDisney = new HashMap<>();
	rewardDisney.put("reward", 150);
	HashMap<String, Integer> rewardJackson = new HashMap<>();
	rewardJackson.put("reward", 50);
	//WHEN
	when(microServiceGPSUtilProxyMock.getAllAttractions()).thenReturn(list);
	when(microServiceRewardCentralProxyMock.getRewardPoints(feignReward)).thenReturn(rewardDisney);
	when(microServiceRewardCentralProxyMock.getRewardPoints(feignReward2)).thenReturn(rewardJackson);
	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.get("/calculateRewardPoint/internalUser20")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$").doesNotExist())
		.andExpect(status().isNotFound());
   }
    
    @Test
    public void cumulativeRewardPointsTest() throws Exception {
	//GIVEN
	User user = userRepository.getUser("internalUser25");
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
	//THEN
	mockMvc.perform(
		MockMvcRequestBuilders.get("/cumulativeRewardPoint/internalUser25")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$").value(150))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());	
    }
    
    @Test
    public void cumulativeZeroRewardPointsTest() throws Exception {
	mockMvc.perform(
		MockMvcRequestBuilders.get("/cumulativeRewardPoint/internalUser12")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$").value(0))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void cumulativeRewardPointsErrorUserTest() throws Exception {
	mockMvc.perform(
		MockMvcRequestBuilders.get("/cumulativeRewardPoint/internalUser120")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
    }
}
