<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
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
<title>Carrito de la compra</title>
<link rel="icon" type="image/png" href="img/booking_icono.png">
</head>
<body>
	<%@ include file="navbar.html"%>
	<!-- Container con la información de la reserva -->
	<div class="container">

		<div class="row ">
			<div class="col-6 mx-auto">
				<h1>Carrito de la compra</h1>
				<c:if test="${empty cart}">
					<div class="alert alert-warning" role="alert">No hay
						habitaciones en el carrito</div>
				</c:if>
				<c:if test="${not empty cart}">
					<div class="row">
						<div class="row justify-content-center">
							<div class="card">
								<div class="card-body">
									<div class="card-title">
										<h4>${property.name}</h4>
										<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>
										<i class="fa-solid fa-star"></i>
									</div>
									<h6>${property.city}a ${property.centerDistance *1000}m del
										centro</h6>
									<h6 class="ecofriendly bold">
										<i class="fa-solid fa-check"></i> Cancelación Gratis
									</h6>
									<h6 class="ecofriendly bold">
										<i class="fa-solid fa-check"></i> Sin pago por adelantado
									</h6>
									<div class="row text-center mb-3">
										<i class="fa-solid fa-trash"></i>
									</div>
								</div>
							</div>


						</div>

						<c:set var="totalPrice" value="0" />
						<div class="row mt-3 justify-content-center">
							<div class="card">
								<div class="card-body">
									<h4>Habitaciones de la reserva:</h4>

									<div class="row ">
										<c:forEach var="entry" items="${cart}">
											<div class="row row-flex p-3">
												<div class="col">${property.name}: ${entry.key.name}</div>
												<div
													class="col-5 bg-primary rounded-4 text-white text-center">
													Habitaciones: ${entry.value}</div>

											</div>
											<c:set var="totalPrice"
												value="${totalPrice + entry.key.price*entry.value}" />
										</c:forEach>
									</div>
								</div>
							</div>




							<div class="row mt-3 justify-content-center">
								<div class="card ">
									<div class="card-body">
										<div class="row">
											<h4>Desglose del precio</h4>


											<h1 class="text-end bold">Total ${totalPrice}€</h1>
											<span class="text-end">Incluye impuestos y cargos</span>
											<p>
												<span class="bold">Información sobre el precio</span> <br>
												<i class="fa-solid fa-money-bill"></i> Incluye ${totalPrice * 0.1}€
												de Impuestos y cargos <br> 10% IVA
											</p>
										</div>
									</div>

								</div>

							</div>
						</div>

						<div class="row text-center">
							<div class="col">
								<a href="" class="btn btn-primary mt-3">Continuar con la
									reserva</a>
							</div>
						</div>
					</div>
				</c:if>
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