package ms.rewardcentral.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RewardCentralServiceTest {
    
    @Autowired
    IRewardCentralService rewardCentralService;
    
    @Test
    public void getRewardPointTest() {
	//GIVEN
	HashMap<String, Integer> firstReward = new HashMap<>();
	//WHEN
	firstReward = rewardCentralService.getRewardPoints(UUID.randomUUID(), UUID.randomUUID());
	//THEN
	assertEquals(firstReward.get("reward").intValue() > 0, true);
    }
    
    @Test
    public void getRewardPointErrorTest() {
	//GIVEN
	HashMap<String, Integer> firstReward = new HashMap<>();
	//WHEN
	firstReward = rewardCentralService.getRewardPoints(null, UUID.randomUUID());
	//THEN
	assertEquals(firstReward.containsKey("reward"), false);
    }
}
