'use strict';

angular.module('diidxavoteApp')
    .controller('MunicipioController', function ($scope, Municipio, Distrito) {
        $scope.municipios = [];
        $scope.distritos = Distrito.query();
        $scope.loadAll = function() {
            Municipio.query(function(result) {
               $scope.municipios = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Municipio.save($scope.municipio,
                function () {
                    $scope.loadAll();
                    $('#saveMunicipioModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Municipio.get({id: id}, function(result) {
                $scope.municipio = result;
                $('#saveMunicipioModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Municipio.get({id: id}, function(result) {
                $scope.municipio = result;
                $('#deleteMunicipioConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Municipio.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMunicipioConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.municipio = {nombre: null, cabeceraMunicipal: null, id: null};
        };
    });
