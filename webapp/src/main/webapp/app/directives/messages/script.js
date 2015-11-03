/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',

        'services/message-send'
    ],
    function (angularAMD, uiTranslations) {
        var controller = [
            '$scope',
            '$routeParams',
            'sendMessage',
            function ($scope, $routeParams, sendMessage) {
                $scope.msgData = {
                    id: $routeParams.id,
                    email: '',
                    title: '',
                    text: ''
                };

                $scope.error = '';

                $scope.submitHandler = function () {
                    var data = $scope.$root.toolkit.serialize($scope.msgData);
                    $scope.error = '';
                    console.log(data);

                    sendMessage(data).then(function (data) {
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
                    }, function (err) {
                        $scope.error = 'Request error';
                        throw err;
                    })
                }
            }
        ];

        angularAMD.directive('dirMessageSend', ['sendMessage', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/message-send/template.html',
                replace: true,
                scope: true,
                controller: controller
            };
        }]);
    }
);