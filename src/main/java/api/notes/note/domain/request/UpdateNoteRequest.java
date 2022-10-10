package api.notes.note.domain.request;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;

public class UpdateNoteRequest {

    @Nullable
    private UUID noteId;

    @Nullable
    private UUID userId;

    @Nullable
    @Length(min = 2, max = 50, message = "The length of notes code must be between 2 to 50 characters.")
    private String noteTitle;

    @Nullable
    @Length(min = 10, max = 1000, message = "The length of notes code must be between 10 to 1000 characters.")
    private String noteMessage;

    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime updatedAt;

    public UUID getNoteId() {
        return noteId;
    }

    public void setNoteId(UUID notesId) {
        this.noteId = notesId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteMessage() {
        return noteMessage;
    }

    public void setNoteMessage(String noteMessage) {
        this.noteMessage = noteMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "notes ["
            + "notesId=" + noteId
            + ", userId=" + userId
            + ", noteTitle=" + noteTitle
            + ", noteMessage=" + noteMessage
            + ", createdAt=" + createdAt
            + ", updatedAt=" + updatedAt
            + "]";
    }
}
