package ms.trippricer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ms.trippricer.controller.exception.ProviderException;
import ms.trippricer.model.Provider;
import ms.trippricer.service.ITripPricerService;

/**
 * La classe TripPricerController est le controller qui permet de g�rer les URL du micro-service
 * TripPricer.
 * 
 * @author Dylan
 *
 */
@Api("API des offres de s�jours")
@RestController
public class TripPricerController {

    @Autowired
    ITripPricerService tripPricerService;

    /**
     * La m�thode getTripDeals permet de r�cup�rer une liste d'offre de s�jour proposer par
     * les fournisseurs selon les pr�f�rences de l'utilisateur.
     * @param idUser l'id de l'utilisateur.
     * @param numberOfAdult le nombre d'adulte.
     * @param numberOfChildren le nombre d'enfant.
     * @param duration la dur�e du s�jour.
     * @param cumulRewardPoint les rewards de l'utilisateur disponibles.
     * @param tripPricerApiKey la cl� d'api.
     * @return List Provider des s�jours propos�s.
     */
    @ApiOperation(value = "Recupere la liste des offres de sejours des fournisseurs.")
    @GetMapping("/getTripDeals")
    public List<Provider> getTripDeals(@RequestParam UUID idUser, @RequestParam int numberOfAdult,
	    @RequestParam int numberOfChildren, @RequestParam int duration, @RequestParam int cumulRewardPoint,
	    @RequestParam String tripPricerApiKey) {
	
	List<Provider> result = new ArrayList<>();
	result = tripPricerService.getTripDeals(idUser, numberOfAdult, numberOfChildren, duration,
		cumulRewardPoint, tripPricerApiKey);
	
	if(result.isEmpty())
	    throw new ProviderException("An error occurred while searching for price of providers");

	return result;
    }
    
    /**
     * La m�thode getTripDealsProxy permet d'envoyer la liste de s�jour proposer par les
     * fournisseurs � TourGuide via feign.
     * @param mapParams map contenant tous les param�tres n�cessaires au fonctionnement du service.
     * @return List Provider des s�jours propos�s.
     */
    @ApiOperation(value = "Envoie la liste des offres de sejours des fournisseurs au proxy.")
    @PostMapping("/getTripDeals")
    public List<Provider> getTripDealsProxy(@RequestBody HashMap<String, Object> mapParams) {
	String id = mapParams.get("userId").toString();
	UUID userId = UUID.fromString(id);
	int numberOfAdult = (int) mapParams.get("numberOfAdult");
	int numberOfChildren = (int) mapParams.get("numberOfChildren");
	int duration = (int) mapParams.get("duration");
	int cumulRewardPoint = (int) mapParams.get("cumulRewardPoint");
	String tripPricerApiKey = (String) mapParams.get("tripPricerApiKey");
	List<Provider> result = new ArrayList<>();
	
	result = tripPricerService.getTripDeals(userId, numberOfAdult, numberOfChildren,
		duration, cumulRewardPoint, tripPricerApiKey);
	
	if(result.isEmpty())
	    throw new ProviderException("An error occurred while searching for price of providers");

	return result;
    }

}
