'use strict';

angular.module('diidxavoteApp')
    .controller('PartidosPoliticosDetailController', function ($scope, $stateParams, PartidosPoliticos) {
        $scope.partidosPoliticos = {};
        $scope.load = function (id) {
            PartidosPoliticos.get({id: id}, function(result) {
              $scope.partidosPoliticos = result;
            });
        };
        $scope.load($stateParams.id);
    });
