package uk.gov.gsi.dwp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.gsi.dwp.controller.UserController;

@SpringBootTest
class DwpUserFinderApplicationTests {

	@Autowired
	private UserController controller;

	@Test
	void contextLoads() {
		Assertions.assertThat(controller).isNotNull();
	}

}
