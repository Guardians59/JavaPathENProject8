package ms.rewardcentral.service;

import java.util.HashMap;
import java.util.UUID;

public interface IRewardCentralService {

    public HashMap<String, Integer> getRewardPoints(UUID attractionId, UUID userId);

}
