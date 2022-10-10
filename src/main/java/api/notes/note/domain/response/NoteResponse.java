package api.notes.note.domain.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class NoteResponse {

    private UUID noteId;

    private UUID userId;

    private String noteTitle;

    private String noteMessage;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public UUID getNoteId() {
        return noteId;
    }

    public void setNoteId(UUID noteId) {
        this.noteId = noteId;
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
            + "noteId=" + noteId
            + ", userId=" + userId
            + ", noteTitle=" + noteTitle
            + ", noteMessage=" + noteMessage
            + ", createdAt=" + createdAt
            + ", updatedAt=" + updatedAt
            + "]";
    }
}
