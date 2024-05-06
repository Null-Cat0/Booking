angular.module('app')
	.controller('resultCtrl', ['$routeParams', '$scope', '$location', 'propertyFactory', 'accommodationFactory', 'reviewFactory', 'userFactory', function($routeParams, $scope, $location, propertyFactory, accommodationFactory, reviewFactory, userFactory) {
		var resutlViewModel = this;

		resutlViewModel.property = {};
		resutlViewModel.services = {};
		resutlViewModel.accommodations = {};
		resutlViewModel.reviews = undefined;
		resutlViewModel.user = undefined;
		resutlViewModel.reviewDone = false;
		resutlViewModel.review = undefined;
		resutlViewModel.selectedRooms = {};

		resutlViewModel.functions = {
			where: function(scope) {
				return $location.path() == scope;
			},
			getProperty: function(id) {
				propertyFactory.getProperty(id).
					then(function(response) {
						resutlViewModel.property = response;
						console.log("Propiedad obtenida:", response);
					})
					.catch(function(error) {
						console.log("Error obteniendo la propiedad:", error);
					});
			},
			getAllServicesAssociates: function(id) {

				propertyFactory.getPropertyServices(id)
					.then(function(response) {

						resutlViewModel.services = response;
						console.log("Obteniendo los servicios asignados: ", response);
					})
					.catch(function(error) {
						console.log("Error al obtener los servicios no asignados: ", error);
					})
			},
			getAccommodations: function(id) {
				accommodationFactory.getPropertyAccommodations(id)
					.then(function(response) {
						resutlViewModel.accommodations = response;
						console.log("Obteniendo los alojamientos: ", response);
					})
					.catch(function(error) {
						console.log("Error al obtener los alojamientos: ", error);
					})
			},
			getReviews: function(id) {

				reviewFactory.getReviews(id)
					.then(function(response) {
						resutlViewModel.reviews = response;
						resutlViewModel.reviews.forEach(function(review) {
							if (review.idu == resutlViewModel.user.id) {
								resutlViewModel.reviewDone = true;
								resutlViewModel.review = review;
							}
							reviewFactory.getUserName(review.id)
								.then(function(response) {
									review.user = response;

								})
								.catch(function(error) {
									console.log("Error al obtener el usuario: ", error);
								})

						});
						console.log("Obteniendo los comentarios: ", resutlViewModel.reviews);
					})
					.catch(function(error) {
						console.log("Error al obtener los comentarios: ", error);
					})


			},
			getUser: function() {
				userFactory.getUser()
					.then(function(response) {
						resutlViewModel.user = response;
						console.log("Obteniendo el usuario: ", response);
					})
					.catch(function(error) {
						console.log("Error al obtener el usuario: ", error);
					})
			},
			addReview: function() {
				console.log("Agregando comentario");
				var review = {
					idp: $routeParams.propertyid,
					review: resutlViewModel.review.comment,
					grade: resutlViewModel.review.grade
				}
				reviewFactory.addReview(review)
					.then(function(response) {
						console.log("Comentario agregado: ", response);
						resutlViewModel.reviewDone = true;
						resutlViewModel.comment = "";
						resutlViewModel.rating = 0;
						resutlViewModel.functions.getReviews($routeParams.propertyid);
					})
					.catch(function(error) {
						console.log("Error al agregar el comentario: ", error);
					})
			},
			deleteReview: function() {
				console.log("Eliminando comentario", resutlViewModel.review.id);
				reviewFactory.deleteReview(resutlViewModel.review.id)
					.then(function(response) {
						console.log("Comentario eliminado: ", response);
						resutlViewModel.reviewDone = false;
						resutlViewModel.review = undefined;
						resutlViewModel.functions.getReviews($routeParams.propertyid);
					})
					.catch(function(error) {
						console.log("Error al eliminar el comentario: ", error);
					})
			},
			bookingHandler: function() {
				console.log("Reservas", resutlViewModel.selectedRooms);
				localStorage.setItem("selectedRooms", JSON.stringify(resutlViewModel.selectedRooms));
				$location.path('/cart/');
			},



		};

		if ($routeParams.propertyid == undefined)
			$location.path('/');
		else {
			resutlViewModel.functions.getUser();
			resutlViewModel.functions.getProperty($routeParams.propertyid);
			resutlViewModel.functions.getAllServicesAssociates($routeParams.propertyid);
			resutlViewModel.functions.getAccommodations($routeParams.propertyid);
			resutlViewModel.functions.getReviews($routeParams.propertyid);


		}
	}
	]);