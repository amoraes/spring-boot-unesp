'use strict';
var app = angular.module('exemplo');
app.controller('EventosInserirController', 
	['$scope', '$routeParams', '$log', '$location', 'EventoResource', 'Utils', 'CONFIG', 	
	function ($scope, $routeParams, $log, $location, EventoResource, Utils, CONFIG) {
		$log.debug('Inicializando EventosInserirController');
		
		var self = this;
		self.evento = {};
	
		self.ready = false;
		
		self.init = function(){
			self.ready = true;
		}
		
		self.voltar = function(){
			$location.path('/eventos/listar');
		}
		
		self.salvar = function(){
			Utils.wait(true);
			var params = Utils.datesToString(self.evento);			
			EventoResource.save(
				params, 
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