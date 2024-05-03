package Spring.WebApp.todo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class TodoService {

    private static final List<Todo> todos = new ArrayList<>();
    private static int todosCount = 0;

    // Static initialization block to pre-populate some sample todos
    static {
        todos.add(new Todo(++todosCount, "in28minutes", "Learn AWS", LocalDate.now(), false));
        todos.add(new Todo(++todosCount, "in28minutes", "DevOps", LocalDate.now(), false));
        todos.add(new Todo(++todosCount, "in28minutes", "Full Stack Development", LocalDate.now(), false));
    }

    // Method to find todos by name
    public List<Todo> findByname(String name) {
        return todos;
    }

    // Method to add a new todo
    public void addTodo(String name, String description, LocalDate targetDate, boolean done) {
        Todo todo = new Todo(++todosCount, name, description, targetDate, done);
        todos.add(todo);
    }

    // Method to delete a todo by id
    public void deleteById(int id) {
        Predicate<? super Todo> predicate = todo -> todo.getId() == id;
        todos.removeIf(predicate);
    }

    // Method to find a todo by id
    public Todo findById(int id) {
        Predicate<? super Todo> predicate = todo -> todo.getId() == id;
        return todos.stream().filter(predicate).findFirst().orElse(null);
    }

    // Method to update a todo
    public void updateTodo(@Valid Todo todo) {
        deleteById(todo.getId()); // Remove the old todo
        todos.add(todo); // Add the updated todo
    }
}
