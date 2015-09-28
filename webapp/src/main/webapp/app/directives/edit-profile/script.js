/**
 * Created by TITUS on 28.09.2015.
 */
define([
        'angularAMD',
        'resources/ui-translations',

        'services/edit-user-info'
    ],
    function (angularAMD, uiTranslations) {
        angularAMD.directive('dirRegistration', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/edit-profile/template.html',
                replace: true,
                scope: true,
                controller: controller
            };
        });

        var controller = ['$scope', 'editUserInfo', function ($scope, editUserInfo) {
            if ($scope.$root.isLoggedIn) {
                location.assign('#/');
                return;
            }

            $scope.uiTranslations = uiTranslations[$scope.language].registration;

            $scope.registrationData = {};

            $scope.error = '';

            $scope.registrationHandler = function() {
                var data = $scope.$root.toolkit.serialize($scope.registrationData);
                $scope.error = '';

                editUserInfo(data, $scope.currUserData.id)
                    .success(function(data) {
                        if (typeof data !== 'object') {
                            $scope.error = 'Something wrong with response';
                            return;
                        }

                        if (data.error === true) {
                            $scope.error = 'User data edit error. Code: ' + data.status;
                            return;
                        }

                        location.assign('#/profile')
                    })
                    .error(function(err) {
                        $scope.error = 'Request failed';
                    })
            };
        }];
    }
);

