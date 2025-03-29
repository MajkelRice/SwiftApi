package github.ryz.swiftapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.io.FileNotFoundException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SwiftCodesCSVNotFoundException extends FileNotFoundException {
    public SwiftCodesCSVNotFoundException() {
        super("swift_codes.csv not found in resources.");
    }

    public SwiftCodesCSVNotFoundException(String message) {
        super(message);
    }
}
