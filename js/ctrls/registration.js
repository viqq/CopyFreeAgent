/**
 * Created by TITUS on 30.07.2015.
 */
/**
 * Created by TITUS on 29.07.2015.
 */
define(['app'], function (app) {
    app.controller('RegistrationCtrl', function ($scope) {
        $scope.message = "Message from LoginCTRL";
        $scope.uiTranslations = {
            'label_login': 'Login',
            'label_password': 'Password'
        }
    });
});