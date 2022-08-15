package tourGuide.service;

import java.util.HashMap;
/**
 * L'interface IUserRewardsService est le service qui calcul les rewards des utilisateurs.
 * @author Dylan
 *
 */
public interface IUserRewardsService {
    /**
     * La méthode calculReward permet de calculer les rewards disponibles pour
     * l'utilisateur selon ses lieux visités.
     * @param userName le nom d'utilisateur.
     * @return HashMap avec le nom de l'utilisateur et le model UserReward avec ses informations
     * de localisation, l'attraction et le nombre de reward.
     */
    public HashMap<String, Object> calculReward(String userName);
    /**
     * La méthode cumulativeRewardPoint permet de récupérer le nombre total de reward gagner
     * par l'utilisateur.
     * @param userName le nom de l'utilisateur.
     * @return int le nombre total de reward enregistrés.
     */
    public int cumulativeRewardPoints(String userName);
    /**
     * La méthode getAllRewards permet de récupérer tous les rewards enregistrés de tous les
     * utilisateurs enregistrés.
     * @return HashMap avec les noms des utilisateurs et leurs rewards respectifs.
     */
    public HashMap<String, Object> getAllRewards();
    /**
     * La méthode calculAllRewardsThread permet de calculer les rewards disponibles pour tous les
     * utilisateurs selon leurs lieux visités, en utilisant le multithreading pour plus de rapidité.
     * @return HashMap avec les noms des utilisateurs et le model UserReward avec leurs informations
     * de localisation, l'attraction et le nombre de reward.
     */
    public HashMap<String, Object> calculAllRewardsThread();

}
