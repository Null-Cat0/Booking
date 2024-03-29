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
<title>Mis favoritos</title>
</head>
<body>
	<%@ include file="navbar.html"%>
	<!-- Fin barra de navegación -->
	<!-- Container con toda la información -->
	<div class="container">
		<h1 class="text-center mt-5">Mis reservas</h1>

		<div class="row mt-5">
			<c:forEach items="${historico}" var="a">
				<div class="col-12">
					<div class="card shadow mb-4">
						<div class="card-body">
							<div class="row align-items-center">
								<div class="col-md-4">
									<img class="img-fluid rounded"
										src="img/ilunion_suites_madrid.jpg"
										alt="Ilunion suites madrid">
								</div>
								<div class="col-md-8">
									<h2>${a.key.city}</h2>
									<h3 class="mt-3">${a.key.name}</h3>
									<p class="mt-3">Reserva</p>
									<h4>${a.value}</h4>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
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