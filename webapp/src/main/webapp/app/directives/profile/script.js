/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',

        'services/check-user-state',
        'services/upload-user-pic'
    ],
    function (angularAMD) {
        var controller = [
            '$scope',
            '$element',
            'checkUserState',
            'uploadUserPic',
            function ($scope, $element, checkUserState, uploadUserPic) {
                var executors = {
                    'initImages' : function() {
                        console.log( $element.find('input'));
                        $scope.imageInput = $element.find('#userpic input[type="file"]');

                        $scope.uploadImage = function () {
                            var data = $scope.imageInput[0].files[0];

                            if (!data) {
                                return;
                            }

                            uploadUserPic(data)
                        };
                    },
                    'initUserInfo' : function() {
                        $scope.userInfo = {};

                        if (!$scope.isLoggedIn) {
                            location.assign('/login');
                            return;
                        }

                        $scope.userInfo = $scope.$root.currUserData;
                    }
                };

                angular.forEach(executors, function(value, key) {
                    value();
                })
            }
        ];

        var dirProfile = function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/profile/template.html',
                replace: true,
                scope: true,
                controller: controller
            };
        };

        angularAMD.directive('dirProfile', dirProfile);
    }
);