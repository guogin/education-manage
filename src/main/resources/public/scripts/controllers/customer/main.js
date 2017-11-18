'use strict';

app.controller('CustomerCtrl', ['$scope', 'i18nService', '$http', 'uiGridConstants',
    function ($scope, i18nService, $http, uiGridConstants) {
    i18nService.setCurrentLang('zh-cn');

    var paginationOptions = {
        pageNumber: 1,
        pageSize: 25,
        sort: null
    };

    $scope.gridOptions = {
        paginationPageSizes: [25, 50, 75],
        paginationPageSize: 25,
        useExternalPagination: true,
        useExternalSorting: true,
        columnDefs: [
            {name: 'id', width: 80, displayName: '客户编号', enableSorting: true, visible: true},
            {name: 'mobilePhone', width: 120, displayName: '手机号码', enableSorting: false},
            {name: 'name', width: 120, displayName: '客户姓名', enableSorting: false},
            {
                name: 'address', width: "*", displayName: '地址', enableSorting: false
            },
            {
                name: 'isActivated', filter: {
	                term: true,
	                type: uiGridConstants.filter.SELECT,
	                selectOptions: [{value: true, label: '是'}, {value: false, label: '否'}]
	            }, 
	            width: 80, displayName: '激活状态', enableSorting: false
            },
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