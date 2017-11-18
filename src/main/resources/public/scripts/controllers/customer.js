'use strict';

app.controller('CustomerCtrl', function($scope, $http) {

	var promise = $http({
		method : 'GET',
		url : '/api/v1/customers'
	});

	var fnSuccess = function(resp){
		$scope.customers = resp.data;
		
		for(var i in $scope.customers){
			if($scope.customers[i].isActivated) {
				$scope.customers[i].enabledText = '是';
			} else {
				$scope.customers[i].enabledText = '否';
			}
		}
	};

	var fnError = function(resp){

	};

	promise.then(fnSuccess, fnError);
	
	$scope.category_header = '客户列表';
	
	$scope.updateState = function(){
		var sUrl = '/api/v1/customer/' + this.customer.id;
		var updatePromise = $http.post(sUrl, this.customer);
		updatePromise.then(function(resp){
			console.log(resp.data + " row of record(s) update success");
		}, function(resp){
			console.log(resp);
		})
	};

});