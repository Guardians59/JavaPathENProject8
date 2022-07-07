package ms.rewardcentral.integration.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ms.rewardcentral.service.IRewardCentralService;

@SpringBootTest
@AutoConfigureMockMvc
public class RewardCentralControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    IRewardCentralService rewardCentralService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getRewardPointsIT() throws Exception {
	UUID idUser = UUID.randomUUID();
	UUID idAttraction = UUID.randomUUID();

	mockMvc.perform(
		MockMvcRequestBuilders.get("/getRewardPoints?attractionId=" + idAttraction + "&userId=" + idUser)
			.accept(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$.reward").exists())
		.andExpect(status().isOk());
    }

    /*@Test
    public void calculateRewardIT() throws Exception {
	// GIVEN
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
	list.add(new Attraction(idJackson, "Jackson Hole", "Jackson Hole", "WY", 40.817590D, -120.922009D));
	list.add(new Attraction(idMojave, "Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
	CalculateRewardModel model = new CalculateRewardModel();
	model.setAttractions(list);
	model.setUser(user);
	List<User> listUser = new ArrayList<>();
	listUser.add(user);
	HashMap<String, Object> map = new HashMap<>();
	map.put("attractions", list);
	map.put("user", listUser);
	Gson gson = new Gson();
	String json = gson.toJson(model);
	// THEN
	mockMvc.perform(MockMvcRequestBuilders.post("/calculateReward")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(jsonPath("$.jon").exists())
		.andExpect(status().isOk());

    }
    
    @Test
    public void calculateAllRewardIT() throws Exception {
	// GIVEN
	User user = new User(UUID.randomUUID(), "jon");
	User user2 = new User(UUID.randomUUID(), "eddy");
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
	List<User> listUser = new ArrayList<>();
	listUser.add(user);
	listUser.add(user2);
	List<Attraction> list = new ArrayList<>();
	UUID idDisney = UUID.randomUUID();
	UUID idJackson = UUID.randomUUID();
	UUID idMojave = UUID.randomUUID();
	list.add(new Attraction(idDisney, "Disneyland", "Anaheim", "CA", 34.817595D, -120.922009D));
	list.add(new Attraction(idJackson, "Jackson Hole", "Jackson Hole", "WY", 40.817590D, -120.922009D));
	list.add(new Attraction(idMojave, "Mojave National Preserve", "Kelso", "CA", 41.141689D, -115.510399D));
	CalculateAllRewardModel model = new CalculateAllRewardModel();
	model.setAttractions(list);
	model.setUsers(listUser);
	Gson gson = new Gson();
	String json = gson.toJson(model);
	// THEN
	mockMvc.perform(MockMvcRequestBuilders.post("/calculateAllReward")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(jsonPath("$.jon").exists())
		.andExpect(jsonPath("$.eddy").exists())
		.andExpect(status().isOk());

    }*/

}
