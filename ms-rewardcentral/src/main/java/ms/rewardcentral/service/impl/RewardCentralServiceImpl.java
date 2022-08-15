package ms.rewardcentral.service.impl;

import java.util.HashMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ms.rewardcentral.service.IRewardCentralService;
import rewardCentral.RewardCentral;

/**
 * La classe RewardCentralServiceImpl est l'implémentation de l'interface de IRewardCentralService.
 * 
 * @see IRewardCentralService
 * @author Dylan
 *
 */
@Service
public class RewardCentralServiceImpl implements IRewardCentralService {

    private Logger logger = LoggerFactory.getLogger(RewardCentralServiceImpl.class);
    public RewardCentral rewardCentral = new RewardCentral();

    @Override
    public HashMap<String, Integer> getRewardPoints(UUID attractionId, UUID userId) {
	int result = 0;
	HashMap<String, Integer> map = new HashMap<>();
	logger.debug("Search the rewards available");
	/*
	 * Nous récupérons les rewards disponibles selon l'attraction et l'utilisateur via
	 * rewardCentral.
	 */
	if(attractionId != null && userId != null) {
	    result = rewardCentral.getAttractionRewardPoints(attractionId, userId);
	    if(result > 0) {
		map.put("reward", result);
		logger.info("Reward available");
	    } else {
		logger.info("Not reward available");
	    }
	} else {
	    logger.error("An error occurred while searching for if reward available");
	}
	return map;
    }
}
