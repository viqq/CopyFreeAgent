/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',

        'css!/app/directives/datepicker/styles.css'
    ],
    function (angularAMD) {
        var directive = function () {
            var ctrl = ['$scope', '$element', '$document', function ($scope, $element, $document) {
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

                            result[week][weekDay].isPicked = $scope.output.dates.indexOf(date.getTime()) !== -1;

                            day++;
                        }
                    }

                    return result;
                };

                var convertMonth = function (month) {
                    var result = Array(7);

                    for (var weekDay = 0; weekDay < 7; weekDay++) {
                        result[weekDay] = new Array(month.length);

                        for (var week = 0; week < result[weekDay].length; week++) {
                            result[weekDay][week] = month[week][weekDay];
                        }
                    }

                    return result;
                };
                var buildWeek = function () {
                    for (var i = 0; i < $scope.week.length; i++) {
                        $scope.week[i] = {
                            date: new Date($scope.month[i][0].date.getTime())
                        };

                        $scope.week[i].isPicked = $scope.output.weekDays.indexOf($scope.week[i].date.getDay()) !== -1
                    }
                };

                var updateDatesInField = function() {
                    $scope.output.dates.forEach(function(val, i) {
                        $scope.datesInField[i] = new Date(val);
                    })
                };

                angular.extend($scope, {
                    month: new Array(7),
                    week: new Array(7),
                    output: $scope.picked,
                    datesInField: [],
                    monthNum: currDate.getMonth(),
                    isVisible: false,
                    changeMonth: function (newMonth) {
                        $scope.monthNum = newMonth;
                        $scope.month = convertMonth( getMonthDays($scope.monthNum, currYear, $scope.config.isMonFirst) );
                    },
                    pickDay: function (day) {
                        if (!$scope.config.editable) {
                            return;
                        }

                        day.isPicked = !day.isPicked;

                        var time = day.date.getTime();
                        var dateIndex = $scope.output.dates.indexOf(time);

                        if (day.isPicked && dateIndex === -1) {
                            $scope.output.dates.push(time);
                        } else if (!day.isPicked && dateIndex !== -1) {
                            $scope.output.dates.splice(dateIndex, 1);
                        }
                    },
                    pickWeek: function (weekDay) {
                        if (!$scope.config.editable) {
                            return;
                        }

                        weekDay.isPicked = !weekDay.isPicked;

                        var day = weekDay.date.getDay();
                        var dayIndex = $scope.output.weekDays.indexOf(day);

                        if (weekDay.isPicked && dayIndex === -1) {
                            $scope.output.weekDays.push(day);
                        } else if (!weekDay.isPicked && dayIndex !== -1) {
                            $scope.output.weekDays.splice(dayIndex, 1);
                        }
                    },
                    openCalendar: function () {
                        $scope.changeMonth($scope.monthNum);
                        buildWeek();
                        $scope.isVisible = true;
                    },
                    submit: function () {
                        updateDatesInField();
                        $scope.isVisible = false;
                    },
                    reset: function () {
                        $scope.output.dates = [];
                        $scope.output.weekDays = [];
                        updateDatesInField();
                        $scope.changeMonth($scope.monthNum);
                        buildWeek();
                    }
                });

                $scope.month = convertMonth(getMonthDays($scope.monthNum, currYear, $scope.config.isMonFirst));
                updateDatesInField();
            }];

            return {
                restrict: 'E',
                templateUrl: 'app/directives/datepicker/template.html',
                replace: true,
                scope: {
                    config: '=config',
                    picked: '=picked'
                },
                controller: ctrl
            };
        };

        angularAMD.directive('dirDatepicker', directive);
    }
);