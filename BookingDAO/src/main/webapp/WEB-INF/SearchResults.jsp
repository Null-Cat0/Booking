<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Css -->
<link rel="stylesheet" href="css/search_results.css">
<!-- Bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
	crossorigin="anonymous">
<!-- Awesome Fots/icons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

<title>Resultados de la búsqueda</title>
<link rel="icon" type="image/png" href="img/booking_icono.png">
</head>

<body>

	<!-- Barra de navegación -->
	<nav class="navbar navbar-light bookingcolor">
		<div class="container-fluid">
			<a href="search_and_list.html"> <img src="img/booking-logo.png"
				class="nav-logo" alt="booking_logo"></a>
			<div class="container d-flex justify-content-end">

				<a href="my_reservations.html" class="btn btn-custom me-2">Mis
					reservas</a> <a href="cart.html" class="btn btn-custom me-2">Cesta</a>

				<c:if test="${sessionScope.user == null}">
					<a href="create_account.html" class="btn btn-custom me-2">Hazte
						una cuenta</a>
					<a href="login.html" class="btn btn-custom me-2">Iniciar sesión</a>
				</c:if>
				<c:if test="${sessionScope.user != null}">
					<form action="ListUserDataServlet.do" method="post">
						<input class="btn btn-custom me-2" type="submit" value="Perfil">
					</form>
					<a href="logout" class="btn btn-custom me-2">Cerrar sesión</a>
					<a href="NewPropertyServlet.do" class="btn btn-custom me-2">Añadir
						alojamiento</a>
				</c:if>
			</div>
		</div>
	</nav>


	<!-- Extensión del color de booking -->
	<div class="container-fluid extColor">
		<div class="container p-3"></div>
	</div>


	<!-- Container con la barra de búsqueda -->
	<div class="container p-5 pt-0">
		<!-- Formulario de busqueda -->
		<div class="d-flex flex-row bg-warning busquedaAlojamiento rounded">
			<form class="d-flex flex-row" action="search_results.html">
				<div class="p-2 form-group">
					<input class="form-control" type="text" id="search" name="search"
						placeholder="¿Adónde vas?">
				</div>
				<div class="p-2 form-group">
					<input class="form-control" type="date" id="checkin" name="checkin">
				</div>
				<div class="p-2 form-group">
					<input class="form-control" type="date" id="checkout"
						name="checkout">
				</div>
				<div class="p-2 form-group">
					<input class="form-control text-truncate" type="number" id="adults"
						name="Número de adultos" placeholder="Nº adultos">
				</div>
				<div class="p-2 form-group">
					<input class="form-control" type="number" id="children"
						name="Número de niños" placeholder="Nº niños">
				</div>
				<div class="p-2 form-group">
					<input class="form-control" type="number" id="rooms"
						name="Número de habitaciones" placeholder="Nº habitaciones">
				</div>
				<div class="p-2 form-group">
					<input class="form-control" type="submit" value="Buscar">
				</div>
			</form>
		</div>


		<!-- Container con el contenido -->
		<div class="container">

			<!-- Filtrado -->
			<h2 class="col offset-2">Resultados de la busqueda</h2>
			<div class="row ">
				<div class="col-2">
					<h2 class="col">Filtrar por</h2>
					<form id="filterForm">
						<div class="form-group">
							<p class="bold">Tipo de alojamiento</p>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									name="accommodationType" value="hotel" id="hotelCheckbox">
								<label class="form-check-label" for="hotelCheckbox">Hotel</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									name="accommodationType" value="apartment"
									id="apartmentCheckbox"> <label class="form-check-label"
									for="apartmentCheckbox">Apartamento</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									name="accommodationType" value="hostel" id="hostelCheckbox">
								<label class="form-check-label" for="hostelCheckbox">Hostal</label>
							</div>
						</div>
						<div class="form-group">
							<p class="bold">Instalaciones</p>
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
							<p class="bold">Puntuación</p>
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



				<!-- Hoteles -->
				<div class="col">
					<c:forEach var="property" items="${listProperties}">
						<div class="card mb-3">
							<div class="row g-0">
								<div class="col-md-3">
									<img src="img/hotel_don_maunel.webp"
										class="img-fluid imagenResultados" alt="Hotel don manuel">
								</div>
								<div class="col-md-5">
									<div class="card-body">
										<div class="card-title">
											<a class="linkResultado" href="accomodation_details.html">
												<h4>${property.name}</h4>
											</a> <i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>
											<i class="fa-solid fa-star"></i>
										</div>
										<h6>${property.city}a ${property.centerDistance}m del
											centro</h6>
										<h6>${property.address}</h6>
										<h6 class="ecofriendly bold">
											<i class="fa-solid fa-check"></i> Cancelación Gratis
										</h6>
										<h6 class="ecofriendly bold">
											<i class="fa-solid fa-check"></i> Sin pago por adelantado
										</h6>
									</div>
								</div>
								<div class="col-md-4 card-body text-end">
									<p class="card-text">Fabuloso ${property.gradesAverage} •
										4.559 comentarios</p>
									<p class="card-text linkResultado bold">Ubicación 9,6</p>
									<h6>2 noches, 2 adultos</h6>
									<h3>€285</h3>
									<p>Incluye impuestos y cargos</p>
									<a href="accomodation_details.html" class="btn btn-primary">
										Ver disponibilidad</a>
								</div>
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