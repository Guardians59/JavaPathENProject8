package tourGuide.service;

import java.util.HashMap;
/**
 * L'interface IUserRewardsService est le service qui calcul les rewards des utilisateurs.
 * @author Dylan
 *
 */
public interface IUserRewardsService {
    /**
     * La m�thode calculReward permet de calculer les rewards disponibles pour
     * l'utilisateur selon ses lieux visit�s.
     * @param userName le nom d'utilisateur.
     * @return HashMap avec le nom de l'utilisateur et le model UserReward avec ses informations
     * de localisation, l'attraction et le nombre de reward.
     */
    public HashMap<String, Object> calculReward(String userName);
    /**
     * La m�thode cumulativeRewardPoint permet de r�cup�rer le nombre total de reward gagner
     * par l'utilisateur.
     * @param userName le nom de l'utilisateur.
     * @return int le nombre total de reward enregistr�s.
     */
    public int cumulativeRewardPoints(String userName);
    /**
     * La m�thode getAllRewards permet de r�cup�rer tous les rewards enregistr�s de tous les
     * utilisateurs enregistr�s.
     * @return HashMap avec les noms des utilisateurs et leurs rewards respectifs.
     */
    public HashMap<String, Object> getAllRewards();
    /**
     * La m�thode calculAllRewardsThread permet de calculer les rewards disponibles pour tous les
     * utilisateurs selon leurs lieux visit�s, en utilisant le multithreading pour plus de rapidit�.
     * @return HashMap avec les noms des utilisateurs et le model UserReward avec leurs informations
     * de localisation, l'attraction et le nombre de reward.
     */
    public HashMap<String, Object> calculAllRewardsThread();

}
