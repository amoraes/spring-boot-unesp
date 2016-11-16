angular.module('exemplo', ['ngMaterial', 'ngMessages', 'ngRoute', 'ngResource'])
.run(['$rootScope', '$log', function($rootScope, $log){
	$log.debug('Inicializando Angular App Exemplo');
	$rootScope.authenticated = false;
}])
.config(['$mdThemingProvider', function($mdThemingProvider){
	$mdThemingProvider.theme('default')
    .primaryPalette('blue')
    .accentPalette('light-blue');
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