package tourGuide.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import tourGuide.controller.exception.ListAllAttractionsIsEmpty;
import tourGuide.controller.exception.ListNearByAttractionsIsEmpty;
import tourGuide.model.Attraction;
import tourGuide.service.IAttractionService;

@RestController
public class AttractionController {
    
    @Autowired
    IAttractionService attractionService;
    
    @GetMapping("getNearByAttractions/{userName}")
    public List<Attraction> getNearByAttractions(@PathVariable String userName) {
	List<Attraction> result = new ArrayList<>();
	result = attractionService.getNearByAttractions(userName);
	if(result.isEmpty())
	    throw new ListNearByAttractionsIsEmpty(
		    "An error occurred while searching for the nearest attractions");
	
	return result;
    }
    
    @GetMapping("getAllAttractions")
    public List<Attraction> getAllAttractions() {
	List<Attraction> result = new ArrayList<>();
	result = attractionService.getAllAttractions();
	if(result.isEmpty())
	    throw new ListAllAttractionsIsEmpty(
		    "The attraction list is empty");
	
	return result;
    }

}
