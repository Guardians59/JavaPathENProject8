package ms.gpsutil.service;

import ms.gpsutil.model.Location;

public interface IDistanceCalculService {
    
    public double getDistance(Location attractionLocation, Location userLocation);

}
