'use strict';

angular.module('diidxavoteApp')
    .controller('DistritoController', function ($scope, Distrito, Estado) {
        $scope.distritos = [];
        $scope.estados = Estado.query();
        $scope.loadAll = function() {
            Distrito.query(function(result) {
               $scope.distritos = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Distrito.save($scope.distrito,
                function () {
                    $scope.loadAll();
                    $('#saveDistritoModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Distrito.get({id: id}, function(result) {
                $scope.distrito = result;
                $('#saveDistritoModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Distrito.get({id: id}, function(result) {
                $scope.distrito = result;
                $('#deleteDistritoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Distrito.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDistritoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.distrito = {nombre: null, id: null};
        };
    });
