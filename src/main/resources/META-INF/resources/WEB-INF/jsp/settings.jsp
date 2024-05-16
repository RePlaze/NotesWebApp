<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Settings</title>
    <style>
        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
            background-color: #1c1c1e;
            color: #fff;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
        }

        h1 {
            color: #fff;
            text-align: center;
        }

        form {
            background-color: #2c2c2e;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 10px;
            color: #ddd;
            font-weight: bold;
        }

        input[type="text"],
        input[type="email"],
        select,
        input[type="date"],
        input[type="submit"] {
            width: calc(100% - 22px); /* Adjust for date picker button width */
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #444;
            border-radius: 5px;
            box-sizing: border-box;
            background-color: #333;
            color: #ddd;
        }

        input[type="submit"] {
            background-color: #007aff;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #0053b3;
        }

        .error-message {
            color: #ff3b30;
            margin-top: 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Settings</h1>
    <form id="settingsForm" action="updateSettings.jsp" method="post" onsubmit="return validateForm()">
        <label for="fullName">Full Name:</label>
        <input type="text" id="fullName" name="fullName" required maxlength="50" oninput="limitFullNameInput(this);"
               value="${fullName}"> <!-- Populate with user's full name -->
        <span id="fullNameError" class="error-message"></span>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required value="${email}"> <!-- Populate with user's email -->
        <span id="emailError" class="error-message"></span>

        <label for="gender">Gender:</label>
        <select id="gender" name="gender">
            <option value="male" ${gender == 'male' ? 'selected' : ''}>Male</option> <!-- Populate and select user's gender -->
            <option value="female" ${gender == 'female' ? 'selected' : ''}>Female</option>
            <option value="other" ${gender == 'other' ? 'selected' : ''}>Other</option>
        </select>

        <label for="dob">Date of Birth:</label>
        <input type="date" id="dob" name="dob" required maxlength="10" pattern="\d{4}-\d{2}-\d{2}"
               value="${dateOfBirth}"> <!-- Populate with user's date of birth -->
        <span id="dobError" class="error-message"></span>

        <input type="submit" value="Save Changes">
    </form>
</div>
<script>
    function limitFullNameInput(input) {
        var value = input.value.trim();

        if (value.length > 50) {
            input.value = value.slice(0, 50);
            value = input.value.trim(); // Update value after truncation
        }

        var spaceCount = (value.match(/\s/g) || []).length;
        var regex = /^[a-zA-Z\u00C0-\u024F]+(?:\s+[a-zA-Z\u00C0-\u024F]+){0,1}$/;

        if (!regex.test(value)) {
            input.value = value.replace(/[^a-zA-Z\u00C0-\u024F\s]/g, ''); // Remove invalid characters
            document.getElementById("fullNameError").textContent = "Please enter a valid full name (only Latin letters, max 2 spaces)";
        } else {
            document.getElementById("fullNameError").textContent = "";
        }
    }

    function validateForm() {
        var email = document.getElementById("email").value;
        var dob = document.getElementById("dob").value;

        // Email validation
        var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(email)) {
            document.getElementById("emailError").textContent = "Please enter a valid email address";
            return false;
        } else {
            document.getElementById("emailError").textContent = "";
        }

        // Date of Birth validation
        var dobPattern = /^\d{4}-\d{2}-\d{2}$/;
        if (!dobPattern.test(dob)) {
            document.getElementById("dobError").textContent = "Please enter a valid date in YYYY-MM-DD format";
            return false;
        } else {
            document.getElementById("dobError").textContent = "";
        }

        return true;
    }
</script>
</body>
</html>
