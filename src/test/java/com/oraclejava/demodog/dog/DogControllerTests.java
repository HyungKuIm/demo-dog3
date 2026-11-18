package com.oraclejava.demodog.dog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class DogControllerTests {

	private static final String BODY = """
			{"kind":"Jindo","price":500000,"image":"jindo.jpg","country":"Korea",
			 "height":50.5,"weight":18.2,"content":"Loyal dog"}
			""";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void crudFlow() throws Exception {
		String location = mockMvc.perform(post("/api/dogs").contentType(MediaType.APPLICATION_JSON).content(BODY))
			.andExpect(status().isCreated())
			.andExpect(header().exists("Location"))
			.andExpect(jsonPath("$.kind").value("Jindo"))
			.andExpect(jsonPath("$.readcount").value(0))
			.andReturn().getResponse().getHeader("Location");

		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(jsonPath("$.readcount").value(1));
		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(jsonPath("$.readcount").value(2));

		mockMvc.perform(get("/api/dogs")).andExpect(status().isOk()).andExpect(jsonPath("$[0].kind").exists());

		mockMvc.perform(put(location).contentType(MediaType.APPLICATION_JSON)
				.content(BODY.replace("Jindo", "Shiba").replace("500000", "700000")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.kind").value("Shiba"))
			.andExpect(jsonPath("$.price").value(700000))
			.andExpect(jsonPath("$.readcount").value(2));

		mockMvc.perform(delete(location)).andExpect(status().isNoContent());
		mockMvc.perform(get(location)).andExpect(status().isNotFound());
	}

	@Test
	void createWithoutKindIsBadRequest() throws Exception {
		mockMvc.perform(post("/api/dogs").contentType(MediaType.APPLICATION_JSON).content("{\"price\":100}"))
			.andExpect(status().isBadRequest());
	}

	@Test
	void unknownIdIsNotFound() throws Exception {
		mockMvc.perform(get("/api/dogs/99999")).andExpect(status().isNotFound());
		mockMvc.perform(delete("/api/dogs/99999")).andExpect(status().isNotFound());
	}

}
