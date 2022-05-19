package tourGuide.service;



import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.VisitedLocation;

public interface IDistanceCalculService {
    
    public double getDistance(Location attractionLocation, Location userLocation);
    
    public boolean isWithinAttractionProximity(Attraction attraction, Location location);
    
    public boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction);

}
