package com.example.demo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoControllerTest {
	private MockMvc mockMvc;

    @Autowired
    private DemoController demoController;
    
    @Before
    public void setUp() throws Exception {
        System.out.println("init demoController");
        mockMvc = MockMvcBuilders.standaloneSetup(demoController).build();
    }
    
    @Test
    public void demoControllerTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/demo/version");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"version\":\"1.0.0\",\"message\":\"hello world\"}")) 
                .andDo(MockMvcResultHandlers.print()) 
                .andReturn(); 
    }
}
