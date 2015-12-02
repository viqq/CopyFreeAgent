/**
 * Created by TITUS on 27.08.2015.
 */
define(
    [
        'angularAMD',

        'services/registration',
        'services/login',

        'directives/form-field/script',

        'css!/css/registration.css',
        'css!/css/form-primary.css'
    ],
    function (angularAMD) {
        var controller = [
            '$scope',
            '$location',
            'registration',
            'login',
            function ($scope, $location, registration, login) {
                if ($scope.$root.isLoggedIn) {
                    $location.path('/');
                    return;
                }

                var validateForm = function() {
                    $scope.validationResults = [];
                    $scope.$emit('validate-form');
                    return $scope.validationResults.indexOf('false') === -1;
                };

                angular.extend($scope, {
                    form: {},
                    error: '',
                    validationResults: [],
                    passwordConfirm: '',
                    passwordsMatch: false,
                    confPassValidation: function () {
                        return $scope.form.password === $scope.passwordConfirm;
                    },
                    registrationHandler: function () {
                        if (!validateForm()) {
                            return;
                        }

                        var data = $scope.$root.toolkit.serialize($scope.form);
                        $scope.error = '';

                        registration(data).success(function (data) {
                            if (typeof data !== 'object') {
                                $scope.error = 'Something wrong with response';
                                return;
                            }

                            if (data.error === true) {
                                $scope.error = 'Registration error. Code: ' + data.status;
                                return;
                            }

                            $scope.loginAfterReg();
                        }).error(function (err) {
                            $scope.error = 'Request failed';
                        })
                    },
                    loginAfterReg: function () {
                        var data = $scope.$root.toolkit.serialize({
                            'j_username': $scope.form.email,
                            'j_password': $scope.form.password,
                            'submit': 'Login'
                        });

                        login(data).then(function (data) {

                            if (typeof data !== 'object') {
                                console.error('login: something wrong with response');
                                return;
                            }

                            if (data.data.error === true || data.data.status) {
                                console.error('login after reg: request error code', data.data.status);
                                return;
                            }

                            return $scope.$root.updateUserInfo();
                        }).then(function () {
                            $location.path('/profile');
                        }, function (err) {
                            throw err;
                        });
                    }
                });
            }
        ];


        angularAMD.controller('RegistrationCtrl', controller);
    }
);