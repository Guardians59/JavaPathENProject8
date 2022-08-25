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
 * La classe UserRewardController permet de g�rer les URL de calcul des rewards.
 * @author Dylan
 *
 */
@RestController
public class UserRewardController {
    
    @Autowired
    IUserRewardsService userRewardsService;
    
    /**
     * La m�thode calculateRewardPoint permet de calculer les rewards disponibles pour
     * l'utilisateur selon ses lieux visit�s.
     * @param userName le nom de l'utilisateur.
     * @return HashMap avec le nom de l'utilisateur et le model UserReward avec ses informations de
     * localisation, l'attraction et le nombre de reward.
     */
    @ApiOperation(value = "R�cup�re le r�sultat du calcul des rewards de l'utilisateur selon ses lieux visit�s.")
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
     * La m�thode cumulativeRewardPoint permet de r�cup�rer le nombre total de reward gagner
     * par l'utilisateur.
     * @param userName le nom de l'utilisateur.
     * @return int le nombre total de reward enregistr�s.
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
     * La m�thode getAllRewardsThread permet de calculer les rewards disponibles pour tous les
     * utilisateurs selon leurs lieux visit�s, en utilisant le multithreading pour plus de rapidit�.
     * @return HashMap avec les noms des utilisateurs et le model UserReward avec leurs informations de
     * localisation, l'attraction et le nombre de reward.
     */
    @ApiOperation(value = "R�cup�re le r�sultat du calcul des rewards de tous les utilisateurs selon leurs lieux visit�s.")
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
