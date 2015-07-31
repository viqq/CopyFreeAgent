/**
 * Created by TITUS on 28.07.2015.
 */
define(
    [
        'angularAMD',
        'angular-route',

        'directives/header/script',
        'directives/login/script',
        'directives/registration/script',

        '../app/controllers/main'
    ],
    function (angularAMD) {
        var app = angular.module('app', ['ngRoute']);

        app.config(function ($routeProvider, $locationProvider) {
            $routeProvider
                .when('/login', angularAMD.route({
                    templateUrl: 'app/views/login.html',
                    controller: 'LoginCtrl',
                    controllerUrl: 'controllers/login'
                }))
                .when('/registration', angularAMD.route({
                    templateUrl: 'app/views/registration.html',
                    controller: 'RegistrationCtrl',
                    controllerUrl: 'controllers/registration'
                }))
                .otherwise({
                    redirectTo: '/login'
                });

            //Commented until solve problems with one page routing without hash
            //$locationProvider.html5Mode(true);
        });

        return angularAMD.bootstrap(app);
    }
);