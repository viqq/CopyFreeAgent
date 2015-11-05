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
                        var layerNum = parseInt($attrs.parallaxLayer, 10),
                            xCoef = 0.013,
                            yCoef = 0.01,
                            wCoef = 0.5,
                            hCoef = 1,
                            timeGap = 200,
                            lastTimestamp = 0;


                        $document.on('mousemove', function (event) {
                            if (event.timestamp - lastTimestamp < (timeGap + 1) * 2) {
                                return;
                            }

                            lastTimestamp = event.timestamp;

                            var deltaX = xCoef * layerNum * (event.clientX - window.innerWidth * wCoef),
                                deltaY = yCoef * layerNum * (event.clientY - window.innerHeight * hCoef),
                                cssString;

                            deltaX = Math.round(deltaX);
                            deltaY = Math.round(deltaY);

                            cssString = 'translate(' + Math.round(deltaX) + 'px, ' + Math.round(deltaY) + 'px)';

                            $element.css({
                                'transform': cssString,
                                '-webkit-transform': cssString,
                                '-ms-transform': cssString,
                                '-moz-transform': cssString
                            })
                        });

                        $document.on('mouseleave', function(event) {
                            $element.addClass('paralax-transition');
                        });

                        $document.on('mouseenter', function (event) {
                            setTimeout(function() {
                                $element.removeClass('paralax-transition');
                            }, timeGap - 1)
                        })
                    }
                ]
            };
        };

        angularAMD.directive('parallaxLayer', directive);
    }
);