<!DOCTYPE html>
<html lang="en">
   <head>
      <%@include file="includes/Head.jsp"%>
      <title>Home</title>
   </head>
   <body>
      <%@include file="includes/Header.jsp"%>
      <div class="container">
         <c:if test="${not empty successMessage}">
            <div class="alert alert-success">
               <strong>Success!</strong> ${successMessage}
            </div>
         </c:if>
         <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
               <strong>Error!</strong> ${errorMessage}
            </div>
         </c:if>
         <c:if test="${not empty userDetails}">
            <div class="col-md-12">
               <h5 class="mt-2"><span class="fa fa-clock-o ion-clock float-right"></span> Welcome <strong>${userDetails.name}</strong> </h5>
            </div>
         </c:if>
         <a href="${pageContext.servletContext.contextPath}/register-user" class="btn btn-outline-dark btn-block" role="button">Register User</a>
         <a href="${pageContext.servletContext.contextPath}/money-withdrawal/INR" class="btn btn-outline-dark btn-block" role="button">Withdraw INR</a>
         <a href="${pageContext.servletContext.contextPath}/money-withdrawal/International" class="btn btn-outline-dark btn-block" role="button">Withdraw International Currency</a>
         <a href="${pageContext.servletContext.contextPath}/check-balance" class="btn btn-outline-dark btn-block" role="button">Check Balance</a>
         <a href="${pageContext.servletContext.contextPath}/delete-user" class="btn btn-outline-dark btn-block" role="button">Delete User</a>
      </div>
   </body>
</html>