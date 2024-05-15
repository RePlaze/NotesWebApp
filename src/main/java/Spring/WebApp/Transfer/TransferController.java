package Spring.WebApp.Transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.time.LocalDate;

@Controller
@SessionAttributes("username")
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("list-Transfers")
    public String listAllTransfers(ModelMap model) {
        String username = (String) model.get("username");
        double balance = transferService.getBalance(username);
        model.addAttribute("username", username);
        model.addAttribute("balance", balance);
        return "listTransfers";
    }

    @PostMapping("withdrawAmount")
    public String withdrawAmount(@RequestParam("phone") String phone, @RequestParam("amount") double amount, ModelMap model) {
        String username = (String) model.get("username");
        transferService.withdrawAmount(username, amount, phone);
        model.addAttribute("balance", transferService.updateLocalBalance(username, amount));
        return "redirect:/list-Transfers";
    }
}