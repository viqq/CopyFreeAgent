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
            var ctrl = ['$scope', function ($scope) {
                var currDate = new Date();
                var currYear = currDate.getFullYear();
                var getMonthDays = function (month, year, isMondayFirst) {
                    var result = new Array(6);
                    var day;
                    var firstWeekDay = (new Date(year, month, 1)).getDay();

                    if (isMondayFirst) {
                        firstWeekDay--;
                        firstWeekDay = firstWeekDay < 0 ? 6 : firstWeekDay
                    }

                    day = 1 - firstWeekDay;

                    for (var week = 0; week < result.length; week++) {
                        result[week] = new Array(7);

                        for (var weekDay = 0; weekDay < result[week].length; weekDay++) {
                            var date = new Date(year, month, day);

                            result[week][weekDay] = {
                                date: date,
                                wrongMonth: date.getMonth() !== (new Date(year, month)).getMonth()
                            };

                            result[week][weekDay].isPicked = $scope.pickedDates.indexOf(date.getTime()) !== -1;

                            day++;
                        }
                    }

                    return result;
                };
                var convertMonth = function(month) {
                    var result = Array(7);

                    for (var weekDay = 0; weekDay < 7; weekDay++) {
                        result[weekDay] = new Array(month.length);

                        for (var week = 0; week < result[weekDay].length; week++) {
                            result[weekDay][week] = month[week][weekDay];
                        }
                    }

                    return result;
                };

                angular.extend($scope, {
                    week: Array(7),
                    pickedDates: $scope.config.pickedDates || [],
                    pickedWeekDays: $scope.config.pickedWeekDays || [],
                    monthNum: currDate.getMonth(),
                    changeMonth: function(newMonth) {
                        $scope.monthNum = newMonth;
                        $scope.month = convertMonth( getMonthDays($scope.monthNum, currYear, true) );
                    },
                    pickDay: function(day) {
                        day.isPicked = !day.isPicked;

                        var time = day.date.getTime();
                        var dateIndex = $scope.pickedDates.indexOf(time);

                        if (day.isPicked && dateIndex === -1) {
                            $scope.pickedDates.push(time);
                        } else if (!day.isPicked && dateIndex !== -1) {
                            $scope.pickedDates.splice(dateIndex, 1);
                        }
                    },
                    pickWeek: function(weekDay) {
                        weekDay.isPicked = !weekDay.isPicked;

                        var day = weekDay.date.getDay();
                        var dayIndex = $scope.pickedWeekDays.indexOf(day);

                        if (weekDay.isPicked && dayIndex === -1) {
                            $scope.pickedWeekDays.push(day);
                        } else if (!weekDay.isPicked && dayIndex !== -1) {
                            $scope.pickedWeekDays.splice(dayIndex, 1);
                        }
                    }
                });

                $scope.changeMonth($scope.monthNum, currYear, true);

                for (var i = 0; i < $scope.week.length; i ++) {
                    $scope.week[i] = {
                        date: new Date( $scope.month[i][0].date.getTime() )
                    };

                    $scope.week[i].isPicked = $scope.pickedWeekDays.indexOf( $scope.week[i].date.getDay() ) !== -1
                }
            }];

            return {
                restrict: 'E',
                templateUrl: 'app/directives/datepicker/template.html',
                replace: true,
                scope: {
                    config: '=config'
                },
                controller: ctrl
            };
        };

        angularAMD.directive('dirDatepicker', dirHeader);
    }
);