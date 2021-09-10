package com.odyssey.pretest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odyssey.pretest.user.User;

import org.hamcrest.Matchers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class UserTests {
  @Autowired
  private MockMvc mockMvc;  

  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @DirtiesContext
  public void shouldHandleGetRequestWithEmptyUsers() throws Exception {
    mockMvc.perform(get("/users"))   
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", Matchers.hasSize(0)))
           .andDo(print());
  }

  @Test
  @DirtiesContext
  public void shouldHandleCreateUser() throws Exception {
    User a1 = new User("Robert Downey Junior");
    String jsonContent = objectMapper.writeValueAsString(a1);

    mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonContent))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.id", Matchers.is(1)))
           .andExpect(jsonPath("$.fullName", Matchers.is("Robert Downey Junior")))
           .andDo(print());
  }

  @Test
  @DirtiesContext
  public void shouldHandleUpdatingExistingUser() throws Exception {
    User a1 = new User("Robert Downey Junior");
    String jsonContent = objectMapper.writeValueAsString(a1);

    User a1Updated = new User("Robert Downey Junior Updated");
    String jsonContentUpdated = objectMapper.writeValueAsString(a1Updated);

    mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonContent));

    mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON).content(jsonContentUpdated))
           // is this ok 200 response  
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id", Matchers.is(1)))
           .andExpect(jsonPath("$.fullName", Matchers.is("Robert Downey Junior Updated")))
           .andDo(print());
  }

  @Test
  @DirtiesContext
  public void shouldHandleDeletingExistingUser() throws Exception {
    User a1 = new User("Robert Downey Junior");
    String jsonContent = objectMapper.writeValueAsString(a1);

    mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonContent));

    mockMvc.perform(delete("/users/1"))
           .andExpect(status().isNoContent())
           .andDo(print());
  }

  @Test
  @DirtiesContext
  public void shouldHandleIgnoreIdProvidedByRequester() throws Exception {
    User a1 = new User(100L, "Holland Rockwell");
    String jsonContent = objectMapper.writeValueAsString(a1);

    mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonContent))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.id", Matchers.is(1)))
           .andExpect(jsonPath("$.fullName", Matchers.is("Holland Rockwell")))
           .andDo(print());
  }

  @Test
  @DirtiesContext
  public void shouldHandleCrudOperation() throws Exception {
    User a1 = new User("Bracklet Snowman");
    String jsonContent = objectMapper.writeValueAsString(a1);

    // create
    mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonContent))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.id", Matchers.is(1)))
           .andExpect(jsonPath("$.fullName", Matchers.is("Bracklet Snowman")))
           .andDo(print());

    // read
    mockMvc.perform(get("/users"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", Matchers.hasSize(1)))
           .andExpect(jsonPath("$[0].id", Matchers.is(1)))
           .andExpect(jsonPath("$[0].fullName", Matchers.is("Bracklet Snowman")));

    // read
    mockMvc.perform(get("/users/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id", Matchers.is(1)))
           .andExpect(jsonPath("$.fullName", Matchers.is("Bracklet Snowman")));

    User a1Updated = new User("Bracklet Spiritman");
    String jsonContentUpdated = objectMapper.writeValueAsString(a1Updated);

    // update
    mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON).content(jsonContentUpdated))
            // not sure
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id", Matchers.is(1)))
           .andExpect(jsonPath("$.fullName", Matchers.is("Bracklet Spiritman")));
    
    // delete
    mockMvc.perform(delete("/users/1"))
           .andExpect(status().isNoContent())
           .andDo(print());
  }
}
