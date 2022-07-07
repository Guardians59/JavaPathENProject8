package tourGuide.service;

import java.util.HashMap;

public interface IUserRewardsService {

    public HashMap<String, Object> calculReward(String userName);
    
    public HashMap<String, Object> calculateAllRewardsOfUsers();

    public int cumulativeRewardPoints(String userName);

}
