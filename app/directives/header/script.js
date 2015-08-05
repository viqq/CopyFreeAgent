/**
 * Created by TITUS on 30.07.2015.
 */
define(['angularAMD', 'uiTranslations'], function (angularAMD, uiTranslations) {
    var dirHeader = function() {
        return {
            restrict: 'E',
            templateUrl: 'app/directives/header/template.html',
            replace: true,
            scope: true,
            controller: function($scope) {
                $scope.uiTranslations = uiTranslations[$scope.language].header;
            }
        };
    };

    angularAMD.directive('dirHeader', dirHeader);
});