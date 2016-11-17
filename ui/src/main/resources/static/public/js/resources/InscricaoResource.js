'use strict';
var app = angular.module('exemplo-public');
/**
 * Factory para interação com camada REST (Backend)
 */
app.factory('InscricaoResource', ['$resource', function($resource){
	return $resource(
		'services/eventos/:idEvento/inscricoes/:idInscricao', {
				idEvento: '@idEvento',
				idInscricao: '@idInscricao'
			}, {
				list: {
					method: 'GET',
					isArray: true
				}	
			}
	);
}]);