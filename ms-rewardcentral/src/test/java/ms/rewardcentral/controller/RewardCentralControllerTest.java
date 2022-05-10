package ms.rewardcentral.controller;

import java.util.HashMap;
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

import ms.rewardcentral.service.IRewardCentralService;

@SpringBootTest
@AutoConfigureMockMvc
public class RewardCentralControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @MockBean
    IRewardCentralService rewardCentralService;
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    @BeforeEach
    public void setUp() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void getRewardPointsTest() throws Exception {
	//GIVEN
	UUID idUser = UUID.randomUUID();
	UUID idAttraction = UUID.randomUUID();
	HashMap<String, Integer> result = new HashMap<>();
	result.put("reward", 100);
	//WHEN
	when(rewardCentralService.getRewardPoints(idAttraction, idUser)).thenReturn(result);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getRewardPoints?attractionId="+idAttraction+"&userId="+idUser)
	.accept(MediaType.APPLICATION_JSON))
	.andDo(MockMvcResultHandlers.print())
	.andExpect(jsonPath("$.reward").value(100))
	.andExpect(status().isOk());
		
    }
    
    @Test
    public void getRewardPointsErrorTest() throws Exception {
	//GIVEN
	UUID idUser = UUID.randomUUID();
	UUID idAttraction = UUID.randomUUID();
	HashMap<String, Integer> result = new HashMap<>();
	//WHEN
	when(rewardCentralService.getRewardPoints(idAttraction, idUser)).thenReturn(result);
	//THEN
	mockMvc.perform(MockMvcRequestBuilders.get("/getRewardPoints?attractionId="+idAttraction+"&userId="+idUser)
	.accept(MediaType.APPLICATION_JSON))
	.andDo(MockMvcResultHandlers.print())
	.andExpect(jsonPath("$.reward").doesNotExist())
	.andExpect(status().isNotFound());
		
    }

}
