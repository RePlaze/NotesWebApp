<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="common/header.jspf"%>

    <title>Todo List</title>
    <style>
        /* Set a background color */
        body {
            background-color: #f5f5f5;
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
    </style>
</head>
<body>
<%@ include file="common/navigation.jspf"%>
<div class="container">
    <div class="welcome">
        <h1>Welcome ${name}</h1>
        <p>Your Todo list ${todos}</p>
        <table class="table">
            <thead>
            <tr>
                <th>Description</th>
                <th>Date</th>
                <th>Complete</th>
                <th>Action</th> <!-- New column for the button -->
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${todos}" var="todo">
                <tr>
                    <td>${todo.description}</td>
                    <td>${todo.targetDate}</td>
                    <td>${todo.done}</td>
                    <td> <!-- Button column -->
                        <a href="delete-todo?id=${todo.id}" class="btn btn-primary">Delete</a>
                    </td>
                    <td> <!-- Button column -->
                        <a href="update-todo?id=${todo.id}" class="btn btn-primary">Update</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <a href="add-todo" class="btn btn-outline-dark">Add Todo</a>
    </div>
</div>
</body>
<script src="webjars/bootstrap/5.2.3/js/bootstrap.min.js"></script>
<script src="webjars/jquery/3.6.4/jquery.min.js"></script>
</html>
