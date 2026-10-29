package arturnikytenko.authapi.Models.DataModel;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "data_model")
public class DataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String passwordHash;

    public DataModel() {
    }

    public DataModel(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UUID getId() {
        return id;
    }
}

