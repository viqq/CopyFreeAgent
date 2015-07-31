/**
 * Created by TITUS on 30.07.2015.
 */
define(['angularAMD', 'uiTranslations'], function (angularAMD, uiTranslations) {
    angularAMD.directive('dirRegistration', function() {
        return {
            restrict: 'E',
            templateUrl: 'app/directives/registration/template.html',
            controller: function($scope) {
                //$scope.uiTranslations = uiTranslations[$scope.language].registration;
            }
        };
    });
});