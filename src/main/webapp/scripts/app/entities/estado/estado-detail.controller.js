'use strict';

angular.module('diidxavoteApp')
    .controller('EstadoDetailController', function ($scope, $stateParams, Estado) {
        $scope.estado = {};
        $scope.load = function (id) {
            Estado.get({id: id}, function(result) {
              $scope.estado = result;
            });
        };
        $scope.load($stateParams.id);
    });
