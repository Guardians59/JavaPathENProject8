package ms.rewardcentral.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    public UUID userId;
    public String userName;
    public List<VisitedLocation> listVisitedLocations = new ArrayList<>();
    public List<UserReward> userRewards = new ArrayList<>();

    public User() {

    }

    public User(UUID userId, String userName) {
	this.userId = userId;
	this.userName = userName;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public void addToListVisitedLocations(VisitedLocation visitedLocation) {
	listVisitedLocations.add(visitedLocation);
    }

    public List<VisitedLocation> getListVisitedLocations() {
        return listVisitedLocations;
    }

    public void setListVisitedLocations(List<VisitedLocation> listVisitedLocations) {
        this.listVisitedLocations = listVisitedLocations;
    }
    
    public void addUserReward(UserReward userReward) {
	if(userRewards.stream().filter(r -> r.attraction.attractionName.equals(userReward.attraction.attractionName)).count() == 0) {
		userRewards.add(userReward);
	}
    }

    public List<UserReward> getUserRewards() {
        return userRewards;
    }

    public void setUserRewards(List<UserReward> userRewards) {
        this.userRewards = userRewards;
    }

    @Override
    public String toString() {
	return "User [userId=" + userId + ", userName=" + userName + ", listVisitedLocations=" + listVisitedLocations
		+ ", userRewards=" + userRewards + "]";
    }

}
