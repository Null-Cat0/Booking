<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="es">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- CSS -->
<link rel="stylesheet" href="css/accomodation_details.css">
<!-- Font awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
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
<title>Detalles de ${property.name}</title>
<link rel="icon" type="image/png" href="img/booking_icono.png">
</head>

<body>


	<%@ include file="navbar.html"%>


	<!-- Container -->
	<div class="container mt-2">

		<!-- Barra de busqueda a la izquierda -->
		<div class="row">

			<div class="col-3 bg-warning rounded">
				<form id="filterForm">
					<h5 class="bold mt-2">Buscar</h5>
					<div class="input-group mb-3">
						<span class="input-group-text" id="basic-addon1"><i
							class="fa fa-search"></i></span> <input type="text" class="form-control"
							placeholder="Cáceres" aria-label="Username"
							aria-describedby="basic-addon1">
					</div>
					<label class="form-label" for="hotelCheckbox">Fecha de
						entrada</label> <input class="form-control" type="date"
						name="accomodationEntryDate" id="accomodationEntryDate"> <label
						class="form-label" for="hotelCheckbox">Fecha de salida</label> <input
						class="form-control" type="date" name="accomodationOutDate"
						id="accomodationOutDate"> <label class="form-label"
						for="hotelCheckbox">Número de adultos</label> <input
						class="form-control" type="number" name="accomodationAdults"
						id="accomodationAdults"> <label class="form-label"
						for="hotelCheckbox">Número de niños</label> <input
						class="form-control" type="number" name="accomodationChildren"
						id="accomodationChildren"> <label class="form-label"
						for="hotelCheckbox">Número de habitaciones</label> <input
						class="form-control" type="number" name="accomodationRooms"
						id="accomodationRooms">
					<div class="mt-3">
						<input type="checkbox" class="form-check-input" id="hotelCheckbox">
						<label class="form-check-label" for="hotelCheckbox">Casas
							y apartamentos enteros</label><br> <input type="checkbox"
							class="form-check-input" id="apartmentCheckbox"> <label
							class="form-check-label" for="apartmentCheckbox">Viajo
							por trabajo</label>
					</div>
					<br>
					<div class="text-center mb-3">
						<button type="submit" class="btn btn-primary mt-5">Aplicar
							filtros</button>
					</div>
				</form>
			</div>

			<!-- Información basica del hotel + fotos -->
			<div class="col">
				<div class="row">
					<div class="col-10 mb-23">
						<h2>${property.name}</h2>
						${property.address}<br> Teléfono para reservas: <span
							class="bold"> ${property.telephone}</span>
					</div>
					<div class="col text-end">
						<a href="cart.html" class="btn btn-reserva"> Reservar ahora</a>

						<h5 class="card-title">Fabuloso</h5>
						<h6 class="card-subtitle mb-2 text-muted">${property.gradesAverage}</h6>
						<p class="card-text">4.559 comentarios</p>
					</div>
				</div>
				<!-- Grid de fotos -->
				<div class="wrapper">
					<div class="one">
						<img src="img/NH_1.jpg" alt="Imagen del hotel NH">
					</div>
					<div class="two">
						<img src="img/NH_2.jpg" alt="Imagen del hotel NH">
					</div>
					<div class="three">
						<img src="img/NH_3.jpg" alt="Imagen del hotel NH">
					</div>
					<div class="four">
						<img src="img/NH_4.jpg" alt="Imagen del hotel NH">
					</div>
					<div class="five">
						<img src="img/NH_5.jpg" alt="Imagen del hotel NH">
					</div>
					<div class="six">
						<img src="img/NH_6.jpg" alt="Imagen del hotel NH">
					</div>
				</div>
			</div>

		</div>

		<!-- Información variada del hotel -->
		<div class="row mt-2">
			<h3>Descripción</h3>
			<div>
				<p>${property.description}</p>
			</div>
		</div>

		<!-- Servicios del hotel -->
		<div class="row mt-2">
			<h3>Servicios</h3>
			<div class="row ">


				<c:if test="${listServices != null}">

					<c:forEach items="${listServices}" var="service">
						<div class="col p-2 card">
							<span class="normal-text"> ${service.name}</span>
						</div>
					</c:forEach>
				</c:if>

				<c:if test="${listServices = null}">
					<p>El hotel no dispone de servicios asociados.</p>
				</c:if>

			</div>
		</div>

		<!-- Filtrado segun disponibilidad de habitaciones del hotel y fechas-->
		<div class="row mt-4">
			<h3>Habitaciones Disponibles</h3>


			<!-- Tabla con las habitaciones disponibles -->
			<form action="ListPropertyData.do" method="post">
				<input type="hidden" name="id" value="${property.id}">
				<div class="row pt-4">
					<table>
					
						<tr>
							<th>Tipo de habitación</th>
							<th>Descripción</th>
							<th>Precio</th>
							<th>Numero de habitaciones disponibles</th>
						</tr>

						<c:forEach items="${listAccommodations}" var="room">
							<tr>
								<td>
									<p>${room.name}<br> <span class="ecofriendly">
											Hay ${room.numAccommodations} habitaciones disponibles.</span>
									</p>

								</td>
								<td>${room.description}</td>
								<td>${room.price}€</td>
								<td><input type="number" min="0" max="${room.numAccommodations}" name="nHabitaciones${room.id}" value="0" class="form-input"></td>
							</tr>
						</c:forEach>

					</table>
				</div>
					<input type="submit" class="btn btn-reserva" value="Reservaré">
			</form>
		</div>
	</div>
	<!-- Cierre del container -->

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