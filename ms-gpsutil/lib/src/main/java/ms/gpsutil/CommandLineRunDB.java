package ms.gpsutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ms.gpsutil.config.DB;

@Component
public class CommandLineRunDB implements CommandLineRunner{

    @Autowired
    DB db;
    
    private Logger logger = LoggerFactory.getLogger(CommandLineRunDB.class);
    
    @Override
    public void run(String... args) throws Exception {
	logger.info("TestMode enabled for GPSUtil");
	logger.debug("Initializing users");
	db.initializeInternalUsers();
	logger.debug("Finished initializing users");
    }

}
