/**
 * Created by TITUS on 15.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var editData = function ($http) {
        return function (data) {
            var d = $http({
                method: 'POST',
                url: 'user',
                data: data,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });

            d.success(function (data) {
                console.log(data)
            }).error(function (err) {
                console.log(err);
            });

            return d;

        }
    };

    angularAMD.factory('registration', ['$http', editData]);
});