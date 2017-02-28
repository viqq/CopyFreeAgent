/**
 * Created by TITUS on 27.08.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',

        'directives/header/script',
        'directives/form-field/script',

        'services/login',

        'css!/css/login.css',
        'css!/css/form-primary.css'
    ],
    function (angularAMD, uiTranslations) {
        var controller =  [
            '$scope',
            '$location',
            'login',
            function ($scope, $location, login) {
                if ($scope.$root.isLoggedIn) {
                    $location.path('/');
                    return;
                }

                angular.extend($scope, {
                    fields: {
                        email: {
                            value: '',
                            label: 'Email',
                            pattern: '^[-a-z0-9!#$%&\'*+/=?^_`{|}~]+(?:\\.[-a-z0-9!#$%&\'*+/=?^_`{|}~]+)*@(?:[a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\\.)*(?:aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z][a-z])$',
                            clientError: 'Цифры и буквы, длина 3-15 знаков.',
                            serverErrors: {}
                        },
                        password: {
                            value: '',
                            label: 'Password',
                            type: 'password',
                            pattern: '((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})',
                            clientError: 'Цифры и буквы, длина 6-20 знаков, хоть одна цифра и заглавная буква.',
                            serverErrors: {}
                        }
                    },
                    error: '',
                    loginHandler: function () {
                        var data = $scope.$root.toolkit.serialize({
                            'username': $scope.fields.email.value,
                            'password': $scope.fields.password.value,
                            'submit': 'Login'
                        });

                        console.log(data);

                        login(data)
                            .then(function (data) {
                                debugger;
                                if (typeof data !== 'object') {
                                    $scope.error = 'Something went wrong';
                                    console.error('login: something wrong with response');
                                    return;
                                }

                                if (data.error === true) {
                                    $scope.error = 'Login error. Code: ' + data.payload;
                                    console.error('login: request error code', data.payload);
                                    return;
                                }

                                return $scope.$root.updateUserInfo();
                            })
                            .then(function() {
                                $location.path('/profile');
                            }, function (err) {
                                throw err;
                            });
                    },
                    uiTranslations: uiTranslations[$scope.language].login
                });
            }
        ];

        angularAMD.controller('LoginCtrl', controller);
    }
);