package tourGuide.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import tourGuide.controller.exception.CalculateRewardPointError;
import tourGuide.controller.exception.CumulativeRewardPointError;
import tourGuide.service.IUserRewardsService;

@RestController
public class UserRewardController {
    
    @Autowired
    IUserRewardsService userRewardsService;
    
    @GetMapping("calculateRewardPoint/{userName}")
    public HashMap<String, Object> calculateRewardPoint(@PathVariable String userName) {
	HashMap<String, Object> result = new HashMap<>();
	result = userRewardsService.calculReward(userName);
	if(result.isEmpty())
	    throw new CalculateRewardPointError(
		    "An error occured while calculation of reward points to the user " + userName);
	
	return result;
    }
    
    @GetMapping("calculateAllRewardsPointOfUsers")
    public HashMap<String, Object> calculateAllRewardPoint() {
	HashMap<String, Object> result = new HashMap<>();
	result = userRewardsService.calculateAllRewardsOfUsers();
	if(result.isEmpty())
	    throw new CalculateRewardPointError(
		    "An error occured while calculation of reward points to the users ");
	
	return result;
    }
    
    @GetMapping("cumulativeRewardPoint/{userName}")
    public int cumulativeRewardPoint(@PathVariable String userName) {
	int result = -1;
	result = userRewardsService.cumulativeRewardPoints(userName);
	if(result == -1)
	    throw new CumulativeRewardPointError(
		    "An error occured while searching for the cumulative reward point to the user " + userName);
	
	return result;
    }

}
