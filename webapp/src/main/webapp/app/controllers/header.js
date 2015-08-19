/**
 * Created by TITUS on 19.08.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',

        'services/logout'
    ],
    function (angularAMD, uiTranslations) {
        var HeaderCtrl = function ($scope, logout) {
            $scope.uiTranslations = uiTranslations[$scope.language].header;

            $scope.loginData = {};

            $scope.logoutHandler = function() {
                logout()
                    .success(function(data) {
                        $scope.$root.isLoggedIn = false;
                        location.assign('#/');
                    })
                    .error(function(err) {
                        console.error('logout: requsest failed', err);
                    })
            };
        };

        angularAMD.controller('HeaderCtrl', ['$scope', 'logout', HeaderCtrl]);
    }
);