<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="common/header.jspf"%>
<title>Transfer List</title>
<style>
    /* Set a background color */
    body {
        background-color: #f5f5f5;
        font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
        color: #333; /* Set default text color */
    }

    /* Center the content */
    .container {
        max-width: 600px;
        margin: 0 auto;
        padding: 20px;
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

    .btn-cancel {
        background-color: #383a40 !important;
        border-color: #535c66T !important;
        color: #fff !important;
    }

    .btn-done {
        background-color: #28a745 !important;
        border-color: #28a745 !important;
        color: #fff !important;
    }

    /* Style the headings */
    h1, h3 {
        text-align: center;
        color: #333;
    }

    /* Style the error message */
    .error-message {
        color: red;
        font-size: 0.8rem;
        margin-top: 0.2rem;
    }
    .error {
        font-weight: bold;
        color: #ff0000;
        text-align: center;
        font-size: 1.8rem;
        display: none;
    }


    /* Style the Transfer creation card */
    .transfer-creation-card {
        opacity: 0;
        max-height: 0;
        overflow: hidden;
        transition: all 0.7s ease; /* Smooth transition */
        margin-bottom: 20px;
        background-color: #343a40; /* Change background color to dark */
        padding: 20px;
        border-radius: 8px;
    }

    .transfer-creation-card.active {
        opacity: 1;
        max-height: 1000px; /* Adjust to a suitable value */
        overflow: visible;
    }

    /* Style the add Transfer button */
    #addTransferButton {
        margin-bottom: 20px;
        transition: all 0.5s ease; /* Smooth transition */
        position: relative;
        z-index: 1;
    }

    #addTransferButton::after {
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

    #addTransferButton:hover::after {
        opacity: 1;
    }

    #addTransferButton span {
        position: relative;
        z-index: 2;
    }

    /* Hover effect on buttons */
    .btn:hover {
        transform: translateY(-2px);
    }
    label {
    color: #ffffff ;
    }
.transfer-card {
    background-color: #333; /* Dark Gray Background */
    border-radius: 15px; /* Rounded Corners */
    padding: 20px;
    box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 1); /* Outer Shadow */
    margin-bottom: 20px;
}
    .sender {
        font-size: 18px;
        font-weight: bold;
        color: #fff; /* White Text */
        margin-bottom: 5px;
    }

.amount, .date {
    font-size: 18px;
    color: #ff0000; /* Red Text */
    margin-bottom: 10px;
}
</style>
</head>
<body>
<%@ include file="common/navigation.jspf"%>
<h1>Phone: ${username}</h1>
<h3>Balance: ${balance}<a href="/list-Transfers"> Update</a></h3>
<p class="error">${error}</p>
<div class="container">
    <h1>Transfer History</h1>
    <button id="addTransferButton" class="btn btn-primary mb-3 fade-in">Send Money</button>
    <div id="TransferCreationCard" class="transfer-creation-card">
        <form id="addTransferForm" method="post" action="/add-Transfer">
            <div class="form-group mb-3">
                <label for="amount">Amount</label>
                <input type="text" id="amount" name="amount" autocomplete="off" required="required" class="form-control" placeholder="150,5 P" pattern="\d+\,\d{0,2}(?!\d)" />
            </div>
            <div class="form-group mb-3">
                <label for="phone">Phone Number</label>
                <input type="tel" id="phone" name="phone" autocomplete="off" required="required" class="form-control" placeholder="Enter phone number" maxlength="11"/>
                <div id="phoneError" class="error-message" style="display: none;">Please enter a valid phone number</div>
                <div id="userNotFoundError" class="error-message" style="display: none;">User not found</div>
            </div>
            <input type="hidden" id="id" name="id" required="required"/>
            <input type="hidden" id="done" name="done" required="required"/>
            <button id="submitTransfer" type="submit" class="btn btn-done btn-block">Done</button>
            <button id="cancelAddTransfer" type="button" style="color: #535c66;" class="btn btn-cancel btn-block">Cancel</button>
        </form>
    </div>
    <c:forEach items="${transfers}" var="transfer">
        <div class="transfer-card">
            <h3 class="sender">Send to: ${transfer.senderUsername}</h3>
            <a style="color: #fff;">Amount:</a><p class="amount">${transfer.amount}</p>
            <a style="color: #fff;">Date:</a><p class="date">${transfer.date}</p>
        </div>
    </c:forEach>
