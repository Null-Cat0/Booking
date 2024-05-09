angular.module('app')
	.controller('searchCtrl', ['$location', '$routeParams', 'propertyFactory', 'accommodationFactory', 'favouriteFactory', '$scope', function($location, $routeParams, propertyFactory, accommodationFactory, favouriteFactory, $scope) {
		var searchVM = this;
		searchVM.properties = []; // Inicializa la lista de propiedades
		searchVM.searchText = ''; // Inicializa el texto de búsqueda  
		searchVM.favourites = [];
		searchVM.propertiesFavourites = [];
		searchVM.listoriginal = [];
		$scope.searchVM = {
			filterOption: 'all' // Valor predeterminado para el filtro
		};
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
						searchVM.listoriginal = response;
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
			},
			isObjectInFavourites: function(obj) {
				return searchVM.favourites.some(function(item) {
					return item.idp === obj.id;
				});
			},
			getFavorites: function() {
				console.log("Obteniendo favoritos del usuario");
				return favouriteFactory.getUserFavourites()
					.then(function(response) {
						searchVM.favourites = response;
						console.log("Favoritos del usuario:", response);
					})
					.catch(function(error) {
						console.log("Error al obtener los favoritos del usuario:", error);
					});
			},
			addFavourite: function(propertyId) {
				var favourite = { idu: 0, idp: propertyId };
				console.log("Añadiendo favorito:", propertyId);
				favouriteFactory.postFavorite(favourite)
					.then(function(response) {
						console.log("Favorito añadido:", response);
						searchVM.functions.getFavorites();
					})
					.catch(function(error) {
						console.log("Error al añadir el favorito:", error);
					});

			},
			removeFavourite: function(propertyId) {
				console.log("Eliminando favorito:", propertyId);
				favouriteFactory.deleteFavourite(propertyId)
					.then(function(response) {
						console.log("Favorito eliminado:", response);
						searchVM.functions.getFavorites();
					})
					.catch(function(error) {
						console.log("Error al eliminar el favorito:", error);
					});
				if (searchVM.functions.where('/favourites')) {
					window.location.reload();
				}
			},
			getPropertyFavourites: function() {
				console.log("Obteniendo favoritos");
				searchVM.favourites.forEach(function(favourite) {
					console.log("Favorito:", favourite);
					propertyFactory.getProperty(favourite.idp)
						.then(function(response) {
							searchVM.propertiesFavourites.push(response);
							console.log("Favoritos de la propiedad:", response);
						})
						.catch(function(error) {
							console.log("Error al obtener los favoritos de la propiedad:", error);
						});
				});
				console.log("Propiedades favoritas:", searchVM.propertiesFavourites);
			},
			orderarListaPorValoracionMedia: function() {
				searchVM.properties.sort(function(a, b) {
					return b.gradesAverage - a.gradesAverage;
				});
				console.log("Lista ordenada por valoración media:", searchVM.properties);
			},
			submitFilter: function() {
				// Accede al valor seleccionado del filtro
				var selectedOption = $scope.searchVM.filterOption;
				console.log("Opción seleccionada:", selectedOption);
				if (selectedOption == 'available') {
					//Only select the properties that have the status available
					searchVM.properties = searchVM.listoriginal.filter(function(property) {
						return property.available == 1;
					});
				} else if (selectedOption == 'notAvailable') {
					//Only select the properties that have the status not available
					searchVM.properties = searchVM.listoriginal.filter(function(property) {
						return property.available == 0;
					});
				} else if (selectedOption == 'all') {
					//Obtain all the properties
					searchVM.properties = searchVM.listoriginal;
				}

			}

		};

		searchVM.searchText = $routeParams.searchText;
		if (searchVM.functions.where('/search/' + searchVM.searchText)) {
			searchVM.functions.submitSearch();
			searchVM.functions.getFavorites();
		} else if (searchVM.functions.where('/properties')) {
			console.log("Entra a properties");
			searchVM.functions.getUserProperties();
		} else if (searchVM.functions.where('/favourites')) {
			console.log("Entra a favourites");
			searchVM.functions.getFavorites().then(function() {
				searchVM.functions.getPropertyFavourites();
			})
		}

	}]);