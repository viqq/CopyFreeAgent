/**
 * Created by TITUS on 27.08.2015.
 */
define(
    [
        'angularAMD',

        'services/registration',
        'services/login',

        'directives/field-text/script'
    ],
    function (angularAMD, uiTranslations) {
        var controller = ['$scope', '$location', 'registration', 'login', function ($scope, $location, registration, login) {
            if ($scope.$root.isLoggedIn) {
                $location.path('/');
                return;
            }

            //$scope.uiTranslations = uiTranslations[$scope.language].registration;

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


        angularAMD.controller('RegistrationCtrl', controller);
    }
);