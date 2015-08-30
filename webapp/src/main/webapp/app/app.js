/**
 * Created by TITUS on 28.07.2015.
 */
define(
    [
        'angularAMD',
        'angular-route',

        'controllers/main'
    ],
    function (angularAMD) {
        var app = angular.module('app', ['ngRoute']);

        app.config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
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
                .when('/profile', angularAMD.route({
                    templateUrl: 'app/views/profile.html',
                    controller: 'ProfileCtrl',
                    controllerUrl: 'controllers/profile'
                }))
                .when('/search', angularAMD.route({
                    templateUrl: 'app/views/search-users.html',
                    controller: 'SearchUsersCtrl',
                    controllerUrl: 'controllers/search-users'
                }))
                .when('/user/:id', angularAMD.route({
                    templateUrl: 'app/views/user.html',
                    controller: 'UserCtrl',
                    controllerUrl: 'controllers/user'
                }))
                .otherwise({
                    redirectTo: '/'
                });

            // TODO Solve problems with one page routing without hash
            //$locationProvider.html5Mode(true);
        }]);

        return angularAMD.bootstrap(app);
    }
);