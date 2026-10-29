package arturnikytenko.authapi.Models.ProcessingLog;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "processing_log")
public class ProcessingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String inputText;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String outputText;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected ProcessingLog() {
    }

    public ProcessingLog(
            UUID userId,
            String inputText,
            String outputText
    ) {
        this.userId = userId;
        this.inputText = inputText;
        this.outputText = outputText;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getInputText() {
        return inputText;
    }

    public String getOutputText() {
        return outputText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

