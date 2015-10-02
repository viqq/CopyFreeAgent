/**
 * Created by TITUS on 07.09.2015.
 */
define(['angularAMD'], function (angularAMD) {
    angularAMD.factory('getAllSports', ['$http', function ($http) {
        return function () {
            var d = $http({
                method: 'GET',
                url: '/api/sport'
            });

            d.success(function (data) {
                console.log(data)
            }).error(function (err) {
                console.log(err);
            });

            return d;

        }
    }]);
});