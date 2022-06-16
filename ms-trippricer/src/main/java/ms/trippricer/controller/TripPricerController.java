package ms.trippricer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ms.trippricer.controller.exception.ProviderException;
import ms.trippricer.model.Provider;
import ms.trippricer.service.ITripPricerService;


@RestController
public class TripPricerController {

    @Autowired
    ITripPricerService tripPricerService;

    @GetMapping("/getTripDeals")
    public List<Provider> getTripDeals(@RequestParam UUID idUser, @RequestParam int numberOfAdult,
	    @RequestParam int numberOfChildren, @RequestParam int duration, @RequestParam int cumulRewardPoint,
	    @RequestParam String tripPricerApiKey) {
	
	List<Provider> result = new ArrayList<>();
	result = tripPricerService.getTripDeals(idUser, numberOfAdult, numberOfChildren, duration,
		cumulRewardPoint, tripPricerApiKey);
	
	if(result.isEmpty())
	    throw new ProviderException("An error occurred while searching for price of providers");

	return result;
    }
    
    @PostMapping("/getTripDeals")
    public List<Provider> getTripDealsProxy(@RequestBody HashMap<String, Object> mapParams) {
	UUID userId = (UUID) mapParams.get("userId");
	int numberOfAdult = (int) mapParams.get("numberOfAdult");
	int numberOfChildren = (int) mapParams.get("numberOfChildren");
	int duration = (int) mapParams.get("duration");
	int cumulRewardPoint = (int) mapParams.get("cumulRewardPoint");
	String tripPricerApiKey = (String) mapParams.get("tripPricerApiKey");
	List<Provider> result = new ArrayList<>();
	
	result = tripPricerService.getTripDeals(userId, numberOfAdult, numberOfChildren,
		duration, cumulRewardPoint, tripPricerApiKey);
	
	if(result.isEmpty())
	    throw new ProviderException("An error occurred while searching for price of providers");

	return result;
    }

}
