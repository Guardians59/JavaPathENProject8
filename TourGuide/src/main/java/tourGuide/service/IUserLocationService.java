package tourGuide.service;

import java.util.HashMap;
import java.util.List;

import tourGuide.model.VisitedLocation;

public interface IUserLocationService {
    
    public HashMap<Object, Object> getCurrentUserLocation(String userName);
    
    public HashMap<Object, Object> getAllCurrentLocation(List<String> userNames);
    
    public HashMap<String, Object> getUsersLocationsHistorical(List<String> userNames);
    
    public VisitedLocation getUserLocation(String userName);

}
