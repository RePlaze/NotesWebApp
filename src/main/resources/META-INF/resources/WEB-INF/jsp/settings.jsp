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

        .success-message {
            opacity: 1;
            margin-top: 10px;
            padding: 10px;
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            border-radius: 3px;
            color: #155724;
            animation: fadeIn 0.5s ease-in-out forwards;
        }

        @keyframes fadeIn {
            0% {
                opacity: 0;
            }
            100% {
                opacity: 1;
            }
        }

        .return-button {
            display: block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            text-align: center;
            text-decoration: none;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .return-button:hover {
            background-color: #0053b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Settings</h1>
    <c:if test="${not empty successMessage}">
        <div id="successMessage" class="success-message">${successMessage}</div>
    </c:if>
    <form id="settingsForm" action="/updateSettings" method="post" onsubmit="return validateForm()">
        <input type="hidden" name="userId" value="${userId}">
        <label for="fullName">Full Name:</label>
        <input type="text" id="fullName" name="fullName" required maxlength="50" value="${fullName}">

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required value="${email}">

        <label for="gender">Gender:</label>
        <select id="gender" name="gender">
            <option value="male" ${gender == 'male' ? 'selected' : ''}>Male</option>
            <option value="female" ${gender == 'female' ? 'selected' : ''}>Female</option>
            <option value="other" ${gender == 'other' ? 'selected' : ''}>Other</option>
        </select>

        <label for="dob">Date of Birth:</label>
        <input type="date" id="dob" name="dob" required maxlength="10" value="${dateOfBirth}">

        <input type="submit" value="Save Changes">
    </form>
    <a href="/list-Transfers" class="return-button">Return to Main Page</a>
</div>
<script>
    function validateForm() {
        var email = document.getElementById("email").value;
        var dob = document.getElementById("dob").value;

        var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(email)) {
            alert("Please enter a valid email address");
            return false;
        }

        var dobPattern = /^\d{4}-\d{2}-\d{2}$/;
        if (!dobPattern.test(dob)) {
            alert("Please enter a valid date in YYYY-MM-DD format");
            return false;
        }

        return true;
    }
</script>
</body>
</html>
