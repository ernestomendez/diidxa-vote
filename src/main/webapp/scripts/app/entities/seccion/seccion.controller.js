'use strict';

angular.module('diidxavoteApp')
    .controller('SeccionController', function ($scope, Seccion, Municipio, Ruta) {
        $scope.seccions = [];
        $scope.municipios = Municipio.query();
        $scope.rutas = Ruta.query();
        $scope.loadAll = function() {
            Seccion.query(function(result) {
               $scope.seccions = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Seccion.save($scope.seccion,
                function () {
                    $scope.loadAll();
                    $('#saveSeccionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Seccion.get({id: id}, function(result) {
                $scope.seccion = result;
                $('#saveSeccionModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Seccion.get({id: id}, function(result) {
                $scope.seccion = result;
                $('#deleteSeccionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Seccion.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSeccionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.seccion = {casilla: null, id: null};
        };
    });
