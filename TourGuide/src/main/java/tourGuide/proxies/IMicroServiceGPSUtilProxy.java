package tourGuide.proxies;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import tourGuide.model.Attraction;
import tourGuide.model.VisitedLocation;

@FeignClient(name = "microservice-gpsutil", url = "localhost:8081")
public interface IMicroServiceGPSUtilProxy {
    
    @GetMapping(value = "/getLocation")
    VisitedLocation getLocation(UUID userId);
    
    @GetMapping(value = "/getNearByAttraction")
    List<Attraction> getNearByAttraction(UUID userId);
    
    @GetMapping(value = "/getAllAttractions")
    List<Attraction> getAllAttractions();
}
