/**
 * Created by TITUS on 15.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var registration = function ($http) {
        return function (data) {
            return $http({
                method: 'POST',
                url: 'user',
                data: data,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            })
                .success(successRegistration)
                .error(errorRegistration);
        }
    };

    var successRegistration = function (data) {
        console.log(data)
    };

    var errorRegistration = function (err) {
        console.log(err)
    };

    angularAMD.factory('registration', ['$http', registration]);
});