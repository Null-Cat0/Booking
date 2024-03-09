<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html id="list">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/stylesheet.css" />
<title>List of Properties</title>
</head>
<body>
	<header>
		<img id="logo" src="img/uex.png" alt="logo">
		<h1>Booking DAO Testing Application</h1>
	</header>
	<div class="centered">
		<h1>Different Lists:</h1>
		<h2>List of Properties:</h2>
	</div>
	<table class="table-responsive">
		<thead>
			<tr>
				<th>Name</th>
				<th>Description</th>
				<th>Address</th>
				<th>Telephone</th>
				<th>City</th>
				<th>Center distance</th>
				<th>Punctuation</th>
				<th>Pet Friendly</th>
				<th>Available</th>
				<th>User</th>
				<th>Category Ids</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="property" items="${propertiesList}">
				<tr>
					<td>${property.first.name}</td>
					<td>${property.first.description}</td>
					<td>${property.first.address}</td>
					<td>${property.first.telephone}</td>
					<td>${property.first.city}</td>
					<td>${property.first.centerDistance}</td>
					<td>${property.first.gradesAverage}</td>
					<td><c:choose>
							<c:when test="${property.first.petFriendly=='1'}">
		    					Yes
		    				</c:when>
							<c:otherwise>
		    					No
		    				</c:otherwise>
						</c:choose></td>
					<td><c:choose>
							<c:when test="${property.first.available=='1'}">
		    					Yes
		    				</c:when>
							<c:otherwise>
		    					No
		    				</c:otherwise>
						</c:choose></td>

					<td>${property.second.name}</td>
					<td><c:forEach var="category" items="${property.third}">
							${category.idct} - 		    	
							</c:forEach></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="centered">
		<h2>List of Properties by Users:</h2>
	</div>
	<div class="sections centered">
		<c:forEach var="propertiesByUser" items="${usersMap}">
			<div class="shadow">
				<h3>User: ${propertiesByUser.key.name}</h3>
				<h3>Email: ${propertiesByUser.key.email}</h3>
				<div class="sections centered">
					<c:forEach var="property" items="${propertiesByUser.value}">
						<section class="accommodation shadow centered">
							<img src="https://uex.be/q3e57" alt="Hotel">
							<h2 class="centered">${property.name}</h4>
								<p>${property.description}</p>
								<p>Address: ${property.address}</p>
								<p>Telephone: ${property.telephone}</p>
								<p>City: ${property.city}</p>
								<p>Center Distance: ${property.centerDistance}</p>
								<p>Punctuation: ${property.gradesAverage}</p>

								<p>
									<c:choose>
										<c:when test="${property.petFriendly=='1'}">
					    					PetFriendly
					    				</c:when>
										<c:otherwise>
					    					Not PetFriendy
					    				</c:otherwise>
									</c:choose>
								</p>
								<p>
									<c:choose>
										<c:when test="${property.available=='1'}">
					    					Available
					    				</c:when>
										<c:otherwise>
					    					Not Available
					    				</c:otherwise>
									</c:choose>
								</p>
								<button>Ver Detalles</button>
						</section>
					</c:forEach>
				</div>
			</div>
		</c:forEach>
	</div>
	<footer>
		<p>&copy; 2024 Booking App - Internet Programming Students</p>
	</footer>
</body>
</html>
