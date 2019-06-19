<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Login Form</title>
	<link type="text/css" rel="stylesheet" href="./css/login_area_style.css">
	<link rel="icon" href="./images/icon_web_window_32.jpg" type="image/jpg" />
</head>

<body>
  <% 
  	  String usrState = (String) session.getAttribute("userstate");
  %>
  <div id="log_area_click" class="login-page">
		<div class="form">
			<a href="javascript:void(0);"><img id="close_btn" src="./images/icon-close.png" alt="Exit button"></a>
			<img id="logo_img" src="./images/uovo.jpg" alt="Logo image not found">
			<p id="wm">Benvenuto</p>
			<form class="login-form" name="login_form" >
				<% if(usrState != null && usrState.equals("present")){%>
					<span id="errUserRegistration">
						<img class="punto_Esc_image" src="./images/puntoEsc.png" alt="(!)">
						<span class="err_txt_reg"> Utente gia' registrato </span>
					</span>
				<%} %>
				<span id="errUserArea">
					<img class="punto_Esc_image" src="./images/puntoEsc.png" alt="(!)">
					<span class="err_txt"> </span>
				</span>
				<input type="text" name="usrname" placeholder="username" id="nomeLog" onblur="validateUsernameLogin($(this))"/>
				<span id="errPwdArea">
					<img class="punto_Esc_image" src="./images/puntoEsc.png" alt="(!)">
					<span class="err_txt"> </span>
				</span>
				<input type="password" name="pwd" placeholder="password" id="passLog" onblur="validatePasswordLogin($(this))"/>
				<button type="button" onclick="formValidationAndSubmit()">Login</button>
				<p class="message">Not registered? <a class="reg_link" href="RegistrationForm.jsp?userType=user">Create an account</a></p>
			</form>
		</div>
	</div>
	<script type="text/javascript" src="script/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="script/login_form_js.js"></script>
</body>

</html>
