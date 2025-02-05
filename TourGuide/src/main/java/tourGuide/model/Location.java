package tourGuide.model;

public class Location {
    
    public double latitude;
    public double longitude;
    
    
    public Location() {
	
    }
    
    public Location(double latitude, double longitude) {
      this.latitude = latitude;
      this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
	return "Location [latitude=" + latitude + ", longitude=" + longitude + "]";
    }

}
