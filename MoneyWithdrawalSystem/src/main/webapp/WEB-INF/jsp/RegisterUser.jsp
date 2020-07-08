<!DOCTYPE html>
<html lang="en">
   <head>
      <%@include file="includes/Head.jsp"%>
      <title>User registration form</title>
   </head>
   <body>
      <%@include file="includes/Header.jsp"%>
      <div class="container">
         <h2>User registration form</h2>
         <a href="${pageContext.servletContext.contextPath}/" class="btn btn-link float-right" role="button">Go back</a>
         <form:form cssClass="form-horizontal" onsubmit="return validateForm();" action="${pageContext.servletContext.contextPath}/register-user" modelAttribute="userDetails" method="POST">
            <div class="form-group">
               <label class="control-label col-sm-2" for="name">Name:</label>
               <div class="col-sm-10">
                  <form:input cssClass="form-control" placeholder="Enter Name" path="name" id="name" onblur="varifyField('Name')"/>
                  <span class="invalid-feedback" id="name_span"></span>
               </div>
            </div>
            <div class="form-group">
               <label class="control-label col-sm-2" for="email">Email:</label>
               <div class="col-sm-10">
                  <form:input cssClass="form-control" placeholder="Enter email" path="email" id="email" onblur="varifyField('Email')"/>
                  <span class="invalid-feedback" id="email_span"></span>
               </div>
            </div>
            <div class="form-group">
               <label class="control-label col-sm-2" for="mobile">Mobile:</label>
               <div class="col-sm-10">
                  <form:input cssClass="form-control" placeholder="Enter Mobile" path="mobile" id="mobile" onblur="varifyField('Mobile')"/>
                  <span class="invalid-feedback" id="mobile_span"></span>
               </div>
            </div>
            <div class="form-group">
               <label class="control-label col-sm-2" for="age">Age:</label>
               <div class="col-sm-10">
                  <form:input cssClass="form-control" placeholder="Enter Age" path="age" id="age" onblur="varifyField('Age')"/>
                  <span class="invalid-feedback" id="age_span"></span>
               </div>
            </div>
            <div class="form-group">
               <label class="control-label col-sm-2" for="gender">Gender:</label>
               <div class="col-sm-10">
                  <label class="radio-inline">
                     <form:radiobutton value="Male" path="gender" checked="checked" onclick="varifyField('Gender')"/>
                     Male
                  </label>
                  <label class="radio-inline">
                     <form:radiobutton value="Female" path="gender" onclick="varifyField('Gender')"/>
                     Female
                  </label>
                  <span class="invalid-feedback" id="gender_span"></span>
               </div>
            </div>
            <div class="form-group">
               <label class="control-label col-sm-2" for="monthlySalary">Monthly salary:</label>
               <div class="col-sm-10">
                  <form:input cssClass="form-control" placeholder="Enter Monthly salary" path="bankAccount.monthlySalary" id="monthlySalary" onblur="varifyField('MonthlySalary')"/>
                  <span class="invalid-feedback" id="monthlySalary_span"></span>
               </div>
            </div>
            <div class="form-group">
               <div class="col-sm-offset-2 col-sm-10">
                  <button type="submit" class="btn btn-success">Submit</button>
               </div>
            </div>
         </form:form>
      </div>
   </body>
</html>
<script type="text/javascript">
$(document).ready(function () {
	$("#mobile").val("");
	$("#age").val("");
	$("#monthlySalary").val("");
});

function validateForm() {
	var flag = false;

	if (varifyField("Name") & varifyField("Email") & varifyField("Mobile") & varifyField("Age") & varifyField("Gender") & varifyField("MonthlySalary")) {
		flag = true;
	}

	return flag;
}

function varifyField(fieldName) {
	var alphabetsReg = /^[a-zA-Z ]{0,100}$/;
	var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	var mobileReg = /^[0-9]{10}$/;
	var ageReg = /^[1-9]?[0-9]{1}$|^100$/;
	var monthlySalaryReg = /^([1-9]\d*|0)(\.\d{2})?$/;

	if (fieldName == "Name") {
		if ($("#name").val() == "") {
			$("#name_span").text("Please enter the details").show();
			return false;
		} else if (!alphabetsReg.test($("#name").val())) {
			$("#name_span").text("Allows max. 100 alphabets").show();;
			return false;
		} else {
			$("#name_span").text("");
			return true;
		}
	}
	if (fieldName == "Email") {
		if ($("#email").val() == "") {
			$("#email_span").text("Please enter the details").show();;
			return false;
		} else if (!emailReg.test($("#email").val())) {
			$("#email_span").text("Please enter the valid email").show();;
			return false;
		} else {
			$("#email_span").text("");
			return true;
		}
	}
	if (fieldName == "Mobile") {
		if ($("#mobile").val() == "") {
			$("#mobile_span").text("Please enter the details").show();;
			return false;
		} else if (!mobileReg.test($("#mobile").val())) {
			$("#mobile_span").text("Allows 10 Digit").show();;
			return false;
		} else {
			$("#mobile_span").text("");
			return true;
		}
	}
	if (fieldName == "Age") {
		if ($("#age").val() == "") {
			$("#age_span").text("Please enter the details").show();;
			return false;
		} else if (!ageReg.test($("#age").val())) {
			$("#age_span").text("Allows only digits and must not be more than 100").show();;
			return false;
		} else {
			$("#age_span").text("");
			return true;
		}
	}
	if (fieldName == "Gender") {
		if (!$('input[name=gender]:checked').length > 0) {
			$("#gender_span").text("Please enter the details").show();;
			return false;
		} else {
			$("#gender_span").text("");
			return true;
		}
	}
	if (fieldName == "MonthlySalary") {
		if ($("#monthlySalary").val() == "") {
			$("#monthlySalary_span").text("Please enter the details").show();;
			return false;
		} else if (!monthlySalaryReg.test($("#monthlySalary").val())) {
			$("#monthlySalary_span").text("Allows only digits, Either 0 or 2 decimal digits and no leading zeroes in integer part").show();;
			return false;
		} else {
			$("#monthlySalary_span").text("");
			return true;
		}
	}
}
</script>
