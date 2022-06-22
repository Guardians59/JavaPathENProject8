package tourGuide.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import tourGuide.model.UserPreferences;

public interface IUserPreferencesService {
    
    public UserPreferences getUserPreferences(String userName);
    
    public int updateUserPreferences(String userName, UserPreferences userPreferences) throws JsonProcessingException;

}
