'use strict';

app.controller('IncomingTaxCtrl', ['$scope', 'i18nService', '$http', 'uiGridConstants',
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
            {name: 'id', width: 80, displayName: '排序号', enableSorting: true, visible: true},
            {name: 'category_id', width: 90, displayName: '类目编码', enableSorting: false},
            {name: 'category_name', width: '*', displayName: '类目名称', enableSorting: false},
            {
                name: 'comment', width: '*', displayName: '备注', enableSorting: false
            },
            {name: 'parent_id', width: 100, displayName: '父类目编码', enableSorting: false},
            {name: 'category_level', width: 60, displayName: '级别', enableSorting: false},
            {name: 'auto_pay_enabled', width: 90, displayName: '自动支付', enableSorting: false},
            {name: 'best_offer_enabled', width: 90, displayName: '最佳货源', enableSorting: false},
            {name: 'leaf_category', width: 90, displayName: '子类目', enableSorting: false},
            {name: 'lsd', width: 60, displayName: 'LSD', enableSorting: false},
            {
                name: 'enabled', filter: {
                term: '1',
                type: uiGridConstants.filter.SELECT,
                selectOptions: [{value: '1', label: '是'}, {value: '0', label: '否'}]
            }, width: 80, displayName: '启用', enableSorting: false
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
        var sUrl = '/api/category/search?s=' +
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
        var sUrl = '/api/category/search?s=' +
            $scope.queryText + "&size=" +
            $scope.gridOptions.paginationPageSize + '&page=' +
            $scope.gridApi.grid.options.paginationCurrentPage;

        if ($scope.queryText == '' || $scope.queryText == undefined || $scope.queryText == 'undefined') {

            sUrl = '/api/category?size=' +
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
        var sUrl = '/api/category?size=' + $scope.gridOptions.paginationPageSize
        var promise = $http({
            method: 'GET',
            url: sUrl
        });
        promise.then(fnSuccess, fnError);
    };

    initialLoadPage();

    $scope.updateState = function () {
        var updatePromise = $http.post('/api/category/update', this.category);
        updatePromise.then(function (resp) {
            console.log(resp.data + " row of record(s) update success");
        }, function (resp) {
            console.log(resp);
        })
    };	
	}]
);