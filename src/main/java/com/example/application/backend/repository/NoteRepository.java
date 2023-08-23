package com.example.application.backend.repository;

import com.example.application.backend.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByCreatedBy(String username);
}
