package ms.trippricer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ms.trippricer.controller.exception.ProviderException;
import ms.trippricer.service.ITripPricerService;
import tripPricer.Provider;

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

}
