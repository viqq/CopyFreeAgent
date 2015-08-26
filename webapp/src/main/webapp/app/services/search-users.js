/**
 * Created by TITUS on 26.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var searchUsers = function ($http) {
        return function (data) {
            return $http({
                method: 'POST',
                url: 'search/user',
                data: data,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
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

    angularAMD.factory('searchUsers', ['$http', searchUsers]);
});