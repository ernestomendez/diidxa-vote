'use strict';

angular.module('diidxavoteApp')
    .controller('ElectorDetailController', function ($scope, $stateParams, Elector, Estado, Distrito, Municipio, Ruta, Seccion, PartidosPoliticos) {
        $scope.elector = {};
        $scope.load = function (id) {
            Elector.get({id: id}, function(result) {
              $scope.elector = result;
            });
        };
        $scope.load($stateParams.id);
    });
