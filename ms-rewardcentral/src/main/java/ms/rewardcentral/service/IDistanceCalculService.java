package ms.rewardcentral.service;

import ms.rewardcentral.model.Attraction;
import ms.rewardcentral.model.Location;
import ms.rewardcentral.model.VisitedLocation;

public interface IDistanceCalculService {

    public double getDistance(Location attractionLocation, Location userLocation);

    public boolean isWithinAttractionProximity(Attraction attraction, Location location);

    public boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction);

}
