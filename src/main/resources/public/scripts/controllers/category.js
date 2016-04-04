'use strict';

app.controller('CategoryCtrl', function($scope, $http) {
	var promise = $http({
		method : 'GET',
		url : '/api/category'
	});

	promise.then(function(resp) {
		$scope.categories = resp.data;
	}, function(resp) {
	})
	
	console.log("Category loading." + $scope.categories);
	$scope.category_header = '产品目录';
});