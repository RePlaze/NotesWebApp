package Spring.WebApp.Transfer;

import java.time.LocalDate;

public class Transfer {
    private String senderUsername;
    private double amount;
    private LocalDate date;

    public Transfer(String senderUsername, double amount, LocalDate date) {
        this.senderUsername = senderUsername;
        this.amount = amount;
        this.date = date;
    }

    // Getters and setters
    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
