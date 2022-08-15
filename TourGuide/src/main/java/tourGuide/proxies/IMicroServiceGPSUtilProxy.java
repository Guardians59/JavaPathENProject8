package tourGuide.proxies;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import tourGuide.model.Attraction;
import tourGuide.model.VisitedLocation;
/**
 * L'interface IMicroServiceGPSUtilProxy est le service qui nous permet d'utiliser le
 * micro-service GPSUtil depuis feign.
 * @author Dylan
 *
 */
@FeignClient(name = "microservice-gpsutil", url = "localhost:8081")
public interface IMicroServiceGPSUtilProxy {
    /**
     * La méthode getLocation permet de récupérer la localisation de l'utilisateur.
     * @param userId l'id de l'utilisateur.
     * @return VisitedLocation la localisation de l'utilisateur avec les informations
     * (Id, Location, Date).
     */
    @GetMapping(value = "/getLocation")
    VisitedLocation getLocation(UUID userId);
    /**
     * La méthode getNearByAttraction permet de récupérer la liste des 5 attractions les plus
     * proches de l'utilisateur.
     * @param userId l'id de l'utilisateur.
     * @return List d'attractions, les attractions les plus proches.
     */
    @GetMapping(value = "/getNearByAttraction")
    List<Attraction> getNearByAttraction(UUID userId);
    /**
     * La méthode getAllAttractions permet de récupérer la liste des attractions.
     * @return List d'attractions, les attractions enregistrées.
     */
    @GetMapping(value = "/getAllAttractions")
    List<Attraction> getAllAttractions();
}
