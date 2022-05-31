package tourGuide.integration.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import tourGuide.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserLocationControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    
     @Autowired 
     UserRepository userRepository;
     

    @BeforeEach
    public void setUp() {
	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getCurrentUserLocationIT() throws Exception {
	String id = userRepository.getUser("internalUser1").getUserId().toString();
	
	mockMvc.perform(
		MockMvcRequestBuilders.get("/getCurrentLocation/internalUser1")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$."+id).exists())
			.andExpect(jsonPath("$."+id+".latitude").exists())
			.andExpect(jsonPath("$."+id+".longitude").exists())
			.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void getCurrentUserLocationErrorIT() throws Exception {
	mockMvc.perform(
		MockMvcRequestBuilders.get("/getCurrentLocation/internalUser101")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$").doesNotExist())
			.andExpect(status().isNotFound());
    }
    
    @Test
    public void getAllCurrentUsersLocationsIT() throws Exception {
	String id = userRepository.getUser("internalUser1").getUserId().toString();
	String id2 = userRepository.getUser("internalUser2").getUserId().toString();
	
	mockMvc.perform(
		MockMvcRequestBuilders.get("/getAllCurrentLocations/internalUser1,internalUser2")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$."+id).exists())
			.andExpect(jsonPath("$."+id2).exists())
			.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print());

}
    
    @Test
    public void getAllCurrentUsersLocationsErrorIT() throws Exception {
	mockMvc.perform(
		MockMvcRequestBuilders.get("/getAllCurrentLocations/internalUser101,internalUser200")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$").doesNotExist())
			.andExpect(status().isNotFound());

}
    
    @Test
    public void getUsersLocationsHistoricalIT() throws Exception {
	mockMvc.perform(
		MockMvcRequestBuilders.get("/getUsersLocationsHistorical/internalUser1,internalUser3")
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.internalUser1").exists())
		.andExpect(jsonPath("$.internalUser3").exists())
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void getUsersLocationsHistoricalErrorIT() throws Exception {
	mockMvc.perform(
		MockMvcRequestBuilders.get("/getUsersLocationsHistorical/internalUser102,internalUser300")
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.internalUser102").doesNotExist())
		.andExpect(jsonPath("$.internalUser300").doesNotExist())
		.andExpect(status().isNotFound());
    }
}