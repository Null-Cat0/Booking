angular.module('app')
	.factory('bookingaccommodationFactory', ['$http', function($http) {
		var url = 'https://localhost:8443/BookingDAO-REST/rest/bookingsaccommodations/';

		var bookingInterface = {

			getUserBookingsAccommodation: function() {
				return $http.get(url).
					then(function(response) {
						return response.data;
					});
			},
			postBookingAccommodation: function(bookingaccommodation) {

				return $http.post(url, bookingaccommodation)
					.then(function(response) {
						return response;
					});
			},
		};
		return bookingInterface;
	}]);