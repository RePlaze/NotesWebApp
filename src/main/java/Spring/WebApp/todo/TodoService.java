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

    static {
        todos.add( new Todo( ++todosCount, "in28minutes", "Learn AWS", LocalDate.now(), false ) );
        todos.add( new Todo( ++todosCount, "in28minutes", " DevOps", LocalDate.now(), false ) );
        todos.add( new Todo( ++todosCount, "in28minutes", " Full Stack Development", LocalDate.now(), false ) );
    }

    public List<Todo> findByname(String name) {
        return todos;
    }

    public void addTodo(String name, String description, LocalDate targetDate, boolean done) {
        Todo todo = new Todo( ++todosCount, name, description, targetDate, done );
        todos.add( todo );
    }

    public void deleteById(int id) {
        Predicate<? super Todo> predicate =
                todo -> todo.getId() == id;
        todos.removeIf( predicate );
    }

    public Todo findById(int id) {
        Predicate<? super Todo> predicate =
                todo -> todo.getId() == id;
        Todo todo = todos.stream().filter( predicate ).findFirst().get();
        return todo;
    }

    public void updateTodo(@Valid Todo todo) {
        deleteById( todo.getId() );
        todos.add( todo );
    }
}