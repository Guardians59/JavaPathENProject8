package ms.gpsutil.controller;

import java.util.ArrayList;
import java.util.Date;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import ms.gpsutil.model.Attraction;
import ms.gpsutil.model.Location;
import ms.gpsutil.model.User;
import ms.gpsutil.model.VisitedLocation;
import ms.gpsutil.service.IGPSUtilService;

@SpringBootTest
@AutoConfigureMockMvc
public class GPSUtilControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    @MockBean
    IGPSUtilService gpsUtilServiceMock;
    
    @MockBean
    User userMock;
    
    @BeforeEach
    public void setUp() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void getLocationTest() throws Exception {
	//GIVEN
	when(userMock.getUserId()).thenReturn(UUID.randomUUID());
	Location location = new Location(34.817595D, -120.922008D);
	Date date = new Date();
	VisitedLocation visitedLocation = new VisitedLocation(userMock.getUserId(), location, date);
	//WHEN
	when(gpsUtilServiceMock.getUserLocation(userMock.getUserId())).thenReturn(visitedLocation);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getLocation?id="+userMock.getUserId())
	.accept(MediaType.APPLICATION_JSON))
	.andExpect(jsonPath("$.location.longitude").value(-120.922008D))
	.andExpect(jsonPath("$.location.latitude").value(34.817595D))
	.andExpect(status().isOk())
	.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void getLocationErrorTest() throws Exception {
	//GIVEN
	when(userMock.getUserId()).thenReturn(UUID.randomUUID());
	//WHEN
	when(gpsUtilServiceMock.getUserLocation(userMock.getUserId())).thenReturn(null);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getLocation?id="+userMock.getUserId())
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
    }
    
    @Test
    public void getNearByAttractionTest() throws Exception {
	//GIVEN
	when(userMock.getUserId()).thenReturn(UUID.randomUUID());
	Location location = new Location(34.817595D, -120.922008D);
	VisitedLocation visitedLocation = new VisitedLocation(userMock.getUserId(), location, null);
	List<Attraction> list = new ArrayList<>();
	list.add(new Attraction("Disneyland", "Anaheim", "CA", 33.817595D, -117.922008D));
	list.add(new Attraction("Jackson Hole", "Jackson Hole", "WY", 43.582767D, -110.821999D));
	list.add(new Attraction("Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
	list.add(new Attraction("Joshua Tree National Park", "Joshua Tree National Park", "CA", 33.881866D, -115.90065D));
	list.add(new Attraction("Buffalo National River", "St Joe", "AR", 35.985512D, -92.757652D));
	//WHEN
	when(userMock.getLastVisitedLocation()).thenReturn(visitedLocation);
	when(gpsUtilServiceMock.getUserLocation(userMock.getUserId())).thenReturn(visitedLocation);
	when(gpsUtilServiceMock.getNearByAttractions(visitedLocation)).thenReturn(list);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getNearByAttraction?id="+userMock.getUserId())
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.[4]").exists())
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void getNearByAttractionErrorTest() throws Exception {
	//GIVEN
	when(userMock.getUserId()).thenReturn(UUID.randomUUID());
	Location location = new Location(34.817595D, -120.922008D);
	VisitedLocation visitedLocation = new VisitedLocation(userMock.getUserId(), location, null);
	List<Attraction> list = new ArrayList<>();
	//WHEN
	when(gpsUtilServiceMock.getNearByAttractions(visitedLocation)).thenReturn(list);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getNearByAttraction?id="+userMock.getUserId())
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
    }
    
}
