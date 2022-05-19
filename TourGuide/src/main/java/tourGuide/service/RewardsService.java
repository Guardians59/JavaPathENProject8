package tourGuide.service;

import org.springframework.stereotype.Service;

import tourGuide.model.Attraction;
import tourGuide.model.User;

@Service
public class RewardsService {
   

	// proximity in miles
    private int defaultProximityBuffer = 10;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = 200;
	//private final GpsUtil gpsUtil;
	//private final RewardCentral rewardsCentral;
	
	public RewardsService() {
		
	}
	
	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}
	
	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}
	
	public void calculateRewards(User user) {
	    //Calcul effectué dans le microService rewardCentral et sauvegarde effectué dans tourGuide
		/*List<VisitedLocation> userLocations = user.getVisitedLocations();
		List<Attraction> attractions = gpsUtil.getAttractions();
		
		for(VisitedLocation visitedLocation : userLocations) {
			for(Attraction attraction : attractions) {
				if(user.getUserRewards().stream().filter(r -> r.attraction.attractionName.equals(attraction.attractionName)).count() == 0) {
					if(nearAttraction(visitedLocation, attraction)) {
						user.addUserReward(new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user)));
					}
				}
			}
		}*/
	}
	
	
	private int getRewardPoints(Attraction attraction, User user) {
	    //Présent dans le microService rewardCentral
	    return 0;
	}

}
