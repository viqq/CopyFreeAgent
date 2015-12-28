/**
 * Created by TITUS on 27.08.2015.
 */
define(
    [
        'angularAMD',

        'services/registration',
        'services/login',

        'directives/header/script',
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

                var validateForm = function () {
                    $scope.validationResults = [];
                    $scope.$emit('validate-form');
                    return $scope.validationResults.indexOf('false') === -1;
                };

                var loginAftReg = function () {
                    var data = $scope.$root.toolkit.serialize({
                        'j_username': $scope.fields.email.value,
                        'j_password': $scope.fields.password.value,
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
                };

                angular.extend($scope, {
                    fields: {
                        name: {
                            value: '',
                            label: 'Name',
                            hint: '',
                            pattern: '^([a-z0-9_-]){3,15}$',
                            errorClient: 'Цифры и буквы, длина 3-15 знаков.',
                            serverError: ''
                        },
                        email: {
                            value: '',
                            label: 'Email',
                            pattern: '^[-a-z0-9!#$%&\'*+/=?^_`{|}~]+(?:\\.[-a-z0-9!#$%&\'*+/=?^_`{|}~]+)*@(?:[a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\\.)*(?:aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z][a-z])$',
                            errorClient: 'Некорректный формат адреса.',
                            serverError: ''
                        },
                        password: {
                            value: '',
                            label: 'Password',
                            type: 'password',
                            pattern: '((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})',
                            errorClient: 'Цифры и буквы, длина 6-20 знаков, хоть одна цифра и заглавная буква.',
                            serverError: ''
                        },
                        confirmPassword: {
                            value: '',
                            label: 'Password confirm',
                            type: 'password',
                            pattern: '',
                            errorClient: 'Пароли должны совпадать.',
                            serverError: '',
                            validation: function () {
                                return $scope.fields.password.value === $scope.fields.confirmPassword.value
                                    && $scope.fields.confirmPassword.value;
                            }
                        }
                    },
                    error: '',
                    validationResults: [],
                    sumbitForm: function () {
                        if (!validateForm()) {
                            return;
                        }

                        var data = $scope.$root.toolkit.serialize({
                            firstName: $scope.fields.name.value,
                            email: $scope.fields.email.value,
                            password: $scope.fields.password.value
                        });

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

                            loginAftReg();
                        }).error(function (err) {
                            $scope.error = 'Request failed';
                        })
                    }
                });
            }
        ];


        angularAMD.controller('RegistrationCtrl', controller);
    }
);