<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Carrito de la compra</title>

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Font Awesome Icons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

<!-- Bootstrap Icons -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">

<!-- Css -->
<link href="css/search_and_list.css" rel="stylesheet" type="text/css">

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>

<!-- Bootstrap JavaScript (requiere Popper.js) -->
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/5.3.2/js/bootstrap.min.js"></script>
</head>
<body>
	<%@ include file="navbar.html"%>

	<!-- Container con la información de la reserva -->
	<div class="container">
		<c:if test="${empty cart}">
			<div class="alert alert-warning" role="alert">No hay reservas
				en el carrito.</div>
		</c:if>

		<c:if test="${not empty cart}">
			<div class="row justify-content-center">
				<div class="col-md-8">
					<h1 class="text-center mb-4">Carrito de la compra</h1>

					<c:set var="totalPrice" value="0" />
					<c:forEach var="aux" items="${cart}" varStatus="status">
						<div class="row mb-3">
							<div class="col-md-6">
								<div class="card">
									<div class="card-body">
										<div class="card-title">
											<h4>${aux.key.name}</h4>
										</div>
										<p class="card-text">${aux.key.city}a
											${aux.key.centerDistance * 1000} m del centro</p>
										<p class="card-text ecofriendly bold">
											<i class="bi bi-check"></i> Cancelación Gratis
										</p>
										<p class="card-text ecofriendly bold">
											<i class="bi bi-check"></i> Sin pago por adelantado
										</p>
										<div class="row text-center mb-3">
											<form action="DeleteCartServlet.do?id="${aux.key.id} method="post">
												<input type="hidden" name="idP" value="${aux.key.id}">
												<button type="submit" class="btn btn-danger">Eliminar</button>
											</form>
										</div>
										<h4 class="mb-3">Habitaciones de la reserva:</h4>
										<div class="row">
											<c:forEach var="entry" items="${aux.value}">
												<div class="col-md-6 mb-3">
													<div class="card">
														<div class="card-body">
															<h5 class="card-title">${aux.key.name}:
																${entry.key.name}</h5>
															<p class="card-text">Habitaciones: ${entry.value}</p>
														</div>
													</div>
												</div>
												<c:set var="totalPrice"
													value="${totalPrice + entry.key.price * entry.value}" />
											</c:forEach>
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>

					<div class="card mt-3">
						<div class="card-body">
							<h4>Desglose del precio</h4>
							<h1 class="text-end bold">Total ${totalPrice}€</h1>
							<p class="text-end">Incluye impuestos y cargos</p>
							<p>
								<span class="bold">Información sobre el precio</span> <br>
								<i class="bi bi-cash"></i> Incluye ${totalPrice * 0.1}€ de
								Impuestos y cargos <br> 10% IVA
							</p>
						</div>
					</div>

					<div class="text-center">
						<form action="AddCartServlet.do" method="post">
							<input type="hidden" name="totalPrice" value="${totalPrice}">
							<input type="submit" class="btn btn-primary mt-3" value="Pagar">
						</form>
					</div>
				</div>
			</div>
		</c:if>
	</div>

	<!-- Footer -->
	<footer class="text-center text-white mt-5"
		style="background-color: #f1f1f1;">
		<div class="container pt-4">
			<section class="mb-4">
				<a class="btn btn-link btn-floating btn-lg text-dark m-1"
					href="https://github.com/Null-Cat0" role="button"
					data-mdb-ripple-color="dark"> <i class="bi bi-github"></i>
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
