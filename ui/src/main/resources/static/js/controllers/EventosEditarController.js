'use strict';
var app = angular.module('exemplo');
app.controller('EventosEditarController', 
	['$scope', '$routeParams', '$log', '$location', 'EventoResource', 'Utils', 'CONFIG',	
	function ($scope, $routeParams, $log, $location, EventoResource, Utils, CONFIG) {
		$log.debug('Inicializando EventosEditarController');
		
		var self = this;
		self.evento = {};
	
		self.ready = false;
		
		self.init = function(){
			Utils.wait(true);
			//pegar os dados baseados no parâmetro
			var params = {};
			params.idEvento = $routeParams.idEvento;
			EventoResource.get(
				params,
				function(data){
					self.evento = Utils.datesToDate(data);
					self.ready = true;
					Utils.wait(false);
				},
				function(error){
					Utils.exception(error);
				}
			);
		}
		
		self.voltar = function(){
			$location.path('/eventos/listar');
		}
		
		self.salvar = function(){
			Utils.wait(true);
			var params = Utils.datesToString(self.evento);	
			$log.debug(params);
			EventoResource.update(params, 
				function(data){
					Utils.success('Evento salvo!');
					Utils.wait(false);
					$location.path('/eventos/listar');
				},
				function(error){
					Utils.exception(error);
				}
			);
		}
		
		Utils.secureInit(self.init, [ CONFIG.ROLE_ADMIN ]);	
		
}]);