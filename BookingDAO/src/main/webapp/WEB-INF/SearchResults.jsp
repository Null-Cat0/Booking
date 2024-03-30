<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Resultados de la búsqueda</title>
<link rel="icon" type="image/png" href="img/booking_icono.png">

<!-- Css -->
<link rel="stylesheet" href="css/search_results.css">
<!-- Bootstrap -->
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
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
</head>

<body>

	<%@ include file="navbar.html"%>

	<!-- Extensión del color de booking -->
	<div class="container-fluid extColor">
		<div class="container p-3"></div>
	</div>

	<!-- Container con la barra de búsqueda -->
	<div class="container">
		<div class="d-flex row bg-warning busquedaAlojamiento rounded">
			<form class="d-flex" action="ListResultsServlet.do" method="GET">
				<div class="pe-2 pt-2 col-11 form-group">
					<input class="form-control" type="text" id="search" name="search"
						placeholder="¿Adónde vas?" value="${search}" required>
				</div>
				<div class="p-2 col-1 form-group">
					<input class="form-control" type="submit" value="Buscar">
				</div>
			</form>
		</div>
	</div>

	<!-- Container con el contenido -->
	<div class="container">
		<!-- Filtrado -->
		<h2 class="col offset-2">Resultados de la búsqueda</h2>
		<div class="row">
			<div class="col-3">
				<div class="card bg-light mb-3">
					<div class="card-header">
						<h5 class="card-title mb-0">Filtrar por</h5>
					</div>
					<div class="card-body">
						<form id="filterForm">
							<div class="form-group">
								<p class="fw-bold">Tipo de alojamiento</p>
								<div class="form-check">
									<input class="form-check-input" type="checkbox"
										name="accommodationType" value="hotel" id="hotelCheckbox">
									<label class="form-check-label" for="hotelCheckbox">Hotel</label>
								</div>
								<div class="form-check">
									<input class="form-check-input" type="checkbox"
										name="accommodationType" value="apartment"
										id="apartmentCheckbox"> <label
										class="form-check-label" for="apartmentCheckbox">Apartamento</label>
								</div>
								<div class="form-check">
									<input class="form-check-input" type="checkbox"
										name="accommodationType" value="hostel" id="hostelCheckbox">
									<label class="form-check-label" for="hostelCheckbox">Hostal</label>
								</div>
							</div>
							<div class="form-group">
								<p class="fw-bold">Instalaciones</p>
								<div class="form-check">
									<input class="form-check-input" type="checkbox"
										name="facilities" value="wifi" id="wifiCheckbox"> <label
										class="form-check-label" for="wifiCheckbox">Wifi</label>
								</div>
								<div class="form-check">
									<input class="form-check-input" type="checkbox"
										name="facilities" value="pool" id="poolCheckbox"> <label
										class="form-check-label" for="poolCheckbox">Piscina</label>
								</div>
								<div class="form-check">
									<input class="form-check-input" type="checkbox"
										name="facilities" value="parking" id="parkingCheckbox">
									<label class="form-check-label" for="parkingCheckbox">Estacionamiento</label>
								</div>
							</div>
							<div class="form-group">
								<p class="fw-bold">Puntuación</p>
								<div class="form-check">
									<input class="form-check-input" type="checkbox" name="rating"
										value="5" id="5starCheckbox"> <label
										class="form-check-label" for="5starCheckbox">5
										estrellas</label>
								</div>
								<div class="form-check">
									<input class="form-check-input" type="checkbox" name="rating"
										value="4" id="4starCheckbox"> <label
										class="form-check-label" for="4starCheckbox">4
										estrellas</label>
								</div>
								<div class="form-check">
									<input class="form-check-input" type="checkbox" name="rating"
										value="3" id="3starCheckbox"> <label
										class="form-check-label" for="3starCheckbox">3
										estrellas</label>
								</div>
							</div>
							<br>
							<button type="submit" class="btn btn-primary">Aplicar
								filtros</button>
						</form>
					</div>
				</div>
			</div>

			<div class="col">
				<div class="card-deck">
					<c:forEach var="property" items="${listProperties}">
						<div class="card mb-3">
							<div class="row g-0">
								<div class="col-md-4">
									<!-- Imagen -->

								</div>
								<div class="col-md-8">
									<div class="card-body">
										<h5 class="card-title">${property.name}</h5>
										<div class="stars">
											<!-- Estrellas -->
											<%-- Determinar el número de estrellas --%>
											<c:set var="numStars" value="${property.gradesAverage}" />
											<%-- Redondear al entero más cercano --%>
											<c:choose>
												<c:when test="${numStars >= 0.5}">
													<c:set var="numStars" value="${numStars + 0.5}" />
												</c:when>
												<c:otherwise>
													<c:set var="numStars" value="${numStars - 0.5}" />
												</c:otherwise>
											</c:choose>
											<%-- Imprimir las estrellas --%>
											<c:forEach begin="1" end="${numStars}" var="star">
												<i class="fa-solid fa-star"></i>
											</c:forEach>
										</div>
										<p class="card-text">${property.city}a
											${property.centerDistance * 1000} m del centro</p>
										<p class="card-text">${property.address}</p>
										<p class="card-text ecofriendly bold">
											<i class="fa-solid fa-check"></i> Cancelación Gratis
										</p>
										<p class="card-text ecofriendly bold">
											<i class="fa-solid fa-check"></i> Sin pago por adelantado
										</p>
										<!-- Botón de añadir o eliminar favoritos -->
										<c:if test="${fn:contains(listProp, property.id)}">
											<form action="DeleteFavouriteServlet.do" method="post">
												<input type="hidden" name="propertyId"
													value="${property.id}">
												<button type="submit" class="btn btn-outline-danger mt-3">
													<i class="far fa-heart"></i> Eliminar de favoritos
												</button>
											</form>
										</c:if>
										<c:if test="${!fn:contains(listProp, property.id)}">
											<form action="NewFavouriteServlet.do" method="post">
												<input type="hidden" name="propertyId"
													value="${property.id}">
												<button type="submit" class="btn btn-outline-danger mt-3">
													<i class="far fa-heart"></i> Añadir a favoritos
												</button>
											</form>
										</c:if>
									</div>
								</div>
							</div>
							<div class="card-footer">
								<div class="position-absolute top-0 end-0">
									<div class=" bg-primary text-white rounded p-2">${property.gradesAverage}</div>
								</div>
								<p class="card-text linkResultado bold">Ubicación 9,6</p>
								<h6>2 noches, 2 adultos</h6>
								<h3>€285</h3>
								<p>Incluye impuestos y cargos</p>
								<a href="ListPropertyData.do?id=${property.id}"
									class="btn btn-primary">Ver disponibilidad</a>
							</div>
						</div>
					</c:forEach>
				</div>
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
