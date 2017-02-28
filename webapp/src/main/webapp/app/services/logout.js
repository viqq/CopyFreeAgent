/**
 * Created by TITUS on 18.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var logout = function ($http) {
        return function () {
            return $http({
                method: 'GET',
                url: '/logout'
            })
                .success(function(data) {
                    console.log(data)
                })
                .error(function(err) {
                    console.log(err);
                });
        }
    };

    angularAMD.factory('logout', ['$http', logout]);
});