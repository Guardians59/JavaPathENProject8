package ms.trippricer.service;

import java.util.List;
import java.util.UUID;

import ms.trippricer.model.Provider;

/**
 * L'interface ITripPricerService est le service qui permet de r�cup�rer la liste des offres des
 * s�jours propos�s par les fournisseurs selon les pr�f�rences de l'utilisateur.
 * @author Dylan
 *
 */
public interface ITripPricerService {

    public List<Provider> getTripDeals(UUID idUser, int numberOfAdult, int numberOfChildren, int duration,
	    int cumulRewardPoints, String tripPricerApiKey);

}
