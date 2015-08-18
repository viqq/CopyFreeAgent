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
        'resources/ui-translations',
        'resources/js-obj-to-param-str',

        'services/registration'
    ],
    function (angularAMD, uiTranslations, jsObjToParamStr) {
        var registrationCtrl = function ($scope, registration) {
            if ($scope.$root.isLoggedIn) {
                location.assign('#/');
                return;
            }

            $scope.uiTranslations = uiTranslations[$scope.language].registration;

            $scope.registrationData = {};

            $scope.registrationHandler = function() {
                registration(jsObjToParamStr($scope.registrationData))
                    .success(function(data) {
                        if (typeof data !== 'object') {
                            console.error('registration: something wrong with response');
                            return;
                        }

                        if (data.error === true) {
                            console.error('registration: request error code' , data.code);
                            return;
                        }

                        location.assign('#/login');
                    })
                    .error(function(err) {
                        console.error('registration: request failed', err);
                    })
            }
        };

        angularAMD.controller('RegistrationCtrl', ['$scope', 'registration', registrationCtrl]);
    }
);