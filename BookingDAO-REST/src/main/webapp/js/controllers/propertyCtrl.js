angular.module('app')
	.controller('propertyCtrl', ['$routeParams', '$location', 'propertyFactory', function($routeParams, $location, propertyFactory) {
		var propertyHandlerViewModel = this;
		propertyHandlerViewModel.property = undefined;
		propertyHandlerViewModel.type = ' ';
		propertyHandlerViewModel.allServices = [];
		propertyHandlerViewModel.propertyServices = [];
		propertyHandlerViewModel.functions = {
			where: function(route) {
				return $location.path() === route;
			},

			isServiceAssociated: function(service) {
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

				propertyFactory.updateProperty(propertyHandlerViewModel.property)
					.then(function(response) {
						console.log("Propiedad actualizada: ", response);
						propertyHandlerViewModel.property = response;
					})
					.catch(function(error) {
						console.log("Error al actualizar la propiedad: ", error);
					})
				console.log("Actualizando propiedad: ", propertyHandlerViewModel.property);
			},
			updateCheckService: function(service) {
				service.isChecked = !service.isChecked;
				console.log("Servicio actualizado: ", service);
			},
			updateServices: function() {
				propertyHandlerViewModel.allServices.forEach(function(service) {
					if (service.isChecked) {
						if (!propertyHandlerViewModel.functions.isServiceAssociated(service)) {
							/*propertyFactory.postPropertyServices(propertyHandlerViewModel.property, service)
								.then(function(response) {
									console.log("Servicio agregado: ", response);
								})
								.catch(function(error) {
									console.log("Error al agregar el servicio: ", error);
								})*/
							console.log("Agregando servicio: ", service);
						} else {
							console.log("El servicio ya esta asociado: ", service);
						}
					} else {
						if (propertyHandlerViewModel.functions.isServiceAssociated(service)) {
							/*	propertyFactory.removeServiceFromProperty(propertyHandlerViewModel.property.id, service.id)
									.then(function(response) {
										console.log("Servicio eliminado: ", response);
									})
									.catch(function(error) {
										console.log("Error al eliminar el servicio: ", error);
									})*/
							console.log("Eliminando servicio: ", service);
						} else {
							console.log("El servicio no esta asociado: ", service);
						}
					}
				});
			},
			insertProperty: function() { 
				propertyFactory.postProperty(propertyHandlerViewModel.property)
					.then(function(response) {
						console.log("Propiedad insertada: ", response);
					})
					.catch(function(error) {
						console.log("Error al insertar la propiedad: ", error);
					})
				console.log("Insertando propiedad: ", propertyHandlerViewModel.property);
			},
			deleteProperty: function()
			{
				propertyFactory.deleteProperty(propertyHandlerViewModel.property.id)
					.then(function(response) {
						console.log("Propiedad eliminada: ", response);
					})
					.catch(function(error) {
						console.log("Error al eliminar la propiedad: ", error);
					})
			},
			getAllServices: function() {
				//get all services from server
				propertyFactory.getAllServices()
					.then(function(response) {
						console.log("Obteniendo los servicios: ", response);
						propertyHandlerViewModel.allServices = response;
						angular.forEach(propertyHandlerViewModel.allServices, function(service) {
							service.isChecked = propertyHandlerViewModel.functions.isServiceAssociated(service);
						});
					})
					.catch(function(error) {
						console.log("Error al obtener los servicios: ", error);
					})
			},
			getAllServicesAssociates: function(id) {

				propertyFactory.getPropertyServices(id)
					.then(function(response) {

						propertyHandlerViewModel.propertyServices = response;
						angular.forEach(propertyHandlerViewModel.propertyServices, function(service) {
							service.isChecked = true;
						});
						console.log("Obteniendo los servicios asignados: ", response);
					})
					.catch(function(error) {
						console.log("Error al obtener los servicios no asignados: ", error);
					})
			},
			getSelectedServices: function() {
				var selectedServices = [];
				angular.forEach(propertyHandlerViewModel.allServices, function(service) {
					if (service.isChecked) {
						selectedServices.push(service);
					}
				});
				return selectedServices;
			},
			propertyHandlerSwitcher: function() {
				if (propertyHandlerViewModel.functions.where('/insertProperties')) {
					propertyHandlerViewModel.functions.insertProperty();
					propertyHandlerViewModel.functions.updateServices();
					console.log($location.path());
				} else if (propertyHandlerViewModel.functions.where('/editProperties/' + propertyHandlerViewModel.property.id)) {
					console.log($location.path());
					propertyHandlerViewModel.functions.updateProperty();
					propertyHandlerViewModel.functions.updateServices();

				} else if (propertyHandlerViewModel.functions.where('/deleteProperties/' + propertyHandlerViewModel.property.id)) {
					propertyHandlerViewModel.functions.deleteProperty();
					console.log($location.path());
				} else {
					console.log($location.path());
				}
				$location.path('/');

			},

		};
		if ($routeParams.propertyid == undefined) {
			console.log("There is no property id, creating new property");
			propertyHandlerViewModel.functions.getAllServices();
			propertyHandlerViewModel.type = 'Añadir';
			
		}
		else {
			if (propertyHandlerViewModel.functions.where('/editProperties/' + $routeParams.propertyid)) {
				propertyHandlerViewModel.type = 'Editar';
			}
			else if (propertyHandlerViewModel.functions.where('/deleteProperties/' + $routeParams.propertyid)) {
				propertyHandlerViewModel.type = 'Eliminar';
			}
			propertyHandlerViewModel.functions.getProperty($routeParams.propertyid);
			propertyHandlerViewModel.functions.getAllServicesAssociates($routeParams.propertyid);
			propertyHandlerViewModel.functions.getAllServices();
		}
	}]);
