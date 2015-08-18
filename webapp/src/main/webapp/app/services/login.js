/**
 * Created by TITUS on 05.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var login = function ($http) {
        return function (data) {
            return $http({
                method: 'POST',
                url: 'j_spring_security_check',
                data: data,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            })
                .success(function() {
                })
                .error(function() {
                });
        }
    };

    angularAMD.factory('login', ['$http', login]);
});