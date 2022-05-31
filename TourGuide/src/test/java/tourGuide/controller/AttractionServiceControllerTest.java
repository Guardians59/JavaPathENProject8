package tourGuide.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import tourGuide.model.User;
import tourGuide.proxies.IMicroServiceGPSUtilProxy;
import tourGuide.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AttractionServiceControllerTest {
    
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
    public void getNearByAttractionsTest() throws Exception {
	//GIVEN
	User user = userRepository.getUser("internalUser20");
	List<Attraction> list = new ArrayList<>();
	list.add(new Attraction("Disneyland", "Anaheim", "CA", 33.817595D, -117.922008D));
	list.add(new Attraction("Jackson Hole", "Jackson Hole", "WY", 43.582767D, -110.821999D));
	list.add(new Attraction("Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
	list.add(new Attraction("Joshua Tree National Park", "Joshua Tree National Park", "CA", 33.881866D, -115.90065D));
	list.add(new Attraction("Buffalo National River", "St Joe", "AR", 35.985512D, -92.757652D));
	//WHEN
	when(microServiceGPSUtilProxyMock.getNearByAttraction(user.getUserId())).thenReturn(list);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getNearByAttractions/internalUser20")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.[0]").exists())
		.andExpect(jsonPath("$.[4]").exists())
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void getNearByAttractionsErrorTest() throws Exception {
	//GIVEN
	User user = userRepository.getUser("internalUser20");
	List<Attraction> list = new ArrayList<>();
	//WHEN
	when(microServiceGPSUtilProxyMock.getNearByAttraction(user.getUserId())).thenReturn(list);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getNearByAttractions/internalUser20")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.[0]").doesNotExist())
		.andExpect(status().isNotFound());
    }
    
    @Test
    public void getAllAttractionsTest() throws Exception {
	//GIVEN
	List<Attraction> list = new ArrayList<>();
	list.add(new Attraction("Disneyland", "Anaheim", "CA", 33.817595D, -117.922008D));
	list.add(new Attraction("Jackson Hole", "Jackson Hole", "WY", 43.582767D, -110.821999D));
	list.add(new Attraction("Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
	list.add(new Attraction("Joshua Tree National Park", "Joshua Tree National Park", "CA", 33.881866D, -115.90065D));
	list.add(new Attraction("Buffalo National River", "St Joe", "AR", 35.985512D, -92.757652D));
	list.add(new Attraction("Buffalo", "St Joe", "AR", 38.985512D, -95.757652D));
	//WHEN
	when(microServiceGPSUtilProxyMock.getAllAttractions()).thenReturn(list);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getAllAttractions")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.[0]").exists())
		.andExpect(jsonPath("$.[5]").exists())
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void getAllAttractionsErrorTest() throws Exception {
	//GIVEN
	List<Attraction> list = new ArrayList<>();
	//WHEN
	when(microServiceGPSUtilProxyMock.getAllAttractions()).thenReturn(list);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getAllAttractions")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.[0]").doesNotExist())
		.andExpect(status().isNotFound());
    }

}
