<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="es">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- CSS -->
<link rel="stylesheet" href="css/accomodation_details.css">
<!-- Font awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
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
<title>Detalles de ${property.name}</title>
<link rel="icon" type="image/png" href="img/booking_icono.png">
</head>

<body>
	<%@ include file="navbar.html"%>

	<!-- Barra de búsqueda -->
	<div class="container mt-4">
	    <div class="row">
	
	        <!-- Información del hotel y fotos -->
	        <div class="col">
	            <div class="row mb-4">
	                <div class="col-10">
	                    <h2 class="fw-bold">${property.name}</h2>
	                    <h3 class="mb-0">${property.address}</h3>
	                    <h3 class="mb-0">Teléfono para reservas: <span class="fw-bold">${property.telephone}</span></h3>
	                </div>
	                <div class="col text-end">
	                    <c:choose>
	                        <c:when test="${property.gradesAverage > 4}">
	                            <h5 class="card-title">Fabuloso</h5>
	                        </c:when>
	                        <c:when test="${property.gradesAverage > 3}">
	                            <h5 class="card-title">Excelente</h5>
	                        </c:when>
	                        <c:when test="${property.gradesAverage > 2}">
	                            <h5 class="card-title">Bueno</h5>
	                        </c:when>
	                        <c:when test="${property.gradesAverage > 1}">
	                            <h5 class="card-title">Regular</h5>
	                        </c:when>
	                        <c:otherwise>
	                            <h5 class="card-title">Malo</h5>
	                        </c:otherwise>
	                    </c:choose>
	                    <h6 class="card-subtitle mb-2 text-muted">${property.gradesAverage}</h6>
	                    <p class="card-text">${fn:length(listReviews)} valoraciones</p>
	                </div>
	            </div>
	            <!-- Grid de fotos -->
	            <div class="row mb-4">
	                <div class="col">
	                    <div class="wrapper">
	                        <div class="one">
	                            <img src="img/NH_1.jpg" alt="Imagen del hotel NH">
	                        </div>
	                        <div class="two">
	                            <img src="img/NH_2.jpg" alt="Imagen del hotel NH">
	                        </div>
	                        <div class="three">
	                            <img src="img/NH_3.jpg" alt="Imagen del hotel NH">
	                        </div>
	                        <div class="four">
	                            <img src="img/NH_4.jpg" alt="Imagen del hotel NH">
	                        </div>
	                        <div class="five">
	                            <img src="img/NH_5.jpg" alt="Imagen del hotel NH">
	                        </div>
	                        <div class="six">
	                            <img src="img/NH_6.jpg" alt="Imagen del hotel NH">
	                        </div>
	                    </div>
	                </div>
	            </div>
	
	            <!-- Descripción del hotel -->
	            <div class="row mb-4">
	                <div class="col">
	                    <h3 class="fw-bold">Descripción</h3>
	                    <p>${property.description}</p>
	                </div>
	            </div>
	
	            <!-- Servicios del hotel -->
	            <div class="row mb-4">
	                <div class="col">
	                    <h3 class="fw-bold">Servicios</h3>
	                    <div class="row">
	                        <c:choose>
	                            <c:when test="${not empty listServices}">
	                                <c:forEach items="${listServices}" var="service">
	                                    <div class="col p-2 card">
	                                        <span class="fw-bold">${service.name}</span>
	                                    </div>
	                                </c:forEach>
	                            </c:when>
								<c:otherwise>
								    <div class="col">
								        <p class="text-danger">El hotel no dispone de servicios asociados.</p>
								    </div>
								</c:otherwise>
	                        </c:choose>
	                    </div>
	                </div>
	            </div>
	
	            <!-- Habitaciones Disponibles -->
	            <c:choose>
	                <c:when test="${fn:length(listAccommodations) > 0}">
	                    <div class="row mb-4">
	                        <div class="col">
	                            <h3 class="fw-bold">Habitaciones Disponibles</h3>
	                            <!-- Tabla con las habitaciones disponibles -->
	                            <form action="ListPropertyData.do" method="post">
	                                <input type="hidden" name="id" value="${property.id}">
	                                <div class="row pt-4">
	                                    <table class="table">
	                                        <thead>
	                                            <tr>
	                                                <th>Tipo de habitación</th>
	                                                <th>Descripción</th>
	                                                <th>Precio</th>
	                                                <th>Número de habitaciones disponibles</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                            <c:forEach items="${listAccommodations}" var="room">
	                                                <tr>
	                                                    <td>
	                                                        <p>${room.name}<br>
	                                                            <span class="ecofriendly">Hay ${room.numAccommodations} habitaciones disponibles.</span>
	                                                        </p>
	                                                    </td>
	                                                    <td>${room.description}</td>
	                                                    <td>${room.price}€</td>
	                                                    <td>
	                                                        <input type="number" min="0" max="${room.numAccommodations}" name="nHabitaciones${room.id}" value="0" class="form-control">
	                                                    </td>
	                                                </tr>
	                                            </c:forEach>
	                                        </tbody>
	                                    </table>
	                                </div>
	                                <div class="text-center">
	                                    <input type="submit" class="btn btn-reserva" value="Reservar">
	                                </div>
	                            </form>
	                        </div>
	                    </div>
	                </c:when>
	                <c:otherwise>
	                    <div class="row mb-4">
	                        <div class="col">
	                            <h3 class="fw-bold">Habitaciones Disponibles</h3>
	                           <p class="text-danger">El hotel no dispone de habitaciones disponibles actualmente.</p>
	                        </div>
	                    </div>
	                </c:otherwise>
	            </c:choose>
	
	            <!-- Valoraciones -->
	            <c:if test="${property.id != user.id and !hasReviewed}">
	                <div class="row mb-4">
	                    <div class="col">
	                        <div class="card">
	                            <div class="card-body">
	                                <h3 class="card-title">Valoraciones</h3>
	                                <form action="NewReviewServlet.do" method="post">
	                                    <input type="hidden" name="propertyId" value="${property.id}">
	                                    <div class="mb-3">
	                                        <textarea class="form-control" name="valoracion" rows="5" placeholder="Escribe aquí tu valoración..."></textarea>
	                                        <input type="number" name="puntuacion" min="0" max="5" class="form-control mt-3" placeholder="Puntuación (0-5)">
	                                    </div>
	                                    <button type="submit" class="btn btn-primary">Dejar valoración</button>
	                                </form>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </c:if>
	
	            <!-- Listado de valoraciones -->
	            <div class="row">
	                <div class="col">
	                    <div class="card">
	                        <div class="card-body">
	                            <h3 class="card-title">Valoraciones de otros usuarios</h3>
	                            <c:choose>
	                                <c:when test="${not empty listReviews}">
	                                    <c:forEach items="${listReviews}" var="review">
	                                        <div class="card mt-3">
	                                            <div class="card-body">
	                                                <h5 class="card-title ${review.value.grade < 3 ? 'text-warning' : ''} ${review.value.grade < 2 ? 'text-danger' : ''}">
	                                                    ${review.key.name}: ${review.value.grade}/5
	                                                </h5>
	                                                <p class="card-text">${review.value.review}</p>
	                                                <c:if test="${review.key.id == user.id}">
	                                					<button class="btn btn-outline-danger me-2" id="eliminarReviewBtn"  data-review-id="${property.id}">Eliminar Review</button>
                                					</c:if>
	                                            </div>
	                                        </div>
	                                    </c:forEach>
	                                </c:when>
	                                <c:otherwise>
	                                    <p>No hay valoraciones para este alojamiento.</p>
	                                </c:otherwise>
	                            </c:choose>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
<!-- Cierre del container -->

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
<script>
	document.getElementById('eliminarReviewBtn').addEventListener('click', function() {
	    var reviewId = this.getAttribute('data-review-id');
	    var url = "DeleteReviewServlet.do";
	    
	    fetch(url, {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/x-www-form-urlencoded',
	        },
	        body: 'id=' + reviewId // Enviar el ID de la revisión en el cuerpo de la solicitud
	    })
	    .then(function(response) {
	        if (!response.ok) {
	            throw new Error('Error al eliminar la revisión');
	        }
	        console.log('Revisión eliminada con éxito');
	        
	        // Recargar la página después de eliminar la revisión
	        window.location.reload(true);
	        window.location.reload();
	    })
	    .catch(function(error) {
	        console.error('Error:', error.message);
	    });
	});
</script>

</html>