/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',

        'services/get-all-messages'
    ],
    function (angularAMD, uiTranslations) {
        var controller = [
            '$scope',
            'getAllMessages',
            function ($scope, getMessages) {
                $scope.messages = [];

                $scope.error = '';

                getMessages().success(function(data) {
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

                    $scope.messages = data.payload;
                }).error(function (err) {
                    $scope.error = 'Request error';
                    throw err;
                });
            }
        ];

        angularAMD.directive('dirMessageRead', [function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/message-read/template.html',
                replace: true,
                scope: true,
                controller: controller
            };
        }]);
    }
);