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
						return response.data;
					}
				);
			},
			insertAccommodation: function(accommodation,id) {
				var urlid = url + 'property/' + id;
				return $http.post(urlid, accommodation).then(
					function(response) {
						return response.data;
					}
				);
			},
			updateAccommodation: function(accommodation) {
				return $http.put(url + accommodation.id, accommodation).then(
					function(response) {
						return response.data;
					}
				);
			},
			deleteAccommodation: function(id) {
				var urlid = url + id;
				return $http.delete(urlid).then(
					function(response) {
						return response.data;
					}
				);
			},
			putAccommodation: function(accommodation) {
				var urlid = url + accommodation.id;
				return $http.put(urlid, accommodation).then(
					function(response) {
						return response.data;
					}
				);
			},
		}
		return propertyInterface;
	}
	])