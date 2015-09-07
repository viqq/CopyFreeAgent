/**
 * Created by TITUS on 07.09.2015.
 */
define(['angularAMD'], function (angularAMD) {
    angularAMD.factory('addSport', ['$http', function ($http) {
        var addSport = function (data) {
            return $http({
                method: 'POST',
                url: 'admin/sport',
                data: data,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            })
        };

        var d = addSport(data);

        d.success(function(data) {
            console.log(data)
        }).error(function(err) {
            console.log(err);
        });

        return d
    }]);
});