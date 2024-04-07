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
<title>Eliminar Habitacion</title>
</head>
<body>
	<%@ include file="navbar.html"%>
	<!-- Fin barra de navegación -->
	
	<!-- Contenido de la propiedad a borrar -->
	<div class="container">
	    <div class="row justify-content-center">
	        <div class="col-md-8 text-center">
	            <h1 class="mb-4">¿Estás seguro de que quieres eliminar esta habitación?</h1>
	        </div>
	    </div>
	    <div class="row justify-content-center">
	        <div class="col-md-6">
	            <div class="card shadow-sm">
	                <div class="card-body">
	                    <ul class="list-group list-group-flush">
	                        <li class="list-group-item"><strong>Nombre:</strong> ${accommodation.name}</li>
	                        <li class="list-group-item"><strong>Descripción:</strong> ${accommodation.description}</li>
	                        <li class="list-group-item"><strong>Precio por noche:</strong> ${accommodation.price}</li>
	                        <li class="list-group-item"><strong>Número de habitaciones disponibles:</strong> ${accommodation.numAccommodations}</li>
	                    </ul>
	                </div>
	            </div>
	        </div>
	    </div>
	    <div class="row justify-content-center mt-4">
	        <div class="col-md-4 text-center">
	            <form action="?id=${accommodation.id}" method="post">
	                <input type="hidden" name="id" value="${accommodation.id}">
	                <button type="submit" class="btn btn-danger btn-lg">Eliminar</button>
	            </form>
	        </div>
	    </div>
	</div>
</body>
</html>