package tourGuide.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CalculateRewardPointError extends RuntimeException {
    
    public CalculateRewardPointError(String s) {
	super(s);
    }

}
