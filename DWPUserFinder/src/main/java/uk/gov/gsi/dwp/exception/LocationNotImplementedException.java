package uk.gov.gsi.dwp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class LocationNotImplementedException extends RuntimeException {

    public LocationNotImplementedException(@NotNull(message = "Location name must be provided") String message) {
        super(message);
    }
}
