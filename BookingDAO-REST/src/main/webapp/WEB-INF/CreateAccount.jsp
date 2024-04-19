<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Css -->
<link rel="stylesheet" href="css/create_account.css">
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
	crossorigin="anonymous">

<!-- Awesome Fots/icons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
	
<!-- Bootstrap JavaScript (requiere jQuery) -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<title>Crea tu cuenta</title>
<link rel="icon" type="image/png" href="img/booking_icono.png">
</head>

<body>
    <!-- Barra de navegación -->
    <c:if test="${sessionScope.user == null}">
        <nav class="navbar navbar-light bookingcolor">
            <a class="navbar-brand ms-3" href="ListCategoriesServlet.do"> <img
                src="img/booking-logo.png" class="nav-logo" alt="booking_logo">
            </a>
        </nav>
    </c:if>

    <c:if test="${sessionScope.user !=null}">
        <%@ include file="navbar.html"%>
    </c:if>

    <!-- Container con el formulario para crear/clear cuenta -->
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6 col-lg-4 mt-5">
                <div class="card p-4">
                    <h1 class="text-center mb-4">${tipoInformacion} Cuenta</h1>
                    <div class="text-danger mb-3">${messages.password}</div>
                    <div class="text-danger mb-3">${messages.email}</div>
                    <form action="?" method="post">
                        <div class="mb-3">
                            <label class="form-label" for="name">Nombre:</label>
                            <input class="form-control" type="text" id="name" name="name" placeholder="Indica tu nombre" value="${empty user ? '' : user.name}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label" for="secondname">Apellidos:</label>
                            <input class="form-control" type="text" id="secondname" name="secondname" placeholder="Indica tus apellidos" value="${empty user ? '' : user.surname}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label" for="email">E-mail:</label>
                            <input class="form-control" type="email" id="email" name="email" placeholder="Indica tu dirección de email" value="${empty user ? '' : user.email}" <c:if test="${sessionScope.user != null}"> readonly </c:if>>
                        </div>
                        <div class="mb-3">
                            <label class="form-label" for="password">Contraseña:</label>
                            <input class="form-control" type="password" id="password" name="password" placeholder="Indica tu contraseña" minlength="8" value="${empty user ? '' : user.password}">
                        </div>
                        <div class="mb-3">
                            <input class="btn btn-primary form-control" type="submit" value="${tipoInformacion} cuenta">
                        </div>
                        <c:if test="${not empty sessionScope.user}">
                            <a href="<c:url value='/DeleteAccountServlet.do' />" class="btn btn-danger form-control">Eliminar Cuenta</a>
                        </c:if>
                    </form>
                    <c:if test="${tipoInformacion == 'Crear'}">
                        <p class="text-center mt-3">¿Ya tienes una cuenta? <a href="LoginServlet.do">Inicia sesión</a></p>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${sessionScope.user != null}">
        <!-- Container para la mensajería interna -->
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card">
                        <div class="card-header">Mensajería Interna</div>

                        <div class="card-body">
                            <!-- Aquí puedes incluir el formulario para enviar mensajes internos -->
                            <form action="NewMessageServlet.do" method="post">
                                <div class="mb-3">
                                    <label for="recipient" class="form-label">Destinatario:</label>
                                    <input type="text" class="form-control" id="recipient" name="recipient" placeholder="Nombre del destinatario" required>
                                </div>
                                <div class="mb-3">
                                    <label for="message" class="form-label">Mensaje:</label>
                                    <textarea class="form-control" id="message" name="message" rows="3" placeholder="Escribe tu mensaje aquí" required></textarea>
                                </div>
                                <button type="submit" class="btn btn-primary">Enviar Mensaje</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Mostrar mensajes recibidos -->
        <div class="container mt-5">
            <h2>Mensajes Recibidos</h2>
            <c:if test="${mRecibidos != null}">
	            <c:forEach var="m" items="${mRecibidos}">
	                <p><strong>De:</strong> ${m.key}</p>
	               <p><strong>Mensajes:</strong></p>
					<c:forEach var="mensaje" items="${m.value}">
						<p>${mensaje.texto}</p>
					</c:forEach>
				</c:forEach>
			</c:if>
        </div>

        <!-- Mostrar mensajes recibidos -->
        <div class="container mt-5">
            <h2>Mensajes Enviados</h2>
            <c:if test="${empty mEnviados}">
            	No has enviado mensajes
            </c:if>
            <c:if test="${mEnviados != null}">
	            <c:forEach var="m" items="${mEnviados}">
	                <p><strong>Para:</strong> ${m.key}</p>
	               <p><strong>Mensajes:</strong></p>
					<c:forEach var="mensaje" items="${m.value}">
						<p>${mensaje.texto}</p>
					</c:forEach>
				</c:forEach>
			</c:if>
        </div>

    </c:if>

    <!-- Footer -->
    <footer class="text-center text-white mt-5" style="background-color: #f1f1f1;">
        <div class="container pt-4">
            <section class="mb-4">
                <a class="btn btn-link btn-floating btn-lg text-dark m-1"
                    href="https://github.com/Null-Cat0" role="button"
                    data-mdb-ripple-color="dark"><i class="fa-brands fa-github"></i>
                </a>
            </section>
        </div>

        <div class="text-center text-dark p-3" style="background-color: rgba(0, 0, 0, 0.2);">© 2024 Asier Serrano Martín - Programación en Internet - Universidad de Extremadura</div>
    </footer>

</body>

</html>