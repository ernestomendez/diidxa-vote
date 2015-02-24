'use strict';

angular.module('diidxavoteApp')
    .controller('ElectorController', function ($scope, Elector, Estado, Distrito, Municipio, Ruta, Seccion, PartidosPoliticos) {
        $scope.electors = [];
        $scope.estados = Estado.query();
        $scope.distritos = Distrito.query();
        $scope.municipios = Municipio.query();
        $scope.rutas = Ruta.query();
        $scope.seccions = Seccion.query();
        $scope.partidospoliticoss = PartidosPoliticos.query();
        $scope.loadAll = function() {
            Elector.query(function(result) {
               $scope.electors = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Elector.save($scope.elector,
                function () {
                    $scope.loadAll();
                    $('#saveElectorModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Elector.get({id: id}, function(result) {
                $scope.elector = result;
                $('#saveElectorModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Elector.get({id: id}, function(result) {
                $scope.elector = result;
                $('#deleteElectorConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Elector.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteElectorConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.elector = {nombre: null, direccion: null, edad: null, genero: null, consecutivoIFE: null, rol: null, email: null, celular: null, telefono: null, id: null};
        };
    });
