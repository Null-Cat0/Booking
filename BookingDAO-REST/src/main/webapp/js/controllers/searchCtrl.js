angular.module('app')
	.controller('searchCtrl', [ '$location',function($location) {
	    var vm = this;
	    vm.searchText = ''; // Inicializa el texto de búsqueda
	    
	    vm.submitSearch = function() {
	        var searchText = vm.searchText;
	        console.log("Texto de búsqueda:", searchText);
			//Obtener todos las propiedades que coincidan con la busqueda
			
			$location.path('/search').search({ search: searchText });
	    };
	}
]);
