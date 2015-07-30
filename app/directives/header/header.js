/**
 * Created by TITUS on 30.07.2015.
 */
define(['angularAMD', 'uiTranslations'], function (angularAMD, uiTranslations) {
    angularAMD.directive('dirHeader', function() {
        return {
            restrict: 'A',
            templateUrl: 'app/directives/header/header.html',
            controller: function($scope) {
                $scope.uiTranslations = uiTranslations[$scope.language].header;
            }
        };
    });
});