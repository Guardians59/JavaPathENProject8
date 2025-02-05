package tourGuide.repositories;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import tourGuide.helper.InternalTestHelper;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;
@Repository
public class DB {

    private Logger logger = LoggerFactory.getLogger(DB.class);

    public final Map<String, User> internalUserMap = new HashMap<>();

    public void initializeInternalUsers() {
	IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
	    String userName = "internalUser" + i;
	    String phone = "000";
	    String email = userName + "@tourGuide.com";
	    User user = new User(UUID.randomUUID(), userName, phone, email);
	    generateUserLocationHistory(user);

	    internalUserMap.put(userName, user);
	});
	logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
    }

    private void generateUserLocationHistory(User user) {
	IntStream.range(0, 3).forEach(i -> {
	    user.addToListVisitedLocations(new VisitedLocation(user.getUserId(),
		    new Location(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
	});
    }

    private double generateRandomLongitude() {
	double leftLimit = -180;
	double rightLimit = 180;
	return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    private double generateRandomLatitude() {
	double leftLimit = -85.05112878;
	double rightLimit = 85.05112878;
	return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    private Date getRandomTime() {
	LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
	return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }

}
