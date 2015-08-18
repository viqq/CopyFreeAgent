/**
 * Created by TITUS on 18.08.2015.
 */
/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',

        'controllers/account'
    ],
    function (angularAMD) {
        var dirAccount = function() {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/account/template.html',
                replace: true,
                scope: true,
                controller: 'AccountCtrl'
            };
        };

        angularAMD.directive('dirAccount', dirAccount);
    }
);