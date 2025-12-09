package findesk.findesk.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
public class Expense {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private double amount;
    private String category;
    private LocalDate date;
    private String description;

    public Expense(double amount, String category, String description) {
        this.id = UUID.randomUUID();
        this.amount = amount;
        this.category = category;
        this.date = LocalDate.now();
        this.description = description;
    }

    public Expense() {

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
