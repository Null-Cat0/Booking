angular.module('app')
	.controller('propertyCtrl', ['$routeParams', '$location', 'propertyFactory', function( $routeParams, $location, propertyFactory) {
		var propertyHandlerViewModel = this;
		propertyHandlerViewModel.property = undefined;
		propertyHandlerViewModel.allServices = [];
		propertyHandlerViewModel.propertyServices = [];
		propertyHandlerViewModel.functions = {
			where: function(route) {
				console.log("Ruta: ", $location.path());
				console.log("Ruta a comparar: ", route);
				return $location.path() === route;

			},

			isServiceAssociated: function isServiceInArray(service) {
				return propertyHandlerViewModel.propertyServices.some(function(item) {
					return item.id === service.id && item.name === service.name;
				});
			},
			getProperty: function(id) {
				//get property from server
				propertyFactory.getProperty(id)
					.then(function(response) {
						console.log("Obteniendo la propiedad: ", response);
						propertyHandlerViewModel.property = response;
					})
					.catch(function(error) {
						console.log("Error al obtener la propiedad: ", error);
					})
			},
			updateProperty: function() {
				/*update property in server
				propertyFactory.updateProperty(propertyHandlerViewModel.property)
					.then(function(response) {
						console.log("Propiedad actualizada: ", response);
						propertyHandlerViewModel.property = response;
					})
					.catch(function(error) {
						console.log("Error al actualizar la propiedad: ", error);
					})*/
				console.log("Actualizando propiedad: ", propertyHandlerViewModel.property);
			},
			getAllServices: function() {
				//get all services from server
				propertyFactory.getAllServices()
					.then(function(response) {
						console.log("Obteniendo los servicios: ", response);
						propertyHandlerViewModel.allServices = response;
					})
					.catch(function(error) {
						console.log("Error al obtener los servicios: ", error);
					})
			},
			getAllServicesAssociates: function(id) {

				propertyFactory.getPropertyServices(id)
					.then(function(response) {
						console.log("Obteniendo los servicios asignados: ", response);
						propertyHandlerViewModel.propertyServices = response;
					})
					.catch(function(error) {
						console.log("Error al obtener los servicios no asignados: ", error);
					})
			},
			propertyHandlerSwitcher: function() {
				if (propertyHandlerViewModel.functions.where('/insertProperty')) {
					console.log($location.path());
				} else if (propertyHandlerViewModel.functions.where('/editProperty/'+propertyHandlerViewModel.property.id)) {
					console.log($location.path());
					propertyHandlerViewModel.functions.updateProperty();
				} else if (propertyHandlerViewModel.functions.where('/deleteProperty/' + propertyHandlerViewModel.property.id)) {
					console.log($location.path());
				} else {
					console.log($location.path());
				}
				$location.path('/');

			},
		};
		if ($routeParams.propertyid == undefined) console.log("No hay id");
		else {

			propertyHandlerViewModel.functions.getProperty($routeParams.propertyid);
			propertyHandlerViewModel.functions.getAllServices();
			propertyHandlerViewModel.functions.getAllServicesAssociates($routeParams.propertyid);

		}
	}]);
