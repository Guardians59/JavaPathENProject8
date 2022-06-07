package tourGuide.service;

import java.util.HashMap;

public interface IUserRewardsService {

    public HashMap<String, Integer> calculReward(String userName);

    public int cumulativeRewardPoints(String userName);

}
