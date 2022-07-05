package ms.rewardcentral.controller;

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

import com.google.gson.Gson;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import ms.rewardcentral.model.Attraction;
import ms.rewardcentral.model.Location;
import ms.rewardcentral.model.User;
import ms.rewardcentral.model.VisitedLocation;
import ms.rewardcentral.service.IRewardCentralService;

@SpringBootTest
@AutoConfigureMockMvc
public class RewardCentralControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @MockBean
    IRewardCentralService rewardCentralService;
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    @BeforeEach
    public void setUp() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void getRewardPointsTest() throws Exception {
	//GIVEN
	UUID idUser = UUID.randomUUID();
	UUID idAttraction = UUID.randomUUID();
	HashMap<String, Integer> result = new HashMap<>();
	result.put("reward", 100);
	//WHEN
	when(rewardCentralService.getRewardPoints(idAttraction, idUser)).thenReturn(result);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getRewardPoints?attractionId="+idAttraction+"&userId="+idUser)
	.accept(MediaType.APPLICATION_JSON))
	.andDo(MockMvcResultHandlers.print())
	.andExpect(jsonPath("$.reward").value(100))
	.andExpect(status().isOk());
		
    }
    
    @Test
    public void getRewardPointsErrorTest() throws Exception {
	//GIVEN
	UUID idUser = UUID.randomUUID();
	UUID idAttraction = UUID.randomUUID();
	HashMap<String, Integer> result = new HashMap<>();
	//WHEN
	when(rewardCentralService.getRewardPoints(idAttraction, idUser)).thenReturn(result);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getRewardPoints?attractionId="+idAttraction+"&userId="+idUser)
	.accept(MediaType.APPLICATION_JSON))
	.andDo(MockMvcResultHandlers.print())
	.andExpect(jsonPath("$.reward").doesNotExist())
	.andExpect(status().isNotFound());
		
    }
    
    @Test
    public void calculateRewardTest() throws Exception {
	//GIVEN
	User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
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
	list.add(new Attraction(idMojave,"Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
	HashMap<String, Integer> result = new HashMap<>();
	result.put("DisneyLand", 150);
	Gson gson = new Gson();
	String json = gson.toJson(list);
	
	String jsonUser = gson.toJson(user);
	
	
	//WHEN
	when(rewardCentralService.calculateReward(list, user)).thenReturn(result);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/calculateReward")
		.param("attractions", json)
		.param("user", jsonUser)
		.accept(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(jsonPath("$.Disneyland").exists())
		.andExpect(status().isOk());
    }
}
