'use strict';
var app = angular.module('exemplo');
app.controller('InscricoesController', 
	['$scope', '$routeParams', '$log', '$location', '$mdDialog', 'EventoResource', 'InscricaoResource', 'SessionService', 'Utils', 'CONFIG',	
	function ($scope, $routeParams, $log, $location, $mdDialog,  EventoResource, InscricaoResource, SessionService, Utils, CONFIG) {
		$log.debug('Inicializando InscricoesController');
		
		var self = this;
		self.eventos = [];
		
		self.ready = false;
		
		self.init = function(){
			Utils.wait(true);
			EventoResource.list(
				function(data){
					self.eventos = data;
					var countEventos = 0;
					//pega o count das inscrições de cada evento
					self.eventos.forEach(function(ev){
						var params = {};
						params.idEvento = ev.idEvento;
						InscricaoResource.count(
							params,
							function(data){
								ev.inscritos = data.count;
								countEventos++;
								if(countEventos == self.eventos.length){
									Utils.wait(false);
									self.ready = true;
								}
							},
							function(error){
								Utils.exception(error);
							}
						);						
					});
				},
				function(error){
					Utils.exception(error);
				});	
		}
		
		Utils.secureInit(self.init, [ CONFIG.ROLE_ADMIN ]);
		
}]);