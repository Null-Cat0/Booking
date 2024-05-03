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
			.when("/infoProperties/:propertyid", {
				templateUrl: "propertyInfoTemplate.html",
				controller: "resultCtrl",
				controllerAs: "resultVM",
			})
			.when("/properties", {
				templateUrl: "listPropertiesTemplate.html",
				controller: "searchCtrl",
				controllerAs: "searchVM",
			})
			.when("/editProperties/:propertyid", {
				templateUrl: "propertyHandlerTemplate.html",
				controller: "propertyCtrl",
				controllerAs: "propertyVM",
			})
			.when("/deleteProperties/:propertyid", {
				templateUrl: "propertyHandlerTemplate.html",
				controller: "propertyCtrl",
				controllerAs: "propertyVM",
			})
			.when("/insertProperties", {
				templateUrl: "propertyHandlerTemplate.html",
				controller: "propertyCtrl",
				controllerAs: "propertyVM",
			})
			.when("/insertAccommodation/:propertyid", {
				templateUrl: "accommodationHandlerTemplate.html",
				controller: "accommodationCtrl",
				controllerAs: "accommodationVM",
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