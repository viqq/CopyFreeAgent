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

                        var initSuccess = function() {
                            $scope.userInfo = $scope.$root.currUserData;
                        };

                        var initError = function () {
                            $scope.isLoggedIn = false;
                            $scope.userInfo = {};
                            location.assign('/login');
                        };

                        $scope.$root.updateUserInfo()
                            .success(function() {
                                initSuccess();
                            }).error(function() {
                                initError();
                            });

                        /* TODO will work when isAuthorised is fixed
                        checkUserState()
                            .success(function (data) {
                                if (!data || data.error || !data.payload) {
                                    initError();
                                    return;
                                }

                                $scope.$root.isLoggedIn = true;

                                if ($scope.$root.currUserData) {
                                    initSuccess()
                                } else {
                                    $scope.$root.updateUserInfo()
                                        .success(function() {
                                            initSuccess();
                                        }).error(function() {
                                            initError();
                                        })
                                }
                            })
                            .error(function (err) {
                                initError();
                            });
                        */
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