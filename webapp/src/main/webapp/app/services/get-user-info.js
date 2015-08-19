/**
 * Created by TITUS on 18.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var getUserInfo = function ($http) {
        return function () {
            return $http({
                method: 'GET',
                url: 'user/info'
            })
                .success(function(data) {
                    console.log(data)
                })
                .error(function(err) {
                    console.log(err);
                });
        }
    };


    angularAMD.factory('getUserInfo', ['$http', getUserInfo]);
});