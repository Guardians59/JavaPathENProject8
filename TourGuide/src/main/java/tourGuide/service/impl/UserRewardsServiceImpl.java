package tourGuide.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tourGuide.model.Attraction;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;
import tourGuide.repositories.UserRepository;
import tourGuide.service.IDistanceCalculService;
import tourGuide.service.IUserRewardsService;
@Service
public class UserRewardsServiceImpl implements IUserRewardsService{
    
    @Autowired
    IDistanceCalculService distanceCalculService;
    
    @Autowired
    UserRepository userRepository;
    
    private Logger logger = LoggerFactory.getLogger(UserRewardsServiceImpl.class);
    boolean result;
    

    @Override
    public boolean saveReward(String userName, int rewardPoint, List<Attraction> attractions) {
	User user = userRepository.getUser(userName);
	List<VisitedLocation> userLocations = user.getListVisitedLocations();
	logger.debug("Verification of user rewards");
	
	if(user != null && rewardPoint > 0) {
	    if(!attractions.isEmpty()) {
	userLocations.forEach(userLocation -> {
	    attractions.forEach(attraction -> {
		if(user.getUserRewards().stream().filter(r -> r.attraction.attractionName.equals(attraction.attractionName)).count() == 0) {
		    if(distanceCalculService.nearAttraction(userLocation, attraction)) {
			user.addUserReward(new UserReward(userLocation, attraction, rewardPoint));
			result = true;
			logger.info("The reward is valid");
		} else {
		    result = false;
		    logger.info("A reward is already saved with these locations");
		}
		}
	    });
	});
	    } else {
		logger.error("An error has occurred, the list of attractions is empty");
	    }
	} else {
	    logger.error("An error has occurred, the user is unknown or the reward is null");
	}
	return result;
    }

    @Override
    public int cumulativeRewardPoints(String userName) {
	User user = userRepository.getUser(userName);
	int resultPoints = 0;
	logger.info("Search the cumulative points of user " + userName);
	if(user != null) {
	    resultPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();
	    if(resultPoints > 0) {
		logger.info("The cumulative points have been successfully found");
	    } else {
		logger.info("The user does not benefit from any reward points");
	    }
	} else {
	    logger.error("An error has occurred, the user is unknown");
	}
	return resultPoints;
    }

}
