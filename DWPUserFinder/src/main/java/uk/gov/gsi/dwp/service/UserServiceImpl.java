package uk.gov.gsi.dwp.service;

import com.tomtom.speedtools.geometry.GeoCircle;
import com.tomtom.speedtools.geometry.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.gsi.dwp.exception.LocationNotImplementedException;
import uk.gov.gsi.dwp.exception.UserNotFoundException;
import uk.gov.gsi.dwp.model.Location;
import uk.gov.gsi.dwp.model.SearchCriteria;
import uk.gov.gsi.dwp.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	private static final Double METRES_IN_MILE = 1609.34;

	@Autowired
	private ExternalAppConnectService externalAppConnectService;

	@Autowired
	private LocationService locationService;

	public List<User> getUsersWithinDistanceOfACity(SearchCriteria searchCriteria) {
		// Find coordinates of the location
		Location cityLocation = locationService.getLocation(searchCriteria.getLocation());
		if (cityLocation == null) {
			throw new LocationNotImplementedException("User Finder Service is not implemented for " + searchCriteria.getLocation());
		}

		GeoPoint geoPoint = new GeoPoint(cityLocation.getLatitude(), cityLocation.getLongitude());
		Double radiusInMeter = METRES_IN_MILE * searchCriteria.getDistance();

		GeoCircle geoCircle = new GeoCircle(geoPoint, radiusInMeter);
		List<User> userList = externalAppConnectService.getAllUsers().stream()
				.filter(user -> geoCircle.contains(geoPointOf(user)))
				.collect(Collectors.toList());

		if (userList.isEmpty()) {
			throw new UserNotFoundException("No User living in or around the city of " + searchCriteria.getLocation());}
		return userList;
	}

	private GeoPoint geoPointOf(User user) {
		return new GeoPoint(user.getLatitude(), user.getLongitude());
	}

}