<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="contextPath" value="${fn:escapeXml(request.contextPath)}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">

    <!-- Bootstrap JavaScript (requiere jQuery) -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <!-- Css -->
    <link href="css/search_and_list.css" rel="stylesheet" type="text/css">
    <!-- Awesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <!-- Bootstrap Icon -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <title>Propiedades de ${user.name}</title>
</head>
<body>
    <%@ include file="navbar.html"%>

    <!-- Container con toda la información -->
    <div class="container">
        <h1 class="text-center">Mis alojamientos</h1>

        <c:forEach var="property" items="${properties}">
            <a href="NewPropertyServlet.do" class="btn btn-custom">Añadir alojamiento</a>
            <div class="row">
                <h2>${reservation.property.city}</h2>
                <div class="col-6 offset-3">
                    <div class="card shadow">
                        <div class="card-body">
                            <div class="row img-center">
                                <div class="col">
                                    <h5>${property.name}</h5>
                                    <p>Dirección: ${property.address}</p>
                                    <p>Teléfono: ${property.telephone}</p>
                                    <p>Teléfono: ${property.telephone}</p>
                                    <p>Distancia al centro: ${property.centerDistance}</p>
                                </div>
                                <div class="col text-end">
                                    <div class="row">
                                        <a href="<c:url value='EditPropertyServlet.do?id=${property.id}'/>">
                                            <img src="${pageContext.request.contextPath}/img/edit.png"
                                                 alt="edit ${property.id}" />
                                        </a>
                                        <a href="<c:url value='DeletePropertyServlet.do?id=${property.id}'/>">
                                            <img src="${pageContext.request.contextPath}/img/delete.png"
                                                 alt="delete${property.id}" />
                                        </a>
                                        <a href="<c:url value='NewAccommodationServlet.do?id=${property.id}'/>"
                                           class="btn btn-custom">Añadir Habitación</a>
                                    </div>
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
