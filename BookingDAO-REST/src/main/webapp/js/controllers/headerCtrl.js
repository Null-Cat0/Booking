
angular.module('app')
	.controller('headerCtrl', ['userFactory', '$location', '$window', function(userFactory, $location, $window) {
		var headerViewModel = this;

		headerViewModel.user = undefined;
		headerViewModel.functions = {
			where: function(scope) {
				return $location.path() == scope;
			},
			readUser: function() {
				userFactory.getUser()
					.then(function(response) {
						headerViewModel.user = response;
						console.log("Getting user with id: ", headerViewModel.user.id, " Response: ", response);
						if (headerViewModel.user == undefined) {
							$location.path('/insertUser');
						}
					}).catch(function(error) {
						console.log("Error reading user on headerCtrl, Response: ", error);
					});
			}
		}
		if (!headerViewModel.functions.where('/insertUser'))
			headerViewModel.functions.readUser();
	}]);