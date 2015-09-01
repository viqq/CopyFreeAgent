/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',

        'services/login'
    ],
    function (angularAMD, uiTranslations) {
        angularAMD.directive('dirLogin', ['login', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/login/template.html',
                replace: true,
                scope: true,
                controller: ['$scope', '$element', 'login', function ($scope, $element, login) {
                    if ($scope.$root.isLoggedIn) {
                        location.assign('#/');
                        return;
                    }

                    $scope.uiTranslations = uiTranslations[$scope.language].login;

                    $scope.form = $element.find('form');

                    $scope.loginData = {
                        j_username: '',
                        j_password: '',
                        submit: 'Login'
                    };

                    $scope.error = '';

                    $scope.loginHandler = function () {
                        var data = $scope.$root.toolkit.serialize($scope.loginData);
                        console.log(data);

                        login(data)
                            .success(function (data) {

                                if (typeof data !== 'object') {
                                    $scope.error = 'Something went wrong';
                                    console.error('login: something wrong with response');
                                    return;
                                }

                                if (data.error === true || data.code) {
                                    $scope.error = 'Login error. Code: ' + data.code;
                                    console.error('login: request error code', data.code);
                                    return;
                                }

                                $scope.$root.isLoggedIn = true;
                                $scope.$root.updateUserInfo();
                                location.assign('#/profile');
                            })
                            .error(function (err) {
                                throw err;
                            })
                    }
                }]
            };
        }]);
    }
);