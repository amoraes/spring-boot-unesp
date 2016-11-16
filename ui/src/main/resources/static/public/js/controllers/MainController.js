'use strict';
var app = angular.module('exemplo');
app.controller('MainController', 
	['$scope', '$rootScope', '$http', '$location', '$mdSidenav', '$mdDialog', '$mdMedia', '$log', 'Utils',
	function ($scope, $rootScope, $http, $location, $mdSidenav,  $mdDialog, $mdMedia, $log, Utils) {
	    var self = this;
	    self.menuItems = [];
	    
	    $rootScope.mdMedia = $mdMedia;
	    self.ready = false;
	    
	    self.init = function(){
    		self.loadMenu();
    		self.ready = true;
	    }
	    
		self.loadMenu = function(){
			 self.menuItems = 
				   [
			        	{"label": "Inscrever-se", "icon": "assignment", "url": "/inscricao"},
			        	{"label": "Administração", "icon": "lock", "url": "../login"},
			       ];	 
			 Utils.wait(false);
		}
	    
	    self.toggleMenu = function () {
	    	$log.debug('[MainController] toogleMenu')
	        $mdSidenav('leftMenu').toggle();
	    };
	    
	    self.openUrl = function(url){
	    	if(url == '../login'){
	    		window.location = '../login';
	    	}else{
	    		$location.url(url);
	        	$mdSidenav('leftMenu').close();
	    	}
	    	
	    };
	    
	    self.init();
}]);