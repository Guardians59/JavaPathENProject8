package ms.rewardcentral.service.impl;

import org.springframework.stereotype.Service;

import ms.rewardcentral.model.Attraction;
import ms.rewardcentral.model.Location;
import ms.rewardcentral.model.VisitedLocation;
import ms.rewardcentral.service.IDistanceCalculService;

@Service
public class DistanceCalculServiceImpl implements IDistanceCalculService{
    
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    private int attractionProximityRange = 200;
    private int proximityBuffer = 10;

    @Override
    public double getDistance(Location attractionLocation, Location userLocation) {
	double lat1 = Math.toRadians(attractionLocation.latitude);
        double lon1 = Math.toRadians(attractionLocation.longitude);
        double lat2 = Math.toRadians(userLocation.latitude);
        double lon2 = Math.toRadians(userLocation.longitude);

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
    }

    @Override
    public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
	Location locationAttraction = new Location(attraction.latitude, attraction.longitude);
	return getDistance(locationAttraction, location) > attractionProximityRange ? false : true;
    }

    @Override
    public boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
	Location locationAttraction = new Location(attraction.latitude, attraction.longitude);
	return getDistance(locationAttraction, visitedLocation.location) > proximityBuffer ? false : true;
    }

}
