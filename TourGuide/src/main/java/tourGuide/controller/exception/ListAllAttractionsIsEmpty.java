package tourGuide.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ListAllAttractionsIsEmpty extends RuntimeException {

    public ListAllAttractionsIsEmpty(String s) {
	super(s);
    }
}
