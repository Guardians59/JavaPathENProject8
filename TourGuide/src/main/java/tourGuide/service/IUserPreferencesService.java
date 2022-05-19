package tourGuide.service;

public interface IUserPreferencesService {
    
    public boolean updateUserPreferences(String userName, int numberOfAdult, int numberOfChildren, int duration);

}
