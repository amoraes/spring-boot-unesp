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
.config(['$mdDateLocaleProvider',function($mdDateLocaleProvider) {
  var regex=new RegExp("(([0-2]{1}[0-9]{1}|3[0-1]{1})[/](0[1-9]|1[0-2])[/][0-9]{4})");
  $mdDateLocaleProvider.formatDate = function(date) {
	if(date != null){
	    var day = date.getDate();
	    var monthIndex = date.getMonth();
	    var year = date.getFullYear();
	
	    return day + '/' + (monthIndex + 1) + '/' + year;
	}
	return '';
  };
  $mdDateLocaleProvider.parseDate = function(dateString){
	  if(regex.test(dateString)){
		  return new Date(dateString.substring(6,10),dateString.substring(3,5)-1,dateString.substring(0,2));
	  }else{
		  return new Date(NaN);
	  }
  }
  $mdDateLocaleProvider.months = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
  $mdDateLocaleProvider.shortMonths = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'];
  $mdDateLocaleProvider.days = ['Domingo', 'Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado'];
  $mdDateLocaleProvider.shortDays = ['D','S','T','Q','Q','S','S'];
}])
.config(['$routeProvider', function($routeProvider) {
	$routeProvider
	.when('/', {
        templateUrl: 'views/home.html',
        controller: 'HomeController as Ctrl'
    })
    .when('/eventos/listar/:idEvento?', {
        templateUrl: 'views/eventosListar.html',
        controller: 'EventosListarController as Ctrl'
    })
    .when('/eventos/inserir', {
        templateUrl: 'views/eventosInserir.html',
        controller: 'EventosInserirController as Ctrl'
    })
    .when('/eventos/editar/:idEvento', {
        templateUrl: 'views/eventosEditar.html',
        controller: 'EventosEditarController as Ctrl'
    })
	.otherwise({
		redirectTo: '/'
    });
}]);