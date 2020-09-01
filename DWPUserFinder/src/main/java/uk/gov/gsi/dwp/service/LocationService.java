package uk.gov.gsi.dwp.service;


import uk.gov.gsi.dwp.model.Location;

/**
 * Service to find coordinates of the City Name
 * returns {@link Location}
 */
public interface LocationService {
	public Location getLocation(String cityName);
}
