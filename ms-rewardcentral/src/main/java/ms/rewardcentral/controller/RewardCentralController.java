package ms.rewardcentral.controller;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ms.rewardcentral.controller.exception.RewardException;
import ms.rewardcentral.service.IRewardCentralService;

@RestController
public class RewardCentralController {

    @Autowired
    IRewardCentralService rewardCentralService;
    
    @GetMapping("/getRewardPoints")
    public HashMap<String, Integer> getRewardPoints(@RequestParam UUID attractionId, @RequestParam UUID userId) {
	HashMap<String, Integer> result = new HashMap<>();
	result = rewardCentralService.getRewardPoints(attractionId, userId);
	if(!result.containsKey("reward")) {
	    throw new RewardException("An error occurred while searching for if reward available");
	}
	return result;
    }
    
    @PostMapping("/getRewardPoints")
    public HashMap<String, Integer> getRewardPointsProxy(@RequestBody HashMap<String, Object> mapId) {
	HashMap<String, Integer> result = new HashMap<>();
	String idAttraction = mapId.get("attractionId").toString();
	UUID attractionId = UUID.fromString(idAttraction);
	String id = mapId.get("userId").toString();
	UUID userId = UUID.fromString(id);
	result = rewardCentralService.getRewardPoints(attractionId, userId);
	if(!result.containsKey("reward")) {
	    throw new RewardException("An error occurred while searching for if reward available");
	}
	
	return result;
    }
}
