package ms.rewardcentral.model;

import java.util.List;

public class CalculateRewardModel {
    
    public List<Attraction> attractions;
    public User user;
    
    public CalculateRewardModel() {
	
    }
    
    public CalculateRewardModel(List<Attraction> attractions, User user) {
	this.attractions = attractions;
	this.user = user;
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
	return "CalculateRewardModel [attractions=" + attractions + ", user=" + user + "]";
    }

}
