/**
 * Created by TITUS on 29.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/uiTranslations',
        'resources/jsObjToParamStr',

        'services/login'
    ],
    function (angularAMD, uiTranslations, jsObjToParamStr) {
        var loginCtrl = function ($scope, login) {
            $scope.uiTranslations = uiTranslations[$scope.language].login;

            $scope.loginData = {};

            $scope.loginHandler = function() {
                login(jsObjToParamStr($scope.loginData))
                    .success(function(data) {
                        $scope.isLoggedIn = true;
                        alert(JSON.stringify(data));
                    })
                    .error(function(err) {
                        throw err;
                    })
            }
        };

        angularAMD.controller('LoginCtrl', ['$scope', 'login', loginCtrl]);
    }
);