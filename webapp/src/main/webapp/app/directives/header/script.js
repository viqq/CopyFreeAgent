/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',

        'services/logout'
    ],
    function (angularAMD, uiTranslations) {
        var dirHeader = function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/header/template.html',
                replace: true,
                scope: true,
                controller: ['$scope', 'logout', function ($scope, logout) {
                    $scope.uiTranslations = uiTranslations[$scope.language].header;

                    $scope.loginData = {};

                    $scope.logoutHandler = function() {
                        logout()
                            .success(function(data) {
                                $scope.$root.isLoggedIn = false;
                                location.assign('/');
                            })
                            .error(function(err) {
                                console.error('logout: requsest failed', err);
                            })
                    };
                }]
            };
        };

        angularAMD.directive('dirHeader', dirHeader);
    }
);