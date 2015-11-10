/**
 * Created by TITUS on 05.11.2015.
 */
define(
    [
        'angularAMD',
        'jquery'
    ],
    function (angularAMD, jquery) {
        var directive = function () {
            return {
                restrict: 'A',
                replace: false,
                scope: true,
                controller: [
                    '$scope',
                    '$document',
                    '$element',
                    '$attrs',
                    function ($scope, $document, $element, $attrs) {
                        var X_COEF = 0.013,
                            Y_COEF = 0.01,
                            W_COEF = 0.5,
                            H_COEF = 0.5,
                            TIME_GAP = 200,

                            layerNum = parseInt($attrs.parallaxLayer, 10),
                            transitionDuration = TIME_GAP / 2 / 1000,
                            lastTimestamp = 0,

                            init = function() {
                                $document
                                    .on('mousemove', parallaxTransformation)
                                    .on('mouseleave', addTransition)
                                    .on('mouseenter', removeTransition);

                                addTransition();
                                removeTransition();
                            },
                            parallaxTransformation = function(event) {
                                if (event.timestamp - lastTimestamp < (TIME_GAP + 1) * 2) {
                                    return;
                                }

                                lastTimestamp = event.timestamp;

                                var deltaX = X_COEF * layerNum * (event.clientX - window.innerWidth * W_COEF),
                                    deltaY = Y_COEF * layerNum * (event.clientY - window.innerHeight * H_COEF),
                                    cssString;

                                deltaX = Math.round(deltaX);
                                deltaY = Math.round(deltaY);

                                cssString = 'translate(' + Math.round(deltaX) + 'px, ' + Math.round(deltaY) + 'px)';

                                $element.css({
                                    'transform': cssString,
                                    '-webkit-transform': cssString,
                                    '-ms-transform': cssString,
                                    '-moz-transform': cssString
                                });
                            },
                            addTransition = function() {
                                $element.css({
                                    'transition': 'transform ' + transitionDuration + 's linear'
                                })
                            },
                            removeTransition = function() {
                                setTimeout(function() {
                                    $element.css({
                                        'transition': 'initial'
                                    })
                                }, TIME_GAP)
                            };


                        setTimeout(init, 10000);
                    }
                ]
            };
        };

        angularAMD.directive('parallaxLayer', directive);
    }
);