package tourGuide.service;

import java.util.List;

import tourGuide.model.Provider;

public interface ITripPricerService {
    
    List<Provider> getTripDealsUser(String userName, String apiKey);

}
