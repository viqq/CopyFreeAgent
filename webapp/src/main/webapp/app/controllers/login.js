/**
 * Created by TITUS on 29.07.2015.
 */
define(
    [
        'angularAMD',
        'uiTranslations',
        'services/login'
    ],
    function (angularAMD, uiTranslations) {
        var loginCtrl = function ($scope, login) {
            $scope.uiTranslations = uiTranslations[$scope.language].login;

            $scope.loginHandler = function() {
                login()
                    .success(function(data) {
                        $scope.isLoggedIn = true;
                        alert(JSON.stringify(data));
                    })
                    .error(function(err) {
                        throw err;
                    })
            }
        };

        angularAMD.controller('LoginCtrl', ['$scope', 'login', loginCtrl]);

        //app.controller('LoginCtrl', function ($scope) {
        //    $scope.message = "Message from LoginCTRL";
        //});
    }
);