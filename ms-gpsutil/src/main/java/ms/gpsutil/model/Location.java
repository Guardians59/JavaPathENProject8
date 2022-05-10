package ms.gpsutil.model;

public class Location {

    public double longitude;

    public double latitude;

    public Location() {
    }

    public Location(double latitude, double longitude) {
	this.latitude = latitude;
	this.longitude = longitude;
    }

    @Override
    public String toString() {
	return "Location [longitude=" + longitude + ", latitude=" + latitude + "]";
    }
    
}
