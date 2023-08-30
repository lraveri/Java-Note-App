package com.example.application.backend.controller;

import com.example.application.backend.model.entity.Note;
import com.example.application.backend.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Note note;

    @BeforeEach
    void setup() {
        note = Note.builder()
                .id(1L)
                .title("nota")
                .build();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void givenNoteObject_whenFindNoteById_thenReturnNote() throws Exception {

        // given
        given(noteService.findById(1L)).willReturn(note);

        // when
        ResultActions response = mockMvc.perform(get("/note/{id}", 1L));

        // then
        response.andDo(MockMvcResultHandlers.print());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.title", CoreMatchers.is(note.getTitle())));
    }

}
