package ms.trippricer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ms.trippricer.model.Provider;

@SpringBootTest
public class TripPricerServiceTest {
    
    @Autowired
    ITripPricerService tripPricerService;
    
    @Test
    public void getTripDealsTest() {
	//GIVEN
	List<Provider> list = new ArrayList<>();
	String tripPricerApiKey = "API-KEY-Pricer";
	//WHEN
	list = tripPricerService.getTripDeals(UUID.randomUUID(), 2, 1, 2, 200, tripPricerApiKey);
	//THEN
	System.out.println(list.get(0).toString());
	System.out.println(list.get(1).toString());
	assertEquals(list.isEmpty(), false);
	
    }
    
    @Test
    public void getTripDealsErrorTest() {
	//GIVEN
	List<Provider> list = new ArrayList<>();
	String tripPricerApiKey = "API-KEY-Pricer";
	//WHEN
	list = tripPricerService.getTripDeals(UUID.randomUUID(), 0, 1, 2, 200, tripPricerApiKey);
	//THEN
	assertEquals(list.isEmpty(), true);
	
    }

}
