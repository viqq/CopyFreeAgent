/**
 * Created by TITUS on 30.07.2015.
 */
define(['angularAMD', 'uiTranslations', 'jquery'], function (angularAMD, uiTranslations, $) {
    angularAMD.directive('dirLogin', function() {
        return {
            restrict: 'E',
            templateUrl: 'app/directives/login/template.html',
            replace: true,
            scope: true,
            controller: function($scope) {
                $scope.uiTranslations = uiTranslations[$scope.language].login;
            }
        };
    });
});