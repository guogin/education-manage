'use strict';

app.directive('headerNotification', function() {
	return {
		templateUrl : 'scripts/directives/header/notifi/notifi.html',
		restrict : 'E',
		replace : true,
	}
});
