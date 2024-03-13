<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<!DOCTYPE html>
		<html lang="en">

		<head>
			<meta charset="UTF-8">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<!-- Css -->
			<link rel="stylesheet" href="css/edit_accomodation.css">
			<!-- Bootstrap -->
			<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
				integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
				crossorigin="anonymous">
			<!-- Font awesome -->
			<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
			<title>Edicion de alojamiento</title>
			<link rel="icon" type="image/png" href="img/booking_icono.png">
		</head>

		<body>
			<!-- Barra de navegación -->
			<nav class="navbar navbar-light bookingcolor">
				<div class="container-fluid">
					<a href="search_and_list.html"> <img src="img/booking-logo.png" class="nav-logo"
							alt="booking_logo"></a>
					<div class="container d-flex justify-content-end">
						<!-- <a href="edit_accomodation.html"> <button type="button" class="btn btn-custom me-2">Editar alojamiento</button></a> -->

						<a href="my_reservations.html" class="btn btn-custom me-2">Mis
							reservas</a> <a href="cart.html" class="btn btn-custom me-2">Cesta</a>

						<c:if test="${sessionScope.user == null}">
							<a href="create_account.html" class="btn btn-custom me-2">Hazte
								una cuenta</a>
							<a href="login.html" class="btn btn-custom me-2">Iniciar sesión</a>
						</c:if>
						<c:if test="${sessionScope.user != null}">
							<form action="ListUserDataServlet.do" method="post">
								<input class="btn btn-custom me-2" type="submit" value="Perfil">
							</form>
							<a href="logout" class="btn btn-custom me-2">Cerrar sesión</a>
							<a href="NewPropertyServlet.do" class="btn btn-custom me-2">Añadir
								alojamiento</a>
						</c:if>
					</div>
				</div>
			</nav>

			<!-- Container con el formulario para editar alojamiento-->

			<div class="container">
				<h1 class="d-flex justify-content-center">Edición de alojamiento</h1>
				<div class="d-flex justify-content-center">
					<form action="NewPropertyServlet.do" method="POST" class="col-6">
						<label for="nombre" class="form-label" > Nombre del alojamiento</label>
						<input type="text" class="form-control" placeholder="Hotel Ritz Madrid"name="nombreAlojamiento" id="nombre"> 
						<label for="direccion" class="form-label"> Dirección</label> 
						<input type="text" class="form-control" placeholder="Plaza de la Lealtad, 5" name="direccion" id="direccion"> 
						<label for="tel" class="form-label"> Teléfono</label> 
						<input type="text" class="form-control" placeholder="915 21 87 00" id="tel" name="tel"> 
						<label for="distancia" class="form-label"> Distancia al centro (en kilometros)</label>
						<input type="text" class="form-control" placeholder="1000" id="distancia" name="distanciaCentro">
						<label for="valoracion" class="form-label"> Valoración media</label>
						<input type="text" class="form-control" placeholder="8.9" id="valoracion" name="valoracionMedia">
						<label for="descripcion" class="form-label"> Descripción</label>
						<textarea class="form-control" id="descripcion" name="descripcion"
							placeholder="El Hotel Ritz Madrid es un hotel de 5 estrellas que se encuentra en el centro de Madrid."></textarea>
						<p>Sevicios ofrecidos</p>

						<!-- Checkbox de servicios -->
						<ul>
							<li><input type="checkbox" id="wifi"> <label for="wifi">Wifi</label></li>
							<li><input type="checkbox" id="parking"> <label for="parking">Parking</label></li>
							<li><input type="checkbox" id="piscina"> <label for="piscina">Piscina</label></li>
							<li><input type="checkbox" id="gimnasio"> <label for="gimnasio">Gimnasio</label></li>
							<li><input type="checkbox" id="spa"> <label for="spa">Spa</label>
							</li>
							<li><input type="checkbox" id="restaurante"> <label for="restaurante">Restaurante</label>
							</li>
							<li><input type="checkbox" id="bar"> <label for="bar">Bar</label>
							</li>
						</ul>
						<label for="mascotas"> Permite mascotas? (Marque solamente
							si las permite)</label> <input type="radio" name="Permiten mascotas" id="mascotas">
						<div class="justify-content-center mt-5">
							<input class="form-control btn-custom" type="submit" value="Guardar cambios">
						</div>
					</form>



				</div>
			</div>

			<!-- Footer -->
			<footer class="text-center text-white mt-5" style="background-color: #f1f1f1;">
				<div class="container pt-4">
					<section class="mb-4">
						<a class="btn btn-link btn-floating btn-lg text-dark m-1" href="https://github.com/Null-Cat0"
							role="button" data-mdb-ripple-color="dark"><i class="fa-brands fa-github"></i>
						</a>
					</section>
				</div>

				<div class="text-center text-dark p-3" style="background-color: rgba(0, 0, 0, 0.2);">© 2024 Asier
					Serrano Martín - Programación en Internet - Universidad de
					Extremadura</div>

			</footer>
		</body>


		</html>