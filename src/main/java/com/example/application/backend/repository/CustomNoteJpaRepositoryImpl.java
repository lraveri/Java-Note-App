package com.example.application.backend.repository;

import com.example.application.backend.exception.NoteNotFoundException;
import com.example.application.backend.model.entity.Note;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Log4j2
public class CustomNoteJpaRepositoryImpl implements CustomNoteJpaRepository {

  public static final String QRY_PARAM_NOTE_ID = "noteId";
  private final EntityManager entityManager;

  /***
   * This is an example of a manually crafted JPQL query
   */
  @Override
  public Optional<Note> findNoteByTitle(String title) {

    String theJpqlQuery = "select n from Note n where upper(n.title) = upper(:title)";

    var typedQuery =
        entityManager.createQuery(theJpqlQuery, Note.class).setParameter("title", title);

    try {
      return Optional.of(typedQuery.getSingleResult());
    } catch (NoResultException ex) {
      return Optional.empty();
    }
  }

  /***
   * This is an example of a manually crafted Criteria Query
   */
  @Override
  public Note findByIdMandatory(Long noteId) {

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Note> criteriaQuery = cb.createQuery(Note.class);

    Root<Note> noteRoot = criteriaQuery.from(Note.class);

    // Selezione
    criteriaQuery.select(noteRoot);

    // Here, we can define the query body, at runtime, in case we'd have multiple filters
    var criterion = new ArrayList<Predicate>();

    Predicate idIsEqual = cb.equal(noteRoot.get("id"), cb.parameter(Long.class, QRY_PARAM_NOTE_ID));
    criterion.add(idIsEqual);

    criteriaQuery.where(cb.and(criterion.toArray(new Predicate[0])));

    // Query
    TypedQuery<Note> theTypedQuery =
        entityManager.createQuery(criteriaQuery).setParameter(QRY_PARAM_NOTE_ID, noteId);

    try {
      return theTypedQuery.getSingleResult();
    } catch (NoResultException ex) {
      throw new NoteNotFoundException(noteId);
    }
  }

  /***
   * This is the same method, but it's using JPQL instead of the Criteria api
   */
  //  @Override
  //  public Note findByIdMandatory(Long noteId) {
  //
  //    String theJpqlQuery = "select n from Note n where n.id = :noteId";
  //
  //    var typedQuery =
  //        entityManager.createQuery(theJpqlQuery, Note.class).setParameter(QRY_PARAM_NOTE_ID,
  // noteId);
  //
  //    try {
  //      return typedQuery.getSingleResult();
  //
  //    } catch (NoResultException ex) {
  //      throw new NoteNotFoundException(noteId);
  //      // Launching your custom exception if no results are found
  //    }
  //  }
}
