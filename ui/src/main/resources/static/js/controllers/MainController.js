'use strict';
var app = angular.module('exemplo');
app.controller('MainController', 
	['$scope', '$rootScope', '$http', '$location', '$mdSidenav', '$mdDialog', '$mdMedia', '$log', 'SessionService', 'Utils', 'CONFIG',	
	function ($scope, $rootScope, $http, $location, $mdSidenav,  $mdDialog, $mdMedia, $log, SessionService, Utils, CONFIG) {
	    var self = this;
	    self.menuItems = [];
	    self.menuAdminItems = [];
	    
	    self.usuario = {};
	    $rootScope.mdMedia = $mdMedia;
	    self.menuAdminVisible = false;
	    self.ready = false;
	    
	    self.init = function(){
	    	//verifica se é página pública
	    	if($location.$$path.indexOf('/public/') == 0){
	    		self.loadMenu();
	    		self.ready = true;
	    	}else{
	    		SessionService.login().then(
    				function(data){
    					self.loadMenu();
    					self.ready = true;
    				},    			
    				function(error){
    					self.loadMenu();
    					self.ready = true;
    				});	
	    	}
	    }
	    
		self.loadMenu = function(){
			 var user = SessionService.getUser();
			 if(user == null){
				 self.menuItems = 
					   [
				        	{"label": "Inscrever-se", "icon": "assignment", "url": "inscricao"},
				       ];	 
			 }else{
				 if(SessionService.hasRole(CONFIG.ROLE_ADMIN)){
					 self.menuAdminItems.push({"label": "Cadastro de Eventos", "icon": "settings", "url": "/admin/eventos/listar"});
					 self.menuAdminItems.push({"label": "Inscrições", "icon": "library_books", "url": "/admin/inscricoes/listar"});
				 }
				 
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
	              .title('Desconectar')
	              .textContent('Deseja sair da conta \''+$rootScope.user.name+'\' neste computador?')
	              .ariaLabel('Desconectar')
	              .targetEvent(ev)
	              .ok('Sim')
	              .cancel('Não');
	        $mdDialog.show(confirm).then(function() {
	        	Utils.wait(true);
	        	//direcionar para a central         	
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