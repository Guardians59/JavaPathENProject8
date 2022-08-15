package tourGuide.service;

import java.util.List;

import tourGuide.model.Provider;
/**
 * L'interface ITripPricerService est le service qui récupére la liste des offres de séjours
 * des fournisseurs.
 * @author Dylan
 *
 */
public interface ITripPricerService {
    /**
     * La méthode getTripDealsUser permet de récupérer la liste des offres de séjours des
     * fournisseurs selon les préférences de l'utilisateur.
     * @param userName le nom de l'utilisateur.
     * @param apiKey la clé d'api.
     * @return List Provider les offres de séjours des fournisseurs.
     */
    List<Provider> getTripDealsUser(String userName, String apiKey);

}
