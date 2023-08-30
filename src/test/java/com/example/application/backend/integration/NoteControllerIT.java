package com.example.application.backend.integration;

import com.example.application.backend.model.entity.Note;
import com.example.application.backend.repository.NoteRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class NoteControllerIT {

    @Container
    private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteRepository noteRepository;

    private Note note;

    @BeforeEach
    void setup() {
        note = Note.builder()
                .id(1L)
                .title("note")
                .build();

        noteRepository.save(note);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void givenNoteObject_whenFindNoteById_thenReturnNote() throws Exception {

        // given

        // when
        ResultActions response = mockMvc.perform(get("/note/{id}", 1L));

        // then
        response.andDo(MockMvcResultHandlers.print());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.title", CoreMatchers.is(note.getTitle())));
    }

}
