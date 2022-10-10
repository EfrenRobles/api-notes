package api.notes.note.application;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import api.notes.note.domain.request.AddNoteRequest;
import api.notes.note.domain.request.UpdateNoteRequest;
import api.notes.note.domain.response.NoteResponse;
import api.shared.domain.response.PaginationResponse;

public interface NoteService {

    public NoteResponse getNote(UUID noteId, UUID userId) throws Exception;

    public PaginationResponse getNoteList(Pageable pageable, NoteResponse note);

    public NoteResponse addNote(AddNoteRequest request) throws Exception;

    public NoteResponse updateNote(UpdateNoteRequest request) throws Exception;

    public UUID deleteNote(UUID noteId, UUID userId) throws Exception;
}
