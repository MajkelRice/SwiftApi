package github.ryz.swiftapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CountryNotFoundException extends IllegalArgumentException {
    public CountryNotFoundException(String message) {
        super(message);
    }
}
