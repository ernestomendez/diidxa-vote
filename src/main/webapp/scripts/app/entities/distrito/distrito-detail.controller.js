'use strict';

angular.module('diidxavoteApp')
    .controller('DistritoDetailController', function ($scope, $stateParams, Distrito, Estado) {
        $scope.distrito = {};
        $scope.load = function (id) {
            Distrito.get({id: id}, function(result) {
              $scope.distrito = result;
            });
        };
        $scope.load($stateParams.id);
    });
