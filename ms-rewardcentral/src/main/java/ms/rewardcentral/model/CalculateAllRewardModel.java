package ms.rewardcentral.model;

import java.util.List;

public class CalculateAllRewardModel {
    
    public List<Attraction> attractions;
    public List<User> users;
    
    public CalculateAllRewardModel() {
	
    }
    public CalculateAllRewardModel(List<Attraction> attractions, List<User> users) {
	
	this.attractions = attractions;
	this.users = users;
    }
    public List<Attraction> getAttractions() {
        return attractions;
    }
    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
    @Override
    public String toString() {
	return "CalculateAllRewardModel [attractions=" + attractions + ", users=" + users + "]";
    }
    
}
