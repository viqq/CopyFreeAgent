/**
 * Created by TITUS on 15.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var editData = function ($http) {
        return function (data, id) {
            if (typeof id !== 'number' || !id && id !== 0) {
                return;
            }

            var d = $http({
                method: 'POST',
                url: '/api/user/' + id,
                data: data,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
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

    angularAMD.factory('editUserInfo', ['$http', editData]);
});