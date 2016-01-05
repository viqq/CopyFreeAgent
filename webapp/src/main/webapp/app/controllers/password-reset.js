/**
 * Created by TITUS on 01.12.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',

        'directives/header/script',
        'directives/form-field/script',

        'services/password-reset',

        'css!/css/form-primary.css',
        'css!/css/password-reset.css'
    ],
    function (angularAMD) {
        var controller = ['$scope', 'passwordReset', function ($scope, passwordReset) {
            angular.extend($scope, {
                fields: {
                    email: {
                        value: '',
                        label: 'Email',
                        pattern: '^[-a-z0-9!#$%&\'*+/=?^_`{|}~]+(?:\\.[-a-z0-9!#$%&\'*+/=?^_`{|}~]+)*@(?:[a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\\.)*(?:aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z][a-z])$',
                        clientError: 'Цифры и буквы, длина 3-15 знаков.',
                        serverErrors: {}
                    }
                },
                submit: function() {
                    var data = $scope.$root.toolkit.serialize({
                        'email': $scope.fields.email.value
                    });

                    console.log(data);

                    passwordReset(data)
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
                        })
                        .then(function() {


                        }, function (err) {
                            throw err;
                        });
                }
            });


        }];

        angularAMD.controller('PasswordResetCtrl', controller);
    }
);