/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',

        'controllers/search-users'
    ],
    function (angularAMD, uiTranslations) {
        var searchUsers = function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/search-users/template.html',
                replace: true,
                scope: true,
                controller: 'SearchUsersCtrl'
            };
        };

        angularAMD.directive('dirSearchUsers', searchUsers);
    }
);