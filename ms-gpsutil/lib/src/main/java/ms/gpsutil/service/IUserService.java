package ms.gpsutil.service;

import java.util.List;

import ms.gpsutil.model.User;

public interface IUserService {

    public User getUser(String userName);

    public List<User> getAllUsers();

    public void addUser(User user);

}
