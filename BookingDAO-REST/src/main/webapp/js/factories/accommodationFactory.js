angular.module('app')
	.factory('accommodationFactory', ['$http', function($http) {
		var url = 'https://localhost:8443/BookingDAO-REST/rest/accommodations/';

		var propertyInterface = {
			getPropertyAccommodations: function(id) {
				var urlid = url + 'property/' + id;
				return $http.get(urlid).then(
					function(response) {
						return response.data;
					}
				);
			},
			getAccommodation: function(id) {
				var urlid = url + id;
				return $http.get(urlid).then(
					function(response) {
						console.log(response.data);
						return response.data;
					}
				);
			},
		}
		return propertyInterface;
	}
	])