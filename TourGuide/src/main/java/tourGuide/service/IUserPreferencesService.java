package tourGuide.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import tourGuide.model.UserPreferences;
/**
 * L'interface IUserPreferencesService est le service qui gère les préférences utilisateurs.
 * @author Dylan
 *
 */
public interface IUserPreferencesService {
    /**
     * La méthode getUserPreferences permet de récupérer les préférences de l'utilisateur pour
     * ses séjours.
     * @param userName le nom de l'utilisateur.
     * @return UserPreferences le model des préférences de l'utilisateur.
     */
    public UserPreferences getUserPreferences(String userName);
    /**
     * La méthode updateUserPreferences permet de modifier les préférences de l'utilisateur pour
     * ses séjours.
     * @param userName le nom de l'utilisateur.
     * @param userPreferences les nouvelles préférences de l'utilisateur.
     * @return int 2 si la mis à jour est réussi, 1 si aucune modification n'est apportées, ou
     * -1 si une erreur est rencontrée.
     * @throws JsonProcessingException si une erreur avec JSON est rencontrée.
     */
    public int updateUserPreferences(String userName, UserPreferences userPreferences) throws JsonProcessingException;

}
