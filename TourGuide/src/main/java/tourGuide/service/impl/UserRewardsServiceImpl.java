package tourGuide.service.impl;

import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tourGuide.model.Attraction;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;
import tourGuide.proxies.IMicroServiceGPSUtilProxy;
import tourGuide.proxies.IMicroServiceRewardCentralProxy;
import tourGuide.repositories.UserRepository;
import tourGuide.service.IDistanceCalculService;
import tourGuide.service.IUserRewardsService;

@Service
public class UserRewardsServiceImpl implements IUserRewardsService {

    @Autowired
    IDistanceCalculService distanceCalculService;

    @Autowired
    UserRepository userRepository;
    
    private final IMicroServiceRewardCentralProxy rewardCentralProxy;
    private final IMicroServiceGPSUtilProxy gpsUtilProxy;
    private Logger logger = LoggerFactory.getLogger(UserRewardsServiceImpl.class);
    
    public UserRewardsServiceImpl(IMicroServiceRewardCentralProxy rewardCentralProxy, IMicroServiceGPSUtilProxy gpsUtilProxy) {
	this.rewardCentralProxy = rewardCentralProxy;
	this.gpsUtilProxy = gpsUtilProxy;
    }

    @Override
    public HashMap<String, Integer> calculReward(String userName) {
	User user = userRepository.getUser(userName);
	List<Attraction> attractions = gpsUtilProxy.getAllAttractions();
	HashMap<String, Integer> result = new HashMap<>();
	logger.debug("Verification of user rewards");

	if(user != null) {
	    List<VisitedLocation> userLocations = user.getListVisitedLocations();
	    
	    if(!attractions.isEmpty()) {
		
		userLocations.forEach(userLocation -> {
		    attractions.forEach(attraction -> {
			
			if(user.getUserRewards().isEmpty() || user.getUserRewards().stream()
				.filter(r -> r.attraction.attractionName.equals(attraction.attractionName))
				.count() == 0) {
			    
			    if(distanceCalculService.nearAttraction(userLocation, attraction)) {
				HashMap<String, Object> mapId = new HashMap<>();
				mapId.put("attractionId", attraction.getAttractionId());
				mapId.put("userId", user.getUserId());
				HashMap<String, Integer> rewardCentralProxyResult = rewardCentralProxy.getRewardPoints(mapId);
				int rewardPoint = rewardCentralProxyResult.get("reward").intValue();
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
		logger.error("An error has occurred, the list of attractions is empty");
	    }
	} else {
	    logger.error("An error has occurred, the user is unknown");
	}
	return result;
    }

    @Override
    public int cumulativeRewardPoints(String userName) {
	User user = userRepository.getUser(userName);
	int resultPoints = -1;
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
