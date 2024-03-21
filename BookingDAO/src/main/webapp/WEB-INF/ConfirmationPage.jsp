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
<title>Eliminar propiedad</title>
<link rel="icon" type="image/png" href="img/booking_icono.png">
</head>



<body>

	<!-- Barra de navegación -->
	<nav class="navbar navbar-light bookingcolor">
		<div class="container-fluid">
			<a href="search_and_list.html"> <img src="img/booking-logo.png"
				class="nav-logo" alt="booking_logo"></a>
			<div class="container d-flex justify-content-end">
				<!-- <a href="edit_accomodation.html"> <button type="button" class="btn btn-custom me-2">Editar alojamiento</button></a> -->



				<c:if test="${sessionScope.user.id == property.idu }">
					<a href="EditPropertyServlet.do?id=${property.id}"
						class="btn btn-custom me-2">Editar alojamiento</a>
					<a href="DeleteProperty.do?id=${property.id}"
						class="btn btn-custom me-2">Eliminar alojamiento</a>
					<a href="" class="btn btn-custom me-2">Añadir habitacion</a>
				</c:if>
				<a href="cart.html" class="btn btn-custom me-2">Cesta</a>
				<c:if test="${sessionScope.user != null}">
					<div class="dropdown">
						<button class="btn btn-custom dropdown-toggle" type="button"
							id="dropdownMenuButton" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">Hola
							${user.name}</button>
						<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
							<a href="my_reservations.html" class="dropdown-item">Mis
								reservas</a> <a href="NewPropertyServlet.do" class="dropdown-item">Añadir
								alojamiento</a> <a href="EditUserServlet.do" class="dropdown-item">Perfil</a>
							<a href="LogOutServlet.do" class="dropdown-item">Cerrar
								sesión</a>
						</div>
					</div>
				</c:if>

				<c:if test="${sessionScope.user == null}">
					<a href="CreateAccountServlet.do" class="btn btn-custom me-2">Hazte
						una cuenta</a>
					<a href="LoginServlet.do" class="btn btn-custom me-2">Iniciar
						sesión</a>
				</c:if>

			</div>
		</div>
	</nav>
	<!-- Fin barra de navegación -->

	<!-- Contenido de la propiedad a borrar -->
	<div class="container">
		<div class="row ">
			<div class="col-12">
				<h1>¿Estás seguro de que quieres eliminar esta propiedad?</h1>
			</div>
		</div>
		<div class="row text-center">
			<ul >
				<li>Nombre: ${property.name}</li>
				<li>Descripción: ${property.description}</li>
				<li>Localización: ${property.address}</li>
				<li>Teléfino: ${property.telephone}</li>
				<li>Valoración: ${property.gradesAverage}</li>
				<li>Dsitancia al centro: ${property.centerDistance}</li>
			</ul>
		</div>
		<div class="row">
			<div class="col-12">
				<form action="?id=${property.id}" method="post">
					<input type="hidden" name="id" value="${property.id}">
					<button type="submit" class="btn btn-danger">Eliminar</button>
				</form>
			</div>
		</div></body>
</html>