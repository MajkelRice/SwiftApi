package github.ryz.swiftapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BankNotFoundException extends IllegalArgumentException {
    public BankNotFoundException(String message) {
        super(message);
    }
}
