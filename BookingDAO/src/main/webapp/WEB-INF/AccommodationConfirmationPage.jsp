<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
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
<title>Eliminar Habitacion</title>
</head>
<body>
	<%@ include file="navbar.html"%>
	<!-- Fin barra de navegación -->

	<!-- Contenido de la propiedad a borrar -->
	<div class="container">
		<div class="row text-center">
			<div class="col-12">
				<h1>¿Estás seguro de que quieres eliminar esta habitación?</h1>
			</div>
		</div>
		<div class="row ">
				
				<div class="col-3 offset-3 card-body shadow-sm">
				<ul>
				
					<li>Nombre: ${accommodation.name}</li>
					<li>Descripción: ${accommodation.description}</li>
					<li>Precio por noche: ${accommodation.price}</li>
					<li>Numero de habitaciones disponibles:
						${accommodation.numAccommodations}</li>
				</ul>
			</div>
		</div>
		<div class="row">
			<div class="col-12 text-center">
				<form action="?id=${accommodation.id}" method="post">
					<input type="hidden" name="id" value="${accommodation.id}">
					<button type="submit" class="btn btn-danger">Eliminar</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>