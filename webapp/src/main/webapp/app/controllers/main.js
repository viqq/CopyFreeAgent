/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/toolkit',
        'resources/ui-translations',

        'services/get-user-info'
    ],
    function (angularAMD, toolkit, uiTranslations) {
        var mainCtrl = function ($scope, getUserInfo) {
            $scope.$root.language = 'en';
            $scope.$root.toolkit = toolkit;

            $scope.$root.isLoggedIn = false;

            $scope.$root.currUserData = {};

            $scope.$root.userPicBaseUrl = '/api/user/getImage/';
            $scope.$root.userPicUrl = '';
            $scope.$root.reloadUserPics = function () {
                var currUserId = $scope.$root.currUserData.id || '';
                $scope.$root.userPicUrl = $scope.$root.userPicBaseUrl +
                currUserId +
                '?_ts=' + new Date().getTime();
            };

            var updUserInfErrHndlr = function() {
                $scope.$root.isLoggedIn = false;
                $scope.$root.currUserData = {};
            };

            $scope.$root.updateUserInfo = function() {
                return getUserInfo()
                    .success(function (data) {
                        if (!data || !data.payload) {
                            updUserInfErrHndlr();
                            return;
                        }

                        $scope.$root.isLoggedIn = true;
                        $scope.$root.currUserData = data.payload;
                        $scope.$root.reloadUserPics();
                    }).error(function() {
                        updUserInfErrHndlr();
                    });
            };

            $scope.$root.updateUserInfo();
        };

        angularAMD.controller('MainCtrl', ['$scope','getUserInfo', mainCtrl]);
    }
);