/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',

        'css!/app/directives/form-field/styles.css'
    ],
    function (angularAMD) {
        var ctrl =  [
            '$scope',
            '$attrs',
            '$element',
            function ($scope, $attrs, $element) {
                var validRegex = new RegExp($scope.pattern);
                var validate = function() {
                    var isPatternValid = true;
                    var isFunctionValid = true;

                    if ($scope.pattern) {
                        isPatternValid = validRegex.test($scope.value || '');
                    }

                    if (typeof $scope.validation === 'function') {
                        isFunctionValid = $scope.validation();
                    }

                    $scope.isValid = isFunctionValid && isPatternValid;
                    $scope.$emit('form-field-validated', {
                        result: $scope.isValid,
                        element: $element
                    });
                };

                angular.extend($scope, {
                    isValid: true,
                    isFocused: false
                });

                $element.find('input').on('focus', function() {
                    $scope.isFocused = true;
                    $scope.$apply();
                }).on('blur', function() {
                    $scope.isFocused = false;
                    validate();
                    $scope.$apply();
                });

                $scope.$root.$on('validate-form', function(evt) {
                    validate();
                    evt.targetScope.validationResults.push($scope.isValid.toString());
                });
            }
        ];

        angularAMD.directive('dirFormField', [function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/form-field/template.html',
                replace: true,
                scope: {
                    value: '=fieldModel',
                    label: '@fieldLabel',
                    error: '@fieldError',
                    hint: '@fieldHint',
                    pattern: '@fieldPattern',
                    validation: '=fieldValidation',
                    type: '@fieldType'
                },
                controller: ctrl
            };
        }]);
    }
);