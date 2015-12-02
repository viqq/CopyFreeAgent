/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',

        'services/add-sport',
        'services/get-all-sports'
    ],
    function (angularAMD, uiTranslations) {
        angularAMD.directive('dirAdmin', ['addSport', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/admin/template.html',
                replace: true,
                scope: true,
                controller: controller
            };
        }]);

        var controller = [
            '$scope', '$element', 'addSport', 'getAllSports', '$q',
            function ($scope, $element, addSport, getAllSports, $q) {
                window.qqq = $q;

                $scope.uiTranslations = uiTranslations[$scope.language].login;

                $scope.form = $element.find('form');

                $scope.form = {
                    name: ''
                };

                $scope.sports = [];

                var updateSports = function() {
                    var d = getAllSports();

                    d.success(function(data) {
                        if (!data.payload.length) {
                            return;
                        }

                        $scope.sports = data.payload;
                    });

                    return d
                };

                $scope.submitHandler = function () {
                    var data = $scope.$root.toolkit.serialize($scope.form);
                    addSport(data).success(function () {
                        updateSports();
                    })
                };

                updateSports();
            }]
    }
);