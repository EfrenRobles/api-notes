package api.notes.note.domain;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteRepository {

    public Page<Note> findAll(Pageable pageable, Note note);

    public Note findByNoteIdAndUserId(UUID noteId, UUID userId);

    public Note save(Note note) throws Exception;

    public void delete(Note note) throws Exception;
}
