angular.module('exemplo', ['ngMaterial', 'ngMessages', 'ngRoute', 'ngResource'])
.constant("CONFIG", {
    "ROLE_ADMIN": "ROLE_UNESP.EXEMPLO_ADMINISTRADOR",
    "CONTEXT_PATH": "exemplo"
})
.run(['$rootScope', '$log', 'SessionService', function($rootScope, $log, SessionService){
	$log.debug('Inicializando Angular App Exemplo');
	$rootScope.authenticated = false;
}])
.config(['$mdThemingProvider', function($mdThemingProvider){
	$mdThemingProvider.theme('default')
    .primaryPalette('blue')
    .accentPalette('light-blue');
}])
.factory('RedirectInterceptor', ['$q', '$location' ,'$window' ,'$log' ,'CONFIG', function($q,$location,$window,$log,CONFIG){
    return  {
        'response':function(response){     
	        if (typeof response.data === 'string' && response.data.indexOf('UNESPAUTH_LOGIN_PAGE') != -1 
	        		&& !$location.$$path.startsWith('/public/')
	        ) {
	        	$log.debug($location);
	        	$log.debug('[RedirectInterceptor] Token inválido, sessão terminada.')
	            window.location = "/"+CONFIG.CONTEXT_PATH+"/sessaoTerminada.html";
	            return $q.reject(response);
	        }else{
	            return response;
	        }
        }
    }
}])
.config(['$httpProvider', function($httpProvider) {
	$httpProvider.interceptors.push('RedirectInterceptor');
}])
.config(['$routeProvider', function($routeProvider) {
	/*$routeProvider
	.when('/public/primeiroAcesso', {
        templateUrl: 'views/primeiroAcesso.html',
        controller: 'PrimeiroAcessoController as PAC'
    })
	.otherwise({
		redirectTo: '/'
    });*/
}]);