/**
 * Created by TITUS on 28.07.2015.
 */
require.config({
    baseUrl: 'app/',

    // alias libraries paths.  Must set 'angular'
    paths: {
        'angular': 'libs/angular.min',
        'angular-route': 'libs/angular-route.min',
        'angular-animate': 'libs/angular-animate.min',
        'angular-aria': 'libs/angular-aria.min',

        'angularAMD': 'libs/angularAMD',
        'angular-material': 'libs/angular-material',

        'jquery': 'libs/jquery'
    },

    // Add angular modules that does not support AMD out of the box, put it in a shim
    shim: {
        'angularAMD': ['angular'],
        'angular-route': ['angular'],
        'angular-animate': ['angular'],
        'angular-aria': ['angular'],
        'angular-material': ['angular']
    },

    // kick start application
    deps: ['app']
});