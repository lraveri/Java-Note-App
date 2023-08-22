package com.example.application.backend.controller;

import com.example.application.backend.model.entity.Note;
import com.example.application.backend.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoteController {

    @Autowired
    NoteService noteService;

    @GetMapping(value = "/note/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findNote(@PathVariable Long id) {
        return new ResponseEntity<>(noteService.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/note", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createNote(@RequestBody Note note) {
        return new ResponseEntity<>(noteService.createNote(note), HttpStatus.OK);
    }

    @PutMapping(value = "/note/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateNote(@RequestBody Note note) {
        return new ResponseEntity<>(noteService.updateNote(note), HttpStatus.OK);
    }

    @DeleteMapping(value = "/note/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
