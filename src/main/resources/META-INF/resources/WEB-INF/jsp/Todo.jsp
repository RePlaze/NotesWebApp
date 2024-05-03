<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="common/header.jspf"%>
<title>Add Todo</title>
<style>
    /* Set a dark background color */
    body {
        background-color: #1e1e1e;
        color: #fff;
    }

    /* Center the content */
    .container {
        max-width: 600px;
        margin: 0 auto;
        padding: 50px 20px;
    }

    /* Style the form */
    form {
        text-align: center;
    }

    /* Style form labels */
    label {
        display: block;
        font-size: 1.2rem;
        margin-bottom: 5px;
    }

    /* Style form inputs */
    input[type="text"] {
        width: 100%;
        padding: 10px;
        border-radius: 5px;
        border: none;
        margin-bottom: 20px;
        background-color: #333;
        color: #fff;
    }

    /* Style submit button */
    input[type="submit"] {
        padding: 10px 20px;
        border: none;
        border-radius: 5px;
        background-color: #007bff;
        color: #fff;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    input[type="submit"]:hover {
        background-color: #0056b3;
    }
</style>
</head>
<body>
<%@ include file="common/navigation.jspf"%>
<div class="container">
    <h1>Add Todo</h1>
    <form:form method="post" modelAttribute="todo">
        <fieldset class="mb-3">
            <label for="description">Description</label>
            <form:input type="text" path="description" required="required" placeholder="Enter todo description"/>
            <form:errors path="description" cssClass="text-bg-warning"/>
        </fieldset>
        <fieldset class="mb-3">
            <label for="targetDate">Target Date</label>
            <input type="text" id="targetDate" name="targetDate" required="required" placeholder="Select target date"/>
            <form:errors path="targetDate" cssClass="text-bg-warning"/>
        </fieldset>
        <input type="hidden" id="id" name="id" required="required"/>
        <input type="hidden" id="done" name="done" required="required"/>
        <input type="submit" value="Add Todo">
    </form:form>
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
    });
</script>
</body>
</html>
