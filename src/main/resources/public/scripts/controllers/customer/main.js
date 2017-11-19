'use strict';

app.controller('CustomerCtrl', ['$scope', 'i18nService', '$http', 'uiGridConstants',
    function ($scope, i18nService, $http, uiGridConstants) {
    i18nService.setCurrentLang('zh-cn');

    var paginationOptions = {
        pageNumber: 1,
        pageSize: 25,
        sort: null
    };
    
    $scope.activateCustomer = function(value, customer){
    	
    	
    	if(customer.activated) {
    		alert(customer.name + ",已经激活");
    	} else {
    		var sUrl = '/api/v1/customers/' + value;
    		customer.activated = true;
    		var updatePromise = $http.put(sUrl, customer);
            updatePromise.then(function (resp) {
            	customer.activated = true;
            	alert("激活客户("+customer.name + "）成功!");
            }, function (resp) {
            	customer.activated = false;
            	alert("激活客户("+customer.name + "）失败!");
            })
    	}
	};
	
    $scope.gridOptions = {
        paginationPageSizes: [25, 50, 75],
        paginationPageSize: 25,
        useExternalPagination: true,
        useExternalSorting: true,
        columnDefs: [
            {name: 'id', width: 100, displayName: '客户编号', enableSorting: true, visible: true, cellClass: 'grid-align'},
            {name: 'mobilePhone', width: "*", displayName: '手机号码', enableSorting: false},
            {name: 'name', width: "*", displayName: '客户姓名', enableSorting: false},
            {
                name: 'address', width: "*", displayName: '地址', enableSorting: false
            },
            {
                name: 'activated', type: 'boolean', cellTemplate: '<input disabled type="checkbox" ng-model="row.entity.activated">' , 
	            width: 100, displayName: '激活状态', enableSorting: false, cellClass: 'grid-align'
            },
            {
            	name:'行动',headerCellClass: "grid-align", cellClass: 'grid-align', cellTemplate: '<div><button class="btn btn-sm btn-primary" ng-click="grid.appScope.activateCustomer(row.entity.id, row.entity)">激活客户</button></div>'
            }
        ],

        onRegisterApi: function (gridApi) {
            $scope.gridApi = gridApi;

            $scope.gridApi.core.on.sortChanged($scope, function (grid, sortColumns) {
                if (sortColumns.length == 0) {
                    paginationOptions.sort = null;
                } else {
                    paginationOptions.sort = sortColumns[0].sort.direction;
                }

                loadDataAfterPaginationChange();
            });

            gridApi.pagination.on.paginationChanged($scope, function (newPage, pageSize) {
                paginationOptions.pageNumber = newPage;
                paginationOptions.pageSize = pageSize;
                loadDataAfterPaginationChange();
            });
        }
    };

    var fnSuccess = function (resp) {
        console.log('> fnSuccess response with status:' + resp.status);
        $scope.gridOptions.totalItems = resp.data.total;
        $scope.gridOptions.data = resp.data.list;
        console.log('< fnSuccess end.')
    };

    var fnError = function (resp) {
        console.error('> response with status:' + resp.status);
    };

    $scope.search = function () {
        var sUrl = '/api/v1/customers/search?s=' +
            $scope.queryText + "&size=" +
            $scope.gridOptions.paginationPageSize

        $http.get(sUrl)
            .success(function (resp) {
                $scope.gridOptions.totalItems = resp.total;
                $scope.gridOptions.data = resp.list;
                $scope.gridApi.core.notifyDataChange(uiGridConstants.dataChange.ALL);
            });
    };

    var loadDataAfterPaginationChange = function () {
        var sUrl = '/api/v1/customers/search?s=' +
            $scope.queryText + "&size=" +
            $scope.gridOptions.paginationPageSize + '&page=' +
            $scope.gridApi.grid.options.paginationCurrentPage;

        if ($scope.queryText == '' || $scope.queryText == undefined || $scope.queryText == 'undefined') {

            sUrl = '/api/v1/customers?size=' +
                $scope.gridOptions.paginationPageSize + '&page=' +
                $scope.gridApi.grid.options.paginationCurrentPage;
        }

        var promise = $http({
            method: 'GET',
            url: sUrl
        });
        promise.then(fnSuccess, fnError);
    };

    var initialLoadPage = function () {
        var sUrl = '/api/v1/customers?size=' + $scope.gridOptions.paginationPageSize
        var promise = $http({
            method: 'GET',
            url: sUrl
        });
        promise.then(fnSuccess, fnError);
    };

    initialLoadPage();

    $scope.updateState = function () {
    	var sUrl = '/api/v1/customers/' + this.customer.id
        var updatePromise = $http.post('/api/v1/customers/update', this.customer);
        updatePromise.then(function (resp) {
            console.log(resp.data + " row of record(s) update success");
        }, function (resp) {
            console.log(resp);
        })
    };	
	}]
);