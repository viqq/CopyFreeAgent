/**
 * Created by TITUS on 07.09.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var getAllDialogs = function ($http) {
        return function () {
            var req = $http({
                method: 'GET',
                url: '/api/message/participants/'
            });

            req.success(function (data) {
                console.log(data)
            }).error(function (err) {
                console.log(err);
            });

            return req;

        }
    };

    angularAMD.factory('getAllDialogs', ['$http', getAllDialogs]);
});