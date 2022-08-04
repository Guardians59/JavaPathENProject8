package tourGuide.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import tourGuide.model.Attraction;
import tourGuide.model.User;
import tourGuide.proxies.IMicroServiceGPSUtilProxy;
import tourGuide.repositories.UserRepository;

@SpringBootTest
public class AttractionServiceTest {
    
    @Autowired
    IAttractionService attractionService;
    
    @Autowired
    UserRepository userRepository;
    
    @MockBean
    IMicroServiceGPSUtilProxy microServiceGPSUtilProxyMock;
    
    @Test
    public void getNearByAttractionTest() {
	//GIVEN
	User user = userRepository.getUser("internalUser4");
	List<Attraction> list = new ArrayList<>();
	list.add(new Attraction(UUID.randomUUID(), "Disneyland", "Anaheim", "CA", 33.817595D, -117.922008D));
	list.add(new Attraction(UUID.randomUUID(), "Jackson Hole", "Jackson Hole", "WY", 43.582767D, -110.821999D));
	list.add(new Attraction(UUID.randomUUID(), "Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
	list.add(new Attraction(UUID.randomUUID(), "Joshua Tree National Park", "Joshua Tree National Park", "CA", 33.881866D, -115.90065D));
	list.add(new Attraction(UUID.randomUUID(), "Buffalo National River", "St Joe", "AR", 35.985512D, -92.757652D));
	List<Attraction> result = new ArrayList<>();
	//WHEN
	when(microServiceGPSUtilProxyMock.getNearByAttraction(user.getUserId())).thenReturn(list);
	result = attractionService.getNearByAttractions("internalUser4");
	//THEN
	assertEquals(result.isEmpty(), false);
	assertEquals(result.size(), 5);
    }
    
    @Test
    public void getNearByAttractionErrorTest() {
	//GIVEN
	User user = userRepository.getUser("internalUser4");
	List<Attraction> list = new ArrayList<>();
	List<Attraction> result = new ArrayList<>();
	//WHEN
	when(microServiceGPSUtilProxyMock.getNearByAttraction(user.getUserId())).thenReturn(list);
	result = attractionService.getNearByAttractions("internalUser4");
	//THEN
	assertEquals(result.isEmpty(), true);
    }
    
    @Test
    public void getNearByAttractionUserErrorTest() {
	//GIVEN
	List<Attraction> result = new ArrayList<>();
	//WHEN
	result = attractionService.getNearByAttractions("internalUser400000");
	//THEN
	assertEquals(result.isEmpty(), true);
    }

}
