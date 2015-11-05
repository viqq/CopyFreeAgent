/**
 * Created by TITUS on 15.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var registration = function ($http) {
        return function (data) {
            return $http({
                method: 'POST',
                url: '/api/user',
                data: data,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                }
            })
                .success(function(data) {
                    console.log(data)
                })
                .error(function(err) {
                    console.log(err);
                });
        }
    };

    angularAMD.factory('registration', ['$http', registration]);
});