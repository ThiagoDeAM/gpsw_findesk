package findesk.findesk.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Revenue {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private double amount;
    private String source;
    private LocalDate date;
    private String description;

    public Revenue(double amount, String source, String description) {
        this.id = UUID.randomUUID();
        this.amount = amount;
        this.source = source;
        this.date = LocalDate.now();
        this.description = description;
    }

    public Revenue() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
