'use strict';
var app = angular.module('exemplo');
/**
 * Factory para determinar se o usuário está logado e obter os dados do usuário
 */
app.factory('SessionService', ['$rootScope', '$http', '$log', '$q', function($rootScope, $http, $log, $q){
	$rootScope.user = {};
	
	var self = this;
	
	$rootScope.loginError = false;
	
	self.isUserStillLoggedIn = function(){
		var q = $q.defer();
		$rootScope.authenticated = false;
		$http.get('unespauth/user').success(function(data) {
			if (data.name) {
    			if($rootScope.user.name != data.name){
    				//se mudou o usuário, retorna erro, essa sessão terminou
    				q.reject('Outro usuário logado');
    			}else{
    				$rootScope.authenticated = true;
    				q.resolve();
    			}
			}
		}).error(function(error) {
    		q.reject('Sessão terminou');
    	});
		return q.promise;
	}
	
	self.login = function(){
		$log.debug('[SessionService] Executando login()');
		$rootScope.loginError = false;
		var q = $q.defer();
		$http.get('unespauth/user?refresh=true').success(function(data) {
    		if (data.name) {
    			$log.debug('[SessionService] Dados recebidos de unespauth/user:');
    			$log.debug(data);
    			$log.debug('[SessionService] Usuário autenticado: '+data.name);
    			$rootScope.user.name = data.name;
    			$rootScope.user.authorities = data.authorities;
    			$log.debug('[SessionService] Autoridades concedidas: '+JSON.stringify($rootScope.user.authorities));
    			//detalhes customizados (não padrão do OAuth)
    			$rootScope.user.details = {};
    			$rootScope.user.details.idUsuario = data.details.idUsuario;
    			$rootScope.user.details.nome = data.details.nome;
    			//---
    			$log.debug($rootScope.user);
    			$rootScope.authenticated = true;
    			$rootScope.loginError = false;
    			q.resolve($rootScope.user);
    		} else {
    			$rootScope.authenticated = false;
    			$rootScope.loginError = true;
    			q.resolve(null);
    		}
    	}).error(function(error) {
    		$log.debug('[SessionService] Erro ao executar o login(): ');
    		$log.error(error);    		
    		$rootScope.authenticated = false;
    		$rootScope.loginError = true;
    		q.reject(error);
    	});
		return q.promise;
	}
	
	self.getUser = function(){
		if(!$rootScope.authenticated){
			return null;
		}else{
			return $rootScope.user;
		}
	}
	
	self.logout = function(){
		$log.debug('[SessionService] Executando logout()');
		$rootScope.user = {};
        $rootScope.authenticated = false;
	}
	
	self.hasRole = function(role){
		$log.debug('[SessionService] Executando hasRole(\''+role+'\')');
		for(var i = 0; i < $rootScope.user.authorities.length; i++){
			if($rootScope.user.authorities[i] == role || $rootScope.user.authorities[i].authority == role){
				return true;
			}
		}
		return false;
	}
	
	return self;
}]);
