package com.example.application.backend.controller;

import com.example.application.backend.model.entity.Note;
import com.example.application.backend.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("note")
@RequiredArgsConstructor
@Log4j2
public class NoteController {

    private final NoteService noteService;

    @GetMapping("{id}")
    public ResponseEntity<Note> findNote(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        return ResponseEntity.ok(noteService.createNote(note));
    }

    @PutMapping("{id}")
    public ResponseEntity<Note> updateNote(@RequestBody Note note) {
        return ResponseEntity.ok(noteService.updateNote(note));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Note> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
