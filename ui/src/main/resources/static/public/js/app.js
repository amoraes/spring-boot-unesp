angular.module('exemplo-public', ['ngMaterial', 'ngMessages', 'ngRoute', 'ngResource'])
.run(['$rootScope', '$log', function($rootScope, $log){
	$log.debug('Inicializando Angular App Exemplo');
	$rootScope.authenticated = false;
}])
.config(['$mdThemingProvider', function($mdThemingProvider){
	$mdThemingProvider.theme('default')
    .primaryPalette('blue')
    .accentPalette('light-blue');
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
    .when('/inscricao', {
        templateUrl: 'views/inscricao.html',
        controller: 'InscricaoController as Ctrl'
    })
	.otherwise({
		redirectTo: '/'
    });
}]);