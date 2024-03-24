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
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<!-- Css -->
<link href="css/search_and_list.css" rel="stylesheet" type="text/css">
<!-- Awesome Icons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
<!-- Bootstrap Icon -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
<title>Propiedades de ${user.name}</title>
</head>
<body>
	<%@ include file="navbar.html"%>

	<!-- Container con toda la información -->
	<div class="container">
		<h1 class="text-center">Mis alojamientos</h1>

		<a href="NewPropertyServlet.do" class="btn btn-custom">Añadir
			alojamiento</a>
		<c:set var="collapseId" value="0" />
		<c:forEach var="entry" items="${propertiesAccommodations}">

			<div class="row mt-4">
				<div class="col-6 offset-3">
					<div class="card shadow">
						<div class="card-body">
							<div class="row img-center ">
								<div class="col">
									<h5>${entry.key.name}</h5>
									<p>Dirección: ${entry.key.address}</p>
									<p>Teléfono: ${entry.key.telephone}</p>
									<p>Reseñas: ${entry.key.gradesAverage}</p>
									<p>Ciudad: ${entry.key.city}</p>
									<p>Distancia al centro: ${entry.key.centerDistance}</p>
								</div>
								<div class="col-4 text-end">
									<div class="row pt-3">

										<a
											href="<c:url value='EditPropertyServlet.do?id=${entry.key.id}'/>"
											class="btn bg-success  text-white"> <!--   <img src="${pageContext.request.contextPath}/img/edit.png"  alt="edit ${property.id}" />-->
											Editar Propiedad
										</a>
									</div>
									<div class="row pt-3">
										<a
											href="<c:url value='DeletePropertyServlet.do?id=${entry.key.id}'/>"
											class="btn bg-danger  text-white"> <!--	 <img src="${pageContext.request.contextPath}/img/delete.png"
                                                 alt="delete${property.id}" />-->
											Eliminar Propiedad
										</a>
									</div>
									<div class="row pt-3">
										<a
											href="<c:url value='NewAccommodationServlet.do?id=${entry.key.id}'/>"
											class="btn btn-custom">Añadir Habitación</a>
									</div>
								</div>
								<p class="d-inline-flex gap-1">
									<a class="btn btn-outline-primary" data-bs-toggle="collapse"
										href="#collapseExample${entry.key.id}" role="button"
										aria-expanded="false"
										aria-controls="collapseExample${entry.key.id}">
										Habitaciones: </a>
								</p>
								<div class="collapse  " id="collapseExample${entry.key.id}">
									<c:forEach var="a" items="${entry.value}">
										<div class="card card-body mt-3">
											<div class="row">
												<div class="col-6">
													<p>Nombre: ${a.name}</p>
													<p>Descripción: ${a.description}</p>
													<p>Precio por noche: ${a.price} €</p>
													<p>Habitaciones disponibles: ${a.numAccommodations}</p>
												</div>
											</div>
											<div class="flex-row">
												<a
													href="<c:url value='EditAccommodationServlet.do?id=${a.id}'/>">
													<img src="${pageContext.request.contextPath}/img/edit.png"
													alt="edit ${a.id}"/>
												</a> 
												<a
													href="<c:url value='DeleteAccommodationServlet.do?id=${a.id}'/>">
													<img
													src="${pageContext.request.contextPath}/img/delete.png"
													alt="edit ${a.id}"/>
												</a>
											</div>
										</div>
									</c:forEach>



								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>
