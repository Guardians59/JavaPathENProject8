package ms.gpsutil.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ms.gpsutil.config.DB;
import ms.gpsutil.model.User;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UserServiceTest {
    
    @Autowired
    IUserService userService;
    
    @Autowired
    DB db;
    
    @Test
    @Order(1)
    public void getAllUsersTest() {
	//GIVEN
	List<User> listUsers = new ArrayList<>();
	//WHEN
	listUsers = userService.getAllUsers();
	//THEN
	assertEquals(listUsers.size(), 100);
    }
    
    @Test
    @Order(2)
    public void getUserTest() {
	//GIVEN
	String username = "internalUser80";
	String email = "internalUser80@tourGuide.com";
	//WHEN
	User user = userService.getUser(username);
	//THEN
	assertEquals(user != null, true);
	assertEquals(user.getEmailAddress(), email);
    }
    
    @Test
    @Order(3)
    public void getErrorUserTest() {
	//GIVEN
	String username = "internalUser800";
	//WHEN
	User user = userService.getUser(username);
	//THEN
	assertEquals(user == null, true);
    }
    
    @Test
    @Order(5)
    public void addUserTest() {
	//GIVEN
	User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
	List<User> listUsers = new ArrayList<>();
	//WHEN
	userService.addUser(user);
	listUsers = userService.getAllUsers();
	//THEN
	assertEquals(listUsers.size(), 101);
    }
    
    @Test
    @Order(4)
    public void addErrorUserTest() {
	//GIVEN
	User user = new User(UUID.randomUUID(), "internalUser1", "000", "internalUser1@tourGuide.com");
	List<User> listUsers = new ArrayList<>();
	//WHEN
	userService.addUser(user);
	listUsers = userService.getAllUsers();
	//THEN
	assertEquals(listUsers.size(), 100);
    }

}
