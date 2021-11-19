package org.example.controller;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.JVM)
public class TaskControllerITest {
    private String baseUrl = "/tasks";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addTask() throws Exception {
        this.mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"asdf1\",\n" +
                                "    \"description\": \"asda\"\n}"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllTasks() throws Exception {
        this.mockMvc.perform(get(baseUrl))
                .andExpect(content().json("[{\"id\":1,\"name\":\"asdf1\",\"description\":\"asda\",\"last_modification_date\":\""+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))+"\"}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void getTask() throws Exception {
        this.mockMvc.perform(get(baseUrl + "/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void changeTask() throws Exception {
        this.mockMvc.perform(put(baseUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\": \"asdf1\",\n" +
                                "    \"description\": \"asda\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllTasksSortedByModificationDate() throws Exception {
        this.mockMvc.perform(get(baseUrl + "/sort-by-modification-date"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTask() throws Exception {
        this.mockMvc.perform(delete(baseUrl + "/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNotFoundTask() throws Exception {
        this.mockMvc.perform(delete(baseUrl + "/2"))
                .andExpect(status().is4xxClientError());
    }
}