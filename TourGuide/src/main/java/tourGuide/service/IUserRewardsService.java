package tourGuide.service;

import java.util.HashMap;

public interface IUserRewardsService {

    public HashMap<String, Object> calculReward(String userName);

    public int cumulativeRewardPoints(String userName);

    public HashMap<String, Object> getAllRewards();

    public HashMap<String, Object> calculateAllRewardsThread();

}
