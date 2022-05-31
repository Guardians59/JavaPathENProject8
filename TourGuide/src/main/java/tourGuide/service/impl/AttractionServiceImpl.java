package tourGuide.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tourGuide.model.Attraction;
import tourGuide.model.User;
import tourGuide.proxies.IMicroServiceGPSUtilProxy;
import tourGuide.repositories.UserRepository;
import tourGuide.service.IAttractionService;

@Service
public class AttractionServiceImpl implements IAttractionService {
    
    @Autowired
    UserRepository userRepository;
    
    private Logger logger = LoggerFactory.getLogger(AttractionServiceImpl.class);
    private final IMicroServiceGPSUtilProxy gpsUtilProxy;
    
    public AttractionServiceImpl(IMicroServiceGPSUtilProxy gpsUtilProxy) {
	this.gpsUtilProxy = gpsUtilProxy;
    }

    @Override
    public List<Attraction> getNearByAttractions(String userName) {
	List<Attraction> result = new ArrayList<>();
	User user = new User();
	user = userRepository.getUser(userName);
	logger.debug("Search the nearest attraction to the user " + userName);
	if(user != null) {
	    result = gpsUtilProxy.getNearByAttraction(user.getUserId());
	    if(result.isEmpty()) {
		logger.error("An error occurred while searching for the nearest attractions");
	    } else {
		logger.info("The list of nearest attractions were successfully found");
	    }
	} else {
	    logger.error("An error has occured, user with username " + userName + " is not found");
	}
	
	return result;
    }

    @Override
    public List<Attraction> getAllAttractions() {
	List<Attraction> result = new ArrayList<>();
	result = gpsUtilProxy.getAllAttractions();
	
	if(!result.isEmpty()) {
	    logger.info("The attraction list were successfully found");
	} else {
	    logger.info("The attraction list is empty");
	}
	
	return result;
    }

}
