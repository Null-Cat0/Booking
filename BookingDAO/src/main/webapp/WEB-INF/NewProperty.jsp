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

	<div class="container">
	    <h1 class="text-center">${tipoInformacion} Alojamiento</h1>
	    <div class="row justify-content-center">
	        <form action="?" method="POST" class="col-lg-6 col-md-8 col-sm-10">
	            <input type="hidden" name="id" value="${property.id}">
	            
	            <div class="mb-3">
	                <label for="nombre" class="form-label">Nombre del Alojamiento</label>
	                <input type="text" class="form-control" id="nombre" name="nombreAlojamiento" placeholder="Hotel Ritz Madrid" value="${property.name}">
	            </div>
	            
	            <div class="mb-3">
	                <label for="direccion" class="form-label">Dirección</label>
	                <input type="text" class="form-control" id="direccion" name="direccion" placeholder="Plaza de la Lealtad, 5" value="${property.address}">
	            </div>
	            
	            <div class="mb-3">
	                <label for="tel" class="form-label">Teléfono</label>
	                <input type="tel" class="form-control" id="tel" name="tel" placeholder="915 21 87 00" value="${property.telephone}">
	            </div>
	            
	            <div class="mb-3">
	                <label for="ciudad" class="form-label">Ciudad</label>
	                <input type="text" class="form-control" id="ciudad" name="ciudad" placeholder="Caceres" value="${property.city}">
	            </div>
	            
	            <div class="mb-3">
	                <label for="distancia" class="form-label">Distancia al Centro (en kilómetros)</label>
	                <input type="text" class="form-control" id="distancia" name="distanciaCentro" placeholder="1000" value="${property.centerDistance}">
	            </div>
	            
	            <div class="mb-3">
	                <label for="descripcion" class="form-label">Descripción</label>
	                <input type="text" class="form-control" id="descripcion" name="descripcion" value="${property.description}">
	            </div>
	            
	            <div class="mb-3">
	                <p>Servicios Ofrecidos</p>
	                <ul class="list-group">
	                    <c:forEach items="${mapServices}" var="entry">
	                        <li class="list-group-item">
	                            <input type="checkbox" name="servicios" id="${entry.key}" value="${entry.key}" ${entry.value ? 'checked' : ''}>
	                            <label for="${entry.key}">${entry.key}</label>
	                        </li>
	                    </c:forEach>
	                </ul>
	            </div>
	            
				<div class="mb-3">
				    <label for="mascotas">¿Permite mascotas?</label>
				    <div class="form-check">
				        <input type="radio" id="mascotas-no" name="permitenMascotas" value="No" class="form-check-input" ${property.petFriendly == 0 ? 'checked' : ''}>
				        <label for="mascotas-no" class="form-check-label">No</label>
				    </div>
				    <div class="form-check">
				        <input type="radio" id="mascotas-si" name="permitenMascotas" value="Si" class="form-check-input" ${property.petFriendly == 1 ? 'checked' : ''}>
				        <label for="mascotas-si" class="form-check-label">Sí</label>
				    </div>
				</div>
	            <div class="mb-3">
	                <label for="mascotas">¿Se encuentra disponible?</label>
	                <div class="form-check">
	                    <input type="radio" id="mascotas-no" name="disponibilidad" value="No" class="form-check-input" ${property.available == 0 ? 'checked' : ''}>
	                    <label for="mascotas-no" class="form-check-label">No</label>
	                </div>
	                <div class="form-check">
	                    <input type="radio" id="mascotas-si" name="disponibilidad" value="Si"class="form-check-input" ${property.available == 1 ? 'checked' : ''}>
	                    <label for="mascotas-si" class="form-check-label">Sí</label>
	                </div>
	            </div>
	            <div class="text-center">
	                <input type="submit" class="btn btn-primary" value="${tipoInformacion} Alojamiento">
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