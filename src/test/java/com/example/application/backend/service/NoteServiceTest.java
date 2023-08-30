package com.example.application.backend.service;

import com.example.application.backend.model.entity.Note;
import com.example.application.backend.repository.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    private Note note;

    @BeforeEach
    void setup() {
        note = Note.builder()
                .id(1L)
                .title("nota")
                .build();
    }

    // BDD style test
    @Test
    void givenANote_whenFindById_thenReturnNote() {
        // given (precondition)

        given(noteRepository.findById(1L)).willReturn(Optional.of(note));

        // when (action occurs)
        Note recoveredNote = noteService.findById(1L);

        log.info(recoveredNote.toString());

        // then (verify the output)
        assertThat(recoveredNote).isNotNull();
        assertThat(recoveredNote.getTitle()).isEqualTo("nota");
    }
}
