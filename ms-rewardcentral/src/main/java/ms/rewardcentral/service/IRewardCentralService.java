package ms.rewardcentral.service;

import java.util.HashMap;
import java.util.UUID;

/**
 * L'interface IRewardCentralService est le service qui nous permet de calculer les rewards.
 * @author Dylan
 *
 */
public interface IRewardCentralService {

    /**
     * La méthode getRewardPoints permet de calculer les rewards disponibles selon l'attraction
     * et l'utilisateur.
     * @param attractionId l'id de l'attraction.
     * @param userId l'id de l'utilisateur.
     * @return HashMap String Integer avec la clé reward et sa valeur.
     */
    public HashMap<String, Integer> getRewardPoints(UUID attractionId, UUID userId);

}
