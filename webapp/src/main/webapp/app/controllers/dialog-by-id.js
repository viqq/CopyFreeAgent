/**
 * Created by TITUS on 15.09.2015.
 */
define(
    [
        'angularAMD',

        'directives/header/script',
        'directives/footer/script',

        'services/get-user-info-by-id',
        'services/message-dialog-get-by-id',
        'services/message-send'
    ],
    function (angularAMD) {
        angularAMD.controller('DialogByIdCtrl', [
            '$scope',
            '$routeParams',
            'getUserInfoById',
            'getDialogById',
            'sendMessage',
            function ($scope,
                      $routeParams,
                      getUserInfoById,
                      getDialogById,
                      sendMessage) {

                if (!$scope.isLoggedIn) {
                    $location.path('/login');
                    return;
                }

                $scope.messages = [];

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

                        updateDialog();
                    }, function (err) {
                        $scope.error = 'Request error';
                        throw err;
                    })
                };

                var updateDialog = function() {
                    var req = getDialogById($routeParams.id);

                    req.success(function(data) {
                        $scope.messages = data.payload;
                    });

                    return req;
                };

                updateDialog();
            }
        ]);
    }
);