package com.test2.test2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test2.test2.controllers.UserController;
import com.test2.test2.entities.User;
import com.test2.test2.services.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
        Assertions.assertThat(userController).isNotNull();
    }

    @Test
    void createUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Mario");
        user.setSurname("Rossi");
        user.setAge(20);
        String userJSON = objectMapper.writeValueAsString(user);

        MvcResult resultActions = this.mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJSON)).andDo(print())
                .andExpect(status().isCreated()).andReturn();
    }
    @Test
    void getUserById() throws Exception {
        Long userId = 1L;
        createUser();

        MvcResult resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", userId))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId)).andReturn();
    }

    @Test
    void getAllUsers() throws Exception {
        createUser();
        MvcResult result = this.mockMvc.perform(get("/user/list"))
                .andDo(print()).andReturn();

        List<User> userFromResponseList = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        assertThat(userFromResponseList.size()).isNotZero();
    }
    @Test
    void updateStudentById() throws Exception {
        Long userId = 1L;
        createUser();
        User updatedUser = new User(userId, "Updated", "Updated2", 22);
        String userJSON = objectMapper.writeValueAsString(updatedUser);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.put("/user/update/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON).content(userJSON))
                .andDo(print()).andExpect(status().isAccepted()).andReturn();

        String content = result.getResponse().getContentAsString();
        org.junit.jupiter.api.Assertions.assertNotNull(content);
    }
    @Test
    void deleteUser() throws Exception {
        createUser();
        Long userId = 1L;


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId)).andReturn();

    }
}
