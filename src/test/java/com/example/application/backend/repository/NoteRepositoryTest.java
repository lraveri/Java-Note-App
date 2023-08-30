package com.example.application.backend.repository;

import com.example.application.backend.model.entity.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach
    void setUp() {
        Note note1 = Note.builder()
                .id(1L)
                .createdBy("Luca")
                .build();
        Note note2 = Note.builder()
                .id(2L)
                .createdBy("Alessio")
                .build();
        Note note3 = Note.builder()
                .id(3L)
                .createdBy("Luca")
                .build();
        noteRepository.saveAll(List.of(note1, note2, note3));
    }

    @Test
    void testFindAllByCreatedBy_NoMatchingNotes() {
        List<Note> notes = noteRepository.findAllByCreatedBy("Matteo");
        assertThat(notes).isEmpty();
    }

    @Test
    void testFindAllByCreatedBy_MatchingNotes() {
        List<Note> notes = noteRepository.findAllByCreatedBy("Alessio");
        assertThat(notes).hasSize(1);
    }

    @Test
    void testFindAllByCreatedBy_MultipleMatchingNotes() {
        List<Note> notes = noteRepository.findAllByCreatedBy("Luca");
        assertThat(notes).hasSize(2);
    }
}
