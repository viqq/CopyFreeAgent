/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',

        'services/logout',

        'css!/app/directives/header/styles.css'
    ],
    function (angularAMD, uiTranslations) {
        var dirHeader = function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/header/template.html',
                replace: true,
                scope: true,
                controller: ['$scope', '$location', 'logout', function ($scope, $location, logout) {
                    $scope.uiTranslations = uiTranslations[$scope.language].header;

                    $scope.loginData = {};

                    $scope.logoutHandler = function() {
                        logout()
                            .success(function(data) {
                                $scope.$root.isLoggedIn = false;
                                $location.path('/');
                            })
                            .error(function(err) {
                                console.error('logout: request failed', err);
                            })
                    };
                }]
            };
        };

        angularAMD.directive('dirHeader', dirHeader);
    }
);