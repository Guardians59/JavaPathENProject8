package ms.gpsutil.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import gpsUtil.location.VisitedLocation;

public class User {
    
    private UUID userId;
    private String userName;
    private String phoneNumber;
    private String emailAddress;
    private Date latestLocationTimestamp;
    private List<VisitedLocation> listVisitedLocations = new ArrayList<>();
    private VisitedLocation lastVisitedLocation;

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

    public String getUserName() {
	return userName;
    }

    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
	return phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
	this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
	return emailAddress;
    }

    public void setLatestLocationTimestamp(Date latestLocationTimestamp) {
	this.latestLocationTimestamp = latestLocationTimestamp;
    }

    public Date getLatestLocationTimestamp() {
	return latestLocationTimestamp;
    }

    public void addToVisitedLocations(VisitedLocation visitedLocation) {
	listVisitedLocations.add(visitedLocation);
    }

    public List<VisitedLocation> getVisitedLocations() {
	return listVisitedLocations;
    }

    public void clearVisitedLocations() {
	listVisitedLocations.clear();
    }

    public void setLastVisitedLocation(VisitedLocation lastVisitedLocation) {
	this.lastVisitedLocation = lastVisitedLocation;
    }
    
    public VisitedLocation getLastVisitedLocation() {
	return lastVisitedLocation;
    }

    @Override
    public String toString() {
	return "User [userId=" + userId + ", userName=" + userName + ", phoneNumber=" + phoneNumber + ", emailAddress="
		+ emailAddress + ", latestLocationTimestamp=" + latestLocationTimestamp + ", listVisitedLocations="
		+ listVisitedLocations + ", lastVisitedLocation=" + lastVisitedLocation + "]";
    }

}
