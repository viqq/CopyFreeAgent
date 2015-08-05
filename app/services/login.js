/**
 * Created by TITUS on 05.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var login = function ($http) {
        return function () {
            return $http.get('j_spring_security_check')
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