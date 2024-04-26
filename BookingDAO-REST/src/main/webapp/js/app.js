angular.module('app', ['ngRoute'])
	.config(function($routeProvider) {
		$routeProvider
		.when('/', {
			templateUrl: 'searchTemplate.html',
			controller: 'searchCtrl',
			controllerAs: 'searchVM'
		})
		.when('/profile', {
			templateUrl: 'profileHandlerTemplate.html',
			controller: 'profileCtrl',
			controllerAs:'profileVM'
		})
		.when('/search', {
			templateUrl: 'resultsTemplate.html',
			controller: 'resultCtrl',
			controllerAs:'resultVM'
		})
	}
)