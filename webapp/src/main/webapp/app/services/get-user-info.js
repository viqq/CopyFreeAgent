/**
 * Created by TITUS on 18.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var getUserInfo = function ($http) {
        return function () {
            var req = $http({
                method: 'GET',
                url: '/api/user/info'
            });

            req.success(function (data) {
                console.log(data)
            }).error(function (err) {
                console.log(err);
            });

            return req;
        }
    };


    angularAMD.factory('getUserInfo', ['$http', getUserInfo]);
});