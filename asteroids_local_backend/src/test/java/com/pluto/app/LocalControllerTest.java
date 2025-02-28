package com.pluto.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests LocalController
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // May customize port here
class LocalControllerTest {

	/**
	 * IDK what autowired annotation does. supposed to do dependency injection or something?
	 */
	@Autowired
	private TestRestTemplate restTemplate;

	/**
	 * Injects the HTTP server port allocated at runtime
	 */
	@LocalServerPort
	private int port;

	@Test
	void contextLoads() {
	}

	/**
	 * Verifies response for login call with default arguments
	 */
	@Test
	void testDefaultLoginResponse() {
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/login", String.class);
		String expected = "{\"name\": \"John Doe\"},{\"pass\": \"123\"}";
		assertEquals(expected, response.getBody());
	}

	/**
	 * Verifies response for login call with specified arguments
	 */
	@Test
	void testLoginResponse() {
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/login?name=steve boss&pass=456", String.class);
		String expected = "{\"name\": \"steve boss\"},{\"pass\": \"456\"}";
		assertEquals(expected, response.getBody());
	}

}
