/**
 * Created by Denis on 3/10/2017.
 */
define(
    [
        'shared/services/user.service',
        'shared/services/sport.service',
        'shared/services/authentication.service'
    ], function (UserService, SportService, AuthenticationService) {

        return window.angular.module('shared', [])
            .factory('UserService', UserService)
            .factory('SportService', SportService)
            .factory('AuthenticationService', AuthenticationService);
    }
);
