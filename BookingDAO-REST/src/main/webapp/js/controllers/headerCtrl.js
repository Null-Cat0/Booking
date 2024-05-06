
angular.module('app')
	.controller('headerCtrl', ['userFactory','$location','$window', function(userFactory, $location, $window) {
		var headerViewModel = this;

		headerViewModel.user = null;
		headerViewModel.functions = {
			where: function(scope) {
				return $location.path() == scope;
			},
			readUser: function() {
				userFactory.getUser()
					.then(function(response) {
						headerViewModel.user = response;
						console.log("Getting user with id: ", headerViewModel.user.id, " Response: ", response);
					}, function(response) {
						console.log("Error reading user on headerCtrl, Response: ", response);
					})
			}
		}
		headerViewModel.functions.readUser();
	}]);