/**
 * Created by TITUS on 05.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var login = function ($http) {
        return function (data) {
            var req = $http({
                method: 'POST',
                url: '/login',
                data: data,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });

            req.success(function (data) {
                console.log(data)
            }).error(function (err) {
                console.log(err);
            });

            return req;
        }
    };

    angularAMD.factory('login', ['$http', login]);
});