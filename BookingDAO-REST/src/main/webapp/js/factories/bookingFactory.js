angular.module('app')
	.factory('bookingFactory', ['$http', function($http) {
		var url = 'https://localhost:8443/BookingDAO-REST/rest/bookings/';

		var bookingInterface = {
			
			getUserBookings: function() {
				return $http.get(url)
					.then(function(response) {
						return response.data;
					});
			},
			postBooking: function(booking) {

				return $http.post(url, booking)
					.then(function(response) {
						return response;
					});
			},
		};
		return bookingInterface;
	}]);