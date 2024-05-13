package Spring.WebApp.Transfer;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class TransferService {

    private static final List<Spring.WebApp.Transfer.Transfer> Transfers = new ArrayList<>();
    private static int TransfersCount = 0;


    // Static initialization block to pre-populate some sample Transfers
    static {
        Transfers.add(new Spring.WebApp.Transfer.Transfer(++TransfersCount, "in28minutes", "Learn AWS", LocalDate.now(), false));
        Transfers.add(new Spring.WebApp.Transfer.Transfer(++TransfersCount, "in28minutes", "DevOps", LocalDate.now(), false));
        Transfers.add(new Spring.WebApp.Transfer.Transfer(++TransfersCount, "in28minutes", "Full Stack Development", LocalDate.now(), false));
    }

    // Method to find Transfers by name
    public List<Spring.WebApp.Transfer.Transfer> findByname(String name) {
        return Transfers;
    }

    // Method to add a new Transfer
    public void addTransfer(String name, String description, LocalDate targetDate, boolean done) {
        Spring.WebApp.Transfer.Transfer Transfer = new Transfer(++TransfersCount, name, description, targetDate, done);
        Transfers.add(Transfer);
    }

    // Method to delete a Transfer by id
    public void deleteById(int id) {
        Predicate<? super Spring.WebApp.Transfer.Transfer> predicate = Transfer -> Transfer.getId() == id;
        Transfers.removeIf(predicate);
    }

    // Method to find a Transfer by id
    public Spring.WebApp.Transfer.Transfer findById(int id) {
        Predicate<? super Spring.WebApp.Transfer.Transfer> predicate = Transfer -> Transfer.getId() == id;
        return Transfers.stream().filter(predicate).findFirst().orElse(null);
    }

    // Method to update a Transfer
    public void updateTransfer(@Valid Spring.WebApp.Transfer.Transfer Transfer) {
        deleteById(Transfer.getId()); // Remove the old Transfer
        Transfers.add(Transfer); // Add the updated Transfer
    }
}
