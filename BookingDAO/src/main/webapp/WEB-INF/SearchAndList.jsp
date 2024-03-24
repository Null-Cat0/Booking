<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

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

<!-- Css -->
<link href="css/search_and_list.css" rel="stylesheet" type="text/css">
<!-- Awesome Icons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
<!-- Bootstrap Icon -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">

<title>Búsqueda</title>
<link rel="icon" type="image/png" href="img/booking_icono.png">
</head>

<body>
	
	<%@ include file="navbar.html" %>

	<!-- Extensión del color de la barra de navegación e indicador de la pagina en la que se encuentra -->
	<div class="container-fluid extColor">
		<div class="container p-5">
			<h1>Encuentra tu próxima estancia</h1>
			<h2>Busca ofertas en hoteles, casas y mucho más...</h2>
		</div>
	</div>

	<!-- Formulario de busqueda -->
	<div class="container p-5 pt-0">

		<div class="d-flex row bg-warning busquedaAlojamiento rounded">
			<form class="d-flex" action="ListResultsServlet.do" method="GET">
				<div class="pe-2 pt-2 col-11 form-group">
					<input class="form-control" type="text" id="search" name="search"
						placeholder="¿Adónde vas?" required>
				</div>
				<div class="p-2 col-1 form-group">
					<input class="form-control" type="submit" value="Buscar">
				</div>
			</form>
		</div>
		<!-- Sección de ofertas -->
		<div class="row">
			<h3>Ofertas</h3>
			<h4>Promociones,descuentos y ofertas especiales para ti</h4>
		</div>

		<div class="row ">
			<div class="col m-3 card  shadow rounded">
				<div class="row g-0">
					<div class="col-md-4">
						<img src="img/family.jpeg"
							class="img-fluid rounded-start imagenOfertas" alt="...">
					</div>
					<div class="col-md-8">
						<div class="card-body">
							<h5 class="card-title">Disfruta de tus vacaciones más largas</h5>
							<p class="card-text">Busca alojamientos que ofrezcan
								estancias largas, hay muchas con tarifa mensual reducida.</p>
						</div>
					</div>
				</div>
			</div>

			<div class="col m-3 card shadow rounded">
				<div class="row g-0">
					<div class="col-md-4">
						<a href="trending.html"> <img src="img/avion.png"
							class="img-fluid rounded-start" alt="...">
						</a>
					</div>
					<div class="col-md-8">
						<div class="card-body">
							<h5 class="card-title">Vuela al destino de tus sueños</h5>
							<p class="card-text">Inspírate, compara y reserva vuelos con
								más flexibilidad.</p>
						</div>
					</div>
				</div>
			</div>

		</div>



		<!-- Seccion de alojamientos -->
		<div class="row">
			<h3>Busca por tipo de alojamiento</h3>
			<div class="d-flex flex-row overflow-auto">
				<div class="card m-1">
					<img src="img/hotel.jpeg" class="card-img-top" alt="...">
					<div class="card-body">
						<h5 class="card-title">Hoteles</h5>
					</div>
				</div>
				<div class="card m-1">
					<img src="img/apartamentos.jpeg" class="card-img-top" alt="...">
					<div class="card-body">
						<h5 class="card-title">Apartamentos</h5>
					</div>
				</div>
				<div class="card m-1">
					<img src="img/resort.jpeg" class="card-img-top" alt="...">
					<div class="card-body">
						<h5 class="card-title">Resort</h5>
					</div>
				</div>
				<div class="card m-1">
					<img src="img/villa.jpeg" class="card-img-top" alt="...">
					<div class="card-body">
						<h5 class="card-title">Villas</h5>
					</div>
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