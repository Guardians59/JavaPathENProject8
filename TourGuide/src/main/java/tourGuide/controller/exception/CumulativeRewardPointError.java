package tourGuide.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CumulativeRewardPointError extends RuntimeException {

    public CumulativeRewardPointError(String s) {
	super(s);
    }
}
