package com.example.demo;

import com.example.demo.dto.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void createUser_Success() throws Exception {
		UserRequest request = new UserRequest();
		request.setName("Juan Rodriguez");
		request.setEmail("juan@rodriguez.org");
		request.setPassword("Hunter12");

		PhoneRequest phone = new PhoneRequest();
		phone.setNumber("1234567");
		phone.setCityCode("1");
		phone.setCountryCode("57");
		request.setPhones(List.of(phone));

		mockMvc.perform(post("/api/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.token").exists());
	}

	@Test
	void createUser_InvalidEmail() throws Exception {
		UserRequest request = new UserRequest();
		request.setName("Juan Rodriguez");
		request.setEmail("invalid-email");
		request.setPassword("Hunter12");

		mockMvc.perform(post("/api/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.mensaje").value("email: Formato de correo inválido"));
	}
}
