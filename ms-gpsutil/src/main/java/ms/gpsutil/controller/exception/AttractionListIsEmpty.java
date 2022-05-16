package ms.gpsutil.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AttractionListIsEmpty extends RuntimeException {
    
    public AttractionListIsEmpty(String s) {
	super(s);
    }

}
