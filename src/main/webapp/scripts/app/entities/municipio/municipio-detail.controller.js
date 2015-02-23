'use strict';

angular.module('diidxavoteApp')
    .controller('MunicipioDetailController', function ($scope, $stateParams, Municipio, Distrito) {
        $scope.municipio = {};
        $scope.load = function (id) {
            Municipio.get({id: id}, function(result) {
              $scope.municipio = result;
            });
        };
        $scope.load($stateParams.id);
    });