</div>
<script src="webjars/jquery/3.6.4/jquery.min.js"></script>
<script src="webjars/bootstrap/5.2.3/js/bootstrap.min.js"></script>
<script src="webjars/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
<script src="webjars/bootstrap-datepicker/1.9.0/locales/bootstrap-datepicker.en-GB.min.js"></script>
<script>
$(document).ready(function () {
    // Allow only digits and one comma to be typed in the amount input field
    $('#amount').on('input', function () {
        var amount = $(this).val();
        // Remove non-digit and non-comma characters
        amount = amount.replace(/[^\d,]/g, '');
        // Remove leading zeroes
        amount = amount.replace(/^0+/, '');
        // Remove extra commas
        amount = amount.replace(/(,)\1+/g, '$1');
        // Limit to one comma
        var parts = amount.split(',');
        if (parts.length > 2) {
            parts = [parts[0], parts.slice(1).join('')];
        }
        // Limit digits after comma to two
        if (parts.length === 2 && parts[1].length > 2) {
            parts[1] = parts[1].substring(0, 2);
        }
        amount = parts.join(',');
        // Update the value in the input field
        $(this).val(amount);
    });

    // Function to validate phone number input
    $('#phone').on('input', function () {
        // Remove non-digits characters
        var phoneNumber = $(this).val().replace(/\D/g, '');
        // Limit to 11 digits
        phoneNumber = phoneNumber.substring(0, 11);
        // Update the value in the input field
        $(this).val(phoneNumber);
        // Check if phone number is exactly 11 digits
        if (phoneNumber.length === 11) {
            $('#phoneError').hide(); // Hide error message
            $('#submitTransfer').prop('disabled', false).fadeIn(200); // Enable and fade in "Done" button with 200ms duration
        } else {
            $('#phoneError').show(); // Show error message
            $('#submitTransfer').prop('disabled', true).fadeOut(200); // Disable and fade out "Done" button with 200ms duration
        }
    });

    // Toggle the Transfer creation card
    $('#addTransferButton').click(function () {
        $('#TransferCreationCard').toggleClass('active');
        $('#addTransferButton').toggleClass('btn-cancel');
        $('#addTransferButton').toggleClass('disabled');
        $('#addTransferButton').toggleClass('fade-in fade-out');
    });

    // Handle "Done" button click
    $('#submitTransfer').click(function (event) {
        event.preventDefault(); // Prevent default form submission

        // Check if form is valid
        if ($('#addTransferForm')[0].checkValidity()) {
            // Extract form data
            var formData = $('#addTransferForm').serialize();
            var phone = $('#phone').val();

            // Replace commas with dots in the amount field
            var amount = $('#amount').val().replace(/,/g, '.');

            // Convert the amount to a float
            amount = parseFloat(amount);

            // Call withdrawAmount function
            $.post('/withdrawAmount', { phone: phone, amount: amount }, function (withdrawResponse) {
                console.log(withdrawResponse); // Log response
                if (withdrawResponse.success) {
                    console.log("Amount withdrawn successfully");
                    // Call addTransfer function
                    $.post('/addTransfer', formData, function (transferResponse) {
                        console.log(transferResponse); // Log response
                        if (transferResponse.success) {
                            console.log("Transfer added successfully");
                            // Reload the page after a successful transfer
                            location.reload(true);
                        } else {
                            console.error("Failed to add transfer");
                            // Display error message
                            $('.error').text("Failed to add transfer").show(); // Show error message
                        }
                    });
                }
            }).fail(function() {
                console.error("Error occurred during AJAX request");
                // Display generic error message
                $('.error').text("Failed to withdraw amount").show(); // Show error message
            });
        }
    });

    // Cancel Transfer creation
    $('#cancelAddTransfer').click(function () {
        $('#TransferCreationCard').removeClass('active');
        $('#addTransferButton').removeClass('btn-cancel');
        $('#addTransferButton').removeClass('fade-out').addClass('fade-in');
        $('#addTransferButton').removeClass('disabled');
    });
});
</script>
</body>
</html>
