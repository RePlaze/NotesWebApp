package Spring.WebApp.login;

import Spring.WebApp.Database.DBConnection;
import Spring.WebApp.Database.SQLQueries;
import Spring.WebApp.Transfer.TransferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Spring.WebApp.Transfer.TransferService.getUserId;

@Controller
public class SettingController {
    private int userID;
    private String username;

    @GetMapping("/Settings")
    public String showSettingsPage(@RequestParam("id") int id, ModelMap model) throws SQLException {
        username = (String) model.get("username");
        userID = getUserId(username);
        System.out.println(username);
        System.out.println(userID);

        // Fetch user profile data from the database
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQueries.SELECT_PROFILE);
        ) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            // Check if user profile exists
            if (resultSet.next()) {
                // Get user profile data from the result set
                String username = resultSet.getString("username");
                String fullName = resultSet.getString("FullName");
                String email = resultSet.getString("Email");
                String gender = resultSet.getString("Gender");
                String dateOfBirth = resultSet.getString("DateOfBirth");

                // Add user profile data to the model
                model.addAttribute("username", username);
                model.addAttribute("fullName", fullName);
                model.addAttribute("email", email);
                model.addAttribute("gender", gender);
                model.addAttribute("dateOfBirth", dateOfBirth);
            } else {
                // User profile not found, handle accordingly
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log SQL exception
        }

        return "settings";
    }

    @PostMapping("/updateSettings")
    public String updateSettings(@RequestParam("fullName") String fullName,
                                 @RequestParam("email") String email,
                                 @RequestParam("gender") String gender,
                                 @RequestParam("dob") String dateOfBirth) {

        // Update user settings in the database
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQueries.UPDATE_USER_PROFILE);
        ) {
            statement.setString(1, fullName);
            statement.setString(2, email);
            statement.setString(3, gender);
            statement.setString(4, dateOfBirth);
            statement.setInt(5, userID);

            int rowsUpdated = statement.executeUpdate();

            // Check if update was successful
            if (rowsUpdated > 0) {
                // Settings updated successfully
            } else {
                // Failed to update settings
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log SQL exception
        }

        // Redirect back to the settings page after updating
        return "redirect:/settings";
    }
    static void setProfileID(String username) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SQLQueries.UPDATE_USER_PROFILE)) {
            int userId = getUserId(username); // Get the user ID
            System.out.println(userId);
            // Set parameters for the INSERT statement
            stmt.setString(1, "fullName");
            stmt.setString(2, "empty@gmail.com");
            stmt.setString(3, "other");
            stmt.setString(4, "2020-02-02");
//            stmt.setInt(5, userID");

            // Execute the INSERT statement
            int affectedRows = stmt.executeUpdate();

            // Check if the insert was successful
            if (affectedRows > 0) {
                connection.commit(); // Commit the transaction
                System.out.println("s");
            } else {
                connection.rollback(); // Rollback the transaction if no rows were affected
                System.out.println("N");
            }
        }
    }

}
