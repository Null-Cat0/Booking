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

	<div class="container mt-5">
		<h1 class="text-center mb-5">Mis Favoritos</h1>

		<div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
			<c:choose>
				<c:when test="${empty listProp}">
					<div class="row">
						<div class="alert alert-info" role="alert">No tienes ningún
							favorito guardado.</div>
					</div>
				</c:when>
				<c:otherwise>
					<c:forEach items="${listProp}" var="property">
						<div class="col">
							<div class="card h-100">
								<img src="img/ilunion_suites_madrid.jpg" class="card-img-top"
									alt="Ilunion Suites Madrid">
								<div class="card-body">
									<h5 class="card-title">${property.city}</h5>
									<h6 class="card-subtitle mb-3">${property.name}</h6>
									<p class="card-text">${property.description}</p>
									<p class="card-text">${property.address}</p>
									<form action="DeleteFavouriteServlet.do" method="post">
										<input type="hidden" name="propertyId" value="${property.id}">
										<button type="submit" class="btn btn-danger">
											<i class="fas fa-heart"></i> Eliminar de favoritos
										</button>
									</form>
								</div>
							</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
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