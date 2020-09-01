package uk.gov.gsi.dwp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.gsi.dwp.exception.LocationNotImplementedException;
import uk.gov.gsi.dwp.exception.UserNotFoundException;
import uk.gov.gsi.dwp.model.Location;
import uk.gov.gsi.dwp.model.SearchCriteria;
import uk.gov.gsi.dwp.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    public static final String LONDON = "London";
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    LocationService mockLocationService;

    @Mock
    ExternalAppConnectService mockExternalAppConnectService;

    Location location;

    private List<User> userList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        location = new Location(LONDON,54.5,55.5);
        userList = new ArrayList<>();
        userList.add(User.builder().firstName("Andrew").lastName("Seabrocke").latitude(54.4).longitude(55.4).build());
        userList.add(User.builder().firstName("Stephen").lastName("Mapstone").latitude(54.4).longitude(55.4).build());
        userList.add(User.builder().firstName("Tiffi").lastName("Colbertson").latitude(54.4).longitude(55.4).build());
    }

    @Test
    void shouldReturnUsersWithInTheSpecifiedDistanceOfCity() {
        SearchCriteria searchCriteria = SearchCriteria.builder()
                .location(LONDON)
                .distance(50.0)
                .build();

        Mockito.when(mockLocationService.getLocation(LONDON)).thenReturn(location);
        Mockito.when(mockExternalAppConnectService.getAllUsers()).thenReturn(userList);

        List<User> userList= userService.getUsersWithinDistanceOfACity(searchCriteria);
        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(3);

        verify(mockExternalAppConnectService, times(1)).getAllUsers();
        verify(mockExternalAppConnectService,times(0)).getAllUsersFromCity(LONDON);

        verify(mockLocationService, times(1)).getLocation(LONDON);
        verifyNoMoreInteractions(mockExternalAppConnectService);
        verifyNoMoreInteractions(mockLocationService);
    }

    @Test
    void shouldThrowExceptionWhenLocationReturnsNull() {
        SearchCriteria searchCriteria = SearchCriteria.builder()
                .location(LONDON)
                .distance(50.0)
                .build();

        Mockito.when(mockLocationService.getLocation(LONDON)).thenReturn(null);

        Assertions.assertThrows(LocationNotImplementedException.class, () -> {
            userService.getUsersWithinDistanceOfACity(searchCriteria);
        });

        verify(mockExternalAppConnectService,times(0)).getAllUsers();
        verify(mockExternalAppConnectService,times(0)).getAllUsersFromCity(LONDON);

        verify(mockLocationService, times(1)).getLocation(LONDON);
        verifyNoMoreInteractions(mockExternalAppConnectService);
        verifyNoMoreInteractions(mockLocationService);
    }

    @Test
    void shouldThrowExceptionWhenNoUserAvailable() {
        SearchCriteria searchCriteria = SearchCriteria.builder()
                .location(LONDON)
                .distance(50.0)
                .build();

        Mockito.when(mockLocationService.getLocation(LONDON)).thenReturn(location);
        Mockito.when(mockExternalAppConnectService.getAllUsers()).thenReturn(new ArrayList<User>());

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.getUsersWithinDistanceOfACity(searchCriteria);
        });
        verify(mockExternalAppConnectService, times(1)).getAllUsers();
        verify(mockExternalAppConnectService,times(0)).getAllUsersFromCity(LONDON);

        verify(mockLocationService, times(1)).getLocation(LONDON);
        verifyNoMoreInteractions(mockExternalAppConnectService);
        verifyNoMoreInteractions(mockLocationService);
    }




}
