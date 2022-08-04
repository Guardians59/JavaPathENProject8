package tourGuide.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    public UserRewardsServiceImpl(IMicroServiceRewardCentralProxy rewardCentralProxy,
	    IMicroServiceGPSUtilProxy gpsUtilProxy) {
	this.rewardCentralProxy = rewardCentralProxy;
	this.gpsUtilProxy = gpsUtilProxy;
    }

    @Override
    public HashMap<String, Object> calculReward(String userName) {
	User user = userRepository.getUser(userName);
	List<Attraction> attractions = gpsUtilProxy.getAllAttractions();
	HashMap<String, Object> result = new HashMap<>();
	logger.debug("Verification of user rewards");

	if(user != null) {
	    List<VisitedLocation> userLocations = user.getListVisitedLocations();
	    
	    if(!attractions.isEmpty() && !userLocations.isEmpty()) {
		userLocations.forEach(userLocation -> {
		    attractions.forEach(attraction -> {
			
			if(user.getUserRewards().isEmpty() || user.getUserRewards().stream().filter
				(r -> r.attraction.getAttractionName().equals(attraction.getAttractionName())).count() == 0) {

			    if(distanceCalculService.nearAttraction(userLocation, attraction)) {
				HashMap<String, Object> mapId = new HashMap<>();
				mapId.put("attractionId", attraction.getAttractionId());
				mapId.put("userId", user.getUserId());
				HashMap<String, Integer> rewardCentralProxyResult = rewardCentralProxy
					.getRewardPoints(mapId);
				int rewardPoint = rewardCentralProxyResult.get("reward").intValue();
				user.addUserReward(new UserReward(userLocation, attraction, rewardPoint));
				logger.info("The reward is valid");
			    } else {
				logger.info("The attraction " + attraction.getAttractionName() + " is too far");
			    }
			} else {
			    logger.info("A reward is already saved with these attraction");
			}
			
		    });
		});
		if(!user.getUserRewards().isEmpty()) {
		result.put(user.getUserName(), user.getUserRewards());
		}
	    } else {
		logger.error("An error has occurred, the list of attractions or locations is empty");
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
    
    @Override
    public HashMap<String, Object> getAllRewards() {
	HashMap<String, Object> result = new HashMap<>();
	List<User> users = userRepository.getAllUsers();
	users.forEach(user -> {
	    result.put(user.getUserName(), user.getUserRewards());
	});
	return result;
    }
    
    @Override
    public HashMap<String, Object> calculateAllRewardsThread() {
	HashMap<String, Object> result = new HashMap<>();
	ExecutorService executor = Executors.newFixedThreadPool(1000);
	List<User> users = userRepository.getAllUsers();
	boolean threadStop = false;
	
	users.forEach(user -> {
	CompletableFuture.supplyAsync(() -> calculReward(user.getUserName()) ,executor);
	
	});
	try {
		executor.shutdown();
		threadStop = executor.awaitTermination(20, TimeUnit.MINUTES);
	    } catch (Exception e) {
		executor.shutdown();
	    }
	if(threadStop = true) {
	    result = getAllRewards();
	}
	return result;
    }

}
