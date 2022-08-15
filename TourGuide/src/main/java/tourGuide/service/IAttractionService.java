package tourGuide.service;

import java.util.List;

import tourGuide.model.Attraction;
/**
 * L'interface IAttractionService est le service qui r�cup�re les diff�rentes listes
 * d'attractions.
 * @author Dylan
 *
 */
public interface IAttractionService {
    /**
     * La m�thode getNearByAttractions permet de r�cup�rer la liste des 5 attractions les plus
     * proches de la position de l'utilisateur.
     * @param userName le nom de l'utilisateur.
     * @return List Attraction des 5 attractions les plus proches.
     */
    public List<Attraction> getNearByAttractions(String userName);
    /**
     * La m�thode getAllAttractions permet de r�cup�rer la liste de toutes les attractions
     * enregistr�es.
     * @return List Attraction les attractions enregistr�es.
     */
    public List<Attraction> getAllAttractions();
}
