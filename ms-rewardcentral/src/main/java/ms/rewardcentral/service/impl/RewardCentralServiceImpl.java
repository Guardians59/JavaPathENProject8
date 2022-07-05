package ms.rewardcentral.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.rewardcentral.model.Attraction;
import ms.rewardcentral.model.User;
import ms.rewardcentral.model.UserReward;
import ms.rewardcentral.model.VisitedLocation;
import ms.rewardcentral.service.IDistanceCalculService;
import ms.rewardcentral.service.IRewardCentralService;
import rewardCentral.RewardCentral;

@Service
public class RewardCentralServiceImpl implements IRewardCentralService {

    @Autowired
    IDistanceCalculService distanceCalculService;

    private Logger logger = LoggerFactory.getLogger(RewardCentralServiceImpl.class);
    public RewardCentral rewardCentral = new RewardCentral();

    @Override
    public HashMap<String, Integer> getRewardPoints(UUID attractionId, UUID userId) {
	int result = 0;
	HashMap<String, Integer> map = new HashMap<>();
	logger.debug("Search the rewards available");

	if(attractionId != null && userId != null) {
	    result = rewardCentral.getAttractionRewardPoints(attractionId, userId);
	    if(result > 0) {
		map.put("reward", result);
		logger.info("Reward available");
	    } else {
		logger.info("Not reward available");
	    }
	} else {
	    logger.error("An error occurred while searching for if reward available");
	}
	return map;
    }

    @Override
    public HashMap<String, Integer> calculateReward(List<Attraction> attractions, User user) {
	HashMap<String, Integer> result = new HashMap<>();
	logger.debug("Verification of user rewards");
	
	if(user.getUserName() != null) {
	    List<VisitedLocation> visitedLocations = user.getListVisitedLocations();

	    if(!attractions.isEmpty() && !visitedLocations.isEmpty()) {

		visitedLocations.forEach(userLocation -> {
		    attractions.forEach(attraction -> {
			
			if(user.getUserRewards().isEmpty() || user.getUserRewards().stream()
				.filter(r -> r.attraction.attractionName.equals(attraction.attractionName))
				.count() == 0) {
			    if(distanceCalculService.nearAttraction(userLocation, attraction)) {
				HashMap<String, Integer> rewardPointResult = getRewardPoints(
					attraction.getAttractionId(), user.getUserId());
				int rewardPoint = rewardPointResult.get("reward").intValue();
				user.addUserReward(new UserReward(userLocation, attraction, rewardPoint));
				result.put(attraction.attractionName, rewardPoint);
				logger.info("The reward is valid");
			    } else {
				logger.info("The attraction is too far");
			    }
			} else {
			    logger.info("A reward is already saved with these attraction");
			}

		    });
		});
	    } else {
		logger.error("An error has occurred, the list of attractions or locations is empty");
	    }
	} else {
	    logger.error("An error has occurred, the user is unknown");
	}
	return result;
    }

    @Override
    public HashMap<String, Object> calculateAllRewardsOfUsers(List<Attraction> attractions, List<User> users) {
	HashMap<String, Object> result = new HashMap<>();
	logger.debug("Verification of all users rewards");

	if(!users.isEmpty()) {
	    users.forEach(user -> {
		HashMap<String, Integer> rewardResult = new HashMap<>();
		
		if(user.getUserName() != null) {
		    List<VisitedLocation> visitedLocations = user.getListVisitedLocations();

		    if(!attractions.isEmpty() && !visitedLocations.isEmpty()) {
			visitedLocations.forEach(userLocation -> {
			    attractions.forEach(attraction -> {

				if(user.getUserRewards().isEmpty() || user.getUserRewards().stream()
					.filter(r -> r.attraction.attractionName.equals(attraction.attractionName))
					.count() == 0) {
				    if(distanceCalculService.nearAttraction(userLocation, attraction)) {
					HashMap<String, Integer> rewardPointResult = getRewardPoints(
						attraction.getAttractionId(), user.getUserId());
					int rewardPoint = rewardPointResult.get("reward").intValue();
					user.addUserReward(new UserReward(userLocation, attraction, rewardPoint));
					rewardResult.put(attraction.attractionName, rewardPoint);
					logger.info("The reward is valid for attraction " + attraction.attractionName);
				    } else {
					logger.info("The attraction " + attraction.attractionName + " is too far for user "
						+ user.getUserName());
				    }
				} else {
				    logger.info("A reward is already saved with these attraction");
				}

			    });
			});
		    } else {
			logger.error("An error has occurred, the list of attractions is empty");
		    } if (!rewardResult.isEmpty()) {
		    result.put(user.getUserName(), rewardResult);
		    }
		} else {
		    logger.error("An error has occurred, the user is unknown");
		}
	    });
	} else {
	    logger.error("An error has occured, the list of users is empty");
	}
	return result;
    }

}
