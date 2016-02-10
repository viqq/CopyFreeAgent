/**
 * Created by TITUS on 27.08.2015.
 */
define(
    [
        'angularAMD',

        'directives/header/script',
        'directives/profile/script',
        'directives/footer/script'
    ],
    function (angularAMD) {
        var controller = [
            '$scope',
            '$location',
            'checkUserState',
            'uploadUserPic',
            function ($scope, $location, checkUserState, uploadUserPic) {
                var executors = {
                    'initImages' : function() {
                        //$scope.imageInput = $element.find('#userpic input[type="file"]');

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
                            $location.path('/login');
                            return;
                        }

                        $scope.userInfo = $scope.$root.currUserInfo;
                    }
                };

                angular.forEach(executors, function(value, key) {
                    value();
                });

                angular.extend($scope, {
                    claimsEdited: false,
                    datePickersConfig: {
                        label: 'Число',
                        isMonFirst: true,
                        editable: false
                    },
                    editClaims: function() {
                        $scope.claimsEdited = !$scope.claimsEdited;
                        $scope.datePickersConfig.editable = $scope.claimsEdited;
                    }
                });

                //$scope.datePickerData = {
                //    label: 'Число',
                //    pickedDates: [1452204000000],
                //    pickedWeekDays: [1, 2],
                //    isMonFirst: true,
                //    editable: true
                //}
            }
        ];

        angularAMD.controller('ProfileCtrl', controller);
    }
);