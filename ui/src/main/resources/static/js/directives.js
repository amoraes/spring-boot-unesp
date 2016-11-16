//Algumas diretivas úteis
var app = angular.module('exemplo');
//comparação de um campo à outro
app.directive("compareTo", function() {
    return {
        require: "ngModel",
        link: function(scope, element, attrs, ctrl) {

            ctrl.$validators.compareTo = function(val) {
                return val == scope.$eval(attrs.compareTo);
            };

            scope.$watch(attrs.compareTo, function() {
                ctrl.$validate();
            });
        }
    };
})
//clique com o botão direito e toque demorado na tela em dispositivos móveis usado para ativar menus de contexto
.directive('onLongPress', ['$parse', '$timeout', '$log' , function ($parse, $timeout, $log) {
    return {
        restrict: 'A',
        link: function ($scope, $elm, $attrs) {
            var timer;
            var timerDuration = (!isNaN($attrs.longPressDuration) && parseInt($attrs.longPressDuration)) || 600;
            // By default we prevent long press when user scrolls
            var preventLongPressOnScroll = ($attrs.preventOnscrolling ? $attrs.preventOnscrolling === 'true' : true)
            // Variable used to prevent long press while scrolling
            var touchStartY;
            var touchStartX;
            var MAX_DELTA = 15;
            // Bind touch, mouse and click event
            $elm.bind('touchstart', onEnter);
            $elm.bind('touchend', onExit);

            $elm.bind('mousedown', onEnter);
            $elm.bind('mouseup', onExit);

            $elm.bind('click', onClick);
            // For windows mobile browser
            $elm.bind('pointerdown', onEnter);
            $elm.bind('pointerup', onExit);
            $elm.bind('contextmenu', onContextMenu);
            if (preventLongPressOnScroll) {
                // Bind touchmove so that we prevent long press when user is scrolling
                $elm.bind('touchmove', onMove);
            }

            function onEnter(evt) {
                var functionHandler = $parse($attrs.onLongPress);
                // For tracking scrolling
                if (evt.touches) {
                    touchStartY = evt.touches[0].screenY;
                    touchStartX = evt.touches[0].screenX;
                }
                //Cancel existing timer
                $timeout.cancel(timer);
                //To handle click event properly
                $scope.longPressSent = false;
                // We'll set a timeout for 600 ms for a long press
                timer = $timeout(function () {
                    $scope.longPressSent = true;
                    // If the touchend event hasn't fired,
                    // apply the function given in on the element's on-long-press attribute
                    $scope.$apply(function () {
                        functionHandler($scope, {
                            $event: evt
                        });
                    });
                }, timerDuration);

            }

            function onExit(evt) {
                var functionHandler = $parse($attrs.onTouchEnd);
                // Prevent the onLongPress event from firing
                $timeout.cancel(timer);
                // If there is an on-touch-end function attached to this element, apply it
                if ($attrs.onTouchEnd) {
                    $scope.$apply(function () {
                        functionHandler($scope, {
                            $event: evt
                        });
                    });
                }

            }

            function onClick(evt) {
                //If long press is handled then prevent click
                if ($scope.longPressSent && (!$attrs.preventClick || $attrs.preventClick === "true")) {
                    evt.preventDefault();
                    evt.stopPropagation();
                    evt.stopImmediatePropagation();
                }

            }

            function onMove(evt) {
                var yPosition = evt.touches[0].screenY;
                var xPosition = evt.touches[0].screenX;

                // If we scrolled, prevent long presses
                if (touchStartY !== undefined && touchStartX !== undefined &&
                    (Math.abs(yPosition - touchStartY) > MAX_DELTA) || Math.abs(xPosition - touchStartX) > MAX_DELTA) {
                    $timeout.cancel(timer);
                }

            }
            
            //right-click acts as long press too
            function onContextMenu(evt) {
            	evt.preventDefault();
            	var functionHandler = $parse($attrs.onLongPress);
            	$scope.longPressSent = true;
                // If the touchend event hasn't fired,
                // apply the function given in on the element's on-long-press attribute
                $scope.$apply(function () {
                    functionHandler($scope, {
                        $event: evt
                    });
                });
            }
        }
    };
}]);