package Spring.WebApp.Transfer;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
@Controller
@SessionAttributes("username")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("list-Transfers")
    public String listAllTransfers(ModelMap model) {
        String username = (String) model.get("username");
        model.addAttribute("username", username);
        return "listTransfers";
    }

    // Show page to add a new Transfer
    @GetMapping("add-Transfer")
    public String showNewTransferPage(ModelMap model) {
        String username = (String) model.get("username");
        model.addAttribute("Transfer", new Transfer(0, username, "", LocalDate.now(), false));
        return "Transfer";
    }

    // Show page to update a Transfer
    @GetMapping("update-Transfer")
    public String showUpdateTransferPage(@RequestParam int id, ModelMap model) {
        model.addAttribute("Transfer", transferService.findById(id));
        return "Transfer";
    }

    // Add a new Transfer
    @PostMapping("add-Transfer")
    public String addNewTransfer(ModelMap model, @Valid Transfer Transfer, BindingResult result) {
        if (result.hasErrors() || Transfer.getDescription().length() < 10) {
            result.rejectValue("description", "length", "Description should be at least 10 characters long");
            return "Transfer";
        }
        String username = (String) model.get("username");
        transferService.addTransfer(username, Transfer.getDescription(), LocalDate.now(), false);
        return "redirect:list-Transfers";
    }

    // Delete a Transfer
    @GetMapping("delete-Transfer")
    public String deleteTransfer(@RequestParam int id) {
        transferService.deleteById(id);
        return "redirect:list-Transfers";
    }

    // Update a Transfer
    @PostMapping("update-Transfer")
    public String updateTransfer(ModelMap model, @Valid Transfer Transfer, BindingResult result) {
        if (result.hasErrors() || Transfer.getDescription().length() < 10) {
            result.rejectValue("description", "length", "Description should be at least 10 characters long");
            return "Transfer";
        }
        transferService.updateTransfer(Transfer);
        return "redirect:list-Transfers";
    }
}
