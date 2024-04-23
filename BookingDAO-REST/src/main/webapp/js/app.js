angular.module('app', ['ngRoute'])
	.config(function($routeProvider) {
		$routeProvider
		.when('/', {
			templateUrl: 'search.html',
			controller: 'searchCtrl',
			controllerAs: 'searchVM'
		})
		.when('/search', {
			templateUrl: 'results.html',
			controller: 'resultCtrl',
			controllerAs:'resultVM'
		})
	}
)