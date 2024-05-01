<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Css -->
<link rel="stylesheet" href="css/login.css">
<!-- Font awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
	crossorigin="anonymous">
<title>Inicia sesión</title>
<link rel="icon" type="image/png" href="img/booking_icono.png">
</head>

<body>
	<nav class="navbar navbar-light bookingcolor">
		<a class="navbar-brand ms-3" href="#"> <img
			src="img/booking-logo.png" class="nav-logo" alt="booking_logo">
		</a>
	</nav>

	<div class="container">



		<div class="row justify-content-center">
			<div class="col-4 m-5">
				<h1 class="text-center">Inicia sesión</h1>
				<h6 class="	text-danger">${messages.password}</h6>
				<h6 class="text-danger">${messages.email}</h6> <br>
				<form action="LoginServlet.do" method="post">
					<label class="form-label" for="email">E-mail:</label><br>
					<input  class="form-control" type="email" id="email" name="email" value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" required><br>
					<label class="form-label" for="password">Contraseña:</label><br>
					<input class="form-control" type="password" id="password"
						name="password" placeholder="Indica tu contraseña" required><br>
					<br>
					<!-- minlength="8"  pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\w).{8,}" -->
					<input class="form-control custom-submit" type="submit"
						value="Iniciar sesión">
				</form>
					<p>
					    ¿No tienes una cuenta? 
					    <a href="#!/insertUser">Crea una cuenta</a>
					</p>

			</div>
		</div>
	</div>
	<!-- Footer -->
	<footer class="text-center text-white mt-5"
		style="background-color: #f1f1f1;">
		<div class="container pt-4">
			<section class="mb-4">
				<a class="btn btn-link btn-floating btn-lg text-dark m-1"
					href="https://github.com/Null-Cat0" role="button"
					data-mdb-ripple-color="dark"><i class="fa-brands fa-github"></i>
				</a>
			</section>
		</div>
		<div class="text-center text-dark p-3"
			style="background-color: rgba(0, 0, 0, 0.2);">© 2024 Asier
			Serrano Martín - Programación en Internet - Universidad de
			Extremadura</div>
	</footer>
</body>

</html>