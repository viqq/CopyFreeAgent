/**
 * Created by TITUS on 05.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var sendMessage = function ($http) {
        return function (data) {
            var d = $http({
                method: 'POST',
                url: '/api/message',
                data: data,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
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

    angularAMD.factory('sendMessage', ['$http', sendMessage]);
});