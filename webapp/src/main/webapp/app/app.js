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
        'directives/account/script',
        'directives/search-users/script',

        '../app/controllers/main'
    ],
    function (angularAMD) {
        var app = angular.module('app', ['ngRoute']);

        app.config(function ($routeProvider, $locationProvider) {
            $routeProvider
                .when('/', angularAMD.route({
                    templateUrl: 'app/views/home.html'
                }))
                .when('/registration', angularAMD.route({
                    templateUrl: 'app/views/registration.html'
                }))
                .when('/login', angularAMD.route({
                    templateUrl: 'app/views/login.html'
                }))
                .when('/profile', angularAMD.route({
                    templateUrl: 'app/views/account.html'
                }))
                .when('/search', angularAMD.route({
                    templateUrl: 'app/views/search-users.html'
                }))
                .otherwise({
                    redirectTo: '/'
                });

            // TODO Solve problems with one page routing without hash
            //$locationProvider.html5Mode(true);
        });

        return angularAMD.bootstrap(app);
    }
);