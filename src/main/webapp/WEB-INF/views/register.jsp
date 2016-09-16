<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Register Page</title>
	<link href="resources/css/style.css" rel="stylesheet">
  	<link href="resources/css/style-responsive.css" rel="stylesheet">
</head>
<body class="login-body">
	<div class="container">
    <form class="form-signin" action="register" method="post">
        <div class="form-signin-heading text-center">
            <h1 class="sign-title">Registration</h1>
            <img src="resources/images/login-logo.png" alt=""/>
        </div>
        <div class="login-wrap">
            <p>Enter your personal details below</p>
            <input type="text" name="fullname" autofocus="" placeholder="Full Name" class="form-control">
            <input type="text" name="email" autofocus="" placeholder="Email" class="form-control">
            <input type="text" name="phonenumber" autofocus="" placeholder="Phone number" class="form-control">
            <input type="text" name="address" autofocus="" placeholder="Address" class="form-control">
            <div class="radios">
                <label for="radio-01" class="label_radio col-lg-6 col-sm-6">
                    <input type="radio" checked="" value="1" id="radio-01" name="gender"> Male
                </label>
                <label for="radio-02" class="label_radio col-lg-6 col-sm-6">
                    <input type="radio" value="0" id="radio-02" name="gender"> Female
                </label>
            </div>

            <p> Enter your account details below</p>
            <input type="text" name="username" autofocus="" placeholder="User Name" class="form-control">
            <input type="password" name="password" placeholder="Password" class="form-control">
            <input type="password" name="confirmPassword" placeholder="Re-type Password" class="form-control">
            <button type="submit" class="btn btn-lg btn-login btn-block">
                <i class="fa fa-check"></i>
            </button>

            <div class="registration">
                Already Registered.
                <a href="${pageContext.request.contextPath }/login" class="">
                    Login
                </a>
            </div>

        </div>
    </form>
	</div>
</body>
</html>