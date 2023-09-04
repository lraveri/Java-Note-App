package com.example.application.backend.service;

import com.example.application.backend.model.entity.Note;
import com.example.application.backend.repository.NoteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class NoteService {

  private final NoteRepository noteRepository;

  public Note findById(Long id) {
    return noteRepository.findByIdMandatory(id);
  }

  public Note createNote(Note note) {
    return noteRepository.save(note);
  }

  public int count() {
    return (int) noteRepository.count();
  }

  public Note updateNote(Note request) {

    var theNote = noteRepository.findByIdMandatory(request.getId());

    Note.update(theNote, request);

    return noteRepository.save(theNote);
  }

  public void deleteNote(Long id) {

    var theNote = noteRepository.findByIdMandatory(id);
    noteRepository.delete(theNote);
  }

  public List<Note> findAllByCreatedBy(String username) {
    return noteRepository.findAllByCreatedBy(username);
  }
}
