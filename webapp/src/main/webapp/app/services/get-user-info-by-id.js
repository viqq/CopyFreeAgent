/**
 * Created by TITUS on 30.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var getUserInfo = function ($http) {
        return function (id) {
            return $http({
                method: 'GET',
                url: 'user/' + id
            })
                .success(function(data) {
                    console.log(data)
                })
                .error(function(err) {
                    console.log(err);
                });
        }
    };


    angularAMD.factory('getUserInfoById', ['$http', getUserInfo]);
});