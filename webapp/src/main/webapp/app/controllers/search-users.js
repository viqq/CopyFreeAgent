/**
 * Created by TITUS on 29.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',
        'resources/js-obj-to-param-str',

        'services/search-users'
    ],
    function (app) {
        var SearchUsersCtrl = function ($scope, $element, searchUsers) {
            $scope.message = "Message from Search CTRL";

            $scope.form = $element.find('form');
            $scope.searchResults = [];

            $scope.searchUsersHandler = function() {
                console.log($scope.form.serialize());
                var data = $scope.form.serialize();

                searchUsers(data)
                    .success(function(data) {
                        if (typeof data !== 'object') {
                            console.error('search users: something wrong with response');
                            return;
                        }

                        if (data.error === true) {
                            console.error('search users: request error code' , data.code);
                            return;
                        }

                        if (!data.payload.length && data.payload.length !== 0) {
                            console.error('search users: payload is not array');
                            return;
                        }

                        $scope.searchResults = data.payload;
                    })
                    .error(function() {

                    })
            };
        };

        app.controller('SearchUsersCtrl', ['$scope', '$element', 'searchUsers', SearchUsersCtrl]);
    }
);