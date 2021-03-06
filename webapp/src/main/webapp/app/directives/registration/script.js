/**
 * Created by TITUS on 30.07.2015.
 */
define([
        'angularAMD',
        'resources/ui-translations',

        'services/registration',
        'services/login'
    ],
    function (angularAMD, uiTranslations) {
        angularAMD.directive('dirRegistration', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/registration/template.html',
                replace: true,
                scope: true,
                controller: controller
            };
        });

        var controller = ['$scope', '$location', 'registration', 'login', function ($scope, $location, registration, login) {
            if ($scope.$root.isLoggedIn) {
                $location.path('/');
                return;
            }

            $scope.uiTranslations = uiTranslations[$scope.language].registration;

            $scope.formData = {};

            $scope.error = '';

            $scope.registrationHandler = function() {
                var data = $scope.$root.toolkit.serialize($scope.formData);
                $scope.error = '';

                registration(data)
                    .success(function(data) {
                        if (typeof data !== 'object') {
                            $scope.error = 'Something wrong with response';
                            return;
                        }

                        if (data.error === true) {
                            $scope.error = 'Registration error. Code: ' + data.status;
                            return;
                        }

                        $scope.loginAfterReg();
                    })
                    .error(function(err) {
                        $scope.error = 'Request failed';
                    })
            };

            $scope.loginAfterReg = function() {
                var data = $scope.$root.toolkit.serialize({
                    'j_username': $scope.formData.email,
                    'j_password': $scope.formData.password,
                    'submit': 'Login'
                });

                login(data)
                    .then(function(data) {

                        if (typeof data !== 'object') {
                            console.error('login: something wrong with response');
                            return;
                        }

                        if (data.data.error === true || data.data.status) {
                            console.error('login after reg: request error code' , data.data.status);
                            return;
                        }

                        return $scope.$root.updateUserInfo();
                    })
                    .then(function() {
                        $location.path('/profile');
                    }, function(err) {
                        throw err;
                    });
            };
        }];
    }
);

