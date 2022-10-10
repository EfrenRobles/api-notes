package api.notes.note.domain.request;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;

public class AddNoteRequest {

    @Nullable
    private UUID userId;

    @NotBlank(message = "The note title is required.")
    @Length(min = 2, max = 50, message = "The length of notes code must be between 2 to 50 characters.")
    private String noteTitle;

    @NotBlank(message = "The note message is required.")
    @Length(min = 10, max = 1000, message = "The length of notes code must be between 10 to 1000 characters.")
    private String noteMessage;

    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime updatedAt;

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
            + "userId=" + userId
            + ", noteTitle=" + noteTitle
            + ", noteMessage=" + noteMessage
            + ", createdAt=" + createdAt
            + ", updatedAt=" + updatedAt
            + "]";
    }
}
