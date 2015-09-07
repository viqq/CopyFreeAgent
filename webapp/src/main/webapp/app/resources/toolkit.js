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

    return toolkit
});