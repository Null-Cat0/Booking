angular.module('app')
	.controller('bookingCtrl', ['propertyFactory', 'bookingaccommodationFactory', 'accommodationFactory', function(propertyFactory, bookingaccommodationFactory, accommodationFactory) {
		var bookingViewModel = this;
		bookingViewModel.bookingsRecord = [];

		bookingViewModel.functions = {
			getBookingRecord: function() {
				bookingaccommodationFactory.getUserBookingsAccommodation().then(function(response) {
					console.log(response);
					response.forEach(function(bookingAccommodation) {
						accommodationFactory.getAccommodation(bookingAccommodation.idacc).then(function(response) {
							console.log(response);
							var record = response;
							//Busqueda de propiedad
							propertyFactory.getProperty(record.idp).then(function(response) {
								record.propertyName = response.name;
							});
							record.numrooms = bookingAccommodation.numAccommodations;
							bookingViewModel.bookingsRecord.push(record);

						});
					});
				});
			},
		}
		bookingViewModel.functions.getBookingRecord();
	}]);
