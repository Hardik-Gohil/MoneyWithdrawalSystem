<!DOCTYPE html>
<html lang="en">
   <head>
      <%@include file="includes/Head.jsp"%>
      <title>Check Balance</title>
   </head>
   <body>
      <%@include file="includes/Header.jsp"%>
      <div class="container">
         <a href="${pageContext.servletContext.contextPath}/" class="btn btn-link float-right" role="button">Go back</a>
         <div>
            <h5 class="mt-2"><span class="fa fa-clock-o ion-clock float-right"></span> Check Balance</h5>
            <table class="table table-sm table-hover table-striped">
               <tbody>
                  <tr>
                     <td>
                        <strong>Name:</strong> ${userDetails.name}
                     </td>
                     <td>
                        <strong>Email:</strong> ${userDetails.email}
                     </td>
                  </tr>
                  <tr>
                     <td>
                        <strong>Mobile:</strong> ${userDetails.mobile}
                     </td>
                     <td>
                        <strong>Age:</strong> ${userDetails.age}
                     </td>
                  </tr>
                  <tr>
                     <td>
                        <strong>Gender:</strong> ${userDetails.gender}
                     </td>
                     <td></td>
                  </tr>
                  <tr>
                     <td>
                        <strong>Account Number:</strong> ${userDetails.bankAccount.bankAccountNumber}
                     </td>
                     <td>
                        <strong>Monthly Salary:</strong> ${userDetails.bankAccount.monthlySalary}
                     </td>
                  </tr>
                  <tr>
                     <td>
                        <strong>Initial Monthly Withdrawal Limit:</strong> ${userDetails.bankAccount.initialMonthlyWithdrawalLimit}
                     </td>
                     <td>
                        <strong>Monthly Withdrawal Limit:</strong> ${userDetails.bankAccount.monthlyWithdrawalLimit}
                     </td>
                  </tr>
                  <tr>
                     <td>
                        <strong>Current Balance:</strong> ${userDetails.bankAccount.currentBalance}
                     </td>
                     <td></td>
                  </tr>
               </tbody>
            </table>
         </div>
         <table class="table table-striped table-sm table-responsive table-bordered">
            <thead>
               <tr>
                  <th scope="col" class="text-center">#</th>
                  <th scope="col" class="text-center">Type</th>
                  <th scope="col" class="text-center">Amount</th>
                  <th scope="col" class="text-center">Amount In INR</th>
                  <th scope="col" class="text-center">Currency</th>
                  <th scope="col" class="text-center">Notes</th>
                  <th scope="col" class="text-center">Notes Quantity</th>
                  <th scope="col" class="text-center">Conversion Rate</th>
                  <th scope="col" class="text-center">withdrawal Fees</th>
                  <th scope="col" class="text-center">Foreign Transaction Fees</th>
                  <th scope="col" class="text-center">Foreign Transaction Fees In INR</th>
                  <th scope="col" class="text-center">Created On</th>
               </tr>
            </thead>
            <tbody>
               <c:choose>
                  <c:when test="${empty userDetails.bankAccount.transactionHistoryList}">
                     <tr>
                        <td colspan="12" class="text-center">No record found</td>
                     </tr>
                  </c:when>
                  <c:otherwise>
                     <c:forEach items="${userDetails.bankAccount.transactionHistoryList}" var="transactionHistory">
                        <tr>
                           <td class="text-center">${transactionHistory.transactionHistoryId}</td>
                           <td class="text-center">${transactionHistory.transactionType}</td>
                           <td class="text-center">${transactionHistory.amount}</td>
                           <td class="text-center">${transactionHistory.amountInINR}</td>
                           <td class="text-center">${transactionHistory.currency.name}</td>
                           <td class="text-center">${transactionHistory.notes}</td>
                           <td class="text-center">${transactionHistory.notesQuantity}</td>
                           <td class="text-center">${transactionHistory.conversionRate}</td>
                           <td class="text-center">${transactionHistory.withdrawalFees}</td>
                           <td class="text-center">${transactionHistory.foreignTransactionFees}</td>
                           <td class="text-center">${transactionHistory.foreignTransactionFeesInINR}</td>
                           <td class="text-nowrap">
                              <fmt:formatDate pattern = "dd-MM-yyyy HH:mm" value="${transactionHistory.createdOn}"/>
                           </td>
                        </tr>
                     </c:forEach>
                  </c:otherwise>
               </c:choose>
            </tbody>
         </table>
      </div>
   </body>
</html>