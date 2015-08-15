/**
 * Created by TITUS on 30.07.2015.
 */
define([
        'angularAMD',
        'controllers/login'
    ],
    function (angularAMD) {
        angularAMD.directive('dirRegistration', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/registration/template.html',
                replace: true,
                scope: true,
                controller: 'RegistrationCtrl'
            };
        });
    });