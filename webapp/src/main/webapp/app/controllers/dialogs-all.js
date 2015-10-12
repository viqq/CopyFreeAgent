/**
 * Created by TITUS on 15.09.2015.
 */
define(
    [
        'angularAMD',

        'directives/header/script',
        'directives/footer/script',

        'services/message-dialogs-get-list',
        'services/get-user-info-by-id'
    ],
    function (angularAMD) {
        angularAMD.controller('AllDialogsCtrl', [
            '$scope',
            '$q',
            'getAllDialogs',
            'getUserInfoById',
            function ($scope, $q, getAllDialogs, getUserInfoById) {
                if (!$scope.isLoggedIn) {
                    $location.path('/login');
                    return;
                }

                $scope.dialogs = [];

                getAllDialogs().then(function(resData) {
                    var requests = [];

                    angular.forEach(resData.data.payload, function(value, key) {
                        var req = getUserInfoById(value.authorId);

                        req.success(function(data) {
                            $scope.dialogs[key] = data.payload;
                        });

                        requests.push(req);
                    });

                    return $q.all(requests);
                }).then(function(resData) {
                    //$scope.dialogs = resData.data.payload;
                })
            }
        ]);
    }
);