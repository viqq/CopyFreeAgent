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
                var currYear = currDate.getFullYear();
                var getMonthDays = function (month, year, isMondayFirst) {
                    var result = new Array(5);
                    var day;
                    var firstWeekDay = (new Date(year, month, 1)).getDay();

                    if (isMondayFirst) {
                        firstWeekDay--;
                        firstWeekDay = firstWeekDay < 0 ? 6 : firstWeekDay
                    }

                    day = 1 - firstWeekDay;

                    /**
                     * Chosen month days
                     */
                    for (var week = 0; week < result.length; week++) {
                        result[week] = new Array(7);

                        for (var weekDay = 0; weekDay < result[week].length; weekDay++) {
                            var date = new Date(year, month, day);

                            result[week][weekDay] = {
                                date: date,
                                wrongMonth: date.getMonth() !== month
                            };
                            day++;
                        }
                    }

                    console.log(result);

                    return result;
                };
                var convertMonth = function(month) {
                    var result = Array(7);

                    for (var weekDay = 0; weekDay < 7; weekDay++) {
                        result[weekDay] = new Array(5);

                        for (var week = 0; week < 5; week++) {
                            result[weekDay][week] = month[week][weekDay];
                        }
                    }

                    return result;
                };

                $scope.changeMonth = function(newMonth) {
                    $scope.monthNum = newMonth;
                    $scope.month = convertMonth( getMonthDays($scope.monthNum, currYear, true) );
                };

                $scope.monthNum = currDate.getMonth();
                $scope.month = convertMonth( getMonthDays($scope.monthNum, currYear, true) );
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