/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',
        'resources/js-obj-to-param-str',

        'services/login'
    ],
    function (angularAMD, uiTranslations, jsObjToParamStr) {
        angularAMD.directive('dirLogin', ['login', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/login/template.html',
                replace: true,
                scope: true,
                controller: ['$scope', 'login', function ($scope, login) {
                    if ($scope.$root.isLoggedIn) {
                        location.assign('#/');
                        return;
                    }

                    $scope.uiTranslations = uiTranslations[$scope.language].login;

                    $scope.loginData = {};

                    $scope.loginHandler = function() {
                        login(jsObjToParamStr($scope.loginData))
                            .success(function(data) {

                                if (typeof data !== 'object') {
                                    console.error('login: something wrong with response');
                                    return;
                                }

                                if (data.error === true) {
                                    console.error('login: request error code' , data.code);
                                    return;
                                }

                                $scope.$root.isLoggedIn = true;
                                location.assign('#/profile');
                            })
                            .error(function(err) {
                                throw err;
                            })
                    }
                }]
            };
        }]);
    }
);