/**
 * Created by TITUS on 30.07.2015.
 */
define(
    [
        'angularAMD',

        'resources/toolkit',

        'services/get-user-info',
        'services/check-user-state'
    ],
    function (angularAMD, toolkit) {
        angularAMD.controller('MainCtrl', [
            '$scope',
            '$q',
            '$route',
            '$location',
            '$locale',
            'getUserInfo',
            'checkUserState',
            function ($scope, $q, $route, $location, $locale, getUserInfo, checkUserState) {
                $locale.id = 'ru-ru';

                $scope.$root.$on("$routeChangeStart", function (event, next, current) {
                    console.log('start', 'nex:' + next.originalPath, 'curr:' + (current && current.originalPath))
                });

                $scope.$root.$on("$routeChangeSuccess", function (event, next, current) {
                    console.log('success', 'nex:' + next.originalPath, 'curr:' + (current && current.originalPath))
                });

                angular.extend($scope.$root, {
                    language: 'en',
                    toolkit: toolkit,
                    isLoggedIn: false,

                    currUserInfo: {},
                    updateUserInfo: function () {
                        var errHandler = function () {
                            $scope.$root.isLoggedIn = false;
                            $scope.$root.currUserInfo = {};
                        };

                        return $q(function (resolve, reject) {
                            checkUserState().then(function (data) {
                                if (!data || !data.data || data.data.error || !data.data.payload) {
                                    reject(data);
                                    return;
                                }

                                return getUserInfo();
                            }).then(function (data) {
                                resolve(data);
                            }, function (err) {
                                reject(err);
                            });
                        }).then(function (res) {
                            if (!res || !res.data || !res.data.payload) {
                                errHandler();
                                return;
                            }

                            $scope.$root.isLoggedIn = true;
                            $scope.$root.currUserInfo = res.data.payload;
                            $scope.$root.reloadUserPics();
                        }, function () {
                            errHandler();
                        });
                    },

                    userPicBaseUrl: '/api/user/getImage/',
                    userPicUrl: '',
                    reloadUserPics: function () {
                        var currUserId = $scope.$root.currUserInfo.id || '';
                        $scope.$root.userPicUrl = $scope.$root.userPicBaseUrl +
                        currUserId +
                        '?_ts=' + new Date().getTime();
                    }
                });

                $scope.$root.updateUserInfo();

                /**
                 * Mock user object
                 * @type {{}}
                 */
                $scope.$root.user = {
                    info: {
                        name: 'Тит Коваленко',
                        email: 't.a.kvlnk@gmail.com',
                        phone: '095-350-83-35',
                        age: 25,
                        sports: [
                            {
                                name: 'Плавание',
                                level: 'Pro'
                            },
                            {
                                name: 'Виндсерфинг',
                                level: 'Middle'
                            },
                            {
                                name: 'Скайдайвинг',
                                level: 'Beginner'
                            }
                        ],
                        description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. At dolores nemo voluptas voluptates. Commodi corporis deleniti impedit labore neque non possimus, repudiandae. Accusantium, enim eos nemo rerum similique tempore veniam.',
                        address: 'Днепропетровск, Днепропетровская область, Украина'
                    },
                    claims: [
                        {
                            calendar: {
                                dates: [1452204000000],
                                weekDays: [1, 2]
                            },
                            startHour: 10,
                            endHour: 18,
                            sport: 'Плавание'
                        },
                        {
                            calendar: {
                                dates: [1452204000000],
                                weekDays: [1, 2]
                            },
                            startHour: 10,
                            endHour: 18,
                            sport: 'Плавание'
                        },
                        {
                            calendar: {
                                dates: [1452204000000],
                                weekDays: [1, 2]
                            },
                            startHour: 10,
                            endHour: 18,
                            sport: 'Плавание'
                        }
                    ]
                };

                $scope.datePickerData = {
                    label: 'Число',
                    pickedDates: [1452204000000],
                    pickedWeekDays: [1, 2],
                    isMonFirst: true,
                    editable: false
                }
            }
        ]);
    }
);