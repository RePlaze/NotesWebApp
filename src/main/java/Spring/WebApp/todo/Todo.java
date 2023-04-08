package Spring.WebApp.todo;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;
public class Todo {

    public Todo(int id, String name, String description, LocalDate targetDate, boolean done) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.targetDate = targetDate;
        this.done = done;
    }

    private int id;
    private String name;
    @Size(min=10, message = "At least 10 characters")
    private String description;
    private LocalDate targetDate;
    private boolean done;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Todo [id=" + id + ", name=" + name + ", description=" + description + ", targetDate="
                + targetDate + ", done=" + done + "]";
    }

}
