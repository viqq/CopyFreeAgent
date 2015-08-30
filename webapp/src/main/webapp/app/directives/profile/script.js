/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',

        'services/get-user-info'
    ],
    function (angularAMD) {
        var dirProfile = function() {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/profile/template.html',
                replace: true,
                scope: true,
                controller: ['$scope', 'getUserInfo', function ($scope, getUserInfo) {
                    $scope.userInfo = {};

                    getUserInfo()
                        .success(function(data) {
                            if (typeof data !== 'object') {
                                console.error('user info: something wrong with response');
                                location.assign('#/login');
                                return;
                            }

                            if (data.error === true) {
                                console.error('user info: request error code' , data.code);
                                location.assign('#/login');
                                return;
                            }

                            if (typeof data.payload !== 'object') {
                                console.error('user info: payload is not object');
                                location.assign('#/login');
                                return;
                            }

                            $scope.userInfo = data.payload;
                        })
                        .error(function(err) {
                            console.error('user info: request failed', err);
                        });
                }]
            };
        };

        angularAMD.directive('dirProfile', dirProfile);
    }
);