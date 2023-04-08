<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="common/header.jspf"%>
    <title>Add Todo</title>
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
        <h1>Add Todo</h1>
        <%--@elvariable id="todo" type=""--%>
        <form:form method="post" modelAttribute="todo">
            <fieldset class="mb-1">
                <form:label path="description">Description </form:label>
                <form:input type="text" path="description" required="required" placeholder="Todo name"/>
                <form:errors path="description" cssClass="text-bg-warning"/>
            </fieldset>
            <fieldset class="mb-3">
                <form:label path="targetDate">Target Date </form:label>
                <form:input type="text" path="targetDate" required="required" placeholder="Todo name"/>
                <form:errors path="targetDate" cssClass="text-bg-warning"/>
            </fieldset>
            <form:input type="hidden" path="id" required="required"/>
            <form:input type="hidden" path="done" required="required"/>
            <input type="submit" class="btn btn-outline-primary">
        </form:form>
    </div>
</div>
<script src="webjars/bootstrap/5.2.3/js/bootstrap.min.js"></script>
<script src="webjars/jquery/3.6.4/jquery.min.js"></script>
<script src="webjars/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker3.standalone.css"></script>
<script src="webjars/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript">
    $('#targetDate').datepicker({
        format: 'dd/mm/yyyy'
    });
</script>
</body>
