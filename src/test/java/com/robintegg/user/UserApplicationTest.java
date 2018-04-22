package com.robintegg.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Transactional
public class UserApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void shouldSaveAUserToTheDatabase() throws Exception {

		// given
		User user = new User("robintegg", "robin", "tegg", 25);

		// when
		ResultActions actions = mockMvc.perform(
				post("/user/add").contentType(MediaType.APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(user)));

		// then
		actions.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.username", is("robintegg"))).andExpect(jsonPath("$.firstname", is("robin")))
				.andExpect(jsonPath("$.lastname", is("tegg"))).andExpect(jsonPath("$.age", is(25)));

		assertEquals(1, userRepository.count());

	}

	@Test
	public void shouldNotCreateUserWhenNoUsernameGiven() throws Exception {

		// given
		User user = new User(null, "robin", "tegg", 25);

		// when
		ResultActions actions = mockMvc.perform(
				post("/user/add").contentType(MediaType.APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(user)));

		// then
		actions.andDo(print()).andExpect(status().isBadRequest());

		assertEquals(0, userRepository.count());

	}

	@Test
	public void shouldNotCreateUserWhenUsernameExceedsLimit() throws Exception {

		// given
		User user = new User("robinteggrobinteggrobinteggrobinteggrobinteggrobintegg", "robin", "tegg", 25);

		// when
		ResultActions actions = mockMvc.perform(
				post("/user/add").contentType(MediaType.APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(user)));

		// then
		actions.andDo(print()).andExpect(status().isBadRequest());

		assertEquals(0, userRepository.count());

	}

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

}
