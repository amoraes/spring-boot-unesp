'use strict';
var app = angular.module('exemplo');
app.controller('MainController', 
	['$scope', '$rootScope', '$http', '$location', '$mdSidenav', '$mdDialog', '$mdMedia', '$log', 'SessionService', 'Utils', 'CONFIG',	
	function ($scope, $rootScope, $http, $location, $mdSidenav,  $mdDialog, $mdMedia, $log, SessionService, Utils, CONFIG) {
	    var self = this;
	    self.menuItems = [];
	    
	    self.usuario = {};
	    $rootScope.mdMedia = $mdMedia;
	    self.menuAdminVisible = false;
	    self.ready = false;
	    
	    self.init = function(){
	    	Utils.wait(true);
	    	//verifica se é página pública
	    	SessionService.login().then(
    			function(data){
    				self.loadMenu();
    				self.ready = true;
    				Utils.wait(false);
    			},    			
    			function(error){
    				window.location = 'login';
    			});	
	    }
	    
		self.loadMenu = function(){
			self.menuItems.push({"label": "Início", "icon": "home", "url": "/"});
			 if(SessionService.hasRole(CONFIG.ROLE_ADMIN)){
				 self.menuItems.push({"label": "Cadastro de Eventos", "icon": "settings", "url": "/eventos/listar"});
			 }
			 //relatório está disponível para admin e relatórios
			 if(SessionService.hasRole(CONFIG.ROLE_ADMIN) || SessionService.hasRole(CONFIG.ROLE_RELATORIOS)){
				 self.menuItems.push({"label": "Inscrições", "icon": "library_books", "url": "/inscricoes"});
			 }
			 Utils.wait(false);
		}
	    
	    self.toggleMenu = function () {
	    	$log.debug('[MainController] toogleMenu')
	        $mdSidenav('leftMenu').toggle();
	    };
	    
	    self.openUrl = function(url){
	    	if(url == 'login'){
	    		window.location = 'login';
	    	}else{
	    		$location.url(url);
	        	$mdSidenav('leftMenu').close();
	    	}
	    	
	    };
	    
	    self.logout = function(ev) {
	        var confirm = $mdDialog.confirm()
	              .title('Finalizar sessão')
	              .textContent('Deseja finalizar sua sessão nesse sistema e retornar para a Central de Acessos?')
	              .ariaLabel('Desconectar')
	              .targetEvent(ev)
	              .ok('Sim')
	              .cancel('Não');
	        $mdDialog.show(confirm).then(function() {
	        	Utils.wait(true);
	        	window.location = 'logout';         	
	        }, function() { });
	    };
	    
	    self.getUser = function(){
	    	return SessionService.getUser();
	    };
	    
	    self.toggleSubMenu = function(menu){
	    	if(menu == 'admin'){
	    		if(self.menuAdminVisible){
	    			self.menuAdminVisible = false;
	    		}else{
	    			self.menuAdminVisible = true;
	    		}
	    	}
	    }
	    
	    self.init();
    
}]);