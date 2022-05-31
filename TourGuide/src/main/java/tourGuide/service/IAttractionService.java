package tourGuide.service;

import java.util.List;

import tourGuide.model.Attraction;

public interface IAttractionService {
    
    public List<Attraction> getNearByAttractions(String userName);
    
    public List<Attraction> getAllAttractions();
}
