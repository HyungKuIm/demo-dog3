package com.oraclejava.demodog.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class SpaWebConfigTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void clientRoutesServeIndexHtml() throws Exception {
		for (String path : new String[] { "/dog/add", "/dog/update/1" }) {
			mockMvc.perform(get(path))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("<div id=\"root\">")));
		}
	}

	@Test
	void rootForwardsToIndexHtml() throws Exception {
		// "/" 는 Spring Boot welcome page 가 index.html 로 forward 한다 (MockMvc 는 forward 를 따라가지 않음)
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(forwardedUrl("index.html"));
	}

	@Test
	void staticFileIsServedAsIs() throws Exception {
		mockMvc.perform(get("/favicon.svg")).andExpect(status().isOk())
			.andExpect(content().string(containsString("<svg")));
	}

	@Test
	void unknownFileAndApiAreNotFound() throws Exception {
		mockMvc.perform(get("/assets/nope.js")).andExpect(status().isNotFound());
		mockMvc.perform(get("/api/unknown")).andExpect(status().isNotFound());
	}

}
