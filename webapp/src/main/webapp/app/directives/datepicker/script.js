/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',

        'css!/app/directives/datepicker/styles.css'
    ],
    function (angularAMD) {
        var dirHeader = function () {
            var ctrl = ['$scope', '$location', function ($scope, $location) {
                var currDate = new Date();
                var getMonthDays = function(month, year, isMondayFirst) {
                    var result = new Array(5);
                    var day = 1;
                    var firstWeekDay = (new Date(year,month,1)).getDay();
                    var dayToAdd;

                    if (isMondayFirst) {
                        firstWeekDay--;
                        firstWeekDay = firstWeekDay < 0 ? 7 : firstWeekDay
                    }

                    for (var week = 0; week < result.length; week++) {
                        result[week] = new Array(7);
                        for (var weekDay = firstWeekDay; weekDay < result[week].length; weekDay++) {
                            dayToAdd = new Date(year,month,day);

                            if (dayToAdd.getMonth() !== month) {
                                break;
                            }

                            result[week][weekDay] = dayToAdd;
                            day++
                        }

                        firstWeekDay = 0;
                    }

                    return result;
                };

                $scope.month = getMonthDays(currDate.getMonth() + 1, currDate.getFullYear(), true);
            }];

            return {
                restrict: 'E',
                templateUrl: 'app/directives/datepicker/template.html',
                replace: true,
                scope: true,
                controller: ctrl
            };
        };

        angularAMD.directive('dirDatepicker', dirHeader);
    }
);