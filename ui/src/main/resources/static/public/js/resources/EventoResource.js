'use strict';
var app = angular.module('exemplo-public');
/**
 * Factory para interação com camada REST (Backend)
 */
app.factory('EventoResource', ['$resource', function($resource){
	return $resource(
		'services/eventos/:idEvento', {
				idEvento: '@idEvento'
			}, {
				list: {
					method: 'GET',
					isArray: true
				}	
			}
	);
}]);