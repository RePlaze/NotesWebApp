package Spring.WebApp.Transfer;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Transfer {

    private int id;
    private double amount;
    private String phone;
    private LocalDate targetDate;

    public Transfer(int id, double amount, String phone, LocalDate targetDate) {
        super();
        this.id = id;
        this.amount = amount;
        this.phone = phone;
        this.targetDate = targetDate;
    }

    // Getters and setters for id, amount, phone, and targetDate
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }
}
