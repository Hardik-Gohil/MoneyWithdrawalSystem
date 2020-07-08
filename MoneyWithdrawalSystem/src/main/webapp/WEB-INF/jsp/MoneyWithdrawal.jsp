<!DOCTYPE html>
<html lang="en">
   <head>
      <%@include file="includes/Head.jsp"%>
      <c:choose>
         <c:when test="${transactionType eq 'INR'}">
            <c:set var="pageTitle" value="INR Withdrawal"/>
         </c:when>
         <c:otherwise>
            <c:set var="pageTitle" value="International Currency Withdrawal"/>
         </c:otherwise>
      </c:choose>
      <title>${pageTitle}</title>
   </head>
   <body>
      <%@include file="includes/Header.jsp"%>
      <div class="container">
         <h2>${pageTitle}</h2>
         <a href="${pageContext.servletContext.contextPath}/" class="btn btn-link float-right" role="button">Go back</a>
         <form class="form-horizontal" onsubmit="return validateForm();" action="${pageContext.servletContext.contextPath}/money-withdrawal" method="POST">
            <input type="hidden" name="transactionType" value="${transactionType}"/>
            <input type="hidden" name="conversionRate" id="conversionRate" value="1"/>
            <input type="hidden" name="foreignTransactionFees" id="foreignTransactionFees" value="0"/>
            <input type="hidden" name="foreignTransactionFeesInINR" id="foreignTransactionFeesInINR" value="0"/>
            <div class="form-group">
               <label class="control-label col-sm-7" for="amount">
                  Withdrawal amount (In multiply of 100):
                  <c:choose>
                     <c:when test="${isFirstTransaction}">
                        <span class="bg-success text-white"> Free Withdrawal </span>
                     </c:when>
                     <c:otherwise>
                        <span class="bg-danger text-white"> Withdrawal fees 5 Rs </span>
                     </c:otherwise>
                  </c:choose>
               </label>
               <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="Enter Withdrawal amount" name="amount" id="amount" onblur="varifyField('Amount')"/>
                  <span class="invalid-feedback" id="amount_span"></span>
                  <span id="transactionFees" class="bg-danger text-white"></span>
               </div>
            </div>
            <div class="form-group">
               <label class="control-label col-sm-7">Select Currency (select one):</label>	
               <div class="col-sm-10">
                  <select class="form-control" id="currencyName" name="currencyName">
                     <c:forEach items="${currencyList}" var="currency" varStatus="currencyIndex">
                        <option value="${currency}" ${currencyIndex.count eq 1 ? 'selected="selected"' : ''}>${currency}</option>
                     </c:forEach>
                  </select>
                  <span class="invalid-feedback" id="currencyName_span"></span>
                  <span id="rateInINR" class="bg-info text-white"></span>
               </div>
            </div>
            <div class="form-group">
               <label class="control-label col-sm-2" for="notes">Choice of notes:</label>
               <div class="col-sm-10" id="choiceOfNotesDiv">
               </div>
            </div>
            <div class="form-group">
               <div class="col-sm-offset-2 col-sm-10">
                  <button type="submit" class="btn btn-success">Submit</button>
               </div>
            </div>
         </form>
      </div>
   </body>
</html>
<script type="text/javascript">
var currencyMap = JSON.parse(JSON.stringify(${currencyMap}));
$(document).ready(function () {
	$("#currencyName").change(function () {
		if (varifyField('CurrencyName')) {
			var base = $("#currencyName").val();
			$.ajax({
				url: "${pageContext.servletContext.contextPath}/get-available-notes?base=" + base,
				success: function (result) {
					$("#choiceOfNotesDiv").html(result).append('<span class="invalid-feedback" id="notes_span"></span>');
					$("input:radio[name=notes]:first").attr('checked', true);
				}
			});

			if ("${transactionType}" != "INR") {
				$("#rateInINR").text("Rate in INR: " + currencyMap[base]["rateInINR"]);
				$("#conversionRate").val(currencyMap[base]["rateInINR"]);
				if ($("#amount").val() != "") {
					$("#amount").blur();
				}
			}

		}
	});
	if ("${transactionType}" != "INR") {
		$("#amount").blur(function () {
			var amount = $("#amount").val();
			var conversionRate = $("#conversionRate").val();
			var foreignTransactionFees = percentage(amount, 2);
			var foreignTransactionFeesInINR = parseFloat(foreignTransactionFees * conversionRate).toFixed(2);
			$("#foreignTransactionFees").val(foreignTransactionFees);
			$("#foreignTransactionFeesInINR").val(foreignTransactionFeesInINR);
			$("#transactionFees").text("Transaction fees of 2% of " + amount + " = " + foreignTransactionFees + " (In INR: " + foreignTransactionFeesInINR + ")");
		});
	}
	$("#currencyName").change();
});

function percentage(num, per) {
	return (num / 100) * per;
}

function validateForm() {
	var flag = false;

	if (varifyField("Amount") & varifyField("CurrencyName") & varifyField("Notes")) {
		flag = true;
	}
	if (flag) {
		if (($("#amount").val() % $("input[name='notes']:checked").val()) != 0) {
			$("#amount_span").text("Should be in multiply of selected notes").show();;
			flag = false;
		}
	}
	return flag;
}

function varifyField(fieldName) {
	var amountReg = /^[0-9]{0,15}$/;

	if (fieldName == "Amount") {
		if ($("#amount").val() == "") {
			$("#amount_span").text("Please enter the details").show();
			return false;
		} else if (!amountReg.test($("#amount").val())) {
			$("#amount_span").text("Allows Max 15 Digit").show();;
			return false;
		} else if (($("#amount").val() % 100) != 0) {
			$("#amount_span").text("Should be in multiply of 100").show();;
			return false;
		} else {
			$("#amount_span").text("");
			return true;
		}
	}
	if (fieldName == "CurrencyName") {
		if ($("#currencyName").val() == "") {
			$("#currencyName_span").text("Please enter the details").show();;
			return false;
		} else {
			$("#currencyName_span").text("");
			return true;
		}
	}
	if (fieldName == "Notes") {
		if (!$('input[name=notes]:checked').length > 0) {
			$("#notes_span").text("Please enter the details").show();;
			return false;
		} else {
			$("#notes_span").text("");
			return true;
		}
	}
}
</script> 
