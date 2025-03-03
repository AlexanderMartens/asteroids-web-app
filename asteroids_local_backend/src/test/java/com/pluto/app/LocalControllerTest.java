package com.pluto.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies correctness of HTTP responses from local backend for user logins
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
		String expected = "{\"success\": \"false\"},{\"error\": \"Invalid login credentials\"}";
		assertEquals(expected, response.getBody());
	}

	/**
	 * Verifies response for login call with specified arguments
	 */
	@Test
	void testLoginResponse() {
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/login?name=steve boss&pass=456", String.class);
		String expected = "{\"success\": \"false\"},{\"error\": \"Invalid login credentials\"}";
		assertEquals(expected, response.getBody());
	}

	/**
	 * Verifies response for register call with default arguments
	 */
	@Test
	void testDefaultRegisterResponse() {
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/register", String.class);
		String expected = "{\"success\": \"false\"},{\"error\": \"User already exists\"}";
		assertEquals(expected, response.getBody());
	}

	/**
	 * Verifies response for register call with specified arguments
	 */
	@Test
	void testRegisterResponse() {
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/register?name=steve boss&pass=456", String.class);
		String expected = "{\"success\": \"false\"},{\"error\": \"User already exists\"}";
		assertEquals(expected, response.getBody());
	}

}
