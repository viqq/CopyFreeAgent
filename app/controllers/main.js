/**
 * Created by TITUS on 30.07.2015.
 */
define(['angularAMD', 'uiTranslations'], function (angularAMD, uiTranslations) {
    angularAMD.controller('MainCtrl', function($scope) {
        console.log('main ctrl');
        $scope.language = 'en';
    });
});