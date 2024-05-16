**Web Application Setup Guide**

This guide will help you set up the Spring Boot web application along with the required dependencies and SQL database.

### Prerequisites

- Java Development Kit (JDK 20)
- Maven
- MySQL Database Server (or else)

### Steps to Setup

1. **Clone the Repository:**

   Clone the project repository from [GitHub](https://github.com) to your local machine.

   ```bash
   git clone <https://github.com/RePlaze/NotesWebApp.git>
   ```

2. **Setup MySQL Database:**

   Create a MySQL database and execute the following SQL script to create the required tables:

   ```sql
   CREATE TABLE profile (
       ID int AUTO_INCREMENT PRIMARY KEY,
       FullName varchar(100),
       Email varchar(100),
       Gender enum('Male','Female','Other'),
       DateOfBirth date
   );

   CREATE TABLE transfers (
       id int AUTO_INCREMENT PRIMARY KEY,
       amount float,
       phone varchar(11),
       transfer_date date,
       user_id int
   );

   CREATE TABLE users (
       id int AUTO_INCREMENT PRIMARY KEY,
       username varchar(50),
       password varchar(100),
       Balance float,
       profile int
   );
   ```

3. **Update Database Configuration:**

   Open the `application.properties` file located in `src/main/resources` and update the database connection properties.

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/<database-name>
   spring.datasource.username=<username>
   spring.datasource.password=<password>
   ```

3. **Database Connection Class:**

   The `DBConnection` class in the `Spring.WebApp.Database` package provides a method to obtain a connection to the database.

   ```java
   package Spring.WebApp.Database;

   import java.sql.Connection;
   import java.sql.DriverManager;
   import java.sql.SQLException;

   public class DBConnection {
       private static final String URL = "jdbc:mysql://localhost:3306/crud";
       private static final String USERNAME = "root";
       private static final String PASSWORD = "password";

       public static Connection getConnection() throws SQLException {
           return DriverManager.getConnection(URL, USERNAME, PASSWORD);
       }
   }
   ```
   Ensure that the `URL`, `USERNAME`, and `PASSWORD` variables match your MySQL database credentials.


4. **Use the Connection in Your Application:**

   You can now use the `getConnection()` method of the `DBConnection` class to obtain a database connection wherever needed in your Spring Boot application.

   ```java
   Connection connection = DBConnection.getConnection();
   ```

With these steps, you have configured the database connection for your Spring Boot application, allowing it to interact with the MySQL database seamlessly.
4. **Run the Application:**

   Navigate to the project directory and run the following Maven command to start the Spring Boot application:

   ```bash
   mvn spring-boot:run
   ```

5. **Access the Application:**

   Once the application is running, you can access it at [localhost:8080/login](http://localhost:8080/login).

### Libraries and Dependencies

- **Spring Boot**
- **MySQL Connector Java:** For connecting to MySQL database.
- **Hibernate Validator:** An implementation of the Bean Validation API.
- *Jakarta Servlet JSP JSTL API:* For JSP and JSTL support.
- *GlassFish JSTL:* For JSTL implementation.
- *WebJars:* For managing frontend dependencies such as Bootstrap, jQuery, and Bootstrap Datepicker.

### Project Structure

- `src/main/java`: Contains Java source files.
- `src/main/resources`: Contains application properties and resource files.
- `src/main/webapp`: Contains JSP files for views.(html)
- `pom.xml`: Contains project dependencies and configuration.

### Additional Notes

- Make sure to adjust the database connection properties according to your MySQL configuration.
- You can customize the application further by modifying the Java source files and JSP views as needed.

That's it! You have successfully set up the Spring Boot web application with MySQL database.