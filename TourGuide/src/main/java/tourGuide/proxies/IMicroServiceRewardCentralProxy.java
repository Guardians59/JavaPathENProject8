package tourGuide.proxies;

import java.util.HashMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import tourGuide.model.FeignRewardPointsModel;

@FeignClient(name = "microservice-rewardcentral", url = "localhost:8082")
public interface IMicroServiceRewardCentralProxy {
    
    @GetMapping(value = "/getRewardPoints")
    HashMap<String, Integer> getRewardPoints(FeignRewardPointsModel feignRewardPointsModel);

}
