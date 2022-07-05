package ms.rewardcentral.service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import ms.rewardcentral.model.Attraction;
import ms.rewardcentral.model.User;

public interface IRewardCentralService {

    public HashMap<String, Integer> getRewardPoints(UUID attractionId, UUID userId);

    public HashMap<String, Integer> calculateReward(List<Attraction> attractions, User user);
    
    public HashMap<String, Object> calculateAllRewardsOfUsers(List<Attraction> attractions,
	    List<User> users);

}
