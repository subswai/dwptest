package uk.gov.gsi.dwp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class LocationServiceImplTest {

    @InjectMocks
    LocationServiceImpl locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnLocationDetails() {
        locationService.setDataFilePath("file:src/test/resources/gb.json");
        assertThat(locationService.getLocation("Glasgow")).isNotNull();
    }

    @Test
    public void shouldReturnNullIfLocationAvailable() {
        locationService.setDataFilePath("file:src/test/resources/gb.json");
        assertThat(locationService.getLocation("Paris")).isNull();
    }

    @Test
    public void shouldReturnDefaultLocationDetailsIfPathNotValid() {
        locationService.setDataFilePath("file:src/test/resources/invalid.json");
        assertThat(locationService.getLocation("London")).isNotNull();
    }
}
