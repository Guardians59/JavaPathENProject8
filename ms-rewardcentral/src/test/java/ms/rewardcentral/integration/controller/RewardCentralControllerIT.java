package ms.rewardcentral.integration.controller;

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

import ms.rewardcentral.service.IRewardCentralService;

@SpringBootTest
@AutoConfigureMockMvc
public class RewardCentralControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    IRewardCentralService rewardCentralService;

    @Autowired
    WebApplicationContext webApplicationContext;
    
    @BeforeEach
    public void setUp() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void getRewardPointsIT() throws Exception {
	UUID idUser = UUID.randomUUID();
	UUID idAttraction = UUID.randomUUID();
	
	mockMvc.perform(MockMvcRequestBuilders.get("/getRewardPoints?attractionId="+idAttraction+"&userId="+idUser)
		.accept(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(jsonPath("$.reward").exists())
		.andExpect(status().isOk());
    }

}
