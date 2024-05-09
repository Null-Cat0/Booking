angular.module('app')
	.controller('profileCtrl', ['$location', 'userFactory', function($location, userFactory) {
		var profileHandlerViewModel = this;
		profileHandlerViewModel.user = {};
		profileHandlerViewModel.errormsg= " "
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
						$location.path('/');
					})
			},
			deleteUser: function() {
				console.log("Deleting user with id: ", profileHandlerViewModel.user.id);
				userFactory.deleteUser()
					.then(function(response) {
						console.log("User deleted, response: ", response);
						window.location.pathname = '/BookingDAO-REST/LogOutServlet.do';

					});
			},
			insertUser: function() {
				console.log("Inserting user: ", profileHandlerViewModel.user);
				userFactory.postUser(profileHandlerViewModel.user)
					.then(function(response) {
						console.log("User inserted, response: ", response);
						profileHandlerViewModel.user.id = response.id;
						$location.path('/');
					})
					.catch(function(error) {
						console.log("Error inserting user: ", error);
						profileHandlerViewModel.errormsg = "Error inserando el usuario";
						profileHandlerViewModel.user = undefined;
					});
			}


		}

		if (profileHandlerViewModel.functions.where('/profile')) {
			profileHandlerViewModel.functions.readUser();
		}
	}]);