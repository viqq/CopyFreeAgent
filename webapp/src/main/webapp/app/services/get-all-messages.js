/**
 * Created by TITUS on 07.09.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var getAllMessages = function ($http) {
        return function () {
            var d = $http({
                method: 'GET',
                url: '/api/message'
            });

            d.success(function (data) {
                console.log(data)
            }).error(function (err) {
                console.log(err);
            });

            return d;

        }
    };

    angularAMD.factory('getAllMessages', ['$http', getAllMessages]);
});