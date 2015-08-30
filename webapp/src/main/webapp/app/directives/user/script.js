/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',

        'services/get-user-info-by-id'
    ],
    function (angularAMD) {
        var dirProfile = function() {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/profile/template.html',
                replace: true,
                scope: true,
                controller: ['$scope', '$routeParams', 'getUserInfoById', function ($scope, $routeParams, getUserInfo) {
                    $scope.userInfo = {};

                    getUserInfo($routeParams.id)
                        .success(function(data) {
                            $scope.userInfo = data.payload;
                        })
                        .error(function(err) {
                            console.error('user info: request failed', err);
                        });
                }]
            };
        };

        angularAMD.directive('dirUser', dirProfile);
    }
);