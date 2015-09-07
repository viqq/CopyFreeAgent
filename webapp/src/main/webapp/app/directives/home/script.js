/**
 * Created by TITUS on 30.07.2015.
 */
define(['angularAMD', 'resources/ui-translations'], function (angularAMD, uiTranslations, $) {
    angularAMD.directive('dirHome', function() {
        return {
            restrict: 'E',
            templateUrl: 'app/directives/home/template.html',
            replace: true,
            scope: true,
            controller: function($scope) {
                $scope.uiTranslations = uiTranslations[$scope.language].home;
            }
        };
    });
});