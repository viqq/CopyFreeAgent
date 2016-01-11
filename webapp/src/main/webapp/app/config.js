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
        'angular-locale_en-us': 'libs/angular-locale_en-us',
        'angular-locale_ru-ru': 'libs/angular-locale_ru-ru',
        'angular-locale_uk-ua': 'libs/angular-locale_uk-ua',

        'angularAMD': 'libs/angularAMD',

        'jquery': 'libs/jquery.min',

        'css': 'libs/require-css.min'
    },

    // Add angular modules that does not support AMD out of the box, put it in a shim
    shim: {
        'angularAMD': ['angular'],
        'angular-route': ['angular'],
        'angular-animate': ['angular'],
        'angular-aria': ['angular'],
        'angular-locale_en-us': ['angular'],
        'angular-locale_ru-ru': ['angular'],
        'angular-locale_uk-ua': ['angular']
    },

    // kick start application
    deps: ['css', 'app']
});