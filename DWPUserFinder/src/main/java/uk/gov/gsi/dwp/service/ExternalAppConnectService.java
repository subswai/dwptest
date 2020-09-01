package uk.gov.gsi.dwp.service;

import org.springframework.web.client.RestClientException;
import uk.gov.gsi.dwp.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Interface to connect to provides Rest API
 * GET https://dwp-techtest.herokuapp.com/
 */
public interface ExternalAppConnectService {

    Collection<User> getAllUsersFromCity(String city)  throws RestClientException;

    Optional<User> getUserById(int id)  throws RestClientException;

    List<User> getAllUsers() throws RestClientException;
}
