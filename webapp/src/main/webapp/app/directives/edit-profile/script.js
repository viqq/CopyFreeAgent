/**
 * Created by TITUS on 28.09.2015.
 */
define([
        'angularAMD',
        'resources/ui-translations',

        'services/edit-user-info',
        'services/user-delete'
    ],
    function (angularAMD, uiTranslations) {
        angularAMD.directive('dirEditProfile', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/edit-profile/template.html',
                replace: true,
                scope: true,
                controller: controller
            };
        });

        var controller = [
            '$scope',
            '$location',
            'editUserInfo',
            'deleteUser',
            function ($scope, $location, editUserInfo, deleteUser) {
                if (!$scope.$root.isLoggedIn) {
                    $location.path('/');
                    return;
                }

                $scope.uiTranslations = uiTranslations[$scope.language].registration;

                $scope.form = angular.copy($scope.currUserInfo);

                $scope.error = '';

                $scope.submitHandler = function () {
                    var data = $scope.$root.toolkit.serialize($scope.form);
                    $scope.error = '';

                    editUserInfo(data, $scope.currUserInfo.id)
                        .then(function (data) {
                            if (typeof data !== 'object') {
                                $scope.error = 'Something wrong with response';
                                return;
                            }

                            if (data.error === true) {
                                $scope.error = 'User data edit error. Code: ' + data.status;
                                return;
                            }

                            return $scope.$root.updateUserInfo();
                        })
                        .then(function () {
                            $location.path('/profile');
                        }, function (err) {
                            $scope.error = 'Request failed';
                        });
                };

                $scope.deleteProfile = function () {
                    deleteUser($scope.currUserInfo.id)
                        .success(function (data) {
                            if (typeof data !== 'object') {
                                $scope.error = 'Something wrong with response';
                                return;
                            }

                            if (data.error === true) {
                                $scope.error = 'User delete error. Code: ' + data.status;
                                return;
                            }

                            $location.path('/');
                        })
                        .error(function (err) {
                            $scope.error = 'Request failed';
                        })
                }
            }
        ];
    }
);

