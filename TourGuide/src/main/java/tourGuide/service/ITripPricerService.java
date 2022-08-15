package tourGuide.service;

import java.util.List;

import tourGuide.model.Provider;
/**
 * L'interface ITripPricerService est le service qui r�cup�re la liste des offres de s�jours
 * des fournisseurs.
 * @author Dylan
 *
 */
public interface ITripPricerService {
    /**
     * La m�thode getTripDealsUser permet de r�cup�rer la liste des offres de s�jours des
     * fournisseurs selon les pr�f�rences de l'utilisateur.
     * @param userName le nom de l'utilisateur.
     * @param apiKey la cl� d'api.
     * @return List Provider les offres de s�jours des fournisseurs.
     */
    List<Provider> getTripDealsUser(String userName, String apiKey);

}
