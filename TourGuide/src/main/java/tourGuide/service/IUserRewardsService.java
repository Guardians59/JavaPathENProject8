package tourGuide.service;

import java.util.HashMap;
import java.util.List;

import tourGuide.model.User;

public interface IUserRewardsService {

    public HashMap<String, Object> calculReward(String userName);
    
    public HashMap<String, Object> calculateAllRewardsOfUsers();

    public int cumulativeRewardPoints(String userName);

    public HashMap<String, Object> getAllRewards();

    public HashMap<String, Object> calculateAllRewardsThread();

}
