package tourGuide.proxies;

import java.util.HashMap;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import tourGuide.model.Provider;

@FeignClient(name = "microservice-trippricer", url = "localhost:8083")
public interface IMicroServiceTripPricerProxy {
    
    @GetMapping(value = "/getTripDeals")
    List<Provider> getTripDeals(HashMap<String, Object> mapParams);

}
