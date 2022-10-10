package api.notes.note.infrastructure.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import api.shared.domain.Logger;
import api.shared.domain.exception.RepositoryException;
import api.shared.infrastructure.persistence.Pagination;

import api.notes.note.domain.Note;

@Component
public class NoteRepositoryImplSql implements api.notes.note.domain.NoteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Page<Note> findAll(Pageable pageable, Note note) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Note> criteriaQuery = criteriaBuilder.createQuery(Note.class);
        Root<Note> root = criteriaQuery.from(criteriaQuery.getResultType());

        List<Predicate> conditions = new ArrayList<>();

        if (note.getNoteTitle() != null) {
            conditions.add(criteriaBuilder.like(root.get("noteTitle"), "%" + note.getNoteTitle() + "%"));
        }

        if (note.getNoteMessage() != null) {
            conditions.add(criteriaBuilder.like(root.get("noteMessage"), "%" + note.getNoteMessage() + "%"));
        }

        conditions.add(criteriaBuilder.equal(root.get("userId"), note.getUserId()));
        criteriaQuery.where(conditions.toArray(new Predicate[conditions.size()]));

        try {

            return new Pagination<Note>(
                entityManager,
                criteriaBuilder,
                criteriaQuery,
                pageable
            ).getPagination();

        } catch (Exception e) {
            Logger.log("Error by: " + e.getMessage());

            return new PageImpl<>(new ArrayList<>());
        }
    }

    @Override
    public Note findByNoteIdAndUserId(UUID noteId, UUID userId) {
        try {

            return noteRepository.findByNoteIdAndUserId(noteId, userId);
        } catch (Exception e) {
            Logger.log(e.getMessage());
            throw new RepositoryException("Internal Server Error");
        }
    }

    @Override
    public Note save(Note note) throws Exception {
        try {

            return noteRepository.save(note);
        } catch (Exception e) {
            Logger.log(e.getMessage());
            
            throw new RepositoryException("Internal Server Error");
        }
    }

    @Override
    public void delete(Note note) throws Exception {
        try {
            noteRepository.delete(note);
        } catch (Exception e) {
            Logger.log(e.getMessage());
            throw new RepositoryException("Internal Server Error");
        }
    }
}
