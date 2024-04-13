<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">

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
<title>Eliminar usuario</title>
<link rel="icon" type="image/png" href="img/booking_icono.png">
</head>



<body>
 	<!-- Barra de navegación -->
    <%@ include file="navbar.html" %>

    <!-- Contenido del usuario a borrar -->
    <div class="container mt-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="display-4 mb-4">¿Estás seguro de que quieres eliminar este usuario?</h1>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-md-6">
                <ul class="list-group">
                    <li class="list-group-item"><strong>Nombre:</strong> ${user.name}</li>
                    <li class="list-group-item"><strong>Apellido:</strong> ${user.surname}</li>
                    <li class="list-group-item"><strong>Email:</strong> ${user.email}</li>
                </ul>
            </div>
        </div>
        <div class="row mt-4">
            <div class="col-12 text-center">
                <form action="?id=${user.id}" method="post">
                    <input type="hidden" name="id" value="${user.id}">
                    <button type="submit" class="btn btn-danger btn-lg">Eliminar</button>
                </form>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>