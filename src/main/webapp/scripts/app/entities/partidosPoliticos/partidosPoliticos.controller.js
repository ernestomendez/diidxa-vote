'use strict';

angular.module('diidxavoteApp')
    .controller('PartidosPoliticosController', function ($scope, PartidosPoliticos) {
        $scope.partidosPoliticoss = [];
        $scope.loadAll = function() {
            PartidosPoliticos.query(function(result) {
               $scope.partidosPoliticoss = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            PartidosPoliticos.save($scope.partidosPoliticos,
                function () {
                    $scope.loadAll();
                    $('#savePartidosPoliticosModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            PartidosPoliticos.get({id: id}, function(result) {
                $scope.partidosPoliticos = result;
                $('#savePartidosPoliticosModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            PartidosPoliticos.get({id: id}, function(result) {
                $scope.partidosPoliticos = result;
                $('#deletePartidosPoliticosConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PartidosPoliticos.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePartidosPoliticosConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.partidosPoliticos = {nombre: null, id: null};
        };
    });
