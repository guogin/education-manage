'use strict';

app.controller('FiExpenseCtrl', ['$scope', 'i18nService', '$http', 'uiGridConstants',
    function ($scope, i18nService, $http, uiGridConstants) {
	i18nService.setCurrentLang('zh-cn');
}]
);