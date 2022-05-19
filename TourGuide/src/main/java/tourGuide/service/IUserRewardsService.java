package tourGuide.service;

import java.util.List;

import tourGuide.model.Attraction;

public interface IUserRewardsService {

    public boolean saveReward(String userName, int rewardPoint, List<Attraction> attractions);

    public int cumulativeRewardPoints(String userName);

}
