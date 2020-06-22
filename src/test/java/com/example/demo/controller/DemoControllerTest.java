package com.example.demo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootDemoApplication.class})
@AutoConfigureMockMvc
public class DemoControllerTest {
	@Autowired
	private MockMvc mvc;

	@Test
	public void versionTest() throws Exception {
		this.mvc.perform(get("/demo/version")).andExpect(status().isOk())
				.andExpect(content().string("{\"version\":\"1.0.0\",\"message\":\"hello world\"}"))
                .andDo(MockMvcResultHandlers.print());
	}
}
