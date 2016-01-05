/**
 * Created by TITUS on 29.12.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var reset = function ($http) {
        return function (data) {
            var req = $http({
                method: 'POST',
                url: '/api/password',
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

    angularAMD.factory('passwordReset', ['$http', reset]);
});