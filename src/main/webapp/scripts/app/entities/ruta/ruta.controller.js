'use strict';

angular.module('diidxavoteApp')
    .controller('RutaController', function ($scope, Ruta) {
        $scope.rutas = [];
        $scope.loadAll = function() {
            Ruta.query(function(result) {
               $scope.rutas = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Ruta.save($scope.ruta,
                function () {
                    $scope.loadAll();
                    $('#saveRutaModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Ruta.get({id: id}, function(result) {
                $scope.ruta = result;
                $('#saveRutaModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Ruta.get({id: id}, function(result) {
                $scope.ruta = result;
                $('#deleteRutaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Ruta.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteRutaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.ruta = {descripcion: null, id: null};
        };
    });
