package tourGuide.performance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tourGuide.helper.InternalTestHelper;
import tourGuide.repositories.DB;
import tourGuide.repositories.UserRepository;
import tourGuide.service.IUserLocationService;
import tourGuide.service.IUserRewardsService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class TestPerformance {
    
    @Autowired
    IUserLocationService userLocationService;
    
    @Autowired
    IUserRewardsService userRewardsService;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    DB db;

    /*
     * A note on performance improvements:
     * 
     * The number of users generated for the high volume tests can be easily
     * adjusted via this method:
     * 
     * InternalTestHelper.setInternalUserNumber(100000);
     * 
     * 
     * These tests can be modified to suit new solutions, just as long as the
     * performance metrics at the end of the tests remains consistent.
     * 
     * These are performance metrics that we are trying to hit:
     * 
     * highVolumeTrackLocation: 100,000 users within 15 minutes:
     * assertTrue(TimeUnit.MINUTES.toSeconds(15) >=
     * TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     *
     * highVolumeGetRewards: 100,000 users within 20 minutes:
     * assertTrue(TimeUnit.MINUTES.toSeconds(20) >=
     * TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     */

    @Test
    @Order(1)
    public void highVolumeTrackLocation() {
	userRepository.deleteAllUsers();
	InternalTestHelper.setInternalUserNumber(100000);
	db.initializeInternalUsers();
	StopWatch stopWatch = new StopWatch();
	stopWatch.start();
	userLocationService.getAllLocationsThread();
	stopWatch.stop();

	System.out.println("highVolumeTrackLocation: Time Elapsed: "
		+ TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
	assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
    }

    @Test
    @Order(2)
    public void highVolumeGetRewards() {
	StopWatch stopWatch = new StopWatch();
	stopWatch.start();
	userRewardsService.calculateAllRewardsThread();
	stopWatch.stop();

	System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime())
		+ " seconds.");
	assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
    }

}
