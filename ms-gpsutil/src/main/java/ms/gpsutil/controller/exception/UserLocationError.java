package ms.gpsutil.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserLocationError extends RuntimeException {
    
    public UserLocationError(String s) {
	super(s);
    }

}
