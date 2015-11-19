/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',

        'css!/app/directives/field-text/styles.css'
    ],
    function (angularAMD) {
        var ctrl =  [
            '$scope',
            '$attrs',
            '$element',
            function ($scope, $attrs, $element) {
                var validRegex = new RegExp($scope.pattern)

                angular.extend($scope, {
                    isValid: true,
                    validate: function() {
                        $scope.isValid = validRegex.test(this.value);
                    }

                });


                $scope.pattern = new RegExp($attrs);
            }
        ];

        angularAMD.directive('dirFieldText', [function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/field-text/template.html',
                replace: true,
                scope: {
                    value: '=ngModel',
                    label: '@fieldLabel',
                    error: '@fieldError',
                    pattern: '@fieldPattern',

                },
                controller: ctrl
            };
        }]);
    }
);