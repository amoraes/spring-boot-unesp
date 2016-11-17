'use strict';
var app = angular.module('exemplo');
/**
 * Factory para interação com camada REST (Backend)
 */
app.factory('InscricaoResource', ['$resource', function($resource){
	return $resource(
		'services/eventos/:idEvento/inscricoes/:idInscricao/:operation', {
				idEvento: '@idEvento',
				idInscricao: '@idInscricao'
			}, {
				list: {
					method: 'GET',
					isArray: true
				},
				count: {
					method: 'GET',
					params: { operation: 'count' }
				}
			}
	);
}]);