package tourGuide.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import tourGuide.model.User;

public class UserRepository {
    
    @Autowired
    DB db;

    public User getUser(String userName) {
	return db.internalUserMap.get(userName);
    }

    public List<User> getAllUsers() {
	return db.internalUserMap.values().stream().collect(Collectors.toList());
    }

    public void addUser(User user) {
	if(!db.internalUserMap.containsKey(user.getUserName())) {
	    db.internalUserMap.put(user.getUserName(), user);
	}
    }

}
