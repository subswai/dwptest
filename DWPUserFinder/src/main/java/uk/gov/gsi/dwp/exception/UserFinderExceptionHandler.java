package uk.gov.gsi.dwp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class UserFinderExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        return new ResponseEntity(createResponse(ex, INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity(createResponse(ex, NOT_FOUND), NOT_FOUND);
    }

    @ExceptionHandler(RestClientException.class)
    @ResponseStatus(SERVICE_UNAVAILABLE)
    public final ResponseEntity<Object> handleRestClientException() {
        return new ResponseEntity(createResponse(new Exception(
                "Service Not Available for https://bpdts-test-ap.herokuapp.com "), SERVICE_UNAVAILABLE), SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(LocationNotImplementedException.class)
    @ResponseStatus(NOT_IMPLEMENTED)
    public final ResponseEntity<Object> handleLocationNotImplementedException(LocationNotImplementedException ex) {
        return new ResponseEntity(createResponse(ex, NOT_IMPLEMENTED), NOT_IMPLEMENTED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        return new ResponseEntity(createResponse(ex, BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(Exception ex,
                                                                            WebRequest request) {
        return handleExceptionInternal(ex, createResponse(ex, BAD_REQUEST),
                new HttpHeaders(), BAD_REQUEST, request);
    }

    private ExceptionResponse createResponse(Exception ex, HttpStatus status) {
        return new ExceptionResponse(new Date(), status.value(), ex.getMessage());
    }
}
