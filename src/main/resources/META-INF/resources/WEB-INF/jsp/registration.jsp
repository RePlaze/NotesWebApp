<%@ include file="common/header.jspf"%>

<title>Registration Page</title>
<style>
    body {
        font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
        background-color: #f7f7f7;
        margin: 0;
    }

    .container {
        margin: 100px auto;
        text-align: center;
        background-color: #fff;
        border-radius: 12px;
        box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);
        width: 400px;
        padding: 40px;
        animation: fadeIn 1s ease-in-out;
    }

    @keyframes fadeIn {
        0% {
            opacity: 0;
            transform: translateY(-40px);
        }
        100% {
            opacity: 1;
            transform: translateY(20);
        }
    }

    .container h4 {
        font-size: 24px;
        font-weight: bold;
        margin-bottom: 20px;
        color: #333;
    }

    .container h5 {
        font-size: 14px;
        color: #8e8e93;
        margin-bottom: 30px;
    }


    input[type="text"],
    input[type="password"] {
        display: block;
        margin: 0 auto;
        background: transparent;
        border: 0;
        border-bottom: 2px solid #ccc;
        border-radius: 0;
        padding: 14px 10px;
        width: 320px;
        outline: none;
        color: #333;
        transition: all .2s ease-out;
        font-size: 16px;
        font-weight: 600;
        margin-bottom: 20px;
    }

    input[type="text"].active,
    input[type="password"].active {
        padding: 10px;
        border-color: lightgray;
    }

    input[type="text"]:focus,
    input[type="password"]:focus {
        border-color: lightgray;
    }

    .reveal-password {
        background: none;
        border: none;
        outline: none;
        position: absolute;
        top: 14px;
        right: 10px;
        cursor: pointer;
    }

    .reveal-password img {
        width: 20px;
        height: auto;
    }
        .btn1 {
            background: #007aff;
            color: #fff;
            border: none;
            border-radius: 100px;
            width: 320px; /* Adjusted width */
            height: 49px;
            font-size: 16px;
            cursor: pointer;
            font-weight: 600;
            transition: background 0.5s ease;
        }

        .btn1:hover {
            background: #0062cc;
        }

</style>
<body id="particles-js">
    <div class="container">
        <form method="post" class="box" action="/registration">
            <h4>Registration</h4>
            <h5>Create a new account.</h5>
            <!-- Display error message if present -->
            <c:if test="${not empty error}">
                <p class="error" style="font-weight: bold; color: #ff0000;">${error}</p>
            </c:if>
            <input type="text" name="username" placeholder="Phone number +7 ...">
            <div style="position: relative;">
                <input type="password" name="password" id="password" placeholder="Password">
                <button type="button" class="reveal-password" id="togglePassword"><img src="https://img.icons8.com/material-rounded/24/000000/visible.png"/></button>
            </div>
            <input type="password" name="repeatPassword" placeholder="Repeat Password">
            <input type="submit" class="btn1" value="Register">
        </form>
    </div>
<script>
        document.addEventListener("DOMContentLoaded", function() {
            const inputs = document.querySelectorAll('input[type="text"], input[type="password"]');
            inputs.forEach(input => {
                input.addEventListener("focus", function() {
                    this.classList.add("active");
                });
                input.addEventListener("blur", function() {
                    if (this.value === '') {
                        this.classList.remove("active");
                    }
                });
            });

            const passwordField = document.getElementById('password');
            const togglePassword = document.getElementById('togglePassword');

            togglePassword.addEventListener('click', function() {
                const type = passwordField.getAttribute('type') === 'password' ? 'text' : 'password';
                passwordField.setAttribute('type', type);
                this.querySelector('img').setAttribute('src', type === 'password' ? 'https://img.icons8.com/material-rounded/24/000000/visible.png' : 'https://img.icons8.com/material-rounded/24/000000/invisible.png');
            });
        });
    </script>
</body>
</html>


