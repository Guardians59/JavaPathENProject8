package ms.rewardcentral.controller;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ms.rewardcentral.controller.exception.RewardException;
import ms.rewardcentral.service.IRewardCentralService;

/**
 * La classe RewardCentralController est le controller qui permet de gérer les URL
 * du micro-service RewardCentral.
 * 
 * @author Dylan
 *
 */
@RestController
public class RewardCentralController {

    @Autowired
    IRewardCentralService rewardCentralService;
    
    /**
     * La méthode getRewardPoints permet de calculer les rewards disponibles selon l'attraction
     * et l'utilisateur.
     * @param attractionId l'id de l'attraction.
     * @param userId l'id de l'utilisateur.
     * @return HashMap String Integer avec la clé reward et sa valeur.
     */
    @GetMapping("/getRewardPoints")
    public HashMap<String, Integer> getRewardPoints(@RequestParam UUID attractionId, @RequestParam UUID userId) {
	HashMap<String, Integer> result = new HashMap<>();
	result = rewardCentralService.getRewardPoints(attractionId, userId);
	if(!result.containsKey("reward")) {
	    throw new RewardException("An error occurred while searching for if reward available");
	}
	return result;
    }
    
    /**
     * La méthode getRewardPointsProxy permet d'envoyer le resultat du calcul des rewards disponibles
     * selon l'attraction et l'utilisateur, à TourGuide via feign.
     * @param mapId les id nécessaires, l'id de l'attraction ainsi que celui de l'utilisateur.
     * @return HashMap String Integer avec la clé reward et sa valeur.
     */
    @PostMapping("/getRewardPoints")
    public HashMap<String, Integer> getRewardPointsProxy(@RequestBody HashMap<String, Object> mapId) {
	HashMap<String, Integer> result = new HashMap<>();
	String idAttraction = mapId.get("attractionId").toString();
	UUID attractionId = UUID.fromString(idAttraction);
	String id = mapId.get("userId").toString();
	UUID userId = UUID.fromString(id);
	result = rewardCentralService.getRewardPoints(attractionId, userId);
	if(!result.containsKey("reward")) {
	    throw new RewardException("An error occurred while searching for if reward available");
	}
	
	return result;
    }
}
