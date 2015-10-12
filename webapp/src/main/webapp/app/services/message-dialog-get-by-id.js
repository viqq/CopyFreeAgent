/**
 * Created by TITUS on 07.09.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var getDialogById = function ($http) {
        return function (id) {
            var req = $http({
                method: 'GET',
                url: '/api/message/history/' + id
            });

            req.success(function (data) {
                console.log(data)
            }).error(function (err) {
                console.log(err);
            });

            return req;

        }
    };

    angularAMD.factory('getDialogById', ['$http', getDialogById]);
});