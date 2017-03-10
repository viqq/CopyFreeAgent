/**
 * Created by Denis on 3/10/2017.
 */
define(
    [
        'shared/services/user.service',
        'shared/services/sport.service'
    ], function (UserService, SportService) {
        console.log(arguments);
        return angular.module('shared', [])
                .factory('UserService', UserService)
                .factory('SportService', SportService);
    }
);
