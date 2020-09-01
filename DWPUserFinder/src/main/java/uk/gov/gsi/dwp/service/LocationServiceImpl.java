package uk.gov.gsi.dwp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import uk.gov.gsi.dwp.model.Location;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LocationServiceImpl implements LocationService{

    private static final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);

    @Value("${location.data.file}")
    @Setter
    private String dataFilePath;

    private Map<String, Location> cityMap = new HashMap<String, Location>();

    public Location getLocation(String cityName) {
        Location[] cityArr = readLocationsFromJSONFile();
        if(cityArr == null)
            cityArr = this.getDefaultLocations();

        Stream.of(cityArr).forEach(x-> this.cityMap.put(x.getName().toUpperCase().trim(), x));
        return this.cityMap.get(cityName.trim().toUpperCase());
    }

    private Location[] readLocationsFromJSONFile() {
        Location[] locationArr = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            locationArr = mapper.readValue(new URL(dataFilePath), Location[].class);
        } catch (Exception e) {
            log.error("Data file cannot be read {}", e.getMessage());
        }
        return locationArr;
    }

    private Location[] getDefaultLocations() {
        return new Location[] {
                new Location("London", 51.50853, -0.12574),
                new Location("Leeds", 53.8, -1.583333),
                new Location("Manchester", 53.5, -2.216667),
        };
    }


}
