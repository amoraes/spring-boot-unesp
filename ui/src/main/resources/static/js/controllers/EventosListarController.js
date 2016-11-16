'use strict';
var app = angular.module('exemplo');
app.controller('EventosListarController', 
	['$scope', '$routeParams', '$log', '$location', '$mdDialog', 'EventoResource', 'SessionService', 'Utils', 'CONFIG',	
	function ($scope, $routeParams, $log, $location, $mdDialog,  EventoResource, SessionService, Utils, CONFIG) {
		$log.debug('Inicializando EventosListarController');
		
		var self = this;
		var eventos = [];
		
		self.ready = false;
		self.idEventoSelecionado = null;
		
		self.init = function(){
			Utils.wait(true);
			EventoResource.list(
					function(data){
						self.eventos = data;
						Utils.wait(false);
						self.ready = true;
					},
					function(error){
						Utils.exception(error);
					});	
		}
		
		self.inserir = function(){
			$location.path('/eventos/inserir');	
		}
		
		self.editar = function(idEvento, contextMenuClick){
			if(self.idEventoSelecionado == null || contextMenuClick){
				$location.path('/eventos/editar/'+idEvento);	
			}
		}
		
		self.excluir = function(idEvento, titulo, ev){
	        var confirm = $mdDialog.confirm()
	              .title('Excluir')
	              .textContent('Confirma exclusão do evento \''+titulo+'\'?')
	              .ariaLabel('Excluir')
	              .targetEvent(ev)
	              .ok('Sim')
	              .cancel('Não');
	        $mdDialog.show(confirm).then(function() {
	        	Utils.wait(true);
	        	var params = {};
	        	params.idEvento = idEvento;
	        	EventoResource.delete(
	        		params, 
	    			function(data){
	    				Utils.success('Evento excluído!');
	    				for(var i = 0; i < self.eventos.length; i++){
	    					if(self.eventos[i].idEvento == idEvento){
	    						self.eventos.splice(i,1);
	    						break;
	    					}
	    				}
	    				Utils.wait(false);
	    			},
	    			function(error){
	    				$log.debug("[EventosListarController] Não foi possível excluir o evento "+idEvento);
	    				Utils.exception(error);
	    			}
	    		);         	
	        }, function() { });
		}
		
		self.selecionar = function(idEvento, ev, $mdOpenMenu){
			self.idEventoSelecionado = idEvento;
			var originatorEv = ev;
	        $mdOpenMenu(ev);
		}
		
		Utils.secureInit(self.init, [ CONFIG.ROLE_ADMIN ]);	
		
		$scope.$on("$mdMenuClose", function() { 
			self.idEventoSelecionado = null;
		});
}]);