package tourGuide.model;

import java.util.UUID;

import com.jsoniter.annotation.JsonProperty;

public class FeignRewardPointsModel {
    
    UUID attractionId;
    UUID userId;
    
    public FeignRewardPointsModel() {
	
    }
   
    public FeignRewardPointsModel(@JsonProperty("attractionId") UUID attractionId, @JsonProperty("userId") UUID userId) {
	this.attractionId = attractionId;
	this.userId = userId;
    }

    public UUID getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(UUID attractionId) {
        this.attractionId = attractionId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
	return "FeignRewardPointsModel [attractionId=" + attractionId + ", userId=" + userId + "]";
    }
    
}
