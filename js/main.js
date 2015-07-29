/**
 * Created by TITUS on 28.07.2015.
 */
require.config({
    baseUrl: 'js/',

    // alias libraries paths.  Must set 'angular'
    paths: {
        'angular': 'libs/angular',
        'angular-route': 'libs/angular-route',
        'angularAMD': 'libs/angularAMD',
        'jquery': 'libs/jquery',

        'uiTranslations': 'resources/uiTranslations'
    },

    // Add angular modules that does not support AMD out of the box, put it in a shim
    shim: {
        'angularAMD': ['angular'],
        'angular-route': ['angular']
    },

    // kick start application
    deps: ['app']
});