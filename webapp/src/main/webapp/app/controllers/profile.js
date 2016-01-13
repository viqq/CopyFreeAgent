/**
 * Created by TITUS on 27.08.2015.
 */
define(
    [
        'angularAMD',

        'directives/header/script',
        'directives/profile/script',
        'directives/footer/script'
    ],
    function (angularAMD) {
        var ctrl = ['$scope', function($scope) {

        }];

        angularAMD.controller('ProfileCtrl', ctrl);
    }
);