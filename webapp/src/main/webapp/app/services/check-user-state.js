/**
 * Created by TITUS on 19.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var checkUserState = function ($http) {
        return function () {
            var d = $http({
                method: 'GET',
                url: '/api/isLoggedIn'
            });

            d.success(function (data) {
                console.log(data)
            }).error(function (err) {
                console.log(err);
            });

            return d;

        }
    };

    angularAMD.factory('checkUserState', ['$http', checkUserState]);
});