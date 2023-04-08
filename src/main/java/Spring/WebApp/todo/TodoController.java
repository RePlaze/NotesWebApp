package Spring.WebApp.todo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("name")
public class TodoController {

    public TodoController(TodoService todoService) {
        super();
        this.todoService = todoService;
    }

    private final TodoService todoService;


    @RequestMapping("list-todos")
    public String listAllTodos(ModelMap model) {
        List<Todo> todos = todoService.findByname( "nazenov" );
        model.addAttribute( "todos", todos );

        return "listTodos";
    }

    //GET, POST
    @RequestMapping(value = "add-todo", method = RequestMethod.GET)
    public String showNewTodoPage(ModelMap model) {
        String name = (String) model.get( "name" );
        Todo todo = new Todo( 0, name, "", LocalDate.now(), false );
        model.put( "todo", todo );
        return "Todo";
    }

    @RequestMapping(value = "add-todo", method = RequestMethod.POST)
    public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
        if (result.hasErrors()) {
            return "Todo";
        }

        String description = todo.getDescription();
        if (description.length() < 10) {
            result.rejectValue( "description", "length", "At least 10 characters" );
            return "Todo";
        }

        String name = (String) model.get( "name" );
        todoService.addTodo( name, description, LocalDate.now(), false );
        return "redirect:list-todos";
    }

    @RequestMapping("delete-todo")
    public String deleteTodo(@RequestParam int id) {
        todoService.deleteById( id );
        return "redirect:list-todos";
    }

    @RequestMapping(value = "update-todo",method = RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
        Todo todo = todoService.findById( id );
        model.addAttribute( "todo", todo );
        return "Todo";
    }
    @RequestMapping(value = "update-todo", method = RequestMethod.POST)
    public String UpdateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
        if (result.hasErrors()) {
            return "Todo";
        }

        String description = todo.getDescription();
        if (description.length() < 10) {
            result.rejectValue( "description", "length", "At least 10 characters" );
            return "Todo";
        }

        String name = (String) model.get( "name" );
        todo.setname( name );
        todoService.updateTodo( todo );
        return "redirect:list-todos";
    }

}
