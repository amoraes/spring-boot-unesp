'use strict';
var app = angular.module('exemplo');
/**
 * Factory para interação com camada REST (Backend)
 */
app.factory('EventoResource', ['$resource', '$log', function($resource, $log){
	return $resource(
		'services/eventos/:idEvento', {
				idEvento: '@idEvento'
			}, {
				update: {
					method: 'PUT'
				},
				list: {
					method: 'GET',
					isArray: true
				}	
				//métodos get(id), save e delete não necessitam de configuração adicional
				//mas estão disponíveis
			}
	);
}]);