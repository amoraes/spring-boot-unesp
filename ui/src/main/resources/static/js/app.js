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
	        		&& !$location.$$path.startsWith('/public/') && !$location.$$path == ''
	        ) {     	
	        	window.location = 'login';
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
	$routeProvider
	.when('/', {
        templateUrl: 'views/home.html',
        controller: 'HomeController as Ctrl'
    })
	.otherwise({
		redirectTo: '/'
    });
}]);