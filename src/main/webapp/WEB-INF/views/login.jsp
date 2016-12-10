<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Login Page</title>
	<link href="resources/css/style.css" rel="stylesheet">
  	<link href="resources/css/style-responsive.css" rel="stylesheet">
</head>
<body class="login-body">
	<section class="login-page">
	<section class="container">
	    <form class="form-signin" action="checkLogin" method="post">
	        <section class="form-signin-heading text-center">
	            <h1 class="sign-title">Sign In</h1>
	            <img src="resources/images/login-logo.png" alt=""/>
	        </section>
	        <section class="login-wrap">
	            <input type="text" name="username" class="form-control" placeholder="Username" autofocus>
	            <input type="password" name="password" class="form-control" placeholder="Password">
	
	            <button class="btn btn-lg btn-login btn-block" type="submit">
	                <i class="fa fa-check"></i>
	            </button>
	        </section>
	    </form>
	</section>
	</section>
</body>
</html>