angular.module('app')
	.factory('favouriteFactory', ['$http', function($http) {
		var url = 'https://localhost:8443/BookingDAO-REST/rest/favourites';

		var favouriteInterface = {
			
			getUserFavourites: function() {
				return $http.get(url)
					.then(function(response) {
						return response.data;
					});
			},
			postFavorite: function(favourite) {
				return $http.post(url, favourite)
					.then(function(response) {
						return response.status;
					});
			},
			deleteFavourite: function(favourite) {
				var deleteUrl = url + '/' + favourite;
				return $http.delete(deleteUrl)
					.then(function(response) {
						return response.status;
					});
			}
		};
		return favouriteInterface;
	}]);