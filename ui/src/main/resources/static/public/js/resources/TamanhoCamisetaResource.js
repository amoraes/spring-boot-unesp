'use strict';
var app = angular.module('exemplo-public');
/**
 * Factory para interação com camada REST (Backend)
 */
app.factory('TamanhoCamisetaResource', ['$resource', function($resource){
	return $resource(
		'services/camisetas/tamanhos', 
			{
			}, {
				list: {
					method: 'GET',
					isArray: true
				}	
			}
	);
}]);