package tourGuide.proxies;

import java.util.HashMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * L'interface IMicroServiceRewardCentralProxy est le service qui nous permet d'utiliser
 * le micro-service RewardCentral depuis feign.
 * @author Dylan
 *
 */
@FeignClient(name = "microservice-rewardcentral", url = "localhost:8082")
public interface IMicroServiceRewardCentralProxy {
    /**
     * La méthode getRewardPoints permet de calculer les rewards disponibles selon l'attraction
     * et l'utilisateur.
     * @param mapId les id nécessaires, l'id de l'attraction ainsi que celui de l'utilisateur.
     * @return HashMap String Integer avec la clé reward et sa valeur.
     */
    @GetMapping(value = "/getRewardPoints")
    HashMap<String, Integer> getRewardPoints(HashMap<String, Object> mapId);

}
