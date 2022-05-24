package tourGuide;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import tourGuide.repositories.DB;

@Component
public class CommandLineRunDB implements CommandLineRunner {

    @Autowired
    DB db;
   
    @Override
    public void run(String... args) throws Exception {
	db.initializeInternalUsers();
	
    }

}
