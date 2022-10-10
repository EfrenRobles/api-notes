package api.notes.note.infrastructure.controller;

import java.util.UUID;

import javax.validation.Valid;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.notes.note.application.NoteService;
import api.notes.note.domain.request.AddNoteRequest;
import api.notes.note.domain.request.UpdateNoteRequest;
import api.notes.note.domain.response.NoteResponse;
import api.shared.domain.Builder;
import api.shared.domain.Logger;
import api.shared.domain.response.OnResponse;
import api.shared.application.PageService;
import api.shared.infrastructure.PaginationConstant;
import api.shared.infrastructure.annotation.Scope;
import api.shared.infrastructure.gateway.AuthGateway;

@RestController
@RequestMapping("/api/v1/note")
@Validated
public class NoteController {

    @Autowired
    private NoteService noteService;
    
    @Autowired
    private AuthGateway authGateway;

    @Scope(value = "NOTE.VIEW")
    @GetMapping(params = "noteId")
    public ResponseEntity<?> getNoteByNoteId(@RequestParam(value = "noteId") UUID noteId) throws Exception {

        return OnResponse.onSuccess(noteService.getNote(noteId, authGateway.getUserId()), HttpStatus.OK);
    }

    @Scope(value = "NOTE.VIEW.LIST")
    @GetMapping
    public ResponseEntity<?> getNoteList(
        @RequestParam(value = "page", defaultValue = PaginationConstant.PAGE_DEFAULT, required = false) Short page,
        @RequestParam(value = "limit", defaultValue = PaginationConstant.LIMIT_DEFAULT, required = false) Byte limit,
        @RequestParam(value = "sortBy", defaultValue = "noteId", required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = PaginationConstant.SORT_ASC, required = false) String sortDir,
        @RequestParam(value = "notesTitle", required = false) @Length(min = 2, max = 50) String notesTitle,
        @RequestParam(value = "notesMessage", required = false) @Length(min = 10, max = 1000) String notesMessage
    ) {
        Pageable pageable = Builder.set(PageService.class)
            .with(p -> p.setPage(page))
            .with(p -> p.setLimit(limit))
            .with(p -> p.setSortBy(sortBy))
            .with(p -> p.setSortDir(sortDir))
            .build()
            .getPageable();

        NoteResponse note = Builder.set(NoteResponse.class)
            .with(u -> u.setUserId(authGateway.getUserId()))
            .with(u -> u.setNoteTitle(notesTitle))
            .with(u -> u.setNoteMessage(notesMessage))
            .build();

        return OnResponse.onSuccessPagination(noteService.getNoteList(pageable, note), HttpStatus.OK);

    }

    @Scope(value = "NOTE.ADD")
    @PostMapping
    public ResponseEntity<?> postNote(@Valid @RequestBody AddNoteRequest note) throws Exception {
        note.setUserId(authGateway.getUserId());
        
        return OnResponse.onSuccess(noteService.addNote(note), HttpStatus.CREATED);
    }

    @Scope(value = "NOTE.EDIT")
    @PatchMapping
    public ResponseEntity<?> patchNote(
        @RequestParam UUID noteId,
        @Valid @RequestBody UpdateNoteRequest note
    ) throws Exception {
        note.setNoteId(noteId);
        note.setUserId(authGateway.getUserId());

        return OnResponse.onSuccess(noteService.updateNote(note), HttpStatus.OK);
    }

    @Scope(value = "NOTE.DELETE")
    @DeleteMapping
    public ResponseEntity<?> deleteNote(@RequestParam UUID noteId) throws Exception {

        return OnResponse.onSuccess(noteService.deleteNote(noteId, authGateway.getUserId()), HttpStatus.NO_CONTENT);
    }
}
