/**
 * Created by Denis on 3/10/2017.
 */
define(
    [
        'shared/services/user.service',
        'shared/services/sport.service',
        'shared/services/authentication.service',
        'shared/directives/login/login.directive',
        'shared/services/ui-translations.service'
    ], function (UserService, SportService, AuthenticationService, LogInDirective, uiTranslations) {

        return window.angular.module('shared', [])
            .factory('UserService', UserService)
            .factory('SportService', SportService)
            .factory('AuthenticationService', AuthenticationService)
            .directive('dirLogin', LogInDirective)
            .factory('uiTranslations', uiTranslations);
    }
);
