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
                .success(successLogout)
                .error(errorLogout);
        }
    };

    var successLogout = function (data) {
        console.log(data)
    };

    var errorLogout = function (err) {
        console.log(err)
    };

    angularAMD.factory('logout', ['$http', logout]);
});