angular.module('app', ['ngRoute'])
	.config(function($routeProvider) {
		$routeProvider
			.when("/", {
				templateUrl: "searchTemplate.html",
				controller: "searchCtrl",
				controllerAs: "searchVM",
				resolve: {
					// produce 500 miliseconds (0,5 seconds) of delay that should be enough to allow the server
					//does any requested update before reading the orders.
					// Extracted from script.js used as example on https://docs.angularjs.org/api/ngRoute/service/$route
					delay: function($q, $timeout) {
						var delay = $q.defer();
						$timeout(delay.resolve, 500);
						return delay.promise;
					}
				}
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
			.when("/cart/", {
				templateUrl: "cart.html",
				controller: "cartCtrl",
				controllerAs: "cartVM",
			})
			.when("/bookingsRecord", {
				templateUrl: "bookingRecordTemplate.html",
				controller: "bookingCtrl",
				controllerAs: "bookingVM",
			})
			.when("/favourites", {
				templateUrl: "listFavouritesTemplate.html",
				controller: "searchCtrl",
				controllerAs: "searchVM",
			})
	}
	)