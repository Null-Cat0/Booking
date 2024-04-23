angular.module('app')
	.factory('userFactory', ['$http', function($http) {
		var url = 'https://localhost:8443/BookingDAO-REST/rest/users/';
		var userInterface = {
			getUsers: function() {
				url = url;
				return $http.get(url).then(
					function(response) {
						return response.data;
					}
				);
			},
			getUser: function(id) {
				url = url + id;
				return $http.get(url).then(
					function(response) {
						return response.data;
					})
			}
		}
		return userInterface;
	}])