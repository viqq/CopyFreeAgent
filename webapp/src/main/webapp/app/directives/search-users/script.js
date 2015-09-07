/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',

        'services/search-users'
    ],
    function (angularAMD, uiTranslations) {
        angularAMD.directive('dirSearchUsers', function () {
            return {
                controller: [
                    '$scope',
                    '$element',
                    'searchUsers',
                    function ($scope, $element, searchUsers) {
                    $scope.form = $element.find('form');
                    $scope.searchResults = [];

                    $scope.searchParams = {
                        sport: '',
                        firstName: '',
                        lastName: '',
                        dateOfBirthFrom: '',
                        dateOfBirthTo: ''
                    };

                    $scope.searchUsersHandler = function() {
                        var data = $scope.$root.toolkit.serialize($scope.searchParams);

                        console.log(data);

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
                }],
                restrict: 'E',
                templateUrl: 'app/directives/search-users/template.html',
                replace: true,
                scope: true
            };
        });
    }
);