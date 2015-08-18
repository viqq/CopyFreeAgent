/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'controllers/header'
    ],
    function (angularAMD) {
        var dirHeader = function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/header/template.html',
                replace: true,
                scope: true,
                controller: 'HeaderCtrl'
            };
        };

        angularAMD.directive('dirHeader', dirHeader);
    }
);