/**
 * Created by TITUS on 29.09.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var deleteUser = function ($http) {
        return function (id) {
            if (typeof id !== 'number' || !id && id !== 0) {
                return;
            }

            var d = $http({
                method: 'DELETE',
                url: '/user/' + id
            });

            d.success(function (data) {
                console.log(data)
            }).error(function (err) {
                console.log(err);
            });

            return d;

        }
    };

    angularAMD.factory('deleteUser', ['$http', deleteUser]);
});