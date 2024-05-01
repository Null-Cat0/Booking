angular.module('app', ['ngRoute'])
	.config(function($routeProvider) {
		$routeProvider
			.when("/", {
				templateUrl: "searchTemplate.html",
				controller: "searchCtrl",
				controllerAs: "searchVM"
			})
			.when("/profile", {
				templateUrl: "profileHandlerTemplate.html",
				controller: "profileCtrl",
				controllerAs: "profileVM"
			})
			.when("/insertUser", {
				templateUrl: "profileHandlerTemplate.html",
				controller: "profileCtrl",
				controllerAs: "profileVM"
			})
			.when("/search/:searchText", {
				templateUrl: "resultsTemplate.html",
				controller: "searchCtrl",
				controllerAs: "searchVM",
			})
			.when("/properties", {
				templateUrl: "listPropertiesTemplate.html",
				controller: "searchCtrl",
				controllerAs: "searchVM",
			})
			.when("/editProperty/:propertyid", {
				templateUrl: "propertyHandlerTemplate.html",
				controller: "propertyCtrl",
				controllerAs: "propertyVM",
			})
			.when("/deleteProperty/:propertyid", {
				templateUrl: "propertyHandlerTemplate.html",
				controller: "propertyCtrl",
				controllerAs: "propertyVM",
			})
			.when("/insertProperty", {
				templateUrl: "propertyHandlerTemplate.html",
				controller: "propertyCtrl",
				controllerAs: "propertyVM",
			})
			.when("/editAccommodation/:accommodationid", {
				templateUrl: "accommodationHandlerTemplate.html",
				controller: "accommodationCtrl",
				controllerAs: "accommodationVM",
			})
			.when("/deleteAccommodation/:accommodationid", {
				templateUrl: "accommodationHandlerTemplate.html",
				controller: "accommodationCtrl",
				controllerAs: "accommodationVM",
			})
	}
	)