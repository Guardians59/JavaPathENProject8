package ms.gpsutil.service.impl;

import org.springframework.stereotype.Service;

import ms.gpsutil.model.Location;
import ms.gpsutil.service.IDistanceCalculService;
/**
 * La classe DistanceCalculServiceImpl est l'implémentation de l'interface IDistanceCalculService.
 * 
 * @see IDistanceCalculService
 * @author Dylan
 *
 */
@Service
public class DistanceCalculServiceImpl implements IDistanceCalculService {

    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    
    @Override
    public double getDistance(Location attractionLocation, Location userLocation) {
	double lat1 = Math.toRadians(attractionLocation.latitude);
	double lon1 = Math.toRadians(attractionLocation.longitude);
	double lat2 = Math.toRadians(userLocation.latitude);
	double lon2 = Math.toRadians(userLocation.longitude);

	double angle = Math
		.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

	double nauticalMiles = 60 * Math.toDegrees(angle);
	double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
	return statuteMiles;
    }

}
