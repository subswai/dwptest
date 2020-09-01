package uk.gov.gsi.dwp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import uk.gov.gsi.dwp.model.SearchCriteria;
import uk.gov.gsi.dwp.model.User;
import uk.gov.gsi.dwp.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    UserService userService;

    @MockBean
    RestTemplate template;

    private static final String VALID_URI="/api/finder/city/London/users";
    private static final String DISTANCE = "distance";

    @BeforeEach
    public void setUp()
    {
        when(userService.getUsersWithinDistanceOfACity(Mockito.any(SearchCriteria.class)))
                .thenReturn(getUsers());
    }

    @Test
    void shouldReturnAllUsersWithinLondon() throws Exception {
        mvc.perform(get(VALID_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].first_name",
                        containsInAnyOrder("Andrew","Stephen","Tiffi")))
                .andExpect(jsonPath("$[*].last_name",
                        containsInAnyOrder("Seabrocke","Mapstone","Colbertson")))
                .andReturn();
    }


    @Test
    void shouldReturnAllUsersWithin50MilesOfLondon() throws Exception {
        mvc.perform(get(VALID_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("distance", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andReturn();
    }

    @Test
    void shouldReturnNotFoundIfInvalidLocationIsProvided() throws Exception {
        mvc.perform(get("/api/finder/city/London/invalid" )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param(DISTANCE, "50"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void shouldReturnBadRequestIfDistanceIsInvalid() throws Exception {
        mvc.perform(get(VALID_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param(DISTANCE, "invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andReturn();
    }

    @Test
    void shouldReturnInternalServerErrorIfException() throws Exception {
        when(userService.getUsersWithinDistanceOfACity(Mockito.any(SearchCriteria.class)))
                .thenThrow(new RuntimeException("Error"));

        mvc.perform(get(VALID_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param(DISTANCE, "50"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andReturn();
    }

    private List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().firstName("Andrew").lastName("Seabrocke").build());
        users.add(User.builder().firstName("Stephen").lastName("Mapstone").build());
        users.add(User.builder().firstName("Tiffi").lastName("Colbertson").build());
        return users;
    }

}