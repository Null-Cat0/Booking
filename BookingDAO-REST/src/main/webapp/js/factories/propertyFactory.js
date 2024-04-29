angular.module('app')
	.factory('propertyFactory', ['$http', function($http) { 
		var url = 'https://localhost:8443/BookingDAO-REST/rest/properties/';
		var services ='https://localhost:8443/BookingDAO-REST/rest/services/';
		var propertyInterface = {
			getProperty: function(id) {
				var urlid = url + id;
				return $http.get(urlid).then(
					function(response) {
						return response.data;
					}
				);
			},
			getPropertySearch:function(search){
				var urlsearch = url + search;
				return $http.get(urlsearch).then(
					function(response) {
						return response.data;
					}
				);
			},
			getPropertiesByUser: function() {
			
				return $http.get(url).then(
					function(response) {
						return response.data;
					}
				);
			},
			postProperty: function(property){
				return $http.post(url,property).then(
					function(response) {
						return response.status;
					}
				);
			},
			putProperty: function(property) {
				return $http.put(url,property).then(
					function(response) {
						return response.status;
					}
				);
			},
			deleteProperty: function(id) {
				var urlid = url + id;
				return $http.delete(urlid).then(
					function(response) {
						return response.status;
					}
				);
			},
			getAllServices: function() {
				return $http.get(services).then(
					function(response) {
						return response.data;
					}
				);
			},
			getPropertyServices: function(id) {
				var urlid = services + id;
				return $http.get(urlid).then(
					function(response) {
						return response.data;
					}
				);
			},
		}
		return propertyInterface;
	}]);