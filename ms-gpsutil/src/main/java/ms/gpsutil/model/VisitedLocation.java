package ms.gpsutil.model;

import java.util.Date;
import java.util.UUID;

public class VisitedLocation {
    
    public UUID userId;

    public Location location;

    public Date timeVisited;

    public VisitedLocation() {
    }

    public VisitedLocation(UUID userId, Location location, Date timeVisited) {
	this.userId = userId;
	this.location = location;
	this.timeVisited = timeVisited;
    }
    
    
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getTimeVisited() {
        return timeVisited;
    }

    public void setTimeVisited(Date timeVisited) {
        this.timeVisited = timeVisited;
    }

    @Override
    public String toString() {
	return "VisitedLocation [userId=" + userId + ", location=" + location + ", timeVisited=" + timeVisited + "]";
    }

}
