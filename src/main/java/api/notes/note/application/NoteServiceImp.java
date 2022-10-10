package api.notes.note.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import api.shared.domain.Builder;
import api.shared.domain.exception.ServiceException;
import api.shared.domain.response.PaginationResponse;
import api.notes.note.domain.NoteRepository;
import api.notes.note.domain.Note;
import api.notes.note.domain.request.AddNoteRequest;
import api.notes.note.domain.request.UpdateNoteRequest;
import api.notes.note.domain.response.NoteResponse;

public class NoteServiceImp implements NoteService {

    private NoteRepository noteRepository;

    public NoteServiceImp(NoteRepository noteRepository) {

        this.noteRepository = noteRepository;
    }

    public static NoteServiceImp build(NoteRepository noteRepository) {

        return new NoteServiceImp(noteRepository);
    }

    @Override
    public NoteResponse getNote(UUID noteId, UUID userId) throws Exception {

        Note note = noteRepository.findByNoteIdAndUserId(noteId, userId);

        if (note == null) {
            throw new ServiceException("Note not found");
        }

        return mapToNoteDto(note);
    }

    @Override
    public PaginationResponse getNoteList(Pageable pageable, NoteResponse data) {

        Builder<Note> builder = Builder.set(Note.class);

        if (data.getNoteTitle() != null) {
            builder.with(r -> r.setNoteTitle(data.getNoteTitle().toUpperCase()));
        }

        if (data.getNoteMessage() != null) {
            builder.with(r -> r.setNoteMessage(data.getNoteMessage()));
        }

        builder.with(r -> r.setUserId(data.getUserId()));
        
        Note note = builder.build();

        Page<Note> notes = noteRepository.findAll(pageable, note);

        List<Note> noteList = notes.getContent();
        List<NoteResponse> content = noteList
            .stream()
            .map(r -> mapToNoteDto(r))
            .toList();

        return Builder.set(PaginationResponse.class)
            .with(p -> p.setData(content))
            .with(p -> p.setPage((short) notes.getNumber()))
            .with(p -> p.setLimit((byte) notes.getSize()))
            .with(p -> p.setTotalItems((short) notes.getTotalElements()))
            .with(p -> p.setTotalPages((short) notes.getTotalPages()))
            .with(p -> p.setLast(notes.isLast()))
            .build();
    }

    @Override
    public NoteResponse addNote(AddNoteRequest data) throws Exception {

        Note note = Builder.set(Note.class)
            .with(u -> u.setNoteId(UUID.randomUUID()))
            .with(u -> u.setUserId(data.getUserId()))
            .with(u -> u.setNoteTitle(data.getNoteTitle()))
            .with(u -> u.setNoteMessage(data.getNoteMessage()))
            .with(u -> u.setCreatedAt(LocalDateTime.now()))
            .build();

        note = noteRepository.save(note);

        if (note == null) {
            throw new ServiceException("The note is already registered");
        }

        return mapToNoteDto(note);
    }

    @Override
    public NoteResponse updateNote(UpdateNoteRequest data) throws Exception {

        Boolean needUpdate = false;

        Note note = noteRepository.findByNoteIdAndUserId(data.getNoteId(), data.getUserId());

        if (note == null) {
            throw new ServiceException("The note was not found");
        }

        if (data.getNoteTitle() != null && !data.getNoteTitle().equalsIgnoreCase(note.getNoteTitle())) {
            note.setNoteTitle(data.getNoteTitle());
            needUpdate = true;
        }

        if (data.getNoteMessage() != null && !data.getNoteMessage().equalsIgnoreCase(note.getNoteMessage())) {
            note.setNoteMessage(data.getNoteMessage());
            needUpdate = true;
        }

        if (needUpdate) {
            note.setUpdatedAt(LocalDateTime.now());
            note = noteRepository.save(note);

            if (note == null) {
                throw new ServiceException("The note is already regitered");
            }
        }

        return mapToNoteDto(note);
    }

    @Override
    public UUID deleteNote(UUID noteId, UUID userId) throws Exception {

        Note note = noteRepository.findByNoteIdAndUserId(noteId, userId);

        if (note == null) {
            throw new ServiceException("The note was not found");
        }

        noteRepository.delete(note);

        return noteId;
    }

    private NoteResponse mapToNoteDto(Note data) {

        return Builder.set(NoteResponse.class)
            .with(u -> u.setNoteId(data.getNoteId()))
            .with(u -> u.setUserId(data.getUserId()))
            .with(u -> u.setNoteTitle(data.getNoteTitle()))
            .with(u -> u.setNoteMessage(data.getNoteMessage()))
            .with(u -> u.setCreatedAt(data.getCreatedAt()))
            .with(u -> u.setUpdatedAt(data.getUpdatedAt()))
            .build();
    }
}
