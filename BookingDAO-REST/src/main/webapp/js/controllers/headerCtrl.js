angular.module('app')
	.controller('headerCtrl', ['userFactory', function(userFactory) {
		var headerViewModel = this;
		headerViewModel.user = {};
		headerViewModel.functions = {
			readUser: function() {
				userFactory.getUser(1)
					.then(function(response) {
						headerViewModel.user = response;
						console.log("Getting user with id: ", headerViewModel.user.id, " Response: ", response);
					}, function(response) {
						console.log("Error reading user");
					})
			}
		}
		headerViewModel.functions.readUser();
}]);