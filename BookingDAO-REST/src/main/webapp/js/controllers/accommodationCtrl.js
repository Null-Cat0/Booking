angular.module('app')
	.controller('accommodationCtrl', ['$routeParams', '$location', 'propertyFactory', 'accommodationFactory', function($routeParams, $location, propertyFactory, accommodationFactory) {

		var accommodationViewModel = this;
		accommodationViewModel.type = '';
		accommodationViewModel.accommodation = undefined;
		accommodationViewModel.functions = {
			where: function(scope) {
				return $location.path() == scope;
			},
			getAccommodation: function(id) {
				// Ensure getAccommodation returns a promise
				return accommodationFactory.getAccommodation(id)
					.then(function(response) {
						accommodationViewModel.accommodation = response;
					});
			},
			accommodationHandler: function() {
				if (accommodationViewModel.functions.where('editAccommodation' + accommodationViewModel.accommodation.id)) {
					console.log('editAccommodation');
				}
			},
			updateAccommodation: function() {
				console.log('Update Accommodation');
			},
			deleteAccommodation: function() {
				console.log('Delete Accommodation');
			},
			insertAccommodation: function() {
				console.log('Inserting Accommodation');
			},
			accommodationHandlerSwitcher: function() {

				if (accommodationViewModel.functions.where('/insertAccommodation')) {
					console.log($location.path());
				} else if (accommodationViewModel.functions.where('/editAccommodation/' + propertyHandlerViewModel.property.id)) {
					console.log($location.path());
				} else if (accommodationViewModel.functions.where('/deleteAccommodation/' + propertyHandlerViewModel.property.id)) {
					console.log($location.path());
				} else {
					console.log($location.path());
				}
				$location.path('/');

			},
		};


		if ($routeParams.accommodationid == undefined) {
			accommodationViewModel.type = 'Insertar';
			console.log("There is no property id, creating new property");
		} else {
			// Call getAccommodation function and handle its promise
			accommodationViewModel.functions.getAccommodation($routeParams.accommodationid)
				.then(function() {
					if (accommodationViewModel.functions.where('/editAccommodation/' + accommodationViewModel.accommodation.id)) {
						console.log('Editar Habitacion');
						accommodationViewModel.type = 'Editar';
						console.log(accommodationViewModel.accommodation);
					} else if (accommodationViewModel.functions.where('/deleteAccommodation/' + accommodationViewModel.accommodation.id)) {
						console.log('Borrar Habitacion');
						accommodationViewModel.type = 'Borrar';
					}
				});
		}
	}]);
