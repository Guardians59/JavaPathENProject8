package tourGuide.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import tourGuide.model.UserPreferences;
/**
 * L'interface IUserPreferencesService est le service qui g�re les pr�f�rences utilisateurs.
 * @author Dylan
 *
 */
public interface IUserPreferencesService {
    /**
     * La m�thode getUserPreferences permet de r�cup�rer les pr�f�rences de l'utilisateur pour
     * ses s�jours.
     * @param userName le nom de l'utilisateur.
     * @return UserPreferences le model des pr�f�rences de l'utilisateur.
     */
    public UserPreferences getUserPreferences(String userName);
    /**
     * La m�thode updateUserPreferences permet de modifier les pr�f�rences de l'utilisateur pour
     * ses s�jours.
     * @param userName le nom de l'utilisateur.
     * @param userPreferences les nouvelles pr�f�rences de l'utilisateur.
     * @return int 2 si la mis � jour est r�ussi, 1 si aucune modification n'est apport�es, ou
     * -1 si une erreur est rencontr�e.
     * @throws JsonProcessingException si une erreur avec JSON est rencontr�e.
     */
    public int updateUserPreferences(String userName, UserPreferences userPreferences) throws JsonProcessingException;

}
