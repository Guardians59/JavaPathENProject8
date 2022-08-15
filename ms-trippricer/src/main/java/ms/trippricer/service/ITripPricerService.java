package ms.trippricer.service;

import java.util.List;
import java.util.UUID;

import ms.trippricer.model.Provider;

/**
 * L'interface ITripPricerService est le service qui permet de récupérer la liste des offres des
 * séjours proposés par les fournisseurs selon les préférences de l'utilisateur.
 * @author Dylan
 *
 */
public interface ITripPricerService {

    public List<Provider> getTripDeals(UUID idUser, int numberOfAdult, int numberOfChildren, int duration,
	    int cumulRewardPoints, String tripPricerApiKey);

}
