'use strict';

app.directive('chat', function () {
        return {
            templateUrl: 'scripts/directives/chat/chat.html',
            restrict: 'E',
            replace: true,
        }
    });


