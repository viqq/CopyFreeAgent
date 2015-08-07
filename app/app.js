/**
 * Created by TITUS on 28.07.2015.
 */
define(
    [
        'angularAMD',
        'angular-route',

        'directives/header/script',
        'directives/footer/script',
        'directives/home/script',
        'directives/registration/script',
        'directives/login/script',
        'directives/search/script',

        '../app/controllers/main'
    ],
    function (angularAMD) {
        var app = angular.module('app', ['ngRoute']);

        app.config(function ($routeProvider, $locationProvider) {
            $routeProvider
                .when('/', angularAMD.route({
                    templateUrl: 'app/views/home.html',
                    controller: 'HomeCtrl',
                    controllerUrl: 'controllers/home'
                }))
                .when('/registration', angularAMD.route({
                    templateUrl: 'app/views/registration.html',
                    controller: 'RegistrationCtrl',
                    controllerUrl: 'controllers/registration'
                }))
                .when('/login', angularAMD.route({
                    templateUrl: 'app/views/login.html',
                    controller: 'LoginCtrl',
                    controllerUrl: 'controllers/login'
                }))
                .when('/search', angularAMD.route({
                    templateUrl: 'app/views/search.html',
                    controller: 'SearchCtrl',
                    controllerUrl: 'controllers/search'
                }))
                .otherwise({
                    redirectTo: '/'
                });

            //Commented until solve problems with one page routing without hash
            //$locationProvider.html5Mode(true);
        });

        return angularAMD.bootstrap(app);
    }
);