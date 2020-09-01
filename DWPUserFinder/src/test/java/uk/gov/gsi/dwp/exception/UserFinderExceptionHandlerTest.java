package uk.gov.gsi.dwp.exception;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class UserFinderExceptionHandlerTest {

    HttpHeaders headers = new HttpHeaders();

    @Mock
    WebRequest request;

    @InjectMocks
    UserFinderExceptionHandler exceptionHandler;

    @Test
    void shouldHandleAllExceptionThrown() {

        IllegalArgumentException exception = mock(IllegalArgumentException.class);
        ResponseEntity<Object> re = exceptionHandler.handleAllExceptions(exception);

        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(re.getBody()).isInstanceOf(ExceptionResponse.class);
        ExceptionResponse exceptionResponse = (ExceptionResponse) re.getBody();
        assertThat(exceptionResponse.getMessage()).isNull();
        assertThat(exceptionResponse.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(exceptionResponse.getTimestamp()).isNotNull();
    }

    @Test
    void shouldHandleUserNotFoundExceptionWhenExceptionThrown() {

        UserNotFoundException exception = mock(UserNotFoundException.class);
        Mockito.when(exception.getMessage()).thenReturn("User Not Found");

        ResponseEntity<Object> re = exceptionHandler.handleUserNotFoundException(exception);

        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(re.getBody()).isInstanceOf(ExceptionResponse.class);
        ExceptionResponse exceptionResponse = (ExceptionResponse) re.getBody();
        assertThat(exceptionResponse.getMessage()).contains("User Not Found");
        assertThat(exceptionResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(exceptionResponse.getTimestamp()).isNotNull();
    }

    @Test
    void shouldHandleLocationNotImplementedExceptionWhenThrown() {

        LocationNotImplementedException exception = mock(LocationNotImplementedException.class);
        Mockito.when(exception.getMessage()).thenReturn("Location not implemented");

        ResponseEntity<Object> re = exceptionHandler.handleLocationNotImplementedException(exception);

        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.NOT_IMPLEMENTED);
        assertThat(re.getBody()).isInstanceOf(ExceptionResponse.class);
        ExceptionResponse exceptionResponse = (ExceptionResponse) re.getBody();
        assertThat(exceptionResponse.getMessage()).contains("Location not implemented");
        assertThat(exceptionResponse.getStatus()).isEqualTo(HttpStatus.NOT_IMPLEMENTED.value());
        assertThat(exceptionResponse.getTimestamp()).isNotNull();
    }

    @Test
    void shouldHandleMethodArgumentNotValidWhenExceptionThrown() {

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        Mockito.when(exception.getMessage()).thenReturn("Validation Failed");
        ResponseEntity<Object> re = exceptionHandler.handleMethodArgumentNotValid(exception, headers, HttpStatus.BAD_REQUEST, request);

        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(re.getBody()).isInstanceOf(ExceptionResponse.class);
        ExceptionResponse exceptionResponse = (ExceptionResponse) re.getBody();
        assertThat(exceptionResponse.getMessage()).isNotNull();
        assertThat(exceptionResponse.getMessage()).isEqualTo("Validation Failed");
        assertThat(exceptionResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(exceptionResponse.getTimestamp()).isNotNull();
    }

    @Test
    void shouldHandleMethodArgumentTypeMismatchExceptionWhenExceptionThrown() {

        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        Mockito.when(exception.getMessage()).thenReturn("Validation Failed");
        ResponseEntity<Object> re = exceptionHandler.handleMethodArgumentTypeMismatchException(exception, request);

        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(re.getBody()).isInstanceOf(ExceptionResponse.class);
        ExceptionResponse exceptionResponse = (ExceptionResponse) re.getBody();
        assertThat(exceptionResponse.getMessage()).isEqualTo("Validation Failed");
        assertThat(exceptionResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(exceptionResponse.getTimestamp()).isNotNull();
    }


    @Test
    void shouldHandleRestClientExceptionWhenExceptionThrown() {
        ResponseEntity<Object> re = exceptionHandler.handleRestClientException();

        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
        assertThat(re.getBody()).isInstanceOf(ExceptionResponse.class);
        ExceptionResponse exceptionResponse = (ExceptionResponse) re.getBody();
        assertThat(exceptionResponse.getMessage()).isEqualTo("Service Not Available for https://bpdts-test-ap.herokuapp.com ");
        assertThat(exceptionResponse.getStatus()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE.value());
        assertThat(exceptionResponse.getTimestamp()).isNotNull();
    }
}
