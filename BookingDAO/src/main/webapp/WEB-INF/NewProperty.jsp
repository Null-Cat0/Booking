<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Css -->
<link rel="stylesheet" href="css/edit_accomodation.css">
<!-- Bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
	crossorigin="anonymous">
<!-- Font awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
<title>Edicion de alojamiento</title>
<link rel="icon" type="image/png" href="img/booking_icono.png">
</head>

<body>
	<%@ include file="navbar.html" %>

	<!-- Container con el formulario para editar alojamiento-->

	<div class="container">
		<h1 class="d-flex justify-content-center">Edición de alojamiento</h1>
		<div class="d-flex justify-content-center">
			<form action="?" method="POST" class="col-6">
				<input type="hidden" name="id" value="${property.id }"> <label
					for="nombre" class="form-label"> Nombre del alojamiento</label> <input
					type="text" class="form-control" placeholder="Hotel Ritz Madrid"
					name="nombreAlojamiento" id="nombre" value='${property.name}'>
				<label for="direccion" class="form-label"> Dirección</label> <input
					type="text" class="form-control"
					placeholder="Plaza de la Lealtad, 5" name="direccion"
					id="direccion" value='${property.address}'>
					 <label
					for="tel" class="form-label"> Teléfono</label> <input type="tel"
					class="form-control" placeholder="915 21 87 00" id="tel" name="tel"
					value='${property.telephone}'> <label for="city"
					class="form-label"> Ciudad</label> <input type="text"
					class="form-control" placeholder="Caceres" id="ciudad"
					name="ciudad" value='${property.telephone}'> <label
					for="distancia" class="form-label"> Distancia al centro (en
					kilometros)</label> <input type="text" class="form-control"
					placeholder="1000" id="distancia" name="distanciaCentro"
					value='${property.centerDistance}'> <label for="valoracion"
					class="form-label"> Valoración media</label> <input type="text"
					class="form-control" placeholder="8.9" id="valoracion"
					name="valoracionMedia" value='${property.gradesAverage}'> <label
					for="descripcion" class="form-label"> Descripción</label> <input
					type="text" class="form-control" id="descripcion"
					name="descripcion" value='${property.description}'>
				<p>Sevicios ofrecidos</p>

				<!-- Checkbox de servicios -->


				<ul>

					<c:forEach items="${mapServices}" var="entry">
						<c:if test="${entry.value == true}">
							<li><input type="checkbox" name="servicios"
								value="${entry.key}" for="${entry.key}"checked  }>
								${entry.key}</li>
						</c:if>
						<c:if test="${entry.value == false}">
							<li><input type="checkbox" name="servicios"
								value="${entry.key}" for="${entry.key}"> ${entry.key}</li>
						</c:if>
					</c:forEach>
				</ul>

				<label for="mascotas"> Permite mascotas?</label> <input type="radio"
					name="permitenMascotas" id="mascotas" value="No"> <label
					for="mascotas">No</label> <input type="radio"
					name="permitenMascotas" id="mascotas" value="Si"> <label
					for="mascotas">Si</label>
				<div class="justify-content-center mt-5">
					<input class="form-control btn-custom" type="submit"
						value="Guardar cambios">
				</div>

			</form>
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