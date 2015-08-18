/**
 * Created by TITUS on 19.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var checkUserState = function ($http) {
        return function () {
            return $http({
                method: 'GET',
                url: 'isAuthentication'
            })
                .success(function(data) {

                })
                .error(function() {

                });
        }
    };

    angularAMD.factory('checkUserState', ['$http', checkUserState]);
});