'use strict';

angular.module('diidxavoteApp')
    .controller('EstadoController', function ($scope, Estado) {
        $scope.estados = [];
        $scope.loadAll = function() {
            Estado.query(function(result) {
               $scope.estados = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Estado.save($scope.estado,
                function () {
                    $scope.loadAll();
                    $('#saveEstadoModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Estado.get({id: id}, function(result) {
                $scope.estado = result;
                $('#saveEstadoModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Estado.get({id: id}, function(result) {
                $scope.estado = result;
                $('#deleteEstadoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Estado.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEstadoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.estado = {nombre: null, id: null};
        };
    });
