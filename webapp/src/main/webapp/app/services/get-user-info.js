/**
 * Created by TITUS on 18.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var getUserInfo = function ($http) {
        return function () {
            return $http({
                method: 'GET',
                url: 'user/info'
            })
                .success(successUserInfo)
                .error(errorUserInfo);
        }
    };

    var successUserInfo = function (data) {
        console.log(data)
    };

    var errorUserInfo = function (err) {
        console.log(err)
    };

    angularAMD.factory('getUserInfo', ['$http', getUserInfo]);
});