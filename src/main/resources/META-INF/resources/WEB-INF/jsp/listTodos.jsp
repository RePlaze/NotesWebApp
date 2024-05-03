<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="common/header.jspf"%>
<title>Todo List</title>
<style>
    /* Set a background color */
    body {
        background-color: #f5f5f5;
        font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
    }

    /* Center the content */
    .container {
        max-width: 600px;
        margin: 0 auto;
        padding: 20px;
    }

    /* Style the todo card */
    .todo-card {
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        padding: 20px;
        margin-bottom: 20px;
        transition: all 0.8s ease; /* Smooth transition */
    }

    .todo-card h2 {
        font-size: 1.5rem;
        margin-top: 0;
        margin-bottom: 10px;
    }

    .todo-card p {
        margin: 0;
        color: #666;
    }

    .todo-actions {
        margin-top: 10px;
    }

    /* Style the buttons */
    .btn {
        padding: 8px 16px;
        border-radius: 5px;
        text-decoration: none;
        transition: all 0.4s ease;
    }

    .btn-primary {
        background-color: #007bff;
        border: 1px solid #007bff;
        color: #fff;
    }

    .btn-outline-dark {
        background-color: transparent;
        border: 1px solid #343a40;
        color: #343a40;
    }

    .btn-cancel {
        background-color: #343a40 !important;
        border-color: #343a40 !important;
        color: #fff !important;
    }

    .btn-done {
        background-color: #28a745 !important;
        border-color: #28a745 !important;
        color: #fff !important;
    }

    .btn.disabled {
        pointer-events: none;
        opacity: 0.6;
    }

    .btn:hover {
        transform: translateY(-2px);
    }

    /* Style the todo creation card */
    .todo-creation-card {
        opacity: 0;
        max-height: 0;
        overflow: hidden;
        transition: all 0.7s ease; /* Smooth transition */
        margin-bottom: 20px;
        background-color: #343a40; /* Change background color to dark */
        padding: 20px;
        border-radius: 8px;
    }

    .todo-creation-card.active {
        opacity: 1;
        max-height: 1000px; /* Adjust to a suitable value */
        overflow: visible;
    }

    /* Style the add todo button */
    #addTodoButton {
        margin-bottom: 20px;
        transition: all 0.5s ease; /* Smooth transition */
        position: relative;
        z-index: 1;
    }

    #addTodoButton::after {
        content: '';
        position: absolute;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
        background-color: rgba(0, 0, 0, 0.3);
        border-radius: 8px;
        transition: all 0.5s ease;
        z-index: -1;
        opacity: 0;
    }

    #addTodoButton:hover::after {
        opacity: 1;
    }

    #addTodoButton span {
        position: relative;
        z-index: 2;
    }

    #description,
    #targetDate {
        color: #fff;
    }

</style>
</head>
<body>
<%@ include file="common/navigation.jspf"%>
<div class="container">
    <h1 style="text-align: center;">Todo List</h1>
    <button id="addTodoButton" class="btn btn-primary mb-3 fade-in">Add Todo<span>+</span></button>
    <div id="todoCreationCard" class="todo-creation-card">
        <form id="addTodoForm" method="post" action="/add-todo">
            <div class="form-group mb-3">
                <label for="description" style="color: #fff;">Description</label>
                <input type="text" id="description" name="description" required="required" class="form-control" placeholder="Enter todo description"/>
            </div>
            <div class="form-group mb-3">
                <label for="targetDate" style="color: #fff;">Target Date</label>
                <input type="text" id="targetDate" name="targetDate" required="required" class="form-control" placeholder="Select target date"/>
            </div>
            <input type="hidden" id="id" name="id" required="required"/>
            <input type="hidden" id="done" name="done" required="required"/>
            <button id="submitTodo" type="submit" class="btn btn-done btn-block">Done</button>
            <button id="cancelAddTodo" type="button" class="btn btn-cancel btn-block">Cancel</button>
        </form>
    </div>
    <c:forEach items="${todos}" var="todo">
        <div class="todo-card">
            <h2>${todo.description}</h2>
            <p>Target Date: ${todo.formattedTargetDate}</p>
            <div class="todo-actions">
                <a href="update-todo?id=${todo.id}" class="btn btn-primary">Edit</a>
                <a href="delete-todo?id=${todo.id}" class="btn btn-outline-dark">Delete</a>
            </div>
        </div>
    </c:forEach>
</div>
<script src="webjars/jquery/3.6.4/jquery.min.js"></script>
<script src="webjars/bootstrap/5.2.3/js/bootstrap.min.js"></script>
<script src="webjars/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
<script src="webjars/bootstrap-datepicker/1.9.0/locales/bootstrap-datepicker.en-GB.min.js"></script>
<script>
    $(document).ready(function () {
        $('#targetDate').datepicker({
            format: 'dd MM yyyy',
            autoclose: true,
            todayHighlight: true,
            orientation: 'bottom'
        });

        $('#addTodoButton').click(function () {
            $('#todoCreationCard').toggleClass('active'); // Toggle the active class for the todo creation card
            $('#addTodoButton').toggleClass('btn-cancel'); // Toggle the cancel button style
            $('#addTodoButton').toggleClass('disabled'); // Disable/Enable the button
            $('#addTodoButton').toggleClass('fade-in fade-out'); // Toggle fade animation for the button
        });

        $('#cancelAddTodo').click(function () {
            $('#todoCreationCard').removeClass('active'); // Hide the todo creation card
            $('#addTodoButton').removeClass('btn-cancel'); // Reset the button style
            $('#addTodoButton').removeClass('fade-out').addClass('fade-in'); // Reset fade animation
            $('#addTodoButton').removeClass('disabled'); // Enable the button
        });

        $('#addTodoForm').submit(function (event) {
            event.preventDefault(); // Prevent default form submission
            var formData = $(this).serialize(); // Serialize form data
            $.post('/add-todo', formData, function (response) {
                $('#todoCreationCard').removeClass('active'); // Hide the todo creation card
                $('#addTodoButton').removeClass('btn-cancel'); // Reset the button style
                $('#addTodoButton').removeClass('fade-out').addClass('fade-in'); // Reset fade animation
                $('#addTodoButton').removeClass('disabled'); // Enable the button
                $('.container').html(response);
                $('.todo-card').addClass('animated fadeInUp'); // Add animation to new todo card
            });
        });
    });
</script>
</body>
</html>
