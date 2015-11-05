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
                        var layerNum = Math.pow(parseInt($attrs.parallaxLayer, 10), 1.2),
                            xCoef = 0.005,
                            yCoef = 0.02;


                        $document.on('mousemove', function(event) {
                            var deltaX = xCoef * layerNum * (event.clientX - window.innerWidth / 2) + 'px',
                                deltaY = yCoef * layerNum * (event.clientY - window.innerHeight / 2) + 'px';

                            $element.css({
                                transform: 'translate(' + deltaX + ', ' + deltaY + ')'
                            })
                        })
                    }
                ]
            };
        };

        angularAMD.directive('parallaxLayer', directive);
    }
);