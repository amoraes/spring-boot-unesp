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
		
		self.download = function(evento){
			var params = {};
			params.idEvento = evento.idEvento;
			InscricaoResource.list(params,
				function(data){
					//define o layout do pdf
					var docDefinition = {
					    content: [
					      {
					        text: 'Inscrições - Evento '+evento.titulo, style: 'title'
					      },
					      {
					    	  text: ' '
					      },
					      {
					        style: 'myTable',
					        table: {
					          widths: ['*', '*', '*', '*'],
					          body: [
					            [{text: 'Nome', style: 'header'}, {text: 'CPF', style: 'header'},
					              {text: 'E-mail', style: 'header'}, {text: 'Camiseta', style: 'header'}
					            ],
					          ]
					        }
					      }
					    ],
					    styles: {
					      title: {
					    	bold: true,
					    	color: '#000',
					    	fontSize: 16,
					    	alignment: 'center'
					      },					    
					      header: {
					        bold: true,
					        color: '#000',
					        fontSize: 12
					      },
					      myTable: {
					        color: '#666',
					        fontSize: 10
					      }
					    }
					  };
					data.forEach(function(inscricao){
						var line = [inscricao.nome, inscricao.cpf, inscricao.email, inscricao.tamanhoCamisetaDescricao];
						docDefinition.content[2].table.body.push(line)
					});
					pdfMake.createPdf(docDefinition).open();
				},
				function(error){
					Utils.exception(error);
				}
			);
		}
		
		Utils.secureInit(self.init, [ CONFIG.ROLE_ADMIN, CONFIG.ROLE_RELATORIOS ]);
		
}]);