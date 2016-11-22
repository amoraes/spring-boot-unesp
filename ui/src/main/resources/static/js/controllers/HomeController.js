'use strict';
var app = angular.module('exemplo');
app.controller('HomeController', 
	['$log','SessionService','Utils','CONFIG', function ($log, SessionService, Utils, CONFIG) {
		var self = this;
		
		self.ready = false;
		self.nome = null;
		
		self.init = function(){
			var user = SessionService.getUser();
			self.nome = user.details.nome;
			self.ready = true;
		}
		
		Utils.secureInit(self.init, [ CONFIG.ROLE_ADMIN, CONFIG.ROLE_RELATORIOS ])
	}
]);