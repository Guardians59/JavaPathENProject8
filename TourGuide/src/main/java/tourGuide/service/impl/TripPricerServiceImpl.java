package tourGuide.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tourGuide.model.Provider;
import tourGuide.model.User;
import tourGuide.proxies.IMicroServiceTripPricerProxy;
import tourGuide.repositories.UserRepository;
import tourGuide.service.ITripPricerService;
import tourGuide.service.IUserRewardsService;

@Service
public class TripPricerServiceImpl implements ITripPricerService {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    IUserRewardsService userRewardsService;
    
    private Logger logger = LoggerFactory.getLogger(TripPricerServiceImpl.class);
    private final IMicroServiceTripPricerProxy tripPricerProxy;
    
    public TripPricerServiceImpl (IMicroServiceTripPricerProxy tripPricerProxy) {
	this.tripPricerProxy = tripPricerProxy;
    }

    @Override
    public List<Provider> getTripDealsUser(String userName, String apiKey) {
	User user = userRepository.getUser(userName);
	List<Provider> result = new ArrayList<>();
	logger.debug("Search trip deals for user " + userName);
	
	if(user != null) {
	   HashMap<String, Object> mapParams = new HashMap<>();
	   mapParams.put("userId", user.getUserId());
	   mapParams.put("numberOfAdult", user.getUserPreferences().getNumberOfAdults());
	   mapParams.put("numberOfChildren", user.getUserPreferences().getNumberOfChildren());
	   mapParams.put("duration", user.getUserPreferences().getTripDuration());
	   int cumulativePoint = userRewardsService.cumulativeRewardPoints(userName);
	   mapParams.put("cumulRewardPoint", cumulativePoint);
	   mapParams.put("tripPricerApiKey", apiKey);
	   result = tripPricerProxy.getTripDeals(mapParams);
	   if(!result.isEmpty()) {
	   logger.info("Trip deals available");
	   } else {
	       logger.info("No trip deals available");
	   }
	} else {
	   logger.error("Error, the user with username " + userName + " is unknow");
	}
	return result;
    }

}
