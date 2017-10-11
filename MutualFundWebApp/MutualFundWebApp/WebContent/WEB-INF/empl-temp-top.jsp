<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Carnegie Financial Services</title>

<!-- Bootstrap core CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">

<!-- Custom styles for this template -->
<link href="css/dashboard.css" rel="stylesheet">
<link href="css/jumbotron-narrow.css" rel="stylesheet">
<link href="css/theme.css" rel="stylesheet">
<link href="css/signin.css" rel="stylesheet">
<link href="css/sticky-footer-navbar.css" rel="stylesheet">

<script src="js/ie-emulation-modes-warning.js"></script>

</head>

<body>
	<!-- Navigation -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" 
			    data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<span class="navbar-brand" style="color: #ffffff">Carnegie Financial Services - Mutual Fund Web App</span>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
		<c:choose>
          <c:when test="${ employee == null && customer == null }">
		    <div class="navbar-form navbar-right">
				<a href="loginEmployee.do" class="btn btn-info" type="submit">Employee Login</a>
			</div>
			<div class="navbar-form navbar-right">
				<a href="loginCustomer.do" class="btn btn-info" type="submit">Customer Login</a>
			</div>
			<div class="navbar-form navbar-right">
				<h5 style="color: #ffffff">Welcome</h5>
			</div>
		  </c:when>
          <c:otherwise>
			<div class="navbar-form navbar-right">
				<a href="logout.do" class="btn btn-info" type="submit">Logout</a>
			</div>
			<div class="navbar-form navbar-right">
				<a href="change-pwd.do" class="btn btn-info" type="submit">Change Password</a>
			</div>
			<div class="navbar-form navbar-right">
				<h5 style="color: #ffffff">Hi, ${employee.firstName} ${employee.lastName}</h5>
			</div>
		  </c:otherwise>
	    </c:choose>
		</div>
	</div>
	</nav>
	<!-- end Nav-->

	<!-- top header ends -->

	<!-- side navbar starts -->

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li><a href="createEmployee.do">Create Employee Account</a></li>
					<li><a href="createCustomer.do">Create Customer Account</a></li>
					<li><a href="customerList.do">Manage Customer Account</a></li>
					<li><a href="createFund.do">Create Fund</a></li>
					<li><a href="transitionDay.do">Transition Day</a></li>
				</ul>
			</div>
			<!-- side navbar ends -->
			
			<!-- main content starts -->
			<div class="col-sm-8 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="row">
					<div>
						<ol class="breadcrumb">
