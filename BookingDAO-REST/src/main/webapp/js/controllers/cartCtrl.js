angular.module('app')
	.controller('cartCtrl', ['$location', 'accommodationFactory', 'propertyFactory', 'bookingFactory', 'bookingaccommodationFactory', function($location, accommodationFactory, propertyFactory, bookingFactory, bookingaccommodationFactory) {

		var cartViewModel = this;
		cartViewModel.cart = [];
		cartViewModel.rooms = [];
		cartViewModel.property = undefined;
		cartViewModel.totalPrice = 0;
		cartViewModel.mssg = " ";
		cartViewModel.functions = {
			where: function(scope) {
				return $location.path() === scope;
			},
			getItems: function() {
				cartViewModel.cart = JSON.parse(localStorage.getItem('selectedRooms'));
				console.log(cartViewModel.cart);
			},
			getRooms: function() {
				for (var clave in cartViewModel.cart) {
					accommodationFactory.getAccommodation(clave)
						.then(function(response) {
							response.qty = cartViewModel.cart[clave];
							cartViewModel.rooms.push(response);
							cartViewModel.totalPrice = cartViewModel.totalPrice + (response.qty * response.price);
							// Una vez que se han obtenido todas las habitaciones, obtener la propiedad
							if (cartViewModel.rooms.length === Object.keys(cartViewModel.cart).length) {
								cartViewModel.functions.getProperty();
							}
						});
				}
				console.log(cartViewModel.rooms);
			},
			getProperty: function() {
				propertyFactory.getProperty(cartViewModel.rooms[0].idp)
					.then(function(response) {
						cartViewModel.property = response;
						console.log(cartViewModel.property);
					});
			},
			removeRoom: function(r) {
				console.log("Quitando la habitación:", r);
				for (var room of cartViewModel.rooms) {
					if (room.id === r.id) {
						var index = cartViewModel.rooms.indexOf(room);
						if (index !== -1) {
							cartViewModel.rooms.splice(index, 1);
							break;
						}
					}
				}
				cartViewModel.totalPrice = cartViewModel.totalPrice - (r.qty * r.price);//Actualizar el precio total
				// Si no hay habitaciones en el carrito, eliminar la propiedad
				console.log(cartViewModel.rooms.length)
				if (cartViewModel.rooms.length == 0)
					cartViewModel.property = undefined;
					
				// Actualizar el carrito
				localStorage.setItem('selectedRooms', JSON.stringify(cartViewModel.rooms));
			},
			booking: function() {
				for (var room of cartViewModel.rooms) {
					if (room.qty > room.numAccommodations) {
						alert("No hay suficientes habitaciones disponibles para " + room.name);
						return;
					}

					var booking = {
						"id": 0,
						"idu": 0,
						"totalPrice": room.qty * room.price
					};

					bookingFactory.postBooking(booking)
						.then(function(response) {
							console.log("Respuesta de la reserva:", response);

							// Verifica si la respuesta contiene datos y si tiene una propiedad 'id'
							if (response && response.data && response.data.id) {
								var bookingId = response.data.id;

								var bookingAccommodation = {
									"idb": bookingId,
									"idacc": room.id,
									"numAccommodations": room.qty
								};

								if (room.qty > room.numAccommodations) {
									cartViewModel.mssg = "Has elegido mas habitaciones de las disponibles, se va a seleccionar el máximo para " + room.name;
								} else if (room.numAccommodations == 0) {
									cartViewModel.mssg = "No hay habitaciones disponibles para " + room.name;
								}

								bookingaccommodationFactory.postBookingAccommodation(bookingAccommodation)
									.then(function(response) {
										console.log(response);
									})
									.catch(function(error) {
										console.error("Error al reservar alojamiento:", error);
									});
							} else {
								console.error("La respuesta de la reserva no contiene un ID válido:", response);
							}
						})
						.catch(function(error) {
							console.error("Error al hacer la reserva:", error);
						});
				};
				$location.path('/');
			}


		};

		cartViewModel.functions.getItems();
		cartViewModel.functions.getRooms();
	}]);
