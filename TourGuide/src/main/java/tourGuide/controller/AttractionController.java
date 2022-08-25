package tourGuide.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import tourGuide.controller.exception.ListAllAttractionsIsEmpty;
import tourGuide.controller.exception.ListNearByAttractionsIsEmpty;
import tourGuide.model.Attraction;
import tourGuide.service.IAttractionService;

/**
 * La classe AttractionController est le controller qui permet de g�rer les URL renvoyant des
 * attractions.
 * @author Dylan
 *
 */
@RestController
public class AttractionController {
    
    @Autowired
    IAttractionService attractionService;
    
    /**
     * La m�thode getNearByAttractions permet de r�cup�rer la liste des 5 attractions les plus
     * proches de la position de l'utilisateur.
     * @param userName le nom de l'utilisateur.
     * @return List Attraction des 5 attractions les plus proches.
     */
    @ApiOperation(value = "R�cup�re la liste des 5 attractions les plus proches de l'utilisateur.")
    @GetMapping("getNearByAttractions/{userName}")
    public List<Attraction> getNearByAttractions(@PathVariable String userName) {
	List<Attraction> result = new ArrayList<>();
	result = attractionService.getNearByAttractions(userName);
	if(result.isEmpty())
	    throw new ListNearByAttractionsIsEmpty(
		    "An error occurred while searching for the nearest attractions");
	
	return result;
    }
    
    /**
     * La m�thode getAllAttractions permet de r�cup�rer la liste de toutes les attractions
     * enregistr�es.
     * @return List Attraction les attractions.
     */
    @ApiOperation(value = "R�cup�re la liste des attractions.")
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
