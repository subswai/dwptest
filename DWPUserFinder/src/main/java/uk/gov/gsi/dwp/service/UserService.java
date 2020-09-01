package uk.gov.gsi.dwp.service;

import uk.gov.gsi.dwp.model.SearchCriteria;
import uk.gov.gsi.dwp.model.User;

import java.util.List;

/**
 * Interface to implement service to connect to do following steps
 * 1. Find coordinates of the location
 * 2. Get all users from provides Rest API
 * 3. Return the list of {@link uk.gov.gsi.dwp.model.User}
 * 				those are living specified distance of the Location
 */
public interface UserService {

	//Response getUsersWithinDistanceOfCity(SearchCriteria searchCriteria);

	List<User> getUsersWithinDistanceOfACity(SearchCriteria searchCriteria);

}
