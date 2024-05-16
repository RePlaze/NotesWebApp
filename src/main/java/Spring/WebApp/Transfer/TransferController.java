package Spring.WebApp.Transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;

@Controller
@SessionAttributes("username")
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("list-Transfers")
    public String listAllTransfers(ModelMap model) throws SQLException {
        String username = (String) model.get("username");
        double balance = transferService.getBalance(username);
        model.addAttribute("username", username);
        model.addAttribute("balance", balance);

        // Fetch transfer data from the backend and populate the "transfers" attribute
        List<Transfer> transfers = transferService.getAllTransfersForUser(username);
        model.addAttribute("transfers", transfers);
        return "listTransfers";
    }

    @PostMapping("withdrawAmount")
    public String withdrawAmount(@RequestParam("phone") String phone, @RequestParam("amount") double amount, ModelMap model, RedirectAttributes redirectAttributes) throws SQLException {
        String username = (String) model.get("username");
        double newBalance = transferService.updateLocalBalance(username, amount);

        if (newBalance >= 0 && transferService.foundPhoneToTransfer(phone) && !transferService.isSameUser(username, phone)) {
            transferService.withdrawAmount(username, amount, phone);
            model.addAttribute("balance", newBalance);
            return "redirect:/list-Transfers";
        }
        return "errorPage";
    }

    @GetMapping("/goToSettings")
    public String goToSettings(ModelMap model) throws SQLException {
        String username = (String) model.get("username");
        int userId = TransferService.getUserId(username);
        return "redirect:/Settings?id=" + userId;
    }
}
