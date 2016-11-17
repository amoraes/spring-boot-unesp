'use strict';
var app = angular.module('exemplo-public');
/**
 * Factory para interação com camada REST (Backend)
 */
app.factory('PessoaResource', ['$resource', function($resource){
	return $resource(
		'services/pessoas', {
			}, {
				filter: {
					method: 'GET',
					isArray: true,
					params: {
						cpf: '@cpf'
					}
				}	
			}
	);
}]);