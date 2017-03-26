/**
 * Created by TITUS on 28.07.2015.
 */
define(
    [
        'angularAMD',
        'angular-route',
        'angular-animate',
        'angular-locale_en-us',
        'angular-locale_ru-ru',
        'angular-locale_uk-ua',

        'jquery',

        'controllers/main',

        'shared/shared.module'
    ],
    function (angularAMD) {
        var app = angular.module('app', ['ngRoute','ngAnimate', 'ngLocale', 'shared']);

        var pagesUrl = 'app/pages/';

        app.config([
            '$routeProvider',
            '$locationProvider',
            function ($routeProvider, $locationProvider) {
                $routeProvider
                    .when('/', angularAMD.route({
                        templateUrl: pagesUrl + 'home/home.html',
                        controller: 'HomeCtrl',
                        controllerUrl: pagesUrl + 'home/home.js'
                    }))
                    .when('/registration', angularAMD.route({
                        templateUrl: pagesUrl + 'registration/registration.html',
                        controller: 'RegistrationCtrl',
                        controllerUrl: pagesUrl + 'registration/registration.js'
                    }))
                    .when('/reset-password', angularAMD.route({
                        templateUrl: 'app/views/password-reset.html',
                        controller: 'PasswordResetCtrl',
                        controllerUrl: 'controllers/password-reset'
                    }))
                    .when('/login', angularAMD.route({
                        templateUrl: pagesUrl + 'login/login.html',
                        controller: 'LoginCtrl',
                        controllerUrl: pagesUrl + 'login/login.js'
                    }))
                    .when('/profile', angularAMD.route({
                        templateUrl: pagesUrl + 'profile/profile.html',
                        controller: 'ProfileCtrl',
                        controllerUrl: pagesUrl + 'profile/profile.js'
                    }))
                    .when('/edit', angularAMD.route({
                        templateUrl: 'app/views/edit.html',
                        controller: 'ProfileEditCtrl',
                        controllerUrl: 'controllers/user-edit'
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
                    .when('/message/send/:id', angularAMD.route({
                        templateUrl: 'app/views/message-send.html',
                        controller: 'MessageSendCtrl',
                        controllerUrl: 'controllers/message-send'
                    }))
                    .when('/message/read', angularAMD.route({
                        templateUrl: 'app/views/message-read.html',
                        controller: 'MessageReadCtrl',
                        controllerUrl: 'controllers/message-read'
                    }))
                    .when('/admin', angularAMD.route({
                        templateUrl: 'app/views/admin.html',
                        controller: 'AdminCtrl',
                        controllerUrl: 'controllers/admin'
                    }))
                    .when('/dialogs', angularAMD.route({
                        templateUrl: 'app/views/dialogs-all.html',
                        controller: 'AllDialogsCtrl',
                        controllerUrl: 'controllers/dialogs-all'
                    }))
                    .when('/dialogs/:id', angularAMD.route({
                        templateUrl: 'app/views/dialog-by-id.html',
                        controller: 'DialogByIdCtrl',
                        controllerUrl: 'controllers/dialog-by-id'
                    }))
                    .otherwise({
                        redirectTo: '/'
                    });

                $locationProvider.html5Mode(true);
            }]);

        return angularAMD.bootstrap(app);
    }
);