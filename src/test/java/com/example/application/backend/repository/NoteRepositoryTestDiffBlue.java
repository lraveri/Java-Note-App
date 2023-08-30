package com.example.application.backend.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.application.backend.model.entity.Note;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {NoteRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.example.application.backend.model.entity"})
@DataJpaTest
class NoteRepositoryTestDiffBlue {

    @Autowired
    private NoteRepository noteRepository;

    /**
     * Method under test: {@link NoteRepository#findAllByCreatedBy(String)}
     */
    @Test
    void testFindAllByCreatedBy() {
        Date createdWhen = mock(Date.class);
        when(createdWhen.getTime()).thenReturn(10L);
        Date lastUpdatedWhen = mock(Date.class);
        when(lastUpdatedWhen.getTime()).thenReturn(10L);

        Note note = new Note();
        note.setContent("Not all who wander are lost");
        note.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        note.setCreatedWhen(createdWhen);
        note.setIdParent(1L);
        note.setIsPinned(1L);
        note.setLastUpdatedWhen(lastUpdatedWhen);
        note.setTags("Tags");
        note.setTitle("Dr");
        Date createdWhen2 = mock(Date.class);
        when(createdWhen2.getTime()).thenReturn(10L);
        Date lastUpdatedWhen2 = mock(Date.class);
        when(lastUpdatedWhen2.getTime()).thenReturn(10L);

        Note note2 = new Note();
        note2.setContent("Content");
        note2.setCreatedBy("Created By");
        note2.setCreatedWhen(createdWhen2);
        note2.setIdParent(2L);
        note2.setIsPinned(-1L);
        note2.setLastUpdatedWhen(lastUpdatedWhen2);
        note2.setTags("com.example.application.backend.model.entity.Note");
        note2.setTitle("Mr");

        noteRepository.save(note);
        noteRepository.save(note2);

        assertTrue(noteRepository.findAllByCreatedBy("janedoe").isEmpty());

        verify(createdWhen).getTime();
        verify(lastUpdatedWhen).getTime();
        verify(createdWhen2).getTime();
        verify(lastUpdatedWhen2).getTime();
    }
}

