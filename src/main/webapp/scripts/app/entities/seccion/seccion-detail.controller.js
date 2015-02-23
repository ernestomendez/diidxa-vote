'use strict';

angular.module('diidxavoteApp')
    .controller('SeccionDetailController', function ($scope, $stateParams, Seccion, Municipio, Ruta) {
        $scope.seccion = {};
        $scope.load = function (id) {
            Seccion.get({id: id}, function(result) {
              $scope.seccion = result;
            });
        };
        $scope.load($stateParams.id);
    });
