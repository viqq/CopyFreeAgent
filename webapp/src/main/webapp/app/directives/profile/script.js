/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',

        'directives/datepicker/script',
        'services/check-user-state',
        'services/upload-user-pic'
    ],
    function (angularAMD) {
        var controller = [
            '$scope',
            '$element',
            '$location',
            'checkUserState',
            'uploadUserPic',
            function ($scope, $element, $location, checkUserState, uploadUserPic) {
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