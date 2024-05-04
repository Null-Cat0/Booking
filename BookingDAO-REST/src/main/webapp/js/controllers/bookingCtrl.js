angular.module('app')
	.controller('bookingCtrl', ['bookingFactory', 'bookingaccommodationFactory', 'accommodationFactory', function(bookingFactory, bookingAccommodation, accommodationFactory) {
		var bookingViewModel = this;
		bookingViewModel.bookings = [];

		bookingViewModel.functions = {
			getBookingRecord: function() {
				bookingFactory.getUserBookings()
					.then(function(response) {
						console.log(response);
						var bookingPromises = [];

						// Procesar cada reserva
						response.forEach(function(booking) {
							var bookingRecord = {
								booking: booking,
								accommodations: []
							};

							// Obtener las habitaciones reservadas para esta reserva
							var accommodationPromise = bookingAccommodation.getUserBookingsAccommodation(booking.id)
								.then(function(response) {
									console.log(response);

									// Procesar cada habitación reservada
									response.forEach(function(accommodation) {
										accommodationFactory.getAccommodation(accommodation.idacc)
											.then(function(response) {
												console.log(response);

												// Agregar información de la habitación a la reserva
												var accommodationInfo = {
													accommodation: response,
													qty: accommodation.qty,
													price: booking.totalPrice
												};

												bookingRecord.accommodations.push(accommodationInfo);
											});
									});
								});

							bookingPromises.push(accommodationPromise);
							bookingViewModel.bookings.push(bookingRecord);
						});

						// Esperar a que todas las solicitudes de alojamiento se completen antes de continuar
						Promise.all(bookingPromises)
							.then(function() {
								console.log(bookingViewModel.bookings);
							})
							.catch(function(error) {
								console.error("Error al obtener alojamientos:", error);
							});
					})
					.catch(function(error) {
						console.error("Error al obtener reservas:", error);
					});
			}
		};


		bookingViewModel.functions.getBookingRecord();



	}
	]);