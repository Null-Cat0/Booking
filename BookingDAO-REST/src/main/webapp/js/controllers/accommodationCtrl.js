angular.module('app')
	.controller('accommodationCtrl', ['$routeParams', '$location', 'propertyFactory', 'accommodationFactory', 'userFactory', function($routeParams, $location, propertyFactory, accommodationFactory, userFactory) {

		var accommodationViewModel = this;
		accommodationViewModel.type = '';
		accommodationViewModel.propertyid = undefined;
		accommodationViewModel.accommodation = undefined;
		accommodationViewModel.user = undefined;
		accommodationViewModel.functions = {
			where: function(scope) {
				return $location.path() == scope;
			},
			getUser: function() {
				userFactory.getUser().then(function(response) {
					accommodationViewModel.user = response;
				});
			},
			getAccommodation: function(id) {
				// Ensure getAccommodation returns a promise
				return accommodationFactory.getAccommodation(id)
					.then(function(response) {
						accommodationViewModel.accommodation = response;
					});
			},

			updateAccommodation: function() {
				console.log('Update Accommodation:', accommodationViewModel.accommodation);
				accommodationFactory.updateAccommodation(accommodationViewModel.accommodation)
					.then(function(response) {
						accommodationViewModel.accommodation = response;
					})
					.catch(function(error) {
						console.log('Error updating accommodation:', error);
					});

			},
			deleteAccommodation: function() {
				console.log('Delete Accommodation with id:', accommodationViewModel.accommodation.id);
				accommodationFactory.deleteAccommodation(accommodationViewModel.accommodation.id)
					.then(function(response) {
						accommodationViewModel.accommodation = response;
					})
					.catch(function(error) {
						console.log('Error deleting accommodation:', error);
					});

			},
			insertAccommodation: function() {
				console.log('Inserting Accommodation: ', accommodationViewModel.accommodation);
				accommodationFactory.insertAccommodation(accommodationViewModel.accommodation, accommodationViewModel.propertyid)
					.then(function(response) {
						accommodationViewModel.accommodation = response;
					})
					.catch(function(error) {
						console.log('Error inserting accommodation:', error);
					});
				$location.path('/properties');
			},
			accommodationHandlerSwitcher: function() {

				if (accommodationViewModel.functions.where('/insertAccommodation/' + accommodationViewModel.propertyid)) {
					accommodationViewModel.functions.insertAccommodation();
					console.log($location.path());
				} else if (accommodationViewModel.functions.where('/editAccommodation/' + accommodationViewModel.accommodation.id)) {
					accommodationViewModel.functions.updateAccommodation();
					console.log($location.path());
				} else if (accommodationViewModel.functions.where('/deleteAccommodation/' + accommodationViewModel.accommodation.id)) {
					accommodationViewModel.functions.deleteAccommodation();
					console.log($location.path());
				} else {
					console.log($location.path());
				}
				$location.path('/properties');

			},
		};


		if ($routeParams.accommodationid == undefined) {
			accommodationViewModel.type = 'Insertar';
			accommodationViewModel.propertyid = $routeParams.propertyid;
			console.log("There is no property id, creating new property");
		} else {
			// Call getUser function and handle its promise
			accommodationViewModel.functions.getUser();

			// Call getAccommodation function and handle its promise
			accommodationViewModel.functions.getAccommodation($routeParams.accommodationid)
				.then(function() {
					propertyFactory.getProperty(accommodationViewModel.accommodation.idp).then(function(response) {
						var property = response;
						if (property.idu != accommodationViewModel.user.id) {
							$location.path('/properties');
						}
					});
					if (accommodationViewModel.functions.where('/editAccommodation/' + accommodationViewModel.accommodation.id)) {
						accommodationViewModel.type = 'Editar';
						console.log(accommodationViewModel.accommodation);
					} else if (accommodationViewModel.functions.where('/deleteAccommodation/' + accommodationViewModel.accommodation.id)) {
						accommodationViewModel.type = 'Borrar';
					}
				});
		}
	}]);
