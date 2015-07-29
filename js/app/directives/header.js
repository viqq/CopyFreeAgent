/**
 * Created by TITUS on 30.07.2015.
 */
define(['angularAMD', 'uiTranslations'], function (angularAMD, uiTranslations) {
    angularAMD.directive('dirHeader', function() {
        return {
            restrict: 'A',
            templateUrl: 'templates/header.html',
            controller: function($scope) {
debugger;
                $scope.uiTranslations = uiTranslations[$scope.language].header;
            }
        };
    });
});