package ms.trippricer.service;

import java.util.List;
import java.util.UUID;

import ms.trippricer.model.Provider;

public interface ITripPricerService {

    public List<Provider> getTripDeals(UUID idUser, int numberOfAdult, int numberOfChildren, int duration,
	    int cumulRewardPoints, String tripPricerApiKey);

}
