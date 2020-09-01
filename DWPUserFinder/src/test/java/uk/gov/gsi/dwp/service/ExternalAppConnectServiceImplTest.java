package uk.gov.gsi.dwp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import uk.gov.gsi.dwp.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ExternalAppConnectServiceImplTest {

    private static final String baseUrl = "http://dwp-techtest.herokuapp.com";

    ExternalAppConnectServiceImpl connectService;

    @BeforeEach
    void setUp() {
        connectService = new ExternalAppConnectServiceImpl();
        connectService.baseUrl=baseUrl;
        connectService.restTemplate = new RestTemplate();
    }

    @Test
    void ShouldGetAllUsersFromCity() {
        List<User> userList = connectService.getAllUsersFromCity("London");
        assertThat(userList).isNotNull();
    }

    @Test
    void ShouldReturnEmptyListForInvalidCity() {
        List<User> userList = connectService.getAllUsersFromCity("Leeds");
        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(0);
    }

    @Test
    void ShouldGetTheUsersForSpecificUserId() {
        Optional<User> user = connectService.getUserById(266);
        assertThat(user.get()).isNotNull();
    }

    @Test
    void shouldGetAllUsers() {
        List<User> userList = connectService.getAllUsers();
        assertThat(userList).isNotNull();
    }

    @Test
    void shouldThrowExceptionFOrInvalidUrl() {
        connectService.baseUrl="http://invalid.herokuapp.com";
        Assertions.assertThrows(HttpServerErrorException.class, () -> {
            connectService.getAllUsers();
        });
    }


}