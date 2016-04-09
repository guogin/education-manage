'use strict';

app.controller('CategoryCtrl', function($scope, $http) {

	var promise = $http({
		method : 'GET',
		url : '/api/category'
	});

	var fnSuccess = function(resp){
		$scope.categories = resp.data;
		
		for(var i in $scope.categories){
			if($scope.categories[i].enabled) {
				$scope.categories[i].enabledText = '是';
			} else {
				$scope.categories[i].enabledText = '否';
			}
		}
	};

	var fnError = function(resp){

	};

	promise.then(fnSuccess, fnError);
	
	$scope.category_header = '产品目录';
	
	$scope.updateState = function(){
		var updatePromise = $http.post('/api/category/update', this.category);
		updatePromise.then(function(resp){
			console.log(resp.data + " row of record(s) update success");
		}, function(resp){
			console.log(resp);
		})
	};

});