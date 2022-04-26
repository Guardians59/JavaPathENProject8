package ms.gpsutil.service;

import gpsUtil.location.Location;

public interface IDistanceCalculService {
    
    public double getDistance(Location attractionLocation, Location userLocation);

}
