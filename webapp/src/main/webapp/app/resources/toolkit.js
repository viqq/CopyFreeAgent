/**
 * Created by TITUS on 31.08.2015.
 */
define([], function () {
    var toolkit = {};

    /**
     * Encodes an object as a string for submission.
     * @param obj
     * @returns {string}
     */
    toolkit.serialize = function(obj) {
        var paramsStr = [];

        for (var key in obj) {
            paramsStr.push(key + '=' + obj[key]);
        }

        return  paramsStr.join('&')
    };

    toolkit.isTouch = function () {
        return Boolean(('ontouchstart' in window) || (navigator.MaxTouchPoints > 0) || (navigator.msMaxTouchPoints > 0));
    };

    return toolkit
});