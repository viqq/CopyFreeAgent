/**
 * Created by TITUS on 27.08.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',
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

                $scope.uiTranslations = uiTranslations[$scope.language].login;

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
                        .then(function (data) {

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

                            return $scope.$root.updateUserInfo();
                        })
                        .then(function() {
                            $location.path('/profile');
                        }, function (err) {
                            throw err;
                        });
                }
            }
        ];

        angularAMD.controller('LoginCtrl', controller);
    }
);