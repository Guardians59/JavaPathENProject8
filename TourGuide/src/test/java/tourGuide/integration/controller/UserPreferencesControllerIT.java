package tourGuide.integration.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

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
import org.zalando.jackson.datatype.money.MoneyModule;

import com.fasterxml.jackson.databind.ObjectMapper;
import tourGuide.model.User;
import tourGuide.model.UserPreferences;
import tourGuide.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserPreferencesControllerIT {
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    @Autowired
    UserRepository userRepository;
    
    @BeforeEach
    public void setUp() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void updateUserPreferencesTest() throws Exception {
	User user = userRepository.getUser("internalUser38");
	UserPreferences newUserPreferences = new UserPreferences();
	newUserPreferences.setNumberOfAdults(3);
	newUserPreferences.setNumberOfChildren(2);
	newUserPreferences.setTripDuration(2);
	newUserPreferences.setTicketQuantity(5);
	newUserPreferences.setAttractionProximity(user.getUserPreferences().getAttractionProximity());
	newUserPreferences.setHighPricePoint(user.getUserPreferences().getHighPricePoint());
	newUserPreferences.setLowerPricePoint(user.getUserPreferences().getLowerPricePoint());
	newUserPreferences.setCurrency(user.getUserPreferences().getCurrency());
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.registerModule(new MoneyModule().withQuotedDecimalNumbers());
	String json = objectMapper.writeValueAsString(newUserPreferences);
	
	mockMvc.perform(MockMvcRequestBuilders.put("/updateUserPreferences/internalUser38")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(content().string("Preferences of user " + "internalUser38" + " updated with success"))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void updateSameUserPreferencesTest() throws Exception {
	User user = userRepository.getUser("internalUser39");
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.registerModule(new MoneyModule().withQuotedDecimalNumbers());
	String json = objectMapper.writeValueAsString(user.getUserPreferences());
	
	mockMvc.perform(MockMvcRequestBuilders.put("/updateUserPreferences/internalUser39")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(content().string("Preferences of user " + "internalUser39" + " haven't been updated,"
			+ " because no information has been changed"))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void updateUserPreferencesErrorNumberAdultsTest() throws Exception {
	User user = userRepository.getUser("internalUser40");
	UserPreferences newUserPreferences = new UserPreferences();
	newUserPreferences.setNumberOfAdults(0);
	newUserPreferences.setNumberOfChildren(2);
	newUserPreferences.setTripDuration(2);
	newUserPreferences.setTicketQuantity(2);
	newUserPreferences.setAttractionProximity(user.getUserPreferences().getAttractionProximity());
	newUserPreferences.setHighPricePoint(user.getUserPreferences().getHighPricePoint());
	newUserPreferences.setLowerPricePoint(user.getUserPreferences().getLowerPricePoint());
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.registerModule(new MoneyModule().withQuotedDecimalNumbers());
	String json = objectMapper.writeValueAsString(newUserPreferences);
	
	mockMvc.perform(MockMvcRequestBuilders.put("/updateUserPreferences/internalUser40")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void getUserPreferencesTest() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders.get("/getUserPreferences/internalUser41")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.numberOfAdults").value(1))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void getUserPreferencesErrorTest() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders.get("/getUserPreferences/internalUser410")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.numberOfAdults").doesNotExist())
		.andExpect(status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
    }

}
