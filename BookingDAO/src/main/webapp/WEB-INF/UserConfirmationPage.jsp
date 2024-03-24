<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Css -->
<link rel="stylesheet" href="css/search_and_list.css">
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
	crossorigin="anonymous">
<!-- Bootstrap JavaScript (requiere jQuery) -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<!-- Awesome Fots/icons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
<title>Eliminar usuario</title>
<link rel="icon" type="image/png" href="img/booking_icono.png">
</head>



<body>

	
	<%@ include file="navbar.html" %>
	
	
		<!-- Contenido de la propiedad a borrar -->
	<div class="container">
		<div class="row ">
			<div class="col-12">
				<h1>¿Estás seguro de que quieres eliminar esta propiedad?</h1>
			</div>
		</div>
		<div class="row text-center">
			<ul>
				<li>Nombre: ${user.name}</li>
				<li>Apellido: ${user.surname}</li>
				<li>Email: ${user.email}</li>
			</ul>
		</div>
		<div class="row">
			<div class="col-12">
				<form action="?id=${user.id}" method="post">
					<input type="hidden" name="id" value="${user.id}">
					<button type="submit" class="btn btn-danger">Eliminar</button>
				</form>
			</div>
		</div>
	</div>

</body>
</html>