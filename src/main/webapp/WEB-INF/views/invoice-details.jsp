<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>




<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
* {
	box-sizing: border-box;
}

input[type=text], select, textarea {
	width: 100%;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 4px;
	resize: vertical;
}

label {
	padding: 12px 12px 12px 0;
	display: inline-block;
}

input[type=submit] {
	background-color: #04AA6D;
	color: white;
	padding: 12px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	float: right;
}

input[type=submit]:hover {
	background-color: #45a049;
}

.container {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}

.col-25 {
	float: left;
	width: 25%;
	margin-top: 6px;
}

.col-75 {
	float: left;
	width: 75%;
	margin-top: 6px;
}

/* Clear floats after the columns */
.row:after {
	content: "";
	display: table;
	clear: both;
}

/* Responsive layout - when the screen is less than 600px wide, make the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
	.col-25, .col-75, input[type=submit] {
		width: 100%;
		margin-top: 0;
	}
}
</style>
</head>



<body>

	<script>
$(document).ready(function() {
  $("donationUpdateform").submit(function(event) {
    // Prevent the default form submission
    event.preventDefault();
    
    // Serialize the form data as JSON
    var formData = {
      payload: {
        roleType: "SUPERADMIN",
        donorName: $("#donorName").val(),
        mobileNumber: $("#mobileNumber").val(),
        emailId: $("#emailId").val(),
        address: $("#address").val(),
        programName: $("#programName").val(),
        amount: $("#amount").val(),
        superadminId: $("#superadminId").val(),
      }
    };
    
    //console.log(formData);
    // Send the JSON data to the server using AJAX
    $.ajax({
      type: "POST",
      url: "addDonation",
      data: JSON.stringify(formData),
      contentType: "application/json",
      success: function(response) {
        // Handle the server response here
        console.log(response);
      },
      error: function(xhr, status, error) {
        // Handle errors
        console.error("AJAX Error: " + error);
      }
    });
  });
});
</script>

	<h2>Responsive Form</h2>
	<p>Resize the browser window to see the effect. When the screen is
		less than 600px wide, make the two columns stack on top of each other
		instead of next to each other.</p>

	<div>${donationDetails.donorName}</div>



	<div class="container">
		<form id="donationUpdateform" action="actionFunction">
			<%-- <c:forEach items="${donationDetails}" var="donation"> --%>
				<div class="row">
					<div class="col-25">
						<label for="fname">Name</label>
					</div>
					<div class="col-75">
						<input type="text" id="donorName" name="donorName" value="${donationDetails.donorName}">
					</div>
				</div>
				<div class="row">
					<div class="col-25">
						<label for="lname">Mobile</label>
					</div>
					<div class="col-75">
						<input type="text" id="mobileNumber" name="mobileNumber" value="${donationDetails.mobileNumber}">
					</div>
				</div>
				<div class="row">
					<div class="col-25">
						<label for="lname">Email Id</label>
					</div>
					<div class="col-75">
						<input type="text" id="emailId" name="emailId" value="${donationDetails.emailId}">
					</div>
				</div>
				<div class="row">
					<div class="col-25">
						<label for="lname">Pan Number</label>
					</div>
					<div class="col-75">
						<input type="text" id="panNumber" name="panNumber" value="${donationDetails.panNumber}">
					</div>
				</div>
				<div class="row">
					<div class="col-25">
						<label for="lname">Address</label>
					</div>
					<div class="col-75">
						<input type="text" id="address" name="address" value="${donationDetails.address}">
					</div>
				</div>
				<div class="row">
					<div class="col-25">
						<label for="lname">Donation For</label>
					</div>
					<div class="col-75">
						<input type="text" id="programName" name="programName" value="${donationDetails.programName}">
					</div>
				</div>
				<div class="row">
					<div class="col-25">
						<label for="lname">Amount</label>
					</div>
					<div class="col-75">
						<input type="text" id="amount" name="amount" value="${donationDetails.amount}">
					</div>
				</div>

				<br>
				<input type="hidden" id="superadminId" name="superadminId" value="${donationDetails.superadminId}">
		<%-- 	</c:forEach> --%>


			<div class="row">
				<input type="submit" value="Submit">
			</div>
		</form>
	</div>

</body>
</html>


