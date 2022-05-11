package ms.trippricer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ms.trippricer.service.ITripPricerService;
import tripPricer.Provider;
import tripPricer.TripPricer;

@Service
public class TripPricerServiceImpl implements ITripPricerService {

    private Logger logger = LoggerFactory.getLogger(TripPricerServiceImpl.class);
    private TripPricer tripPricer = new TripPricer();

    @Override
    public List<Provider> getTripDeals(UUID idUser, int numberOfAdult, int numberOfChildren, int duration,
	    int cumulRewardPoints, String tripPricerApiKey) {
	List<Provider> providers = new ArrayList<>();
	logger.debug("Search price of providers");
	if(idUser != null && numberOfAdult > 0 && duration > 0) {
	    providers = tripPricer.getPrice(tripPricerApiKey, idUser, numberOfAdult, numberOfChildren, duration,
		    cumulRewardPoints);
	    if(!providers.isEmpty()) {
		logger.info("Price of providers available");
	    } else {
		logger.info("Not price of providers available");
	    }
	} else {
	    logger.error("An error occurred while searching for price of providers");
	}
	return providers;
    }

}
