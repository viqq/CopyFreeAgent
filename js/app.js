/**
 * Created by TITUS on 28.07.2015.
 */
define(['angularAMD', 'angular-route'], function (angularAMD) {
    var app = angular.module('webapp', ['ngRoute']);

    app.config(function ($routeProvider, $locationProvider) {
        $routeProvider
            .when('/home', angularAMD.route({
                templateUrl: 'templates/view_home.html',
                controller: 'HomeCtrl',
                controllerUrl: 'ctrls/controller_home'
            }))
            .when('/view1', angularAMD.route({
                templateUrl: 'templates/view_view1.html',
                controller: 'View1Ctrl',
                controllerUrl: 'ctrls/controller_view1'
            }))
            .otherwise({
                redirectTo: '/home'
            });

        //Commented untill solve problems with one page routing without hash
        //$locationProvider.html5Mode(true);
    });


    return angularAMD.bootstrap(app);
});