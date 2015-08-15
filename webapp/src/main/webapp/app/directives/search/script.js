/**
 * Created by TITUS on 30.07.2015.
 */
define(['angularAMD', 'resources/uiTranslations'], function (angularAMD, uiTranslations) {
    angularAMD.directive('dirSearch', function() {
        return {
            restrict: 'E',
            templateUrl: 'app/directives/search/template.html',
            replace: true,
            scope: true,
            controller: function($scope) {
                $scope.uiTranslations = uiTranslations[$scope.language].search;
            }
        };
    });
});