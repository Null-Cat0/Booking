angular.module('app')
	.factory('reviewFactory', ['$http', function($http) {
		var url = 'https://localhost:8443/BookingDAO-REST/rest/reviews/';
		var reviewInterface = {
			getReviews: function(id) {
				var urlid= url +'property/'+ id;
				return $http.get(urlid)
					.then(function(response) {
						return response.data;
					});
			},
			getUserName: function(id) {
				var urlid = url + id ;
				return $http.get(urlid)
					.then(function(response) {
						return response.data;
					});
			},
			addReview: function(review) {
				return $http.post(url, review)
					.then(function(response) {
						return response.data;
					});
			},
			deleteReview: function(id) {
				var urlid = url + id;
				return $http.delete(urlid)
					.then(function(response) {
						return response.data;
					});
			},
		};
		return reviewInterface;
	}]);