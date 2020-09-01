package uk.gov.gsi.dwp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.gov.gsi.dwp.model.User;

import java.util.*;

@Service
public class ExternalAppConnectServiceImpl implements ExternalAppConnectService {

    private static final Logger log = LoggerFactory.getLogger(ExternalAppConnectServiceImpl.class);

    @Value("${bpdts.api.url}")
    protected String baseUrl;

    @Autowired
    protected RestTemplate restTemplate;

    @Override
    public List<User> getAllUsersFromCity(String city) {

        log.debug("Getting All Users from {}", city);
        return restTemplate.exchange(baseUrl + "/city/" + city + "/users", HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {}).getBody();
    }

    @Override
    public Optional<User> getUserById(int id) {
        log.debug("Get User By ID {}", id);
        ResponseEntity<User> response = restTemplate.getForEntity(baseUrl + "/user/" + id, User.class);
        return Optional.ofNullable(response.getBody());
    }

    public List<User> getAllUsers() {
        log.debug("Get All Users");
        List<User> users= restTemplate.exchange(baseUrl+"/users", HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {}).getBody();
        return users;
    }

}
