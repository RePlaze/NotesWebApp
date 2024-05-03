package Spring.WebApp.todo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("name")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // List all todos
    @GetMapping("list-todos")
    public String listAllTodos(ModelMap model) {
        String name = (String) model.get("name");
        model.addAttribute("todos", todoService.findByname(name));
        return "listTodos";
    }

    // Show page to add a new todo
    @GetMapping("add-todo")
    public String showNewTodoPage(ModelMap model) {
        String name = (String) model.get("name");
        model.addAttribute("todo", new Todo(0, name, "", LocalDate.now(), false));
        return "Todo";
    }

    // Show page to update a todo
    @GetMapping("update-todo")
    public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
        model.addAttribute("todo", todoService.findById(id));
        return "Todo";
    }

    // Add a new todo
    @PostMapping("add-todo")
    public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
        if (result.hasErrors() || todo.getDescription().length() < 10) {
            result.rejectValue("description", "length", "Description should be at least 10 characters long");
            return "Todo";
        }
        String name = (String) model.get("name");
        todoService.addTodo(name, todo.getDescription(), LocalDate.now(), false);
        return "redirect:list-todos";
    }

    // Delete a todo
    @GetMapping("delete-todo")
    public String deleteTodo(@RequestParam int id) {
        todoService.deleteById(id);
        return "redirect:list-todos";
    }

    // Update a todo
    @PostMapping("update-todo")
    public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
        if (result.hasErrors() || todo.getDescription().length() < 10) {
            result.rejectValue("description", "length", "Description should be at least 10 characters long");
            return "Todo";
        }
        String name = (String) model.get("name");
        todo.setname(name);
        todoService.updateTodo(todo);
        return "redirect:list-todos";
    }
}
