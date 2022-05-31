package tourGuide.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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


import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;
import tourGuide.proxies.IMicroServiceGPSUtilProxy;
import tourGuide.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserLocationControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    @Autowired
    UserRepository userRepository;
    
    @MockBean
    IMicroServiceGPSUtilProxy microServiceGPSUtilProxyMock;
    
    @BeforeEach
    public void setUp() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void getUserLocationTest() throws Exception {
	//GIVEN
	User user = userRepository.getUser("internalUser20");
	VisitedLocation visitedLocation = new VisitedLocation();
	visitedLocation.setUserId(user.getUserId());
	Location location = new Location(34.817595D, -120.922008D);
	visitedLocation.setLocation(location);
	//WHEN
	when(microServiceGPSUtilProxyMock.getLocation(user.getUserId())).thenReturn(visitedLocation);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getLocation/internalUser20")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.location.latitude").value(34.817595D))
		.andExpect(jsonPath("$.location.longitude").value(-120.922008D))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void getUserLocationErrorTest() throws Exception {
	//GIVEN
	User user = userRepository.getUser("internalUser21");
	VisitedLocation visitedLocation = new VisitedLocation();
	//WHEN
	when(microServiceGPSUtilProxyMock.getLocation(user.getUserId())).thenReturn(visitedLocation);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getLocation/internalUser21")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
    }

}
