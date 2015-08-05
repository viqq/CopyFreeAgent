/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'controllers/login'
    ],
    function (angularAMD) {
        var dirLogin = function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/login/template.html',
                replace: true,
                scope: true,
                controller: 'LoginCtrl'
            };
        };

        angularAMD.directive('dirLogin', ['login', dirLogin]);
    }
);