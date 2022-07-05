package ms.rewardcentral.model;

public class UserReward {

    public VisitedLocation visitedLocation;
    public Attraction attraction;
    private int rewardPoints;

    public UserReward(VisitedLocation visitedLocation, Attraction attraction, int rewardPoints) {
	this.visitedLocation = visitedLocation;
	this.attraction = attraction;
	this.rewardPoints = rewardPoints;
    }

    public UserReward(VisitedLocation visitedLocation, Attraction attraction) {
	this.visitedLocation = visitedLocation;
	this.attraction = attraction;
    }

    public VisitedLocation getVisitedLocation() {
        return visitedLocation;
    }

    public void setVisitedLocation(VisitedLocation visitedLocation) {
        this.visitedLocation = visitedLocation;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    @Override
    public String toString() {
	return "UserReward [visitedLocation=" + visitedLocation + ", attraction=" + attraction + ", rewardPoints="
		+ rewardPoints + "]";
    }

}
