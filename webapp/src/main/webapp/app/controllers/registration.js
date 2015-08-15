/**
 * Created by TITUS on 30.07.2015.
 */
/**
 * Created by TITUS on 29.07.2015.
 */
/**
 * Created by TITUS on 29.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/uiTranslations',
        'resources/jsObjToParamStr',

        'services/registration'
    ],
    function (angularAMD, uiTranslations, jsObjToParamStr) {
        var loginCtrl = function ($scope, registration) {
            $scope.uiTranslations = uiTranslations[$scope.language].registration;

            $scope.registrationData = {};

            $scope.registrationHandler = function() {
                registration(jsObjToParamStr($scope.registrationData))
                    .success(function(data) {
                        alert(JSON.stringify(data));
                    })
                    .error(function(err) {
                        throw err;
                    })
            }
        };

        angularAMD.controller('RegistrationCtrl', ['$scope', 'registration', loginCtrl]);
    }
);