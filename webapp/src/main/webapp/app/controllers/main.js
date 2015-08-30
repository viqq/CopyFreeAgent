/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/toolkit',
        'resources/ui-translations',

        'services/check-user-state'
    ],
    function (angularAMD, toolkit, uiTranslations) {
        var mainCtrl = function ($scope, checkUserState) {
            $scope.$root.language = 'en';
            $scope.$root.toolkit = toolkit;

            checkUserState()
                .success(function (data) {
                    if (typeof data !== 'object') {
                        console.error('user info: something wrong with response');
                        $scope.isLoggedIn = false;
                        return;
                    }

                    if (data.error === true) {
                        console.error('user info: request error code', data.code);
                        $scope.$root.isLoggedIn = false;
                        return;
                    }

                    if (data.payload) {
                        $scope.$root.isLoggedIn = true;
                    } else {
                        $scope.$root.isLoggedIn = false;
                    }
                })
                .error(function(err) {
                    console.error('user info: request failed', err);
                });

        };

        angularAMD.controller('MainCtrl', ['$scope', 'checkUserState', mainCtrl]);
    }
);