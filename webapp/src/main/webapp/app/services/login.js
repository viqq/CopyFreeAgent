/**
 * Created by TITUS on 05.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var login = function ($http) {
        return function (data) {
            return $http.post('j_spring_security_check', data)
                .success(successLogin)
                .error(errorLogin);
        }
    };

    var successLogin = function (data) {
        console.log(data)
    };

    var errorLogin = function (err) {
        console.log(err)
    };

    angularAMD.factory('login', ['$http', login]);
});