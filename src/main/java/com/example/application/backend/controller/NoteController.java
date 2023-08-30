package com.example.application.backend.controller;

import com.example.application.backend.model.entity.Note;
import com.example.application.backend.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
    return new ResponseEntity<>(noteService.findById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Note> createNote(@RequestBody Note note) {
    return new ResponseEntity<>(noteService.createNote(note), HttpStatus.OK);
  }

  @PutMapping("{id}")
  public ResponseEntity<Note> updateNote(@RequestBody Note note) {
    return new ResponseEntity<>(noteService.updateNote(note), HttpStatus.OK);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Note> deleteNote(@PathVariable Long id) {
    noteService.deleteNote(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
