package tourGuide.service;

import java.util.HashMap;
import java.util.List;

public interface IUserLocationService {
    
    public HashMap<Object, Object> getCurrentUserLocation(String userName);
    
    public HashMap<Object, Object> getAllCurrentLocation(List<String> userNames);
    
    public HashMap<String, Object> getAllLocationsHistorical(List<String> userNames);

}
