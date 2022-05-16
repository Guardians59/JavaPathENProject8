package ms.gpsutil.integration.controller;

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

import ms.gpsutil.model.User;
import ms.gpsutil.service.IGPSUtilService;

@SpringBootTest
@AutoConfigureMockMvc
public class GPSUtilControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    IGPSUtilService gpsUtilServiceMock;


    @BeforeEach
    public void setUp() {
	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getLocationIT() throws Exception {
	User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

	mockMvc.perform(MockMvcRequestBuilders.get("/getLocation?id="+user.getUserId())
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.location.longitude").isNotEmpty())
		.andExpect(jsonPath("$.location.latitude").isNotEmpty())
		.andExpect(jsonPath("$.timeVisited").isNotEmpty())
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void getNearByAttractionIT() throws Exception {
	User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
	
	mockMvc.perform(MockMvcRequestBuilders.get("/getNearByAttraction?id="+user.getUserId())
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.[4]").exists())
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void getAllAttractionsIT() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders.get("/getAllAttractions")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.[0]").exists())
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }
    
}
