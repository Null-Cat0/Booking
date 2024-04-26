angular.module('app')
	.factory('userFactory', ['$http', function($http) {
		var url = 'https://localhost:8443/BookingDAO-REST/rest/users/';
		var userInterface = {
			getUser: function() {
				return $http.get(url).then(
					function(response) {
						return response.data;
					}
				);
			},
			putUser: function(user) {
				return $http.put(url,user).then(
					function(response) {
						return response.data;
					}
				);
			},
			deleteUser: function() {
				return $http.delete(url).then(
					function(response) {
						return response.status;
					}
				);
			}
		}
		return userInterface;
	}])