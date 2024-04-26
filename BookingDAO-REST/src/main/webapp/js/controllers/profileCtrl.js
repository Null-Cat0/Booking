angular.module('app')
	.controller('profileCtrl', ['$location', 'userFactory', function($location, userFactory) {
		var profileHandlerViewModel = this;
		profileHandlerViewModel.user = {};
		profileHandlerViewModel.functions = {
			where: function(route) {
				return $location.path() == route;
			},
			readUser: function() {
				userFactory.getUser()
					.then(function(response) {
						profileHandlerViewModel.user = response;
						console.log("Getting user on profileCtrl with id: ", profileHandlerViewModel.user.id, " Response: ", response);
					},
						function(response) {
							console.log("Error reading user on profileCtrl, Response: ", response);
						})
			},
			updateUser: function() {
				console.log("User to update: ", profileHandlerViewModel.user);
				userFactory.putUser(profileHandlerViewModel.user)
					.then(function(response) {
						console.log("Updated user on profileCtrl, Response:", response);
					})
			},
			deleteUser: function() {
				console.log("Deleting user with id: ", profileHandlerViewModel.user.id);
				userFactory.deleteUser()
					.then(function(response) { 
						console.log("User deleted, response: ",response);
					});
			}

		}
		profileHandlerViewModel.functions.readUser();
	}]);