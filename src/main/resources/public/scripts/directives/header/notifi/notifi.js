'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description # adminPosHeader
 */
angular.module('sbAdminApp').directive('headerNotification', function() {
	return {
		templateUrl : 'scripts/directives/header/notifi/notifi.html',
		restrict : 'E',
		replace : true,
	}
});
