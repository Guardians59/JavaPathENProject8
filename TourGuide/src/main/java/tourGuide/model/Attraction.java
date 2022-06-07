package tourGuide.model;

import java.util.UUID;

public class Attraction {
    
    public String attractionName;
    public String city;
    public String state;
    public Double latitude;
    public Double longitude;
    public UUID attractionId;
    
    public Attraction() {
	
    }
    
    public Attraction(UUID attractionId, String attractionName, String city, String state,
	    double latitude, double longitude) {
      this.attractionId = attractionId;
      this.attractionName = attractionName;
      this.city = city;
      this.state = state;
      this.latitude = latitude;
      this.longitude = longitude;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public UUID getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(UUID attractionId) {
        this.attractionId = attractionId;
    }

    @Override
    public String toString() {
	return "Attraction [attractionName=" + attractionName + ", city=" + city + ", state=" + state + ", latitude="
		+ latitude + ", longitude=" + longitude + ", attractionId=" + attractionId + "]";
    }

}
