'use strict';

app.directive('tiles', function () {
    return {
        templateUrl: 'scripts/directives/dashboard/tiles/tiles.html',
        restrict: 'E',
        replace: true,
        scope: {
            'model': '=',
            'comments': '@',
            'number': '@',
            'name': '@',
            'colour': '@',
            'details': '@',
            'type': '@',
            'goto': '@'
        }

    }
});
