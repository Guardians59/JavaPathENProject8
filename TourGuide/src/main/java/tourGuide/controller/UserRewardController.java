package tourGuide.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import tourGuide.controller.exception.CalculateRewardPointError;
import tourGuide.controller.exception.CumulativeRewardPointError;
import tourGuide.controller.exception.ThreadTimeOut;
import tourGuide.service.IUserRewardsService;
/**
 * La classe UserRewardController permet de gérer les URL de calcul des rewards.
 * @author Dylan
 *
 */
@RestController
public class UserRewardController {
    
    @Autowired
    IUserRewardsService userRewardsService;
    
    /**
     * La méthode calculateRewardPoint permet de calculer les rewards disponibles pour
     * l'utilisateur selon ses lieux visités.
     * @param userName le nom de l'utilisateur.
     * @return HashMap avec le nom de l'utilisateur et le model UserReward avec ses informations de
     * localisation, l'attraction et le nombre de reward.
     */
    @ApiOperation(value = "Récupère le résultat du calcul des rewards de l'utilisateur selon ses lieux visités.")
    @GetMapping("calculateRewardPoint/{userName}")
    public HashMap<String, Object> calculateRewardPoint(@PathVariable String userName) {
	HashMap<String, Object> result = new HashMap<>();
	result = userRewardsService.calculReward(userName);
	if(result.isEmpty())
	    throw new CalculateRewardPointError(
		    "An error occured while calculation of reward points to the user " + userName);
	
	return result;
    }
    /**
     * La méthode cumulativeRewardPoint permet de récupérer le nombre total de reward gagner
     * par l'utilisateur.
     * @param userName le nom de l'utilisateur.
     * @return int le nombre total de reward enregistrés.
     */
    @GetMapping("cumulativeRewardPoint/{userName}")
    public int cumulativeRewardPoint(@PathVariable String userName) {
	int result = -1;
	result = userRewardsService.cumulativeRewardPoints(userName);
	if(result == -1)
	    throw new CumulativeRewardPointError(
		    "An error occured while searching for the cumulative reward point to the user " + userName);
	
	return result;
    }
    /**
     * La méthode getAllRewardsThread permet de calculer les rewards disponibles pour tous les
     * utilisateurs selon leurs lieux visités, en utilisant le multithreading pour plus de rapidité.
     * @return HashMap avec les noms des utilisateurs et le model UserReward avec leurs informations de
     * localisation, l'attraction et le nombre de reward.
     */
    @ApiOperation(value = "Récupère le résultat du calcul des rewards de tous les utilisateurs selon leurs lieux visités.")
    @GetMapping("getAllRewards")
    public HashMap<String, Object> getAllRewardsThread() {
	HashMap<String, Object> result = userRewardsService.calculAllRewardsThread();
	if(!result.isEmpty()) {
	    return result;
	} else {
	    throw new ThreadTimeOut("Thread execution timed out, more than 20 minutes of searching");
	}
	
    }

}
