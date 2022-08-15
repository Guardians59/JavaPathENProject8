package tourGuide.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tourGuide.controller.exception.TripDealsError;
import tourGuide.model.Provider;
import tourGuide.service.ITripPricerService;

/**
 * La classe TripPricerController est le controller qui permet de g�rer les URL des offres
 * de s�jours des fournisseurs.
 * @author Dylan
 *
 */
@RestController
public class TripPricerController {
    
    @Autowired
    ITripPricerService tripPricerService;
    
    /**
     * La m�thode getTripDealsUser permet de r�cup�rer la liste des offres de s�jours des
     * fournisseurs selon les pr�f�rences de l'utilisateur.
     * @param userName le nom de l'utilisateur.
     * @param apiKey la cl� d'api.
     * @return List Provider les offres de s�jours des fournisseurs.
     */
    @GetMapping("getTripDeals/{userName}")
    public List<Provider> getTripDealsUser(@PathVariable String userName, @RequestParam String apiKey) {
	List<Provider> result = new ArrayList<>();
	result = tripPricerService.getTripDealsUser(userName, apiKey);
	if(result.isEmpty())
	    throw new TripDealsError("An error occurred while searching for price of providers");
	
	return result;
    }

}
