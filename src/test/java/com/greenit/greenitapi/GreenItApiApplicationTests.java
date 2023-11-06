package com.greenit.greenitapi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GreenItApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFollowedByUser() throws Exception {
        mockMvc.perform(get("http://localhost:8080/followedByUser?userId=13"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].displayName").value("jrber23"));

    }

    @Test
    public void testFollowersUser() throws Exception {
        mockMvc.perform(get("http://localhost:8080/followersUser?userId=14"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].displayName").value("rizna"));

    }

}
