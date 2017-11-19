'use strict';

app.controller('StudentCtrl', ['$scope', 'i18nService', '$http', 'uiGridConstants',
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
            {name: 'id', width: 80, displayName: '学号', enableSorting: true, visible: true, cellClass: 'grid-align'},
            {name: 'studentName', width: 120, displayName: '姓名', enableSorting: false},
            {name: 'birthday', width: 120, displayName: '生日', enableSorting: false},
            {
                name: 'classPeriod', width: 100, displayName: '总课时', enableSorting: false
            },
            {name: 'donePeriods', width: 100, displayName: '完成课时', enableSorting: false},
            {name: 'leftPeriods', width: 100, displayName: '剩余课时', enableSorting: false},
            {name: 'parentName', width: 120, displayName: '家长姓名', enableSorting: false},
            {name: 'mobilePhone', width: 120, displayName: '家长手机', enableSorting: false},
            {
                name: 'child', type: 'boolean', cellTemplate: '<input disabled type="checkbox" ng-model="row.entity.child">', 
                width: 100, displayName: '成人', enableSorting: false, headerCellClass: "grid-align", cellClass: 'grid-align'
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
        var sUrl = '/api/v1/students/search?s=' +
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
        var sUrl = '/api/v1/students/search?s=' +
            $scope.queryText + "&size=" +
            $scope.gridOptions.paginationPageSize + '&page=' +
            $scope.gridApi.grid.options.paginationCurrentPage;

        if ($scope.queryText == '' || $scope.queryText == undefined || $scope.queryText == 'undefined') {

            sUrl = '/api/v1/students?size=' +
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
        var sUrl = '/api/v1/students?size=' + $scope.gridOptions.paginationPageSize
        var promise = $http({
            method: 'GET',
            url: sUrl
        });
        promise.then(fnSuccess, fnError);
    };

    initialLoadPage();

    $scope.updateState = function () {
    	var sUrl = '/api/v1/students/' + this.student.id
        var updatePromise = $http.post(sUrl, this.student);
        updatePromise.then(function (resp) {
            console.log(resp.data + " row of record(s) update success");
        }, function (resp) {
            console.log(resp);
        })
    };	
	}]
);