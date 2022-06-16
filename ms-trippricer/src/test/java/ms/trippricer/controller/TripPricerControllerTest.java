package ms.trippricer.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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

import ms.trippricer.model.Provider;
import ms.trippricer.service.ITripPricerService;


@SpringBootTest
@AutoConfigureMockMvc
public class TripPricerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    ITripPricerService tripPricerService;

    @BeforeEach
    public void setUp() {
	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getTripDealsTest() throws Exception {
	//GIVEN
	UUID idUser = UUID.randomUUID();
	int numberOfAdult = 2;
	int numberOfChildren = 1;
	int duration = 2;
	int cumulRewardPoint = 100;
	String tripPricerApiKey = "API-KEY-Pricer";
	List<Provider> list = new ArrayList<>();
	Provider provider = new Provider(UUID.randomUUID(), "Holiday Travels", 150);
	list.add(provider);
	//WHEN
	when(tripPricerService.getTripDeals(idUser, numberOfAdult, numberOfChildren, duration, cumulRewardPoint,
		tripPricerApiKey)).thenReturn(list);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getTripDeals?idUser="+idUser+"&numberOfAdult="+numberOfAdult+
		"&numberOfChildren="+numberOfChildren+"&duration="+duration+"&cumulRewardPoint="+cumulRewardPoint+"&tripPricerApiKey="+tripPricerApiKey)
		.accept(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(jsonPath("$.[0].name").value("Holiday Travels"))
		.andExpect(jsonPath("$.[0].price").value("150.0"))
		.andExpect(status().isOk());
    }
    
    @Test
    public void getTripDealsErrorTest() throws Exception {
	//GIVEN
	UUID idUser = UUID.randomUUID();
	int numberOfAdult = 2;
	int numberOfChildren = 1;
	int duration = 0;
	int cumulRewardPoint = 100;
	String tripPricerApiKey = "API-KEY-Pricer";
	List<Provider> list = new ArrayList<>();
	//WHEN
	when(tripPricerService.getTripDeals(idUser, numberOfAdult, numberOfChildren, duration, cumulRewardPoint,
		tripPricerApiKey)).thenReturn(list);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getTripDeals?idUser="+idUser+"&numberOfAdult="+numberOfAdult+
		"&numberOfChildren="+numberOfChildren+"&duration="+duration+"&cumulRewardPoint="+cumulRewardPoint+"&tripPricerApiKey="+tripPricerApiKey)
		.accept(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(jsonPath("$.[0].name").doesNotExist())
		.andExpect(jsonPath("$.[0].price").doesNotExist())
		.andExpect(status().isNotFound());
    }

}
