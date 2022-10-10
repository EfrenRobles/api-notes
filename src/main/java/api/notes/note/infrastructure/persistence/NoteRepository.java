package api.notes.note.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import api.notes.note.domain.Note;

public interface NoteRepository extends JpaRepository<Note, UUID> {

    Note findByNoteId(UUID noteId);

    Note findByNoteIdAndUserId(UUID noteId, UUID userId);
}
