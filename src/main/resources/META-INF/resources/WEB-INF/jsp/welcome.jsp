<%@ include file="common/header.jspf"%>

<style>
        /* Set a background color */
        body {
            background-color: #f2f2f2;
            font-family: "Helvetica Neue", "Helvetica", Arial, sans-serif;
            font-size: 16px;
            line-height: 1.5;
        }

        /* Center the welcome div */
        .welcome {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        /* Style the heading and paragraph */
        h1 {
            font-size: 3rem;
            margin-bottom: 1rem;
            text-align: center;
            color: #333;
        }

        p {
            font-size: 1.5rem;
            margin-top: 0;
            text-align: center;
            color: #666;
        }

        /* Style the link */
        a {
            font-size: 1.5rem;
            text-decoration: none;
            color: #007aff;
            margin-top: 1rem;
            text-align: center;
        }

        /* Style the link on hover */
        a:hover {
            text-decoration: underline;
        }

        /* Style the button */
        .button {
            display: inline-block;
            background-color: #007aff;
            color: #fff;
            padding: 1rem 2rem;
            border-radius: 0.5rem;
            text-decoration: none;
            margin-top: 1rem;
            text-align: center;
            font-size: 1.5rem;
            transition: background-color 0.3s ease;
        }

        /* Style the button on hover */
        .button:hover {
            background-color: #0051a6;
        }
    </style>
</head>
<body>
<%@ include file="common/navigation.jspf"%>

<div class="container">
    <div class="welcome">
        <h1>Welcome ${name}</h1>
        <p>Thank you for visiting our website!</p>
        <a href="list-todos" class="button">Manage Your Todos</a>
        <a href="contact-us">Contact Us</a>
    </div>
</div>
</body>
</html>
