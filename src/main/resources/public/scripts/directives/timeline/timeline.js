'use strict';

app.directive('timeline', function () {
    return {
        templateUrl: 'scripts/directives/timeline/timeline.html',
        restrict: 'E',
        replace: true,
    }
});
