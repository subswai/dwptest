package uk.gov.gsi.dwp.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uk.gov.gsi.dwp.model.SearchCriteria;
import uk.gov.gsi.dwp.model.User;
import uk.gov.gsi.dwp.service.UserService;

import java.util.List;

/**
 * Represents REST endpoint which calls this provides API,
 * and returns people who are listed as either living in London,
 * or whose current coordinates are within 50 miles of London.
 *
 * GET http://localhost:8282/api/finder/city/London/users
 * GET http://localhost:8282/api/finder/city/London/users?distance=500
 *
 * @author Subhasis Swain
 */
@RestController
@RequestMapping("/api/finder")
@OpenAPIDefinition(
		info = @Info(
				title = "DWP User Finder", version = "1.0",
				description = "DWP User Finder is a REST API which provides Users " +
						"who are listed as either living in London, " +
						"or whose current coordinates are within 50 miles of London." +
						"Distance from the location is defaulted to 50 miles although can be changed.",
				contact = @Contact(name = "Subhasis Swain", email = "swain.subhasis@gmail.com")
		)
)
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "city/{location}/users")
	public List<User> getUserWithinCityDistance(
			@PathVariable String location, 
			@RequestParam(name = "distance", defaultValue = "50") double distance)
	{
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.location(location)
				.distance(distance)
				.build();
		return userService.getUsersWithinDistanceOfACity(searchCriteria);
	}
}
