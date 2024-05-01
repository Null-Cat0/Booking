angular.module('app')
	.controller('searchCtrl', ['$location', '$routeParams', 'propertyFactory', 'accommodationFactory', function($location, $routeParams, propertyFactory,accommodationFactory) {
		var searchVM = this;
		searchVM.properties = []; // Inicializa la lista de propiedades
		searchVM.searchText = ''; // Inicializa el texto de búsqueda  

		searchVM.functions = {
			where: function(route) {
				return $location.path() == route;
			},

			submitSearch: function() {
				var searchText = searchVM.searchText;

				console.log("Texto de búsqueda:", searchText); // Obtener todos las propiedades que coincidan con la búsqueda
				propertyFactory.getPropertySearch(searchText)
					.then(function(response) {
						searchVM.properties = response;
						console.log("Propiedades encontradas:", response);
					})
					.catch(function(error) {
						console.log("Error al obtener las propiedades:", error);
					});
				$location.path('/search/' + searchText);

			},
			getUserProperties: function() {
				console.log("Obteniendo propiedades del usuario");
				propertyFactory.getPropertiesByUser()
					.then(function(response) {
						searchVM.properties = response;
						var promises = [];
						searchVM.properties.forEach(function(property) {
							var promise = accommodationFactory.getPropertyAccommodations(property.id)
								.then(function(response) {
									property.accommodations = response;
									console.log("Habitaciones del alojamiento:", response);
								})
								.catch(function(error) {
									console.log("Error al obtener las habitaciones del alojamiento:", error);
								});
							promises.push(promise);
						});
						return Promise.all(promises);
					})
					.then(function() {
						console.log("Propiedades del usuario:", searchVM.properties);
					})
					.catch(function(error) {
						console.log("Error al obtener las propiedades del usuario:", error);
					});
			}

		};
		searchVM.searchText = $routeParams.searchText;
		if (searchVM.functions.where('/search/' + searchVM.searchText)) {
			searchVM.functions.submitSearch();
		} else if (searchVM.functions.where('/properties')) {
			console.log("Entra a properties");
			searchVM.functions.getUserProperties();
		}

	}]);