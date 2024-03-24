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
<title>Añadir Habitación</title>
</head>
<body>
	<%@ include file="navbar.html"%>

	<!-- Container con el formulario para editar alojamiento-->

	<div class="container">
		<h1 class="text-center mt-5">${tipoInformacion} Habitación</h1>
		<div class="row ">
			<div class="d-flex justify-content-center ">
			
				<form action="?" method="post" class="col-6 ">
					<input type="hidden" name="idp" value='${idp}'>
					<input type="hidden" name="ida" value='${accommodation.id}'>
					<label for="name" class="form-label"> Nombre</label>
					 <input
						type="text" class="form-control" placeholder="Suite" name="name"
						id="name" value='${accommodation.name}' required> 
						<label
						for="price" class="form-label"> Precio por habitación:</label> 
						<input
						type="number" class="form-control" placeholder="50$" name="price"
						id="price" value='${accommodation.price}'required> 
						<label
						for="descripcion" class="form-label"> Descripcion:</label> 
						<input
						type="text" class="form-control" placeholder="Habitación bonita"
						name="description" id="descripcion"
						value='${accommodation.description}'required>
						 <label
						for="nAccommodations" class="form-label"> Número de
						habitaciones disponibles:</label> 
						<input type="number" class="form-control"
						placeholder="15" name="nAccommodations" id="nAccommodations"
						value='${accommodation.numAccommodations}'required>
				
						<div class="form-group d-flex justify-content-center">
						    <button type="submit" class="btn btn-primary mt-3 mx-auto">${tipoInformacion} habitación</button>
						</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>