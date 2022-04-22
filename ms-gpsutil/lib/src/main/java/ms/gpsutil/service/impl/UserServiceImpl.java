package ms.gpsutil.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.gpsutil.config.DB;
import ms.gpsutil.model.User;
import ms.gpsutil.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    DB db;
    
    @Override
    public User getUser(String userName) {
	return db.internalUserMap.get(userName);
    }

    @Override
    public List<User> getAllUsers() {
	return db.internalUserMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public void addUser(User user) {
	if(!db.internalUserMap.containsKey(user.getUserName())) {
		db.internalUserMap.put(user.getUserName(), user);
	}
	
    }

}
