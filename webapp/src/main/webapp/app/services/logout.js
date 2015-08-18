/**
 * Created by TITUS on 18.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var logout = function ($http) {
        return function () {
            return $http({
                method: 'GET',
                url: 'j_spring_security_logout'
            })
                .success(function () {
                })
                .error(function () {
                });
        }
    };

    angularAMD.factory('logout', ['$http', logout]);
});