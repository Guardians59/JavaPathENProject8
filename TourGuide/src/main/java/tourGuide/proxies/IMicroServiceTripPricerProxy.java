package tourGuide.proxies;

import java.util.HashMap;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import tourGuide.model.Provider;
/**
 * L'interface IMicroServiceTripPricerProxy est le service qui nous permet d'utiliser le
 * micro-service TripPricer depuis feign.
 * @author Dylan
 *
 */
@FeignClient(name = "microservice-trippricer", url = "localhost:8083")
public interface IMicroServiceTripPricerProxy {
    /**
     * La m�thode getTripDeals permet de r�cup�rer une liste d'offre de s�jour proposer par
     * les fournisseurs selon les pr�f�rences de l'utilisateur.
     * @param mapParams map contenant tous les param�tres n�cessaires au fonctionnement du service.
     * @return List Provider des s�jours propos�s.
     */
    @GetMapping(value = "/getTripDeals")
    List<Provider> getTripDeals(HashMap<String, Object> mapParams);

}
