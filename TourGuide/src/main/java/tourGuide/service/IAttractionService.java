package tourGuide.service;

import java.util.List;

import tourGuide.model.Attraction;
/**
 * L'interface IAttractionService est le service qui récupère les différentes listes
 * d'attractions.
 * @author Dylan
 *
 */
public interface IAttractionService {
    /**
     * La méthode getNearByAttractions permet de récupérer la liste des 5 attractions les plus
     * proches de la position de l'utilisateur.
     * @param userName le nom de l'utilisateur.
     * @return List Attraction des 5 attractions les plus proches.
     */
    public List<Attraction> getNearByAttractions(String userName);
    /**
     * La méthode getAllAttractions permet de récupérer la liste de toutes les attractions
     * enregistrées.
     * @return List Attraction les attractions enregistrées.
     */
    public List<Attraction> getAllAttractions();
}
