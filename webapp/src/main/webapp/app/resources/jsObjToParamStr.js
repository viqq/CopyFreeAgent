/**
 * Created by TITUS on 15.08.2015.
 */
define([], function () {
    return function(obj) {
        var paramsStr = [];

        for (var key in obj) {
            paramsStr.push(key + '=' + obj[key]);
        }

        return  paramsStr.join('&')
    };
});