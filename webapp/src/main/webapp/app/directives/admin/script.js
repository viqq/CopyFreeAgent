/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',
        'resources/ui-translations',

        'services/add-sport'
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

        var controller = ['$scope', '$element', 'addSport', '$q', function ($scope, $element, addSport, $q) {
            window.qqq = $q;

            $scope.uiTranslations = uiTranslations[$scope.language].login;

            $scope.form = $element.find('form');

            $scope.formData = {
                name: ''
            };

            $scope.submitHandler = function() {
                var data = $scope.$root.toolkit.serialize($scope.formData);
                addSport(data).success(function() {

                }).error(function() {

                });
            }
        }]
    }
);