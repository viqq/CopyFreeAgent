/**
 * Created by TITUS on 31.08.2015.
 */
define(['angularAMD'], function (angularAMD) {
    var uploadUserPic = function ($http, $rootScope) {
        return function (file) {
            var data = new FormData;
            data.append('file', file);

            return $http({
                method: 'POST',
                data: data,
                url: '/api/user/setImage',
                transformRequest: angular.identity,
                headers: {
                    'Content-Type': undefined
                }
            })
                .success(function(data) {
                    $rootScope.reloadUserPics();
                })
                .error(function(err) {
                    $rootScope.reloadUserPics();
                });
        }
    };

    angularAMD.factory('uploadUserPic', ['$http', '$rootScope', uploadUserPic]);
});