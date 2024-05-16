package Spring.WebApp.login;

import Spring.WebApp.Database.DBConnection;
import Spring.WebApp.Database.SQLQueries;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
@SessionAttributes("username")
public class SettingController {

    @GetMapping("/Settings")
    public String showSettingsPage(@RequestParam(value = "id", required = false) String id, ModelMap model) throws SQLException {
        if (id == null || id.isEmpty()) {
            return "errorPage";
        }

        int userId;
        try {
            userId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return "errorPage";
        }

        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQLQueries.SELECT_PROFILE);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                model.addAttribute("userId", userId);
                model.addAttribute("fullName", resultSet.getString("FullName"));
                model.addAttribute("email", resultSet.getString("Email"));
                model.addAttribute("gender", resultSet.getString("Gender"));
                model.addAttribute("dateOfBirth", resultSet.getString("DateOfBirth"));
            }
            statement.close();
        }
        return "settings";
    }

    @PostMapping("/updateSettings")
    public String updateSettings(@RequestParam(value = "userId", required = false) String userId,
                                 @RequestParam(value = "fullName", required = false) String fullName,
                                 @RequestParam(value = "email", required = false) String email,
                                 @RequestParam(value = "gender", required = false) String gender,
                                 @RequestParam(value = "dob", required = false) String dob, ModelMap model) throws SQLException {
        try (Connection connection = DBConnection.getConnection()) {
            if (userId != null && !userId.isEmpty()) {
                PreparedStatement statement = connection.prepareStatement(SQLQueries.UPDATE_USER_PROFILE);
                if (fullName != null) statement.setString(1, fullName);
                else statement.setNull(1, java.sql.Types.VARCHAR);
                if (email != null) statement.setString(2, email);
                else statement.setNull(2, java.sql.Types.VARCHAR);
                if (gender != null) statement.setString(3, gender);
                else statement.setNull(3, java.sql.Types.VARCHAR);
                if (dob != null) statement.setString(4, dob);
                else statement.setNull(4, java.sql.Types.DATE);
                statement.setInt(5, Integer.parseInt(userId));
                statement.executeUpdate();
                statement.close();
                model.addAttribute("successMessage", "Settings updated successfully!");
            }
        }
        return "redirect:/Settings?id=" + userId;
    }

    @GetMapping("/redirectToSettings")
    public String redirectToSettings(@RequestParam int userId, ModelMap model) {
        return "redirect:/Settings?id=" + userId;
    }
}
