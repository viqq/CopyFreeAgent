/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/toolkit',
        'resources/ui-translations',

        'services/get-user-info',
        '/app/services/check-user-state.js'
    ],
    function (angularAMD, toolkit, uiTranslations) {
        var mainCtrl = function ($scope, $q, getUserInfo, checkUserState) {
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

            var updUserInfErrHndlr = function () {
                $scope.$root.isLoggedIn = false;
                $scope.$root.currUserData = {};
            };

            window.$q = $q;

            $scope.$root.updateUserInfo = function () {
                return $q(function (resolve, reject) {
                    checkUserState().then(function (data) {
                        if (!data || !data.data || data.data.error || !data.data.payload) {
                            reject(data);
                            return;
                        }

                        return getUserInfo();
                    }).then(function (data) {
                        resolve(data);
                    }, function (err) {
                        reject(err);
                    });
                }).then(function (res) {
                    if (!res || !res.data || !res.data.payload) {
                        updUserInfErrHndlr();
                        return;
                    }

                    $scope.$root.isLoggedIn = true;
                    $scope.$root.currUserData = res.data.payload;
                    $scope.$root.reloadUserPics();
                }, function () {
                    updUserInfErrHndlr();
                });

                //return checkUserState()
                //    .then(function(res) {
                //        if (!res || !res.data || res.data.error || !res.data.payload) {
                //            updUserInfErrHndlr();
                //            return;
                //        }
                //
                //        $scope.$root.isLoggedIn = true;
                //
                //        return getUserInfo();
                //    })
                //    .then(function (res) {
                //        if (!res || !res.data || !res.data.payload) {
                //            updUserInfErrHndlr();
                //            return;
                //        }
                //
                //        $scope.$root.isLoggedIn = true;
                //        $scope.$root.currUserData = res.data.payload;
                //        $scope.$root.reloadUserPics();
                //    }, function () {
                //        updUserInfErrHndlr();
                //    });
            };

            $scope.$root.updateUserInfo();
        };

        angularAMD.controller('MainCtrl', ['$scope', '$q', 'getUserInfo', 'checkUserState', mainCtrl]);
    }
);