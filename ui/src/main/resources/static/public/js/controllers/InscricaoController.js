'use strict';
var app = angular.module('exemplo-public');
app.controller('InscricaoController', 
	['$log','EventoResource','InscricaoResource','TamanhoCamisetaResource','PessoaResource','Utils',function ($log,EventoResource,InscricaoResource,TamanhoCamisetaResource,PessoaResource,Utils) {
	
		//guarda uma referência ao próprio controller para ser usada dentro das funções
		var self = this;
		
		//dados básicos da tela
		self.ready = false;
		self.eventos = [];
		self.inscricao = {};
		self.abaSelecionada = 0;
		self.tamanhosCamiseta = [];
		
		//rotina de inicialização
		self.init = function(){
			Utils.wait(true);
			EventoResource.list(
					function(data){
						self.eventos = data;
						TamanhoCamisetaResource.list(
								function(data){
									self.tamanhosCamiseta = data;
									self.ready = true;			
									Utils.wait(false);
								},
								function(error){
									Utils.exception(error);
								}
						);
					},
					function(error){
						Utils.exception(error);
					}
			);
		}
		
		//primeira etapa, seleção do evento
		self.avancar1 = function(){
			if(self.inscricao.idEvento != null){
				self.abaSelecionada = 1;
			}
		}
		
		//segunda etapa, buscar o cadastro através do cpf
		self.avancar2 = function(){
			Utils.wait(true);
			var params = {};
			params.cpf = self.inscricao.cpf;
			PessoaResource.filter(
					params,
					function(data){
						if(data.length > 0){
							self.inscricao.nome = data[0].nome;
							self.inscricao.email = data[0].email;
							self.abaSelecionada = 2;
							Utils.wait(false);
						}else{
							Utils.error('Cadastro não encontrado!');
						}
					},
					function(error){
						Utils.exception(error);
					}
			);
		}
		self.voltar2 = function(){ //voltar da segunda etapa
			self.abaSelecionada = 0;
		}
		
		//terceira etapa, salvar a inscrição
		self.avancar3 = function(){
			Utils.wait(true);
			InscricaoResource.save(self.inscricao,
				function(data){
					self.inscricao = data;
					self.abaSelecionada = 3;
					Utils.wait(false);
				},
				function(error){
					Utils.exception(error);
				}
			);
		}
		self.voltar3 = function(){ //voltar da terceira etapa
			self.abaSelecionada = 1;
		}
				
		//chama a rotina de inicialização
		self.init();
		
	}
]);