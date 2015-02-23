'use strict';

angular.module('diidxavoteApp')
    .controller('RutaDetailController', function ($scope, $stateParams, Ruta) {
        $scope.ruta = {};
        $scope.load = function (id) {
            Ruta.get({id: id}, function(result) {
              $scope.ruta = result;
            });
        };
        $scope.load($stateParams.id);
    });
