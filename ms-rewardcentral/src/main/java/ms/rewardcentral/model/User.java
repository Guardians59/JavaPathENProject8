package ms.rewardcentral.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;




public class User {
    private UUID userId;
    private String userName;
    private String phoneNumber;
    private String emailAddress;
    private Date latestLocationTimestamp;
    private List<VisitedLocation> listVisitedLocations = new ArrayList<>();
    private List<UserReward> userRewards = new ArrayList<>();

    public User() {

    }

    public User(UUID userId, String userName, String phoneNumber, String emailAddress) {
	this.userId = userId;
	this.userName = userName;
	this.phoneNumber = phoneNumber;
	this.emailAddress = emailAddress;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getLatestLocationTimestamp() {
        return latestLocationTimestamp;
    }

    public void setLatestLocationTimestamp(Date latestLocationTimestamp) {
        this.latestLocationTimestamp = latestLocationTimestamp;
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
	return "User [userId=" + userId + ", userName=" + userName + ", phoneNumber=" + phoneNumber + ", emailAddress="
		+ emailAddress + ", latestLocationTimestamp=" + latestLocationTimestamp + ", listVisitedLocations="
		+ listVisitedLocations + ", userRewards=" + userRewards + "]";
    }

}
