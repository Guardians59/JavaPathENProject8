package ms.trippricer.integration.controller;

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

import ms.trippricer.service.ITripPricerService;

@SpringBootTest
@AutoConfigureMockMvc
public class TripPricerControllerIT {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ITripPricerService tripPricerService;

    @BeforeEach
    public void setUp() {
	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void getTripDealsIT() throws Exception {
	UUID idUser = UUID.randomUUID();
	int numberOfAdult = 2;
	int numberOfChildren = 1;
	int duration = 2;
	int cumulRewardPoint = 100;
	String tripPricerApiKey = "API-KEY-Pricer";
	
	mockMvc.perform(MockMvcRequestBuilders.get("/getTripDeals?idUser="+idUser+"&numberOfAdult="+numberOfAdult+
		"&numberOfChildren="+numberOfChildren+"&duration="+duration+"&cumulRewardPoint="+cumulRewardPoint+"&tripPricerApiKey="+tripPricerApiKey)
		.accept(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(jsonPath("$.[0].name").exists())
		.andExpect(jsonPath("$.[0].price").exists())
		.andExpect(status().isOk());
    }
    
    @Test
    public void getTripDealsErrorIT() throws Exception {
	UUID idUser = UUID.randomUUID();
	int numberOfAdult = 2;
	int numberOfChildren = 1;
	int duration = 0;
	int cumulRewardPoint = 100;
	String tripPricerApiKey = "API-KEY-Pricer";
	
	mockMvc.perform(MockMvcRequestBuilders.get("/getTripDeals?idUser="+idUser+"&numberOfAdult="+numberOfAdult+
		"&numberOfChildren="+numberOfChildren+"&duration="+duration+"&cumulRewardPoint="+cumulRewardPoint+"&tripPricerApiKey="+tripPricerApiKey)
		.accept(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(jsonPath("$.[0].name").doesNotExist())
		.andExpect(jsonPath("$.[0].price").doesNotExist())
		.andExpect(status().isNotFound());
    }

}
